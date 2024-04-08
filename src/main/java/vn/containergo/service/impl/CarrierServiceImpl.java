package vn.containergo.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.containergo.domain.Carrier;
import vn.containergo.repository.CarrierRepository;
import vn.containergo.service.CarrierService;
import vn.containergo.service.dto.CarrierDTO;
import vn.containergo.service.mapper.CarrierMapper;

/**
 * Service Implementation for managing {@link vn.containergo.domain.Carrier}.
 */
@Service
public class CarrierServiceImpl implements CarrierService {

    private final Logger log = LoggerFactory.getLogger(CarrierServiceImpl.class);

    private final CarrierRepository carrierRepository;

    private final CarrierMapper carrierMapper;

    public CarrierServiceImpl(CarrierRepository carrierRepository, CarrierMapper carrierMapper) {
        this.carrierRepository = carrierRepository;
        this.carrierMapper = carrierMapper;
    }

    @Override
    public CarrierDTO save(CarrierDTO carrierDTO) {
        log.debug("Request to save Carrier : {}", carrierDTO);
        Carrier carrier = carrierMapper.toEntity(carrierDTO);
        carrier = carrierRepository.save(carrier);
        return carrierMapper.toDto(carrier);
    }

    @Override
    public CarrierDTO update(CarrierDTO carrierDTO) {
        log.debug("Request to update Carrier : {}", carrierDTO);
        Carrier carrier = carrierMapper.toEntity(carrierDTO);
        carrier = carrierRepository.save(carrier);
        return carrierMapper.toDto(carrier);
    }

    @Override
    public Optional<CarrierDTO> partialUpdate(CarrierDTO carrierDTO) {
        log.debug("Request to partially update Carrier : {}", carrierDTO);

        return carrierRepository
            .findById(carrierDTO.getId())
            .map(existingCarrier -> {
                carrierMapper.partialUpdate(existingCarrier, carrierDTO);

                return existingCarrier;
            })
            .map(carrierRepository::save)
            .map(carrierMapper::toDto);
    }

    @Override
    public Page<CarrierDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Carriers");
        return carrierRepository.findAll(pageable).map(carrierMapper::toDto);
    }

    @Override
    public Optional<CarrierDTO> findOne(UUID id) {
        log.debug("Request to get Carrier : {}", id);
        return carrierRepository.findById(id).map(carrierMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Carrier : {}", id);
        carrierRepository.deleteById(id);
    }
}
