package com.mlorenzo.game.client;

import com.mlorenzo.game.Die;
import com.mlorenzo.game.GameServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GameClientTest {
    GameServiceGrpc.GameServiceStub nonBlockingStub;

    @BeforeAll
    void setUp() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();
        nonBlockingStub = GameServiceGrpc.newStub(managedChannel);
    }

    // Bidirectional streaming
    @Test
    void clientGameTest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        GameStateStreamingResponse gameStateStreamingResponse = new GameStateStreamingResponse(countDownLatch);
        StreamObserver<Die> dieStreamObserver = nonBlockingStub.roll(gameStateStreamingResponse);
        gameStateStreamingResponse.setDieStreamObserver(dieStreamObserver);
        gameStateStreamingResponse.roll();
        countDownLatch.await();
    }
}
