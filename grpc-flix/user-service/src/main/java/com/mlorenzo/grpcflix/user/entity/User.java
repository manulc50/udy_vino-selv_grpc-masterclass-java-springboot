package com.mlorenzo.grpcflix.user.entity;

import com.mlorenzo.grpcflix.common.Genre;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    private String loginId;

    private String name;

    @Enumerated(EnumType.STRING)
    private Genre genre;
}
