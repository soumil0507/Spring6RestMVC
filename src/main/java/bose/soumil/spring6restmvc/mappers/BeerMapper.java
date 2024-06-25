package bose.soumil.spring6restmvc.mappers;

import bose.soumil.spring6restmvc.entities.Beer;
import bose.soumil.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerToBeerDto(Beer beer);
}
