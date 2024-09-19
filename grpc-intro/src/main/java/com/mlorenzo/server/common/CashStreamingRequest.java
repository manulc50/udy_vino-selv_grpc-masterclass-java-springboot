package com.mlorenzo.server.common;

import com.mlorenzo.models.Balance;
import com.mlorenzo.models.DepositRequest;
import io.grpc.stub.StreamObserver;

// Clase que representa el canal del cliente

public class CashStreamingRequest implements StreamObserver<DepositRequest> {
    // Propiedad que va a tener la referencia del canal del servidor
    private final StreamObserver<Balance> balanceStreamObserver;
    private int amountBalance;

    public CashStreamingRequest(StreamObserver<Balance> balanceStreamObserver) {
        this.balanceStreamObserver = balanceStreamObserver;
    }

    // Por cada petición del cliente, se invocará este método
    @Override
    public void onNext(DepositRequest depositRequest) {
        int accountNumber = depositRequest.getAccountNumber();
        int amount = depositRequest.getAmount();
        amountBalance = AccountDatabase.addBalance(accountNumber, amount);
    }

    @Override
    public void onError(Throwable throwable) {
    }

    // Cuando el cliente termine de realizar peticiones, se invocará este método
    @Override
    public void onCompleted() {
        Balance totalBalance = Balance.newBuilder()
                .setAmount(amountBalance)
                .build();
        // Este es el canal del servidor(No del cliente)
        // En este caso, cuando el cliente ha finalizado de realizar peticiones al servidor, el servidor
        // responde con el balance total de las peticiones del cliente y finaliza el canal o la comunicación
        balanceStreamObserver.onNext(totalBalance);
        balanceStreamObserver.onCompleted();
    }
}
