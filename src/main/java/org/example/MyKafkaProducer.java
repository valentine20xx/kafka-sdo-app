package org.example;


import commonj.sdo.DataObject;
import commonj.sdo.helper.DataFactory;
import commonj.sdo.helper.XSDHelper;
import java.util.List;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.logging.Logger;


public class MyKafkaProducer {
    private static final Logger LOGGER = Logger.getLogger(MyKafkaProducer.class.getSimpleName());

    public static void main(String[] args) throws Exception {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tY-%1$tm-%1$tdT%1$tk:%1$tM:%1$tS] %3$s %4$s: %5$s%n");

        Thread thread = Thread.currentThread();
        ClassLoader classLoader = thread.getContextClassLoader();
        InputStream modelInputStream = classLoader.getResourceAsStream("model.xsd");
        List list = XSDHelper.INSTANCE.define(modelInputStream, "http://example.org/");
        LOGGER.info(list.size() + " loaded");

        Properties properties = Utils.getInitialProperties("producer.properties");

        MyKafkaProducer myKafkaProducer = new MyKafkaProducer();
        myKafkaProducer.send(properties);
    }

    public void send(Properties props) throws Exception {
        Producer<DataObject, DataObject> kafkaProducer = new KafkaProducer<>(props);

        for (int i = 0; i < 10; i++) {
            DataObject key = DataFactory.INSTANCE.create("http://example.org/", "Key");
            key.setString("id", UUID.randomUUID().toString());

            DataObject sayHello = DataFactory.INSTANCE.create("http://example.org/", "SayHelloRequest");
            sayHello.setString("message", "Ololo " + (i + 1));

            ProducerRecord<DataObject, DataObject> producerRecord = new ProducerRecord<>("test", key, sayHello);
            Future<RecordMetadata> metadata = kafkaProducer.send(producerRecord);

            LOGGER.info("Message sent successfully");
        }

        kafkaProducer.close();
    }
}
