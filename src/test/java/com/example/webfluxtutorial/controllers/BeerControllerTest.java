package com.example.webfluxtutorial.controllers;

import com.example.webfluxtutorial.model.BeerDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BeerControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    void testListBeers() {
        webTestClient.get()
                .uri(BeerController.BEER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(3);
    }

    @Test
    void testGetById() {
        webTestClient.get()
                .uri(BeerController.BEER_PATH_ID, 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody(BeerDTO.class);
    }

    @Test
    void testGetByIdNotFound() {
        webTestClient.get()
                .uri(BeerController.BEER_PATH_ID, 8)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testSave() {
        webTestClient.post()
                .uri(BeerController.BEER_PATH)
                .body(Mono.just(getTestBeer()), BeerDTO.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody(BeerDTO.class);
    }

    @Test
    void testSaveBadData() {
        var beer = getTestBeer();
        beer.setBeerName("");
        webTestClient.post()
                .uri(BeerController.BEER_PATH)
                .body(Mono.just(beer), BeerDTO.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testUpdate() {
        webTestClient.put()
                .uri(BeerController.BEER_PATH_ID, 2)
                .body(Mono.just(getTestBeer()), BeerDTO.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody().jsonPath("$.beerName").isEqualTo("Space Dust")
                .jsonPath("$.beerStyle").isEqualTo("IPA")
                .jsonPath("$.price").isEqualTo(BigDecimal.TEN)
                .jsonPath("$.upc").isEqualTo("123213");
    }

    @Test
    void testUpdateNotFound() {
        webTestClient.put()
                .uri(BeerController.BEER_PATH_ID, 8)
                .body(Mono.just(getTestBeer()), BeerDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdateBadData() {
        var beer = getTestBeer();
        beer.setBeerName("");
        webTestClient.put()
                .uri(BeerController.BEER_PATH_ID, 2)
                .body(Mono.just(beer), BeerDTO.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testDelete() {
        webTestClient.delete()
                .uri(BeerController.BEER_PATH_ID, 1)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testDeleteNotFound() {
        webTestClient.delete()
                .uri(BeerController.BEER_PATH_ID, 9)
                .exchange()
                .expectStatus().isNotFound();
    }

    private BeerDTO getTestBeer() {
        return BeerDTO.builder()
                .beerName("Space Dust")
                .beerStyle("IPA")
                .price(BigDecimal.TEN)
                .upc("123213")
                .build();
    }
}