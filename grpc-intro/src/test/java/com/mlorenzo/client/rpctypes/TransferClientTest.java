package com.mlorenzo.client.rpctypes;

import com.mlorenzo.models.TransferRequest;
import com.mlorenzo.models.TransferServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransferClientTest {
    // Non blocking client
    TransferServiceGrpc.TransferServiceStub nonServiceStub;

    @BeforeAll
    void setUp() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();
        nonServiceStub = TransferServiceGrpc.newStub(managedChannel);
    }

    // Bidirectional streaming
    @Test
    void transferTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        TransferStreamingResponse transferStreamingResponse = new TransferStreamingResponse(countDownLatch);
        StreamObserver<TransferRequest> streamObserver = nonServiceStub.transfer(transferStreamingResponse);
        for(int i = 1; i <= 100; i++) {
            TransferRequest transferRequest = TransferRequest.newBuilder()
                    // Genera números aleatorios entre [1, 10]
                    .setFromAccount(ThreadLocalRandom.current().nextInt(1, 11))
                    .setToAccount(ThreadLocalRandom.current().nextInt(1, 11))
                    .setAmount(ThreadLocalRandom.current().nextInt(1, 21))
                    .build();
            streamObserver.onNext(transferRequest);
        }
        streamObserver.onCompleted();
        countDownLatch.await();
    }
}
