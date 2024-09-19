package com.mlorenzo.protobuf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.mlorenzo.json.JPerson;
import com.mlorenzo.models.Person;

// Esta clase de prueba demuestra que el proceso de serialización de objetos a bytes y el proceso de deserialización
// de bytes a objetos es mucho más rápida usando Protobuf que Json. También demuestra que el tamaño de los bytes es
// menor usando Proto que Json

public class PerformanceDemo {

    public static void main(String[] args) {
        // Json
        JPerson person = new JPerson();
        person.setName("Sam");
        person.setAge(10);
        ObjectMapper mapper = new ObjectMapper();
        Runnable jsonRunnable = () -> {
            try {
                byte[] bytes = mapper.writeValueAsBytes(person);
                System.out.println(bytes.length);
                mapper.readValue(bytes, JPerson.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        // Protobuf
        Person sam = Person.newBuilder()
                .setName("Sam")
                .setAge(10)
                .build();
        Runnable protoRunnable = () -> {
            try {
                byte[] bytes = sam.toByteArray();
                System.out.println(bytes.length);
                Person.parseFrom(bytes);
            } catch (InvalidProtocolBufferException e) {
                throw new RuntimeException(e);
            }
        };

        for (int i = 1; i <= 1; i++) {
            runPerformanceTest(jsonRunnable, "JSON");
            runPerformanceTest(protoRunnable, "PROTO");
        }
    }

    private static void runPerformanceTest(Runnable runnable, String protocolName) {
        long time1 = System.currentTimeMillis();
        for(int i = 0; i < 1; i++)
            runnable.run();
        long time2 = System.currentTimeMillis();
        System.out.println(protocolName + ": " + (time2 - time1) + " ms");
    }
}
