package vn.containergo.service.mapper;

import java.util.Objects;
import java.util.UUID;
import org.mapstruct.*;
import vn.containergo.domain.Container;
import vn.containergo.domain.ShipmentPlan;
import vn.containergo.service.dto.ContainerDTO;
import vn.containergo.service.dto.ShipmentPlanDTO;

/**
 * Mapper for the entity {@link ShipmentPlan} and its DTO {@link ShipmentPlanDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShipmentPlanMapper extends EntityMapper<ShipmentPlanDTO, ShipmentPlan> {
    @Mapping(target = "container", source = "container", qualifiedByName = "containerId")
    ShipmentPlanDTO toDto(ShipmentPlan s);

    @Named("containerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ContainerDTO toDtoContainerId(Container container);

    default String map(UUID value) {
        return Objects.toString(value, null);
    }
}
