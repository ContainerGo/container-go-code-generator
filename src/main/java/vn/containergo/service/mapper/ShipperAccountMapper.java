package vn.containergo.service.mapper;

import java.util.Objects;
import java.util.UUID;
import org.mapstruct.*;
import vn.containergo.domain.Shipper;
import vn.containergo.domain.ShipperAccount;
import vn.containergo.service.dto.ShipperAccountDTO;
import vn.containergo.service.dto.ShipperDTO;

/**
 * Mapper for the entity {@link ShipperAccount} and its DTO {@link ShipperAccountDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShipperAccountMapper extends EntityMapper<ShipperAccountDTO, ShipperAccount> {
    @Mapping(target = "shipper", source = "shipper", qualifiedByName = "shipperId")
    ShipperAccountDTO toDto(ShipperAccount s);

    @Named("shipperId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ShipperDTO toDtoShipperId(Shipper shipper);

    default String map(UUID value) {
        return Objects.toString(value, null);
    }
}
