package com.mlorenzo.protobuf;

import com.google.protobuf.Int32Value;
import com.google.protobuf.StringValue;
import com.mlorenzo.models.Address;
import com.mlorenzo.models.Car;
import com.mlorenzo.models.Person;
import com.mlorenzo.models.PersonWithWrappers;

import java.util.List;

public class CompositionDemo {

    public static void main(String[] args) {
        Address address = Address.newBuilder()
                .setPostbox(123)
                .setStreet("Main Street")
                .setCity("Atlanta")
                .build();

        Car accord = Car.newBuilder()
                .setMake("Honda")
                .setModel("Accord")
                .setYear(2020)
                .build();

        Car civic = Car.newBuilder()
                .setMake("Honda")
                .setModel("Civic")
                .setYear(2005)
                .build();

        PersonWithWrappers person = PersonWithWrappers.newBuilder()
                // El tipo de dato de "name" es un wrapper del tipo primitivo "string"
                .setName(StringValue.newBuilder().setValue("Sam").build())
                // El tipo de dato de "age" es un wrapper del tipo primitivo "int32"
                .setAge(Int32Value.newBuilder().setValue(25).build())
                .addAllCar(List.of(accord, civic))
                .setAddress(address)
                .build();

        System.out.println(person);
    }
}
