package com.mlorenzo.client.rpctypes;

import com.mlorenzo.models.TransferResponse;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;

public class TransferStreamingResponse implements StreamObserver<TransferResponse> {
    private final CountDownLatch countDownLatch;

    public TransferStreamingResponse(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void onNext(TransferResponse transferResponse) {
        System.out.println("Status: " + transferResponse.getStatus());
        transferResponse.getAccountsList()
                .forEach(account -> System.out.println(account.getAccountNumber() + ": " + account.getAmount()));
        System.out.println("------------------------");
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(throwable.getMessage());
        countDownLatch.countDown();
    }

    @Override
    public void onCompleted() {
        System.out.println("All transfers done!");
        countDownLatch.countDown();
    }
}
