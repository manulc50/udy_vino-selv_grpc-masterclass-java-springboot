package com.mlorenzo.protobuf;

import com.mlorenzo.models.Television;
import com.mlorenzo.models.TvType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class VersionCompatibilityDemo {

    public static void main(String[] args) throws IOException {
        //generateV1FileAndOrigV1AndDestV1();
        //generateV2FileAndOrigV2AndDestV2();
        //generateV3FileAndOrigV3AndDestV3();
        //generateV4FileAndOrigV4AndDestV4();
        //generateV5FileAndOrigV5AndDestV5();
        //runOriginV2AndDestinyV1();
        //runOriginV3AndDestinyV1();
        //runOriginV3AndDestinyV2();
        //runOriginV4AndDestinyV1();
        runOriginV5AndDestinyV1();

    }

    // Descomentar la v1 del mensaje Television el el archivo Proto "television.proto"
    // Para generar el archivo "television-v1" con el formato de la versión 1 del mensaje Televisión
    // Y, además, probar el caso de que tanto el servicio origen como el servicio destino tienen la versión 1 del mensaje Television
    /*private static void generateV1FileAndOrigV1AndDestV1() throws IOException {
        Television television = Television.newBuilder()
                .setBrand("sony")
                .setYear(2015)
                .build();

        Path path = Paths.get("files/television-v1");
        Files.write(path, television.toByteArray());
        byte[] bytes = Files.readAllBytes(path);
        System.out.println(Television.parseFrom(bytes));
    }*/

    // Descomentar la v2 del mensaje Television el el archivo Proto "television.proto"
    // Para generar el archivo "television-v2" con el formato de la versión 2 del mensaje Televisión
    // Y, además, probar el caso de que tanto el servicio origen como el servicio destino tienen la versión 2 del mensaje Television
    /*private static void generateV2FileAndOrigV2AndDestV2() throws IOException {
        Television television = Television.newBuilder()
                .setBrand("sony")
                .setModel(2015)
                .setType(TvType.OLED)
                .build();

        Path path = Paths.get("files/television-v2");
        Files.write(path, television.toByteArray());
        byte[] bytes = Files.readAllBytes(path);
        System.out.println(Television.parseFrom(bytes));
    }*/

    // Descomentar la v3 del mensaje Television el el archivo Proto "television.proto"
    // Para generar el archivo "television-v3" con el formato de la versión 3 del mensaje Televisión
    // Y, además, probar el caso de que tanto el servicio origen como el servicio destino tienen la versión 3 del mensaje Television
    /*private static void generateV3FileAndOrigV3AndDestV3() throws IOException {
        Television television = Television.newBuilder()
                .setBrand("sony")
                .setType(TvType.OLED)
                .build();

        Path path = Paths.get("files/television-v3");
        Files.write(path, television.toByteArray());
        byte[] bytes = Files.readAllBytes(path);
        System.out.println(Television.parseFrom(bytes));
    }*/

    // Descomentar la v4 del mensaje Television el el archivo Proto "television.proto"
    // Para generar el archivo "television-v2" con el formato de la versión 4 del mensaje Televisión
    // Y, además, probar el caso de que tanto el servicio origen como el servicio destino tienen la versión 4 del mensaje Television
    /*
    private static void generateV4FileAndOrigV4AndDestV4() throws IOException {
        Television television = Television.newBuilder()
                .setBrand("sony")
                .setPrice(200)
                .setType(TvType.OLED)
                .build();

        Path path = Paths.get("files/television-v4");
        Files.write(path, television.toByteArray());
        byte[] bytes = Files.readAllBytes(path);
        System.out.println(Television.parseFrom(bytes));
    }*/

    // Descomentar la v5 del mensaje Television el el archivo Proto "television.proto"
    // Para generar el archivo "television-v2" con el formato de la versión 5 del mensaje Televisión
    // Y, además, probar el caso de que tanto el servicio origen como el servicio destino tienen la versión 5 del mensaje Television
    /*private static void generateV5FileAndOrigV5AndDestV5() throws IOException {
        Television television = Television.newBuilder()
                .setBrand("sony")
                .setType(TvType.OLED)
                .setPrice(200)
                .build();

        Path path = Paths.get("files/television-v5");
        Files.write(path, television.toByteArray());
        byte[] bytes = Files.readAllBytes(path);
        System.out.println(Television.parseFrom(bytes));
    }*/

    // Descomentar la v1 del mensaje Television el el archivo Proto "television.proto"
    // Y, además, probar el caso donde el servicio cliente tiene la v2 del mensaje y el servicio destino tienen la v1
    // del formato del mensaje
    private static void runOriginV2AndDestinyV1() throws IOException {
        Path path = Paths.get("files/television-v2");
        byte[] bytes = Files.readAllBytes(path);
        System.out.println(Television.parseFrom(bytes));
    }

    // Descomentar la v3 del mensaje Television el el archivo Proto "television.proto"
    // Y, además, probar el caso donde el servicio cliente tiene la v3 del mensaje y el servicio destino tienen la v1
    // del formato del mensaje
    private static void runOriginV3AndDestinyV1() throws IOException {
        Path path = Paths.get("files/television-v1");
        byte[] bytes = Files.readAllBytes(path);
        System.out.println(Television.parseFrom(bytes));
    }

    // Descomentar la v3 del mensaje Television el el archivo Proto "television.proto"
    // Y, además, probar el caso donde el servicio cliente tiene la v3 del mensaje y el servicio destino tienen la v2
    // del formato del mensaje
    private static void runOriginV3AndDestinyV2() throws IOException {
        Path path = Paths.get("files/television-v2");
        byte[] bytes = Files.readAllBytes(path);
        System.out.println(Television.parseFrom(bytes));
    }

    // Descomentar la v4 del mensaje Television el el archivo Proto "television.proto"
    // Y, además, probar el caso donde el servicio cliente tiene la v4 del mensaje y el servicio destino tienen la v1
    // del formato del mensaje
    // En este caso hay incompatibilidad porque se supone que destino espera el campo "model" asociado al tag 2 pero el
    // cliente envía el campo "price" en ese tag
    private static void runOriginV4AndDestinyV1() throws IOException {
        Path path = Paths.get("files/television-v1");
        byte[] bytes = Files.readAllBytes(path);
        System.out.println(Television.parseFrom(bytes));
    }

    // Descomentar la v5 del mensaje Television el el archivo Proto "television.proto"
    // Y, además, probar el caso donde el servicio cliente tiene la v5 del mensaje y el servicio destino tienen la v1
    // del formato del mensaje
    private static void runOriginV5AndDestinyV1() throws IOException {
        Path path = Paths.get("files/television-v1");
        byte[] bytes = Files.readAllBytes(path);
        System.out.println(Television.parseFrom(bytes));
    }
}
