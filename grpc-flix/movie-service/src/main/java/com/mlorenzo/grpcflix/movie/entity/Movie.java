package com.mlorenzo.grpcflix.movie.entity;

import com.mlorenzo.grpcflix.common.Genre;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    private int id;

    private String title;

    // Nota: Parece ser que en la base de datos H2 la palabra "year" es una palabra reservada y no se puede usar
    @Column(name = "release_year")
    private int year;

    private double rating;

    @Enumerated(EnumType.STRING)
    private Genre genre;
}
