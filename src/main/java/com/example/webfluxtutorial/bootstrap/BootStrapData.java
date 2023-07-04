package com.example.webfluxtutorial.bootstrap;

import com.example.webfluxtutorial.domain.Beer;
import com.example.webfluxtutorial.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class BootStrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();

        beerRepository.count().subscribe(count -> log.info("Count is: {}", count));
    }

    private void loadBeerData() {
        beerRepository.count().subscribe(count -> {
            if (count == 0) {
                var beer = Beer.builder()
                        .beerName("Dzik")
                        .beerStyle("IPA")
                        .upc("123456222")
                        .price(new BigDecimal("11.99"))
                        .quantityOnHand(122)
                        .build();

                var beer2 = Beer.builder()
                        .beerName("Harnaś")
                        .beerStyle("Pale Ale")
                        .upc("123123")
                        .price(new BigDecimal("12.99"))
                        .quantityOnHand(222)
                        .build();

                var beer3 = Beer.builder()
                        .beerName("Warka")
                        .beerStyle("IPA")
                        .upc("123456333")
                        .price(new BigDecimal("13.99"))
                        .quantityOnHand(133)
                        .build();
                beerRepository.save(beer).subscribe();
                beerRepository.save(beer2).subscribe();
                beerRepository.save(beer3).subscribe();
            }
        });
    }
}
