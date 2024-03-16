package vn.containergo.service.mapper;

import org.mapstruct.*;
import vn.containergo.domain.Carrier;
import vn.containergo.domain.CarrierPerson;
import vn.containergo.service.dto.CarrierDTO;
import vn.containergo.service.dto.CarrierPersonDTO;

/**
 * Mapper for the entity {@link CarrierPerson} and its DTO {@link CarrierPersonDTO}.
 */
@Mapper(componentModel = "spring")
public interface CarrierPersonMapper extends EntityMapper<CarrierPersonDTO, CarrierPerson> {
    @Mapping(target = "carrier", source = "carrier", qualifiedByName = "carrierId")
    CarrierPersonDTO toDto(CarrierPerson s);

    @Named("carrierId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CarrierDTO toDtoCarrierId(Carrier carrier);
}
