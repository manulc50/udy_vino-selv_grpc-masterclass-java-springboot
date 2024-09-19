package com.mlorenzo.server.rpctypes;

import com.mlorenzo.server.common.BankService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(6565)
                .addService(new BankService())
                .addService(new TransferService())
                .build();
        server.start();
        // Para que el servidor se quede esuchando una vez que se inicia
        server.awaitTermination();
    }
}
