package fincons.academy.services;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;

import fincons.academy.web.model.BeerDto;
import fincons.academy.web.model.BeerPagedList;
import fincons.academy.web.model.BeerStyleEnum;



public interface BeerService {
	
	BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand);

    BeerDto getById(UUID beerId, Boolean showInventoryOnHand);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);

    BeerDto getByUpc(String upc);

    void deleteBeerById(UUID beerId);

}
