package vn.containergo.service.mapper;

import org.mapstruct.*;
import vn.containergo.domain.Ward;
import vn.containergo.service.dto.WardDTO;

/**
 * Mapper for the entity {@link Ward} and its DTO {@link WardDTO}.
 */
@Mapper(componentModel = "spring")
public interface WardMapper extends EntityMapper<WardDTO, Ward> {}
