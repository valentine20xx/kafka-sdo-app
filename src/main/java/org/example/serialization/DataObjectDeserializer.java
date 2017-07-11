package org.example.serialization;

import commonj.sdo.DataObject;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

public class DataObjectDeserializer implements Deserializer<DataObject> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public DataObject deserialize(String topic, byte[] data) {
        DataObject dataObject = null;

        try {
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            ObjectInputStream is = new ObjectInputStream(in);

            dataObject = (DataObject) is.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return dataObject;

    }

    @Override
    public void close() {

    }
}
