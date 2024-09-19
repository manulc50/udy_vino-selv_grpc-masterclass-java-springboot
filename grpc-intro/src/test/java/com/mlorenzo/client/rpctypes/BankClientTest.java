package com.mlorenzo.client.rpctypes;

import com.mlorenzo.client.common.BalanceStreamObserver;
import com.mlorenzo.client.common.MoneyStreamingResponse;
import com.mlorenzo.models.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BankClientTest {
    // Blocking client
    BankServiceGrpc.BankServiceBlockingStub blockingStub;
    // Non blocking client
    BankServiceGrpc.BankServiceStub nonServiceStub;

    // Nota: Un canal es una abstracción de la comunicación entre un cliente y el servidor
    // Nota: La conexión entre un cliente y un servidor gRPC es perezosa(lazy), es decir, aunque se
    // establezca un canal entre el cliente y el servidor, la conexión entre ellos se produce cuando el
    // cliente envía la primera petición rpc al servidor

    @BeforeAll
    void setUp() {
        // Crea un canal entre este cliente y el servidor gRPC
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565)
                // Canal sin seguridad, es decir, la información se transmite por el canal en texto plano sin ecriptar
                .usePlaintext()
                .build();
        // Crea los Stubs correspondientes a los servicios expuestos por el servidor gRPC.
        // Éstos Stubs nos permiten poder invocar dichos servicios desde un cliente
        blockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
        nonServiceStub = BankServiceGrpc.newStub(managedChannel);
    }

    // Unary
    @Test
    void balanceTest() {
        int accountNumber = 7;
        BalanceCheckRequest request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(accountNumber)
                .build();
        Balance balance = blockingStub.getBalance(request);
        System.out.println("Received: " + balance.getAmount());
    }

    // Server-side streaming
    @Test
    void withdrawTest() {
        WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder()
                .setAccountNumber(7)
                .setAmount(40)
                .build();
        blockingStub.withdraw(withdrawRequest)
                .forEachRemaining(money -> System.out.println("Received: " + money.getValue()));
    }

    // Server-side streaming
    @Test
    void withdrawAsyncTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder()
                .setAccountNumber(10)
                .setAmount(50)
                .build();
        nonServiceStub.withdraw(withdrawRequest, new MoneyStreamingResponse(countDownLatch));
        // Interrumpe la ejecución hasta que el valor de "countDownLatch" sea 0
        countDownLatch.await();
    }

    // Client-side streaming
    @Test
    void cashStreamingRequest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        StreamObserver<DepositRequest> streamObserver = nonServiceStub
                .cashDeposit(new BalanceStreamObserver(countDownLatch));
        for(int i = 1; i <= 10; i++) {
            DepositRequest depositRequest = DepositRequest.newBuilder()
                    .setAccountNumber(8)
                    .setAmount(10)
                    .build();
            streamObserver.onNext(depositRequest);
        }
        streamObserver.onCompleted();
        // Interrumpe la ejecución hasta que el valor de "countDownLatch" sea 0
        countDownLatch.await();
    }
}
