package vn.containergo.service.mapper;

import org.mapstruct.*;
import vn.containergo.domain.Container;
import vn.containergo.domain.Offer;
import vn.containergo.service.dto.ContainerDTO;
import vn.containergo.service.dto.OfferDTO;

/**
 * Mapper for the entity {@link Offer} and its DTO {@link OfferDTO}.
 */
@Mapper(componentModel = "spring")
public interface OfferMapper extends EntityMapper<OfferDTO, Offer> {
    @Mapping(target = "container", source = "container", qualifiedByName = "containerId")
    OfferDTO toDto(Offer s);

    @Named("containerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ContainerDTO toDtoContainerId(Container container);
}
