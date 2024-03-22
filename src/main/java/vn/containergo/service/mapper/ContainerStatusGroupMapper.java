package vn.containergo.service.mapper;

import org.mapstruct.*;
import vn.containergo.domain.ContainerStatusGroup;
import vn.containergo.service.dto.ContainerStatusGroupDTO;

/**
 * Mapper for the entity {@link ContainerStatusGroup} and its DTO {@link ContainerStatusGroupDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContainerStatusGroupMapper extends EntityMapper<ContainerStatusGroupDTO, ContainerStatusGroup> {}
