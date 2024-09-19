package com.mlorenzo.grpcflix.aggregator.service;

import com.mlorenzo.grpcflix.aggregator.dto.RecommendedMovie;
import com.mlorenzo.grpcflix.aggregator.dto.UserGenre;
import com.mlorenzo.grpcflix.movie.MovieSearchRequest;
import com.mlorenzo.grpcflix.movie.MovieSearchResponse;
import com.mlorenzo.grpcflix.user.UserGenreUpdateRequest;
import com.mlorenzo.grpcflix.user.UserResponse;
import com.mlorenzo.grpcflix.user.UserSearchRequest;
import com.mlorenzo.grpcflix.user.UserServiceGrpc;
import com.mlorenzo.grpcflix.movie.MovieServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AggregatorService {

    // Nota: El nombre del identificador del cliente pasado a la anotación @GrpcClient tiene que coincidir con el
    // nombre del identificador del cliente indicado en al archivo de propiedades
    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userStub;

    @GrpcClient("movie-service")
    private MovieServiceGrpc.MovieServiceBlockingStub movieStub;

    public List<RecommendedMovie> getUserMovieSuggestions(String loginId) {
        UserSearchRequest userSearchRequest = UserSearchRequest.newBuilder()
                .setLoginId(loginId)
                .build();
        UserResponse userResponse = userStub.getUserGenre(userSearchRequest);
        MovieSearchRequest movieSearchRequest = MovieSearchRequest.newBuilder()
                .setGenre(userResponse.getGenre())
                .build();
        MovieSearchResponse movieSearchResponse = movieStub.getMovies(movieSearchRequest);
        return movieSearchResponse.getMoviesList().stream()
                .map(movieDto -> new RecommendedMovie(movieDto.getTitle(), movieDto.getYear(), movieDto.getRating()))
                .collect(Collectors.toList());
    }

    public void updateUserGenre(String loginId, UserGenre userGenre) {
        UserGenreUpdateRequest userGenreUpdateRequest = UserGenreUpdateRequest.newBuilder()
                .setGenre(userGenre.getGenre())
                .setLoginId(loginId)
                .build();
        userStub.updateUserGenre(userGenreUpdateRequest);
    }
}
