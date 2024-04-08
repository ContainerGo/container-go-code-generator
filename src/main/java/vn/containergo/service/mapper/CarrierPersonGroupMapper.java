package vn.containergo.service.mapper;

import org.mapstruct.*;
import vn.containergo.domain.CarrierPersonGroup;
import vn.containergo.service.dto.CarrierPersonGroupDTO;

/**
 * Mapper for the entity {@link CarrierPersonGroup} and its DTO {@link CarrierPersonGroupDTO}.
 */
@Mapper(componentModel = "spring")
public interface CarrierPersonGroupMapper extends EntityMapper<CarrierPersonGroupDTO, CarrierPersonGroup> {}
