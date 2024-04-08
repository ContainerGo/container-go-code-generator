package vn.containergo.service.mapper;

import java.util.Objects;
import java.util.UUID;
import org.mapstruct.*;
import vn.containergo.domain.Carrier;
import vn.containergo.domain.CarrierPerson;
import vn.containergo.domain.CarrierPersonGroup;
import vn.containergo.service.dto.CarrierDTO;
import vn.containergo.service.dto.CarrierPersonDTO;
import vn.containergo.service.dto.CarrierPersonGroupDTO;

/**
 * Mapper for the entity {@link CarrierPerson} and its DTO {@link CarrierPersonDTO}.
 */
@Mapper(componentModel = "spring")
public interface CarrierPersonMapper extends EntityMapper<CarrierPersonDTO, CarrierPerson> {
    @Mapping(target = "group", source = "group", qualifiedByName = "carrierPersonGroupId")
    @Mapping(target = "carrier", source = "carrier", qualifiedByName = "carrierId")
    CarrierPersonDTO toDto(CarrierPerson s);

    @Named("carrierPersonGroupId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CarrierPersonGroupDTO toDtoCarrierPersonGroupId(CarrierPersonGroup carrierPersonGroup);

    @Named("carrierId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CarrierDTO toDtoCarrierId(Carrier carrier);

    default String map(UUID value) {
        return Objects.toString(value, null);
    }
}
