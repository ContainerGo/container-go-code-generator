package vn.containergo.service.mapper;

import org.mapstruct.*;
import vn.containergo.domain.ShipperPersonGroup;
import vn.containergo.service.dto.ShipperPersonGroupDTO;

/**
 * Mapper for the entity {@link ShipperPersonGroup} and its DTO {@link ShipperPersonGroupDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShipperPersonGroupMapper extends EntityMapper<ShipperPersonGroupDTO, ShipperPersonGroup> {}
