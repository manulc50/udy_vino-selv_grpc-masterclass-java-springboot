package com.mlorenzo.protobuf;

import com.mlorenzo.models.Credentials;
import com.mlorenzo.models.EmailCredentials;
import com.mlorenzo.models.PhoneOTP;

public class OneOfDemo {

    public static void main(String... args) {
        EmailCredentials emailCredentials = EmailCredentials.newBuilder()
                .setEmail("john.doe@email.com")
                .setPassword("admin123")
                .build();

        PhoneOTP phoneOTP = PhoneOTP.newBuilder()
                .setNumber(1231231234)
                .setCode(456)
                .build();

        // Tenemos 2 opciones para establecer; credenciales por email o credenciales por OTP
        // Si establecemos las 2 opciones, sólo se establecerá la última opción(En este caso, credenciales por OTP)
        Credentials credentials1 = Credentials.newBuilder()
                .setEmailMode(emailCredentials)
                .setPhoneMode(phoneOTP)
                .build();

        // En este caso, se establece las credencias por email porque es la última opción en establecerse
        Credentials credentials2 = Credentials.newBuilder()
                .setPhoneMode(phoneOTP)
                .setEmailMode(emailCredentials)
                .build();

        // En la realidad, sólo vamos a indicar una opción para establecerse
        Credentials credentials3 = Credentials.newBuilder()
                .setEmailMode(emailCredentials)
                .build();

        login(credentials1);
        System.out.println("------------");
        login(credentials2);
        System.out.println("------------");
        login(credentials3);
    }

    private static void login(Credentials credentials) {
        switch (credentials.getModeCase()) {
            case EMAIL_MODE -> System.out.println(credentials.getEmailMode());
            case PHONE_MODE -> System.out.println(credentials.getPhoneMode());
        }
    }
}
