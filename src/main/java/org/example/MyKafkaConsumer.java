package org.example;

import commonj.sdo.DataObject;
import commonj.sdo.helper.XSDHelper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class MyKafkaConsumer {
    private static final Logger LOGGER = Logger.getLogger(MyKafkaConsumer.class.getSimpleName());

    public static void main(String[] args) throws Exception {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tY-%1$tm-%1$tdT%1$tk:%1$tM:%1$tS] %3$s %4$s: %5$s%n");

        InputStream modelInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("model.xsd");
        List list = XSDHelper.INSTANCE.define(modelInputStream, "http://example.org/");
        LOGGER.info(list.size() + " loaded");

        Properties properties = Utils.getInitialProperties("consumer.properties");

        MyKafkaConsumer myKafkaConsumer = new MyKafkaConsumer();
        myKafkaConsumer.consume(properties);
    }

    public void consume(Properties properties) throws Exception {
        KafkaConsumer<DataObject, DataObject> kafkaConsumer = new KafkaConsumer<>(properties);

        List<String> topics = Arrays.asList("test");
        kafkaConsumer.subscribe(topics);

        LOGGER.info("Subscribed to topics: " + topics);

        while (true) {
            ConsumerRecords<DataObject, DataObject> records = kafkaConsumer.poll(1000);
            for (ConsumerRecord<DataObject, DataObject> record : records) {
                DataObject key = record.key();
                DataObject value = record.value();

                String topic = record.topic();
                long timestamp = record.timestamp();
                long offset = record.offset();
                String id = key.getString("id");
                String message = value.getString("message");

                LOGGER.info(String.format("topic = %s, timestamp = %d, offset = %d, key = %s, value = %s", topic, timestamp, offset, id, message));
            }
        }
    }
}