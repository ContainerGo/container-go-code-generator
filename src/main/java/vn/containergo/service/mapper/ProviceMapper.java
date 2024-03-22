package vn.containergo.service.mapper;

import org.mapstruct.*;
import vn.containergo.domain.Provice;
import vn.containergo.service.dto.ProviceDTO;

/**
 * Mapper for the entity {@link Provice} and its DTO {@link ProviceDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProviceMapper extends EntityMapper<ProviceDTO, Provice> {}
