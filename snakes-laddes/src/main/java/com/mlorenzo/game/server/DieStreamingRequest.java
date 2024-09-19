package com.mlorenzo.game.server;

import com.mlorenzo.game.Die;
import com.mlorenzo.game.GameState;
import com.mlorenzo.game.Player;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.ThreadLocalRandom;

public class DieStreamingRequest implements StreamObserver<Die> {
    private final StreamObserver<GameState> gameStateStreamObserver;
    private Player client;
    private Player server;

    public DieStreamingRequest(StreamObserver<GameState> gameStateStreamObserver, Player client, Player server) {
        this.gameStateStreamObserver = gameStateStreamObserver;
        this.client = client;
        this.server = server;
    }

    @Override
    public void onNext(Die die) {
        client = getNewPlayerPosition(client, die.getValue());
        if(client.getPosition() != 100)
            server = getNewPlayerPosition(server, ThreadLocalRandom.current().nextInt(1, 7));
        gameStateStreamObserver.onNext(GameState.newBuilder()
                .addPlayers(client)
                .addPlayers(server)
                .build());
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        gameStateStreamObserver.onCompleted();
    }

    private Player getNewPlayerPosition(Player player, int dieValue) {
        int newPosition = player.getPosition() + dieValue;
        newPosition = newPosition <= 100 ? SnakesAndLaddersMap.getPosition(newPosition)
                : player.getPosition();
        return Player.newBuilder()
                .setName(player.getName())
                .setPosition(newPosition)
                .build();
    }
}
