package com.example.webfluxtutorial.repository;

import com.example.webfluxtutorial.domain.Person;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class PersonRepositoryImplTest {

    private final PersonRepository personRepository = new PersonRepositoryImpl();

    @Test
    void testMonoByIdBlock() {
        var personMono = personRepository.getById(1);
        var person = personMono.block();
        System.out.println(person);
    }

    @Test
    void testGetByIdSubscriber() {
        var personMono = personRepository.getById(1);
        personMono.subscribe(System.out::println);
    }

    @Test
    void testMapOperation() {
        var personMono = personRepository.getById(1);
        personMono.map(Person::getFirstName)
                .subscribe(System.out::println);
    }

    @Test
    void testBlockFirst() {
        var personFlux = personRepository.findAll();
        var person = personFlux.blockFirst();
        System.out.println(person);
    }

    @Test
    void testFluxSubscriber() {
        var personFlux = personRepository.findAll();
        personFlux.subscribe(System.out::println);
    }

    @Test
    void convertFluxToListMono() {
        var personFlux = personRepository.findAll();
        var listMono = personFlux.collectList();
        listMono.subscribe(list -> list.forEach(System.out::println));
    }

    @Test
    void filterFluxOnName() {
        var personFlux = personRepository.findAll();
        personFlux.filter(person -> person.getFirstName().equalsIgnoreCase("fiona"))
                .subscribe(System.out::println);
    }

    @Test
    public void testGetById() {
        var fionaMono = personRepository.findAll()
                .filter(person -> person.getFirstName().equalsIgnoreCase("fiona"))
                .next();
        fionaMono.subscribe(System.out::println);
    }

    @Test
    public void testFindPersonByIdNotFound() {
        var personFlux = personRepository.findAll();
        var personMono = personFlux.filter(person -> person.getId() == 8).single()
                .doOnError(throwable -> {
                    System.out.println("Error occurred in flux");
                    System.out.println(throwable.toString());
                });
        personMono.subscribe(System.out::println, throwable -> {
            System.out.println("Error occured in the mono");
            System.out.println(throwable.toString());
        });
    }

    @Test
    public void testFindPersonByIdNotFoundStepVerifier() {
        var personMono = personRepository.getById(6);
        StepVerifier.create(personMono).expectNextCount(0).verifyComplete();
        personMono.subscribe(System.out::println);
    }

    @Test
    void testGetByIdStepVerifier() {
        var personMono = personRepository.getById(1);
        StepVerifier.create(personMono).expectNextCount(1).verifyComplete();
        personMono.subscribe(System.out::println);
    }
}