package com.mlorenzo.server.common;

import com.mlorenzo.models.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

    // Unary
    @Override
    public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {
        int accountNumber =  request.getAccountNumber();
        System.out.println("Received the request for " + accountNumber);
        Balance balance =  Balance.newBuilder()
                .setAmount(AccountDatabase.getBalance(accountNumber))
                .build();
        // Invocamos este método "onNext" para emitir datos en la respuesta
        responseObserver.onNext(balance);
        // Cuando terminemos de emitir datos en la respuesta, tenemos que indicar que hemos terminado
        // invocando este método "onCompleted"
        responseObserver.onCompleted();
    }

    // Server-side streaming
    @Override
    public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver) {
        int accountNumber =  request.getAccountNumber();
        int amount = request.getAmount();
        int balance = AccountDatabase.getBalance(accountNumber);
        if(balance < amount) {
            Status status = Status.FAILED_PRECONDITION.withDescription("No enough money. You have only " + balance);
            responseObserver.onError(status.asRuntimeException());
            return;
        }
        for(int i = 0; i < (amount/10); i++) {
            AccountDatabase.deductBalance(accountNumber, 10);
            Money money = Money.newBuilder()
                    .setValue(10)
                    .build();
            responseObserver.onNext(money);
        }
        responseObserver.onCompleted();
    }

    // Client-side streaming
    @Override
    public StreamObserver<DepositRequest> cashDeposit(StreamObserver<Balance> responseObserver) {
        return new CashStreamingRequest(responseObserver);
    }
}
