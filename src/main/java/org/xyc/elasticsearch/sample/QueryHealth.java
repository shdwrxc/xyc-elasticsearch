package org.xyc.elasticsearch.sample;

import java.net.InetAddress;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.cluster.health.ClusterIndexHealth;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.xyc.elasticsearch.api.ClientInstance;

/**
 * Created by CCC on 2016/6/12.
 */
public class QueryHealth extends QueryCommon {

    public static void health(){

        ClusterHealthResponse healths = ClientInstance.getClient().admin().cluster().prepareHealth().get(); //Get information for all indices
        String clusterName = healths.getClusterName();      //Access the cluster name
        int numberOfDataNodes = healths.getNumberOfDataNodes(); //Get the total number of data nodes
        int numberOfNodes = healths.getNumberOfNodes(); //Get the total number of nodes

        for (ClusterIndexHealth health : healths.getIndices().values()) { //Iterate over all indices
            String index = health.getIndex();   //Index name
            int numberOfShards = health.getNumberOfShards();    //Number of shards
            int numberOfReplicas = health.getNumberOfReplicas();    //Number of replicas
            ClusterHealthStatus status = health.getStatus();    //Index status
        }


        ClientInstance.getClient().admin().cluster().prepareHealth()        //Prepare a health request
                .setWaitForYellowStatus()       //Wait for the cluster being yellow
                .get();
        ClientInstance.getClient().admin().cluster().prepareHealth("company")   // Prepare the health request for index company
                .setWaitForGreenStatus()        //Wait for the index being green
                .get();

        ClientInstance.getClient().admin().cluster().prepareHealth("employee")  //Prepare the health request for index employee
                .setWaitForGreenStatus()    //Wait for the index being green
                .setTimeout(TimeValue.timeValueSeconds(2))  //Wait at most for 2 seconds
                .get();

//        If the index does not have the expected status and you want to fail in that case, you need to explicitly interpret the result:
        ClusterHealthResponse response = ClientInstance.getClient().admin().cluster().prepareHealth("company")
                .setWaitForGreenStatus()        //Wait for the index being green
                .get();

        ClusterHealthStatus status = response.getIndices().get("company").getStatus();
        if (!status.equals(ClusterHealthStatus.GREEN)) {
            throw new RuntimeException("Index is in " + status + " state");     //Throw an exception if not GREEN
        }
    }

    public static void main(String[] args) {
        health();
    }
}
