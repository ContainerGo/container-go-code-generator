package vn.containergo.service.mapper;

import org.mapstruct.*;
import vn.containergo.domain.Truck;
import vn.containergo.service.dto.TruckDTO;

/**
 * Mapper for the entity {@link Truck} and its DTO {@link TruckDTO}.
 */
@Mapper(componentModel = "spring")
public interface TruckMapper extends EntityMapper<TruckDTO, Truck> {}
