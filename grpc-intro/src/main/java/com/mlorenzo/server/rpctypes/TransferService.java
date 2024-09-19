package com.mlorenzo.server.rpctypes;

import com.mlorenzo.models.TransferRequest;
import com.mlorenzo.models.TransferResponse;
import com.mlorenzo.models.TransferServiceGrpc;
import io.grpc.stub.StreamObserver;

public class TransferService extends TransferServiceGrpc.TransferServiceImplBase {

    // Bidirectional streaming
    @Override
    public StreamObserver<TransferRequest> transfer(StreamObserver<TransferResponse> responseObserver) {
        return new TransferStreamingRequest(responseObserver);
    }
}
