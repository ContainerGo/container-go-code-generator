package vn.containergo.service.mapper;

import org.mapstruct.*;
import vn.containergo.domain.ContainerStatus;
import vn.containergo.domain.ContainerStatusGroup;
import vn.containergo.service.dto.ContainerStatusDTO;
import vn.containergo.service.dto.ContainerStatusGroupDTO;

/**
 * Mapper for the entity {@link ContainerStatus} and its DTO {@link ContainerStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContainerStatusMapper extends EntityMapper<ContainerStatusDTO, ContainerStatus> {
    @Mapping(target = "group", source = "group", qualifiedByName = "containerStatusGroupId")
    ContainerStatusDTO toDto(ContainerStatus s);

    @Named("containerStatusGroupId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ContainerStatusGroupDTO toDtoContainerStatusGroupId(ContainerStatusGroup containerStatusGroup);
}
