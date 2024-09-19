package com.mlorenzo.grpcflix.movie.service;

import com.mlorenzo.grpcflix.movie.MovieDto;
import com.mlorenzo.grpcflix.movie.MovieSearchRequest;
import com.mlorenzo.grpcflix.movie.MovieSearchResponse;
import com.mlorenzo.grpcflix.movie.MovieServiceGrpc;
import com.mlorenzo.grpcflix.movie.repository.MovieRepository;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
// Esta anotación crea de forma automática una instancia de esta clase servicio y la añade al servidor gRPC
@GrpcService
public class MovieService extends MovieServiceGrpc.MovieServiceImplBase {
    private final MovieRepository movieRepository;

    @Override
    public void getMovies(MovieSearchRequest request, StreamObserver<MovieSearchResponse> responseObserver) {
        List<MovieDto> movieDtoList = movieRepository.findByGenreOrderByYearDesc(request.getGenre())
                .stream()
                .map(movie -> MovieDto.newBuilder()
                        .setTitle(movie.getTitle())
                        .setRating(movie.getRating())
                        .setYear(movie.getYear())
                        .build()
                )
                .collect(Collectors.toList());
        MovieSearchResponse movieSearchResponse = MovieSearchResponse.newBuilder()
                .addAllMovies(movieDtoList)
                .build();
        responseObserver.onNext(movieSearchResponse);
        responseObserver.onCompleted();
    }
}
