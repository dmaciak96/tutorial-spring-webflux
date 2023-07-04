package com.example.webfluxtutorial.repository;

import com.example.webfluxtutorial.domain.Beer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BeerRepository extends ReactiveCrudRepository<Beer, Integer> {
}
