package com.mlorenzo.client.common;

import com.mlorenzo.models.Balance;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;

// Esta clase representa el canal del servidor

public class BalanceStreamObserver implements StreamObserver<Balance> {
    private CountDownLatch countDownLatch;

    public BalanceStreamObserver(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void onNext(Balance balance) {
        System.out.println("Final Balance: " + balance.getAmount());
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(countDownLatch.getCount());
        System.out.println(throwable.getMessage());
        countDownLatch.countDown();
    }

    @Override
    public void onCompleted() {
        System.out.println("Server is done!");
        countDownLatch.countDown();
    }
}
