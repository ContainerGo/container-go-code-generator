package vn.containergo.service.mapper;

import org.mapstruct.*;
import vn.containergo.domain.ContainerType;
import vn.containergo.service.dto.ContainerTypeDTO;

/**
 * Mapper for the entity {@link ContainerType} and its DTO {@link ContainerTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContainerTypeMapper extends EntityMapper<ContainerTypeDTO, ContainerType> {}
