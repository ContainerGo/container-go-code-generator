package vn.containergo.service.mapper;

import java.util.Objects;
import java.util.UUID;
import org.mapstruct.*;
import vn.containergo.domain.Shipper;
import vn.containergo.domain.ShipperPerson;
import vn.containergo.domain.ShipperPersonGroup;
import vn.containergo.service.dto.ShipperDTO;
import vn.containergo.service.dto.ShipperPersonDTO;
import vn.containergo.service.dto.ShipperPersonGroupDTO;

/**
 * Mapper for the entity {@link ShipperPerson} and its DTO {@link ShipperPersonDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShipperPersonMapper extends EntityMapper<ShipperPersonDTO, ShipperPerson> {
    @Mapping(target = "group", source = "group", qualifiedByName = "shipperPersonGroupId")
    @Mapping(target = "shipper", source = "shipper", qualifiedByName = "shipperId")
    ShipperPersonDTO toDto(ShipperPerson s);

    @Named("shipperPersonGroupId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ShipperPersonGroupDTO toDtoShipperPersonGroupId(ShipperPersonGroup shipperPersonGroup);

    @Named("shipperId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ShipperDTO toDtoShipperId(Shipper shipper);

    default String map(UUID value) {
        return Objects.toString(value, null);
    }
}
