package org.xyc.elasticsearch.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Properties;

import com.google.common.collect.Maps;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 * elastic search client
 * singleton
 */
public class ClientInstance {

    private static final String ELASTIC_CLUSTER_NAME = "elastic.cluster.name";
    private static final String ELASTIC_CLUSTER_NAME_DEFAULT = "elasticsearch"; //default cluster name

    private static final String ELASTIC_HOST_ONE = "elastic.host.one";
    private static final String ELASTIC_HOST_ONE_DEFAULT = "127.0.0.1";     //default on localhost

    private static final String ELASTIC_PORT_ONE = "elastic.port.one";
    private static final String ELASTIC_PORT_ONE_DEFAULT = "9300";      //default port

    private static String clusterName = "";
    private static String host = "";
    private static int port;

    private static final Map<String, TransportClient> client_map = Maps.newHashMap();

    static {
        Properties p = new Properties();
        InputStream is = null;
        try {
            is = ClientInstance.class.getClassLoader().getResourceAsStream("elasticsearch.properties");
            p.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        clusterName = p.getProperty(ELASTIC_CLUSTER_NAME, ELASTIC_CLUSTER_NAME_DEFAULT);
        host = p.getProperty(ELASTIC_HOST_ONE, ELASTIC_HOST_ONE_DEFAULT);
        port = Integer.valueOf(p.getProperty(ELASTIC_PORT_ONE, ELASTIC_PORT_ONE_DEFAULT));
    }

    private static class ClientFactory {

        private static Client client = null;

        static {
            try {
                Settings settings = Settings.settingsBuilder()
                        .put("cluster.name", clusterName)
                        .put("client.transport.sniff", true)
                        .build();
                client = TransportClient.builder().settings(settings).build()
                        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }

    public static Client getClient() {
        return ClientFactory.client;
    }
}
