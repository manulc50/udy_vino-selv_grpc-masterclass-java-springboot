package com.mlorenzo.grpcflix.movie.repository;

import com.mlorenzo.grpcflix.common.Genre;
import com.mlorenzo.grpcflix.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findByGenreOrderByYearDesc(Genre genre);
}
