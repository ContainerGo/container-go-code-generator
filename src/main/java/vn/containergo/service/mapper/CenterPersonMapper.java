package vn.containergo.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import vn.containergo.domain.CenterPerson;
import vn.containergo.domain.CenterPersonGroup;
import vn.containergo.service.dto.CenterPersonDTO;
import vn.containergo.service.dto.CenterPersonGroupDTO;

/**
 * Mapper for the entity {@link CenterPerson} and its DTO {@link CenterPersonDTO}.
 */
@Mapper(componentModel = "spring")
public interface CenterPersonMapper extends EntityMapper<CenterPersonDTO, CenterPerson> {
    @Mapping(target = "groups", source = "groups", qualifiedByName = "centerPersonGroupIdSet")
    CenterPersonDTO toDto(CenterPerson s);

    @Mapping(target = "removeGroups", ignore = true)
    CenterPerson toEntity(CenterPersonDTO centerPersonDTO);

    @Named("centerPersonGroupId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CenterPersonGroupDTO toDtoCenterPersonGroupId(CenterPersonGroup centerPersonGroup);

    @Named("centerPersonGroupIdSet")
    default Set<CenterPersonGroupDTO> toDtoCenterPersonGroupIdSet(Set<CenterPersonGroup> centerPersonGroup) {
        return centerPersonGroup.stream().map(this::toDtoCenterPersonGroupId).collect(Collectors.toSet());
    }
}
