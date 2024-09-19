package com.mlorenzo.server.ssl;

import com.mlorenzo.server.common.BankService;
import io.grpc.Server;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContextBuilder;

import java.io.File;
import java.io.IOException;

public class GrpcServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        SslContext sslContext = GrpcSslContexts.configure(
                SslContextBuilder.forServer(
                        new File("ssl-tls/localhost.crt"),
                        new File("ssl-tls/localhost.pem")))
                .build();
        Server server = NettyServerBuilder.forPort(6565)
                .addService(new BankService())
                .sslContext(sslContext)
                .build();
        server.start();
        // Para que el servidor se quede esuchando una vez que se inicia
        server.awaitTermination();
    }
}
