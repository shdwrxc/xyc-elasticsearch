package org.xyc.elasticsearch.sample;

import java.util.Calendar;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilders;
import org.xyc.elasticsearch.api.ClientInstance;

/**
 * Created by CCC on 2016/6/17.
 */
public class QueryFuzzy extends QueryCommon {

    public static void search() {
        Calendar calendar = Calendar.getInstance();
        SearchResponse response = ClientInstance.getClient()
                .prepareSearch(indexes)
                .setTypes(types)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                //fuzzy主要的搜索方式是后加多少个字能够匹配，比如有字符串“莱斯特城成功的拿到了英超冠军”
                //fuzziness方法，针对string，可以设定0，1，2，数字代表slop
                //fuzziness方法，针对数字，-fuzziness <= field value <= +fuzziness，代表范围
                //fuzziness方法，针对日期，和数字类似
                //content=军冠，不能搜到，如果加上.fuzziness(Fuzziness.ONE),就可以搜到了。
                //age=16，加上.fuzziness(Fuzziness.build(6))，可以查询10-22的记录
                //firstDate=当前时间，加上.fuzziness(Fuzziness.build("30d"))，可以查询当前日期减去30天，到加上30天的记录，这个地方，可以是30d这种表示方法，也是可以毫秒数
//                .setQuery(QueryBuilders.fuzzyQuery("content", "军冠").fuzziness(Fuzziness.ONE))
//                .setQuery(QueryBuilders.fuzzyQuery("age", "16").fuzziness(Fuzziness.build(6)))
                .setQuery(QueryBuilders.fuzzyQuery("firstDate", calendar.getTime()).fuzziness(Fuzziness.build("30d")))
                .setFrom(0).setSize(60).setExplain(true)
                .execute()
                .actionGet();

        System.out.println(response.getHits().totalHits());
    }

    public static void main(String[] args) {
        search();
    }
}
