package com.example.webfluxtutorial.repository;

import com.example.webfluxtutorial.domain.Person;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Repository
public class PersonRepositoryImpl implements PersonRepository {
    @Override
    public Mono<Person> getById(Integer id) {
        return Arrays.stream(createPersons())
                .filter(e -> Objects.equals(e.getId(), id))
                .findFirst()
                .map(Mono::just)
                .orElseGet(Mono::empty);
    }

    @Override
    public Flux<Person> findAll() {
        return Flux.just(createPersons());
    }

    private Person[] createPersons() {
        return List.of(Person.builder().id(1).firstName("Michael").lastName("Weston").build(),
                Person.builder().id(2).firstName("Fiona").lastName("Weston").build(),
                Person.builder().id(3).firstName("Sam").lastName("Weston").build(),
                Person.builder().id(4).firstName("Jesse").lastName("Weston").build()).toArray(new Person[4]);
    }
}
