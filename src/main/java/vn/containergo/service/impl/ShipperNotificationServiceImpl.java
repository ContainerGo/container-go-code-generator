package vn.containergo.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.containergo.domain.ShipperNotification;
import vn.containergo.repository.ShipperNotificationRepository;
import vn.containergo.service.ShipperNotificationService;
import vn.containergo.service.dto.ShipperNotificationDTO;
import vn.containergo.service.mapper.ShipperNotificationMapper;

/**
 * Service Implementation for managing {@link vn.containergo.domain.ShipperNotification}.
 */
@Service
public class ShipperNotificationServiceImpl implements ShipperNotificationService {

    private final Logger log = LoggerFactory.getLogger(ShipperNotificationServiceImpl.class);

    private final ShipperNotificationRepository shipperNotificationRepository;

    private final ShipperNotificationMapper shipperNotificationMapper;

    public ShipperNotificationServiceImpl(
        ShipperNotificationRepository shipperNotificationRepository,
        ShipperNotificationMapper shipperNotificationMapper
    ) {
        this.shipperNotificationRepository = shipperNotificationRepository;
        this.shipperNotificationMapper = shipperNotificationMapper;
    }

    @Override
    public ShipperNotificationDTO save(ShipperNotificationDTO shipperNotificationDTO) {
        log.debug("Request to save ShipperNotification : {}", shipperNotificationDTO);
        ShipperNotification shipperNotification = shipperNotificationMapper.toEntity(shipperNotificationDTO);
        shipperNotification = shipperNotificationRepository.save(shipperNotification);
        return shipperNotificationMapper.toDto(shipperNotification);
    }

    @Override
    public ShipperNotificationDTO update(ShipperNotificationDTO shipperNotificationDTO) {
        log.debug("Request to update ShipperNotification : {}", shipperNotificationDTO);
        ShipperNotification shipperNotification = shipperNotificationMapper.toEntity(shipperNotificationDTO);
        shipperNotification = shipperNotificationRepository.save(shipperNotification);
        return shipperNotificationMapper.toDto(shipperNotification);
    }

    @Override
    public Optional<ShipperNotificationDTO> partialUpdate(ShipperNotificationDTO shipperNotificationDTO) {
        log.debug("Request to partially update ShipperNotification : {}", shipperNotificationDTO);

        return shipperNotificationRepository
            .findById(shipperNotificationDTO.getId())
            .map(existingShipperNotification -> {
                shipperNotificationMapper.partialUpdate(existingShipperNotification, shipperNotificationDTO);

                return existingShipperNotification;
            })
            .map(shipperNotificationRepository::save)
            .map(shipperNotificationMapper::toDto);
    }

    @Override
    public Page<ShipperNotificationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShipperNotifications");
        return shipperNotificationRepository.findAll(pageable).map(shipperNotificationMapper::toDto);
    }

    @Override
    public Optional<ShipperNotificationDTO> findOne(UUID id) {
        log.debug("Request to get ShipperNotification : {}", id);
        return shipperNotificationRepository.findById(id).map(shipperNotificationMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete ShipperNotification : {}", id);
        shipperNotificationRepository.deleteById(id);
    }
}
