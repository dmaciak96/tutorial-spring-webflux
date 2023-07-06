package com.example.webfluxtutorial.mappers;

import com.example.webfluxtutorial.domain.Beer;
import com.example.webfluxtutorial.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BeerMapper {

    BeerDTO beerToBeerDTO(Beer beer);
}
