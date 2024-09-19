package com.mlorenzo.grpcflix.aggregator.dto;

import lombok.Value;

@Value
public class RecommendedMovie {
    String title;
    int year;
    double rating;
}
