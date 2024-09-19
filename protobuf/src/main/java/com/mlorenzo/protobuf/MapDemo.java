package com.mlorenzo.protobuf;

import com.mlorenzo.models.BodyStyle;
import com.mlorenzo.models.Car;
import com.mlorenzo.models.Dealer;

import java.util.Map;

public class MapDemo {

    public static void main(String[] args) {
        Car accord = Car.newBuilder()
                .setMake("Honda")
                .setModel("Accord")
                .setYear(2020)
                .setBodyStyle(BodyStyle.SEDAN)
                .build();

        Car civic = Car.newBuilder()
                .setMake("Honda")
                .setModel("Civic")
                .setYear(2005)
                .build();

        // Es un "wrapper" de un Map
        Dealer dealer = Dealer.newBuilder()
                .putAllModel(Map.of(2005, civic, 2020, accord))
                .build();

        System.out.println(dealer.getModelOrThrow(2005));
        System.out.println(dealer.getModelOrDefault(2019, accord));
        System.out.println(dealer.getModelMap());
        System.out.println(dealer.getModelOrThrow(2005).getBodyStyle());
        System.out.println(dealer.getModelOrThrow(2020).getBodyStyle());
    }
}
