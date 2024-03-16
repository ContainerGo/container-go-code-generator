package vn.containergo.service.mapper;

import org.mapstruct.*;
import vn.containergo.domain.Carrier;
import vn.containergo.domain.CarrierAccount;
import vn.containergo.service.dto.CarrierAccountDTO;
import vn.containergo.service.dto.CarrierDTO;

/**
 * Mapper for the entity {@link CarrierAccount} and its DTO {@link CarrierAccountDTO}.
 */
@Mapper(componentModel = "spring")
public interface CarrierAccountMapper extends EntityMapper<CarrierAccountDTO, CarrierAccount> {
    @Mapping(target = "carrier", source = "carrier", qualifiedByName = "carrierId")
    CarrierAccountDTO toDto(CarrierAccount s);

    @Named("carrierId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CarrierDTO toDtoCarrierId(Carrier carrier);
}
