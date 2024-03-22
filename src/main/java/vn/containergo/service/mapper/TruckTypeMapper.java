package vn.containergo.service.mapper;

import org.mapstruct.*;
import vn.containergo.domain.TruckType;
import vn.containergo.service.dto.TruckTypeDTO;

/**
 * Mapper for the entity {@link TruckType} and its DTO {@link TruckTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface TruckTypeMapper extends EntityMapper<TruckTypeDTO, TruckType> {}
