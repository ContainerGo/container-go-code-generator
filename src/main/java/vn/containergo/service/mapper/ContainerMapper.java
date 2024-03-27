package vn.containergo.service.mapper;

import org.mapstruct.*;
import vn.containergo.domain.Container;
import vn.containergo.domain.ContainerOwner;
import vn.containergo.domain.ContainerStatus;
import vn.containergo.domain.ContainerType;
import vn.containergo.domain.District;
import vn.containergo.domain.Provice;
import vn.containergo.domain.Truck;
import vn.containergo.domain.TruckType;
import vn.containergo.domain.Ward;
import vn.containergo.service.dto.ContainerDTO;
import vn.containergo.service.dto.ContainerOwnerDTO;
import vn.containergo.service.dto.ContainerStatusDTO;
import vn.containergo.service.dto.ContainerTypeDTO;
import vn.containergo.service.dto.DistrictDTO;
import vn.containergo.service.dto.ProviceDTO;
import vn.containergo.service.dto.TruckDTO;
import vn.containergo.service.dto.TruckTypeDTO;
import vn.containergo.service.dto.WardDTO;

/**
 * Mapper for the entity {@link Container} and its DTO {@link ContainerDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContainerMapper extends EntityMapper<ContainerDTO, Container> {
    @Mapping(target = "pickupProvice", source = "pickupProvice", qualifiedByName = "proviceId")
    @Mapping(target = "pickupDistrict", source = "pickupDistrict", qualifiedByName = "districtId")
    @Mapping(target = "pickupWard", source = "pickupWard", qualifiedByName = "wardId")
    @Mapping(target = "dropoffProvice", source = "dropoffProvice", qualifiedByName = "proviceId")
    @Mapping(target = "dropoffDistrict", source = "dropoffDistrict", qualifiedByName = "districtId")
    @Mapping(target = "dropoffWard", source = "dropoffWard", qualifiedByName = "wardId")
    @Mapping(target = "type", source = "type", qualifiedByName = "containerTypeId")
    @Mapping(target = "status", source = "status", qualifiedByName = "containerStatusId")
    @Mapping(target = "truckType", source = "truckType", qualifiedByName = "truckTypeId")
    @Mapping(target = "truck", source = "truck", qualifiedByName = "truckId")
    @Mapping(target = "owner", source = "owner", qualifiedByName = "containerOwnerId")
    ContainerDTO toDto(Container s);

    @Named("proviceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProviceDTO toDtoProviceId(Provice provice);

    @Named("districtId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DistrictDTO toDtoDistrictId(District district);

    @Named("wardId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WardDTO toDtoWardId(Ward ward);

    @Named("containerTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ContainerTypeDTO toDtoContainerTypeId(ContainerType containerType);

    @Named("containerStatusId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ContainerStatusDTO toDtoContainerStatusId(ContainerStatus containerStatus);

    @Named("truckTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TruckTypeDTO toDtoTruckTypeId(TruckType truckType);

    @Named("truckId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TruckDTO toDtoTruckId(Truck truck);

    @Named("containerOwnerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ContainerOwnerDTO toDtoContainerOwnerId(ContainerOwner containerOwner);
}
