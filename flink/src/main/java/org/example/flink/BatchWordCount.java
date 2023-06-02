package org.example.flink;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.operators.UnsortedGrouping;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * @author heyc
 * @version 1.0
 * @date 2023/5/26 17:09
 */
public class BatchWordCount {

    public static void main(String[] args) throws Exception {
        // 1. 创建执行环境
        ExecutionEnvironment environment = ExecutionEnvironment.getExecutionEnvironment();
        // 2. 从文件读取数据 按行读取(存储的元素就是每行的文本)
        DataSource<String> textFile = environment.readTextFile("words.txt");
        // 3. 转换数据格式
        FlatMapOperator<String, Tuple2<String, Long>> wordAndOne = textFile
            .flatMap((String line, Collector<Tuple2<String, Long>> out) -> {
                String[] words = line.split(" ");
                for (String word : words) {
                    out.collect(Tuple2.of(word, 1L));
                }
            }).returns(Types.TUPLE(Types.STRING, Types.LONG));
        //当Lambda表达式使用 Java 泛型的时候, 由于泛型擦除的存在, 需要显示的声明类型信息

        // 4. 按照 word 进行分组
        UnsortedGrouping<Tuple2<String, Long>> wordAndOneUG = wordAndOne.groupBy(0);
        // 5. 分组内聚统计
        AggregateOperator<Tuple2<String, Long>> sum = wordAndOneUG.sum(1);
        // 6. 打印结果
        sum.print();

    }

}