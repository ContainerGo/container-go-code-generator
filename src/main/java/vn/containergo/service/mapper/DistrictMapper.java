package vn.containergo.service.mapper;

import org.mapstruct.*;
import vn.containergo.domain.District;
import vn.containergo.service.dto.DistrictDTO;

/**
 * Mapper for the entity {@link District} and its DTO {@link DistrictDTO}.
 */
@Mapper(componentModel = "spring")
public interface DistrictMapper extends EntityMapper<DistrictDTO, District> {}
