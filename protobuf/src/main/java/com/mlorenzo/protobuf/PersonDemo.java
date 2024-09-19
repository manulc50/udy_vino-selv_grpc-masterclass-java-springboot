package com.mlorenzo.protobuf;

import com.mlorenzo.models.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PersonDemo {

    public static void main(String... args) throws IOException {
        Person sam = Person.newBuilder()
                .setName("Sam")
                .setAge(10)
                .build();

        Path path = Paths.get("sam.ser");
        //Files.write(path, sam.toByteArray());
        byte[] bytes = Files.readAllBytes(path);
        Person samPerson = Person.parseFrom(bytes);
        System.out.println(samPerson);
    }
}
