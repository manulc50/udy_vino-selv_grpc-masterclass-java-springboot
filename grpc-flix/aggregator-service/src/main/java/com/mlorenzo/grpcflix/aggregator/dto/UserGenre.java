package com.mlorenzo.grpcflix.aggregator.dto;

import com.mlorenzo.grpcflix.common.Genre;
import lombok.Data;
import lombok.Value;

@Data
public class UserGenre {
    private Genre genre;
}
