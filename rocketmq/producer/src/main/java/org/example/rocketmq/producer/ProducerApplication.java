package org.example.rocketmq.producer;

import java.nio.charset.StandardCharsets;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author heyc
 * @version 1.0
 * @date 2023/4/25 11:53
 */
@SpringBootApplication
public class ProducerApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ProducerApplication.class, args);
        sendMessage();
    }

    private static void sendMessage() throws Exception {
        // sh mqadmin updateTopic -c DefaultCluster -n 192.168.71.31:9876 -t CrossborderNoticeTopic
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setNamesrvAddr("192.168.71.31:9876");
        producer.setProducerGroup("CROSSBORDER_PRODUCER");
        producer.start();

        Message message = new Message();
        message.setTopic("CrossborderNoticeTopic");
        message.setBody("test".getBytes(StandardCharsets.UTF_8));
        producer.send(message);
    }

}
