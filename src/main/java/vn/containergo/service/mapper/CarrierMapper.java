package vn.containergo.service.mapper;

import org.mapstruct.*;
import vn.containergo.domain.Carrier;
import vn.containergo.service.dto.CarrierDTO;

/**
 * Mapper for the entity {@link Carrier} and its DTO {@link CarrierDTO}.
 */
@Mapper(componentModel = "spring")
public interface CarrierMapper extends EntityMapper<CarrierDTO, Carrier> {}
