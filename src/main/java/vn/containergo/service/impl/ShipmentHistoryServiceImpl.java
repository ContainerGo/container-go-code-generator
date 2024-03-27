package vn.containergo.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.containergo.domain.ShipmentHistory;
import vn.containergo.repository.ShipmentHistoryRepository;
import vn.containergo.service.ShipmentHistoryService;
import vn.containergo.service.dto.ShipmentHistoryDTO;
import vn.containergo.service.mapper.ShipmentHistoryMapper;

/**
 * Service Implementation for managing {@link vn.containergo.domain.ShipmentHistory}.
 */
@Service
public class ShipmentHistoryServiceImpl implements ShipmentHistoryService {

    private final Logger log = LoggerFactory.getLogger(ShipmentHistoryServiceImpl.class);

    private final ShipmentHistoryRepository shipmentHistoryRepository;

    private final ShipmentHistoryMapper shipmentHistoryMapper;

    public ShipmentHistoryServiceImpl(ShipmentHistoryRepository shipmentHistoryRepository, ShipmentHistoryMapper shipmentHistoryMapper) {
        this.shipmentHistoryRepository = shipmentHistoryRepository;
        this.shipmentHistoryMapper = shipmentHistoryMapper;
    }

    @Override
    public ShipmentHistoryDTO save(ShipmentHistoryDTO shipmentHistoryDTO) {
        log.debug("Request to save ShipmentHistory : {}", shipmentHistoryDTO);
        ShipmentHistory shipmentHistory = shipmentHistoryMapper.toEntity(shipmentHistoryDTO);
        shipmentHistory = shipmentHistoryRepository.save(shipmentHistory);
        return shipmentHistoryMapper.toDto(shipmentHistory);
    }

    @Override
    public ShipmentHistoryDTO update(ShipmentHistoryDTO shipmentHistoryDTO) {
        log.debug("Request to update ShipmentHistory : {}", shipmentHistoryDTO);
        ShipmentHistory shipmentHistory = shipmentHistoryMapper.toEntity(shipmentHistoryDTO);
        shipmentHistory = shipmentHistoryRepository.save(shipmentHistory);
        return shipmentHistoryMapper.toDto(shipmentHistory);
    }

    @Override
    public Optional<ShipmentHistoryDTO> partialUpdate(ShipmentHistoryDTO shipmentHistoryDTO) {
        log.debug("Request to partially update ShipmentHistory : {}", shipmentHistoryDTO);

        return shipmentHistoryRepository
            .findById(shipmentHistoryDTO.getId())
            .map(existingShipmentHistory -> {
                shipmentHistoryMapper.partialUpdate(existingShipmentHistory, shipmentHistoryDTO);

                return existingShipmentHistory;
            })
            .map(shipmentHistoryRepository::save)
            .map(shipmentHistoryMapper::toDto);
    }

    @Override
    public Page<ShipmentHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShipmentHistories");
        return shipmentHistoryRepository.findAll(pageable).map(shipmentHistoryMapper::toDto);
    }

    @Override
    public Optional<ShipmentHistoryDTO> findOne(Long id) {
        log.debug("Request to get ShipmentHistory : {}", id);
        return shipmentHistoryRepository.findById(id).map(shipmentHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShipmentHistory : {}", id);
        shipmentHistoryRepository.deleteById(id);
    }
}
