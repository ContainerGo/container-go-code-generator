package vn.containergo.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.containergo.domain.ShipperPersonGroup;
import vn.containergo.repository.ShipperPersonGroupRepository;
import vn.containergo.service.ShipperPersonGroupService;
import vn.containergo.service.dto.ShipperPersonGroupDTO;
import vn.containergo.service.mapper.ShipperPersonGroupMapper;

/**
 * Service Implementation for managing {@link vn.containergo.domain.ShipperPersonGroup}.
 */
@Service
public class ShipperPersonGroupServiceImpl implements ShipperPersonGroupService {

    private final Logger log = LoggerFactory.getLogger(ShipperPersonGroupServiceImpl.class);

    private final ShipperPersonGroupRepository shipperPersonGroupRepository;

    private final ShipperPersonGroupMapper shipperPersonGroupMapper;

    public ShipperPersonGroupServiceImpl(
        ShipperPersonGroupRepository shipperPersonGroupRepository,
        ShipperPersonGroupMapper shipperPersonGroupMapper
    ) {
        this.shipperPersonGroupRepository = shipperPersonGroupRepository;
        this.shipperPersonGroupMapper = shipperPersonGroupMapper;
    }

    @Override
    public ShipperPersonGroupDTO save(ShipperPersonGroupDTO shipperPersonGroupDTO) {
        log.debug("Request to save ShipperPersonGroup : {}", shipperPersonGroupDTO);
        ShipperPersonGroup shipperPersonGroup = shipperPersonGroupMapper.toEntity(shipperPersonGroupDTO);
        shipperPersonGroup = shipperPersonGroupRepository.save(shipperPersonGroup);
        return shipperPersonGroupMapper.toDto(shipperPersonGroup);
    }

    @Override
    public ShipperPersonGroupDTO update(ShipperPersonGroupDTO shipperPersonGroupDTO) {
        log.debug("Request to update ShipperPersonGroup : {}", shipperPersonGroupDTO);
        ShipperPersonGroup shipperPersonGroup = shipperPersonGroupMapper.toEntity(shipperPersonGroupDTO);
        shipperPersonGroup = shipperPersonGroupRepository.save(shipperPersonGroup);
        return shipperPersonGroupMapper.toDto(shipperPersonGroup);
    }

    @Override
    public Optional<ShipperPersonGroupDTO> partialUpdate(ShipperPersonGroupDTO shipperPersonGroupDTO) {
        log.debug("Request to partially update ShipperPersonGroup : {}", shipperPersonGroupDTO);

        return shipperPersonGroupRepository
            .findById(shipperPersonGroupDTO.getId())
            .map(existingShipperPersonGroup -> {
                shipperPersonGroupMapper.partialUpdate(existingShipperPersonGroup, shipperPersonGroupDTO);

                return existingShipperPersonGroup;
            })
            .map(shipperPersonGroupRepository::save)
            .map(shipperPersonGroupMapper::toDto);
    }

    @Override
    public Page<ShipperPersonGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShipperPersonGroups");
        return shipperPersonGroupRepository.findAll(pageable).map(shipperPersonGroupMapper::toDto);
    }

    @Override
    public Optional<ShipperPersonGroupDTO> findOne(UUID id) {
        log.debug("Request to get ShipperPersonGroup : {}", id);
        return shipperPersonGroupRepository.findById(id).map(shipperPersonGroupMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete ShipperPersonGroup : {}", id);
        shipperPersonGroupRepository.deleteById(id);
    }
}
