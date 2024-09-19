package com.mlorenzo.game.server;

import com.mlorenzo.game.Die;
import com.mlorenzo.game.GameServiceGrpc;
import com.mlorenzo.game.GameState;
import com.mlorenzo.game.Player;
import io.grpc.stub.StreamObserver;

public class GameService extends GameServiceGrpc.GameServiceImplBase {

    // Bidirectional streaming
    @Override
    public StreamObserver<Die> roll(StreamObserver<GameState> responseObserver) {
        Player client = Player.newBuilder().setName("client").setPosition(0).build();
        Player server = Player.newBuilder().setName("server").setPosition(0).build();
        return new DieStreamingRequest(responseObserver, client, server);
    }
}
