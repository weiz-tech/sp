package tech.weizhang.learn.spark;

import org.apache.kafka.clients.KafkaClient;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.weizhang.learn.repo.TestObject;
import tech.weizhang.utils.KafkaUtil;

import java.util.Properties;

public class TestMain {

    private static final Logger logger = LoggerFactory.getLogger(TestMain.class);
    public static void main(String[] args) {
        logger.error("kafka连接："+ String.join("@",KafkaUtil.getConf()));
        String[] hosts = KafkaUtil.getConf();
        try {
            if(hosts.length==0){
                logger.error("未找到kafka配置信息");
                throw new RuntimeException("未找到kafka配置信息");
            }
            //producer
            Properties prop = new Properties();
            prop.put("bootstrap.servers",hosts[0]);
            prop.put("acks","0");
            prop.put("retries","0");
            prop.put("batch.size","16384");
            prop.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
            prop.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
            KafkaProducer<String,String> producer = new KafkaProducer<>(prop);
            ProducerRecord<String,String> record = new ProducerRecord<>(KafkaUtil.getTopic(),0,"123","");
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if(e != null){
                        e.printStackTrace();
                    }

                }
            });
            producer.close();



            //client
            Properties p = new Properties();
            p.put("group.id","1");
            p.put("enable.auto.commit","");
            p.put("auto.commit.interval.ms","1000");
            p.put("session.timeout.ms","3000");
            p.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
            p.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");



        }catch (Exception e){
            logger.error("连接出现异常",e);
        }


    }
}
