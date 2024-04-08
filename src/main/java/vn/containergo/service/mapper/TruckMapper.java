package vn.containergo.service.mapper;

import java.util.Objects;
import java.util.UUID;
import org.mapstruct.*;
import vn.containergo.domain.Carrier;
import vn.containergo.domain.Truck;
import vn.containergo.domain.TruckType;
import vn.containergo.service.dto.CarrierDTO;
import vn.containergo.service.dto.TruckDTO;
import vn.containergo.service.dto.TruckTypeDTO;

/**
 * Mapper for the entity {@link Truck} and its DTO {@link TruckDTO}.
 */
@Mapper(componentModel = "spring")
public interface TruckMapper extends EntityMapper<TruckDTO, Truck> {
    @Mapping(target = "type", source = "type", qualifiedByName = "truckTypeId")
    @Mapping(target = "carrier", source = "carrier", qualifiedByName = "carrierId")
    TruckDTO toDto(Truck s);

    @Named("truckTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TruckTypeDTO toDtoTruckTypeId(TruckType truckType);

    @Named("carrierId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CarrierDTO toDtoCarrierId(Carrier carrier);

    default String map(UUID value) {
        return Objects.toString(value, null);
    }
}
