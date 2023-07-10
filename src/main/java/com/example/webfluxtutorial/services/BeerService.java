package com.example.webfluxtutorial.services;

import com.example.webfluxtutorial.model.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BeerService {
    Flux<BeerDTO> findAll();

    Mono<BeerDTO> save(Mono<BeerDTO> beerDTO);

    Mono<BeerDTO> save(BeerDTO beerDTO);

    Mono<BeerDTO> getById(String id);

    Mono<BeerDTO> update(String id, BeerDTO beerDTO);

    Mono<BeerDTO> patch(String id, BeerDTO beerDTO);

    void deleteById(String id);

}
