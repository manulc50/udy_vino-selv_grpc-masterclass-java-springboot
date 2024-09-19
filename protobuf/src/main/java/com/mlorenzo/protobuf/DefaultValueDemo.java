package com.mlorenzo.protobuf;

import com.mlorenzo.models.Person;

public class DefaultValueDemo {

    public static void main(String... args) {
        Person person = Person.newBuilder().build();
        // Nota: En Proto no existe el valor null
        System.out.println("City: " + person.getAddress().getCity());
        // Nota: Cuando un campo se trata de un tipo personalizado, en la clase generada a partir del
        // archivo .proto, se genera también un método "has..." que nos permite saber si eses campo está o no está definido
        System.out.println(person.hasAddress());
    }
}
