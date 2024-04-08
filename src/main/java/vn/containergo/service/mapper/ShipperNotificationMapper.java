package vn.containergo.service.mapper;

import java.util.Objects;
import java.util.UUID;
import org.mapstruct.*;
import vn.containergo.domain.ShipperNotification;
import vn.containergo.domain.ShipperPerson;
import vn.containergo.service.dto.ShipperNotificationDTO;
import vn.containergo.service.dto.ShipperPersonDTO;

/**
 * Mapper for the entity {@link ShipperNotification} and its DTO {@link ShipperNotificationDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShipperNotificationMapper extends EntityMapper<ShipperNotificationDTO, ShipperNotification> {
    @Mapping(target = "person", source = "person", qualifiedByName = "shipperPersonId")
    ShipperNotificationDTO toDto(ShipperNotification s);

    @Named("shipperPersonId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ShipperPersonDTO toDtoShipperPersonId(ShipperPerson shipperPerson);

    default String map(UUID value) {
        return Objects.toString(value, null);
    }
}
