package vn.containergo.service.mapper;

import org.mapstruct.*;
import vn.containergo.domain.ContainerOwner;
import vn.containergo.service.dto.ContainerOwnerDTO;

/**
 * Mapper for the entity {@link ContainerOwner} and its DTO {@link ContainerOwnerDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContainerOwnerMapper extends EntityMapper<ContainerOwnerDTO, ContainerOwner> {}
