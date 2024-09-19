package com.mlorenzo.server.loadbalancing;

import com.mlorenzo.server.common.BankService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer2 {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(7575)
                .addService(new BankService())
                .build();
        server.start();
        // Para que el servidor se quede esuchando una vez que se inicia
        server.awaitTermination();
    }
}
