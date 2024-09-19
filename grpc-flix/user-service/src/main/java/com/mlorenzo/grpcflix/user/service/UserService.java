package com.mlorenzo.grpcflix.user.service;

import com.mlorenzo.grpcflix.user.UserGenreUpdateRequest;
import com.mlorenzo.grpcflix.user.UserResponse;
import com.mlorenzo.grpcflix.user.UserSearchRequest;
import com.mlorenzo.grpcflix.user.UserServiceGrpc;
import com.mlorenzo.grpcflix.user.entity.User;
import com.mlorenzo.grpcflix.user.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@RequiredArgsConstructor
// Esta anotación crea de forma automática una instancia de esta clase servicio y la añade al servidor gRPC
@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {
    private final UserRepository userRespoitory;

    @Override
    public void getUserGenre(UserSearchRequest request, StreamObserver<UserResponse> responseObserver) {
        userRespoitory.findById(request.getLoginId())
                .ifPresent(user -> {
                    UserResponse userResponse = UserResponse.newBuilder()
                            .setLoginId(user.getLoginId())
                            .setName(user.getName())
                            .setGenre(user.getGenre())
                            .build();
                    responseObserver.onNext(userResponse);
                });
        responseObserver.onCompleted();
    }

    @Override
    public void updateUserGenre(UserGenreUpdateRequest request, StreamObserver<UserResponse> responseObserver) {
        userRespoitory.findById(request.getLoginId())
                .ifPresent(user -> {
                    user.setGenre(request.getGenre());
                    User updatedUser = userRespoitory.save(user);
                    UserResponse userResponse = UserResponse.newBuilder()
                            .setLoginId(updatedUser.getLoginId())
                            .setName(updatedUser.getName())
                            .setGenre(updatedUser.getGenre())
                            .build();
                    responseObserver.onNext(userResponse);
                });
        responseObserver.onCompleted();
    }
}
