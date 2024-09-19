package com.mlorenzo.game.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

// Nota: Esta aplicación simula el juego de mesa de serpientes y escaleras

public class GrpcServer {

    public static void main(String[] args) throws InterruptedException, IOException {
        Server server = ServerBuilder.forPort(6565)
                .addService(new GameService())
                .build();
        server.start();
        server.awaitTermination();
    }
}
