package com.mlorenzo.grpcflix.aggregator.controller;

import com.mlorenzo.grpcflix.aggregator.dto.RecommendedMovie;
import com.mlorenzo.grpcflix.aggregator.dto.UserGenre;
import com.mlorenzo.grpcflix.aggregator.service.AggregatorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("users")
public class AggregatorController {
    private AggregatorService aggregatorService;

    @GetMapping("{login-id}")
    List<RecommendedMovie> getMovies(@PathVariable("login-id") String loginId) {
        return aggregatorService.getUserMovieSuggestions(loginId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{login-id}")
    public void updateUserGenre(@PathVariable(value = "login-id") String loginId,
                                @RequestBody UserGenre userGenre) {
        aggregatorService.updateUserGenre(loginId, userGenre);
    }
}
