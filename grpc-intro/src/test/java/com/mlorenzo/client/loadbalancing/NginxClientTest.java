package com.mlorenzo.client.loadbalancing;

import com.mlorenzo.models.Balance;
import com.mlorenzo.models.BalanceCheckRequest;
import com.mlorenzo.models.BankServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.shaded.io.netty.util.internal.ThreadLocalRandom;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NginxClientTest {

    // Blocking client
    BankServiceGrpc.BankServiceBlockingStub blockingStub;

    @BeforeAll
    void setUp() {
        // Crea un canal entre este cliente y el servidor Proxy Nginx
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8585)
                // Canal sin seguridad, es decir, la información se transmite por el canal en texto plano sin ecriptar
                .usePlaintext()
                .build();
        blockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
    }

    // Unary
    @Test
    void balanceTest() throws InterruptedException {
        for(int i = 1; i <= 10; i++) {
            BalanceCheckRequest request = BalanceCheckRequest.newBuilder()
                    .setAccountNumber(ThreadLocalRandom.current().nextInt(1, 11))
                    .build();
            Balance balance = blockingStub.getBalance(request);
            System.out.println("Received: " + balance.getAmount());
        }
    }
}
