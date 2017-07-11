package org.example.serialization;

import commonj.sdo.DataObject;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Map;

public class DataObjectSerializer implements Serializer<DataObject> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, DataObject data) {
        byte[] yourBytes = null;

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutput outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(data);
            outputStream.flush();
            yourBytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
        } catch (IOException ioe) {
        }

        return yourBytes;
    }

    @Override
    public void close() {

    }
}
