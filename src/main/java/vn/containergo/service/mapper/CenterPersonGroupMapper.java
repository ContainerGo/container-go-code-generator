package vn.containergo.service.mapper;

import org.mapstruct.*;
import vn.containergo.domain.CenterPersonGroup;
import vn.containergo.service.dto.CenterPersonGroupDTO;

/**
 * Mapper for the entity {@link CenterPersonGroup} and its DTO {@link CenterPersonGroupDTO}.
 */
@Mapper(componentModel = "spring")
public interface CenterPersonGroupMapper extends EntityMapper<CenterPersonGroupDTO, CenterPersonGroup> {}
