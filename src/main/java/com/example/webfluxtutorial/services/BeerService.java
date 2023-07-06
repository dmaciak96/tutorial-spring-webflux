package com.example.webfluxtutorial.services;

import com.example.webfluxtutorial.model.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BeerService {

    Flux<BeerDTO> listBeers();

    Mono<BeerDTO> findById(int beerId);
}
