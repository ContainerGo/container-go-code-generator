package vn.containergo.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.containergo.domain.ShipmentPlan;
import vn.containergo.repository.ShipmentPlanRepository;
import vn.containergo.service.ShipmentPlanService;
import vn.containergo.service.dto.ShipmentPlanDTO;
import vn.containergo.service.mapper.ShipmentPlanMapper;

/**
 * Service Implementation for managing {@link vn.containergo.domain.ShipmentPlan}.
 */
@Service
public class ShipmentPlanServiceImpl implements ShipmentPlanService {

    private final Logger log = LoggerFactory.getLogger(ShipmentPlanServiceImpl.class);

    private final ShipmentPlanRepository shipmentPlanRepository;

    private final ShipmentPlanMapper shipmentPlanMapper;

    public ShipmentPlanServiceImpl(ShipmentPlanRepository shipmentPlanRepository, ShipmentPlanMapper shipmentPlanMapper) {
        this.shipmentPlanRepository = shipmentPlanRepository;
        this.shipmentPlanMapper = shipmentPlanMapper;
    }

    @Override
    public ShipmentPlanDTO save(ShipmentPlanDTO shipmentPlanDTO) {
        log.debug("Request to save ShipmentPlan : {}", shipmentPlanDTO);
        ShipmentPlan shipmentPlan = shipmentPlanMapper.toEntity(shipmentPlanDTO);
        shipmentPlan = shipmentPlanRepository.save(shipmentPlan);
        return shipmentPlanMapper.toDto(shipmentPlan);
    }

    @Override
    public ShipmentPlanDTO update(ShipmentPlanDTO shipmentPlanDTO) {
        log.debug("Request to update ShipmentPlan : {}", shipmentPlanDTO);
        ShipmentPlan shipmentPlan = shipmentPlanMapper.toEntity(shipmentPlanDTO);
        shipmentPlan = shipmentPlanRepository.save(shipmentPlan);
        return shipmentPlanMapper.toDto(shipmentPlan);
    }

    @Override
    public Optional<ShipmentPlanDTO> partialUpdate(ShipmentPlanDTO shipmentPlanDTO) {
        log.debug("Request to partially update ShipmentPlan : {}", shipmentPlanDTO);

        return shipmentPlanRepository
            .findById(shipmentPlanDTO.getId())
            .map(existingShipmentPlan -> {
                shipmentPlanMapper.partialUpdate(existingShipmentPlan, shipmentPlanDTO);

                return existingShipmentPlan;
            })
            .map(shipmentPlanRepository::save)
            .map(shipmentPlanMapper::toDto);
    }

    @Override
    public Page<ShipmentPlanDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShipmentPlans");
        return shipmentPlanRepository.findAll(pageable).map(shipmentPlanMapper::toDto);
    }

    @Override
    public Optional<ShipmentPlanDTO> findOne(UUID id) {
        log.debug("Request to get ShipmentPlan : {}", id);
        return shipmentPlanRepository.findById(id).map(shipmentPlanMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete ShipmentPlan : {}", id);
        shipmentPlanRepository.deleteById(id);
    }
}
