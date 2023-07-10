package com.example.webfluxtutorial.services;

import com.example.webfluxtutorial.mappers.BeerMapper;
import com.example.webfluxtutorial.model.BeerDTO;
import com.example.webfluxtutorial.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;


    @Override
    public Flux<BeerDTO> findAll() {
        return null;
    }

    @Override
    public Mono<BeerDTO> save(Mono<BeerDTO> beerDTO) {
        return beerDTO.map(beerMapper::beerDtoToBeer)
                .flatMap(beerRepository::save)
                .map(beerMapper::beerToBeerDTO);
    }

    @Override
    public Mono<BeerDTO> save(BeerDTO beerDTO) {
        return null;
    }

    @Override
    public Mono<BeerDTO> getById(String id) {
        return beerRepository.findById(Mono.just(id))
                .map(beerMapper::beerToBeerDTO);
    }

    @Override
    public Mono<BeerDTO> update(String id, BeerDTO beerDTO) {
        return null;
    }

    @Override
    public Mono<BeerDTO> patch(String id, BeerDTO beerDTO) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }
}
