package vn.containergo.service.mapper;

import java.util.Objects;
import java.util.UUID;
import org.mapstruct.*;
import vn.containergo.domain.Container;
import vn.containergo.domain.ShipmentHistory;
import vn.containergo.service.dto.ContainerDTO;
import vn.containergo.service.dto.ShipmentHistoryDTO;

/**
 * Mapper for the entity {@link ShipmentHistory} and its DTO {@link ShipmentHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShipmentHistoryMapper extends EntityMapper<ShipmentHistoryDTO, ShipmentHistory> {
    @Mapping(target = "container", source = "container", qualifiedByName = "containerId")
    ShipmentHistoryDTO toDto(ShipmentHistory s);

    @Named("containerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ContainerDTO toDtoContainerId(Container container);

    default String map(UUID value) {
        return Objects.toString(value, null);
    }
}
