package com.example.webfluxtutorial.services;

import com.example.webfluxtutorial.mappers.BeerMapper;
import com.example.webfluxtutorial.model.BeerDTO;
import com.example.webfluxtutorial.repository.BeerRepository;
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
    public Flux<BeerDTO> listBeers() {
        return beerRepository.findAll()
                .map(beerMapper::beerToBeerDTO);
    }

    @Override
    public Mono<BeerDTO> findById(int beerId) {
        return beerRepository.findById(beerId)
                .map(beerMapper::beerToBeerDTO);
    }

    @Override
    public Mono<BeerDTO> save(BeerDTO beerDTO) {
        return beerRepository.save(beerMapper.beerDtoToBeer(beerDTO))
                .map(beerMapper::beerToBeerDTO);
    }

    @Override
    public Mono<BeerDTO> update(int beerId, BeerDTO beerDTO) {
        beerDTO.setId(beerId);
        return beerRepository.findById(beerId)
                .map(beer -> {
                    beer.setBeerName(beerDTO.getBeerName());
                    beer.setBeerStyle(beerDTO.getBeerStyle());
                    beer.setPrice(beerDTO.getPrice());
                    beer.setUpc(beerDTO.getUpc());
                    beer.setQuantityOnHand(beerDTO.getQuantityOnHand());
                    return beer;
                })
                .flatMap(beerRepository::save)
                .map(beerMapper::beerToBeerDTO);
    }

    @Override
    public Mono<Void> delete(int beerId) {
        return beerRepository.deleteById(beerId);
    }
}
