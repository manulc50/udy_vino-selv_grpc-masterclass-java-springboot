package com.mlorenzo.server.rpctypes;

import com.mlorenzo.models.Account;
import com.mlorenzo.models.TransferRequest;
import com.mlorenzo.models.TransferResponse;
import com.mlorenzo.models.TransferStatus;
import com.mlorenzo.server.common.AccountDatabase;
import io.grpc.stub.StreamObserver;

// Clase que representa el canal del cliente

public class TransferStreamingRequest implements StreamObserver<TransferRequest> {
    // Propiedad que va a tener la referencia del canal del servidor
    private final StreamObserver<TransferResponse> transferResponseStreamObserver;

    public TransferStreamingRequest(StreamObserver<TransferResponse> transferResponseStreamObserver) {
        this.transferResponseStreamObserver = transferResponseStreamObserver;
    }

    // Por cada petición del cliente, se invocará este método
    // En este caso, por cada petición del cliente, se envía una respuesta desde el servidor
    @Override
    public void onNext(TransferRequest transferRequest) {
        int fromAccountNumber = transferRequest.getFromAccount();
        int toAccountNumber = transferRequest.getToAccount();
        int amount = transferRequest.getAmount();
        int balance = AccountDatabase.getBalance(fromAccountNumber);
        TransferStatus transferStatus = TransferStatus.FAILED;

        if(balance >= amount && fromAccountNumber != toAccountNumber) {
            AccountDatabase.deductBalance(fromAccountNumber, amount);
            AccountDatabase.addBalance(toAccountNumber, amount);
            transferStatus = TransferStatus.SUCCESS;
        }

        Account fromAccount = Account.newBuilder()
                .setAccountNumber(fromAccountNumber)
                .setAmount(AccountDatabase.getBalance(fromAccountNumber))
                .build();
        Account toAccount = Account.newBuilder()
                .setAccountNumber(toAccountNumber)
                .setAmount(AccountDatabase.getBalance(toAccountNumber))
                .build();
        TransferResponse transferResponse = TransferResponse.newBuilder()
                .setStatus(transferStatus)
                .addAccounts(fromAccount)
                .addAccounts(toAccount)
                .build();
        transferResponseStreamObserver.onNext(transferResponse);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    // Cuando el cliente finalice de realizar peticiones, finalizamos también el canal del servidor, es decir, cerramos
    // también cerramos la comunicación por parte del servidor
    @Override
    public void onCompleted() {
        AccountDatabase.printAccountsDetails();
        transferResponseStreamObserver.onCompleted();
    }
}
