package vn.containergo.service.mapper;

import org.mapstruct.*;
import vn.containergo.domain.Shipper;
import vn.containergo.domain.ShipperPerson;
import vn.containergo.service.dto.ShipperDTO;
import vn.containergo.service.dto.ShipperPersonDTO;

/**
 * Mapper for the entity {@link ShipperPerson} and its DTO {@link ShipperPersonDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShipperPersonMapper extends EntityMapper<ShipperPersonDTO, ShipperPerson> {
    @Mapping(target = "shipper", source = "shipper", qualifiedByName = "shipperId")
    ShipperPersonDTO toDto(ShipperPerson s);

    @Named("shipperId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ShipperDTO toDtoShipperId(Shipper shipper);
}
