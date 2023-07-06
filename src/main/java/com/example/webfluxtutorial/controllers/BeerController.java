package com.example.webfluxtutorial.controllers;

import com.example.webfluxtutorial.model.BeerDTO;
import com.example.webfluxtutorial.services.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class BeerController {

    public static final String BEER_PATH = "/api/beers";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    private final BeerService beerService;

    @GetMapping(BEER_PATH)
    public Flux<BeerDTO> listBeers() {
        return beerService.listBeers();
    }

    @GetMapping(BEER_PATH_ID)
    public Mono<BeerDTO> findById(@PathVariable int beerId) {
        return beerService.findById(beerId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @PostMapping(BEER_PATH)
    public Mono<ResponseEntity<BeerDTO>> save(@Validated @RequestBody BeerDTO beerDTO) {
        return beerService.save(beerDTO)
                .map(savedDto -> ResponseEntity.created(UriComponentsBuilder
                                .fromHttpUrl("http://localhost:8080/" + BEER_PATH + "/" + savedDto.getId())
                                .build().toUri())
                        .body(savedDto));
    }

    @PutMapping(BEER_PATH_ID)
    public Mono<ResponseEntity<BeerDTO>> update(@PathVariable int beerId,
                                                @Validated @RequestBody BeerDTO beerDTO) {
        return beerService.update(beerId, beerDTO)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(ResponseEntity::ok);
    }

    @DeleteMapping(BEER_PATH_ID)
    public Mono<ResponseEntity<Void>> delete(@PathVariable int beerId) {
        return beerService.findById(beerId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(beerDTO -> beerService.delete(beerDTO.getId()))
                .thenReturn(ResponseEntity.noContent().build());
    }
}
