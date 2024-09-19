package com.mlorenzo.game.client;

import com.google.common.util.concurrent.Uninterruptibles;
import com.mlorenzo.game.Die;
import com.mlorenzo.game.GameState;
import com.mlorenzo.game.Player;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class GameStateStreamingResponse implements StreamObserver<GameState> {
    private StreamObserver<Die> dieStreamObserver;
    private final CountDownLatch countDownLatch;

    public GameStateStreamingResponse(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void onNext(GameState gameState) {
        List<Player> playersList = gameState.getPlayersList();
        playersList
                .forEach(p -> System.out.println(p.getName() + ": " + p.getPosition()));
        boolean anyWinner = playersList.stream().anyMatch(p -> p.getPosition() == 100);
        if(anyWinner) {
            System.out.println("Game Over!");
            dieStreamObserver.onCompleted();
        }
        else {
            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
            roll();
        }
        System.out.println("----------------------------");
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(throwable.getMessage());
        countDownLatch.countDown();
    }

    @Override
    public void onCompleted() {
        countDownLatch.countDown();
    }

    public void setDieStreamObserver(StreamObserver<Die> dieStreamObserver) {
        this.dieStreamObserver = dieStreamObserver;
    }

    public void roll() {
        Die die = Die.newBuilder()
                .setValue(ThreadLocalRandom.current().nextInt(1, 7))
                .build();
        dieStreamObserver.onNext(die);
    }
}
