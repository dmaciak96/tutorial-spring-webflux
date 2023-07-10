package com.example.webfluxtutorial.services;

import com.example.webfluxtutorial.domain.Beer;
import com.example.webfluxtutorial.mappers.BeerMapper;
import com.example.webfluxtutorial.model.BeerDTO;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BeerServiceImplTest {

    @Autowired
    private BeerService beerService;

    @Autowired
    private BeerMapper beerMapper;

    private BeerDTO beerDTO;

    @BeforeEach
    void setUp() {
        beerDTO = beerMapper.beerToBeerDTO(getTestBeer());
    }

    @Test
    void saveTest() {
        var atomicBoolean = new AtomicBoolean(false);
        var atomicDto = new AtomicReference<BeerDTO>();

        beerService.save(Mono.just(beerDTO))
                .subscribe(beerDTO -> {
                    atomicBoolean.set(true);
                    atomicDto.set(beerDTO);
                });

        Awaitility.await().untilTrue(atomicBoolean);

        assertThat(atomicDto.get()).isNotNull();
        assertThat(atomicDto.get().getId()).isNotBlank();
    }

    private static Beer getTestBeer() {
        return Beer.builder()
                .beerName("Harnaś")
                .beerStyle("IPA")
                .price(BigDecimal.TEN)
                .quantityOnHand(2)
                .upc("123123")
                .build();
    }
}