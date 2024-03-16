package vn.containergo.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.containergo.domain.Shipper;
import vn.containergo.repository.ShipperRepository;
import vn.containergo.service.ShipperService;
import vn.containergo.service.dto.ShipperDTO;
import vn.containergo.service.mapper.ShipperMapper;

/**
 * Service Implementation for managing {@link vn.containergo.domain.Shipper}.
 */
@Service
public class ShipperServiceImpl implements ShipperService {

    private final Logger log = LoggerFactory.getLogger(ShipperServiceImpl.class);

    private final ShipperRepository shipperRepository;

    private final ShipperMapper shipperMapper;

    public ShipperServiceImpl(ShipperRepository shipperRepository, ShipperMapper shipperMapper) {
        this.shipperRepository = shipperRepository;
        this.shipperMapper = shipperMapper;
    }

    @Override
    public ShipperDTO save(ShipperDTO shipperDTO) {
        log.debug("Request to save Shipper : {}", shipperDTO);
        Shipper shipper = shipperMapper.toEntity(shipperDTO);
        shipper = shipperRepository.save(shipper);
        return shipperMapper.toDto(shipper);
    }

    @Override
    public ShipperDTO update(ShipperDTO shipperDTO) {
        log.debug("Request to update Shipper : {}", shipperDTO);
        Shipper shipper = shipperMapper.toEntity(shipperDTO);
        shipper = shipperRepository.save(shipper);
        return shipperMapper.toDto(shipper);
    }

    @Override
    public Optional<ShipperDTO> partialUpdate(ShipperDTO shipperDTO) {
        log.debug("Request to partially update Shipper : {}", shipperDTO);

        return shipperRepository
            .findById(shipperDTO.getId())
            .map(existingShipper -> {
                shipperMapper.partialUpdate(existingShipper, shipperDTO);

                return existingShipper;
            })
            .map(shipperRepository::save)
            .map(shipperMapper::toDto);
    }

    @Override
    public Page<ShipperDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Shippers");
        return shipperRepository.findAll(pageable).map(shipperMapper::toDto);
    }

    @Override
    public Optional<ShipperDTO> findOne(Long id) {
        log.debug("Request to get Shipper : {}", id);
        return shipperRepository.findById(id).map(shipperMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Shipper : {}", id);
        shipperRepository.deleteById(id);
    }
}
