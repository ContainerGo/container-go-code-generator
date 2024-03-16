package vn.containergo.service.mapper;

import org.mapstruct.*;
import vn.containergo.domain.Shipper;
import vn.containergo.service.dto.ShipperDTO;

/**
 * Mapper for the entity {@link Shipper} and its DTO {@link ShipperDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShipperMapper extends EntityMapper<ShipperDTO, Shipper> {}
