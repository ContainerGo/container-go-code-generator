package vn.containergo.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.containergo.domain.Truck;
import vn.containergo.repository.TruckRepository;
import vn.containergo.service.TruckService;
import vn.containergo.service.dto.TruckDTO;
import vn.containergo.service.mapper.TruckMapper;

/**
 * Service Implementation for managing {@link vn.containergo.domain.Truck}.
 */
@Service
public class TruckServiceImpl implements TruckService {

    private final Logger log = LoggerFactory.getLogger(TruckServiceImpl.class);

    private final TruckRepository truckRepository;

    private final TruckMapper truckMapper;

    public TruckServiceImpl(TruckRepository truckRepository, TruckMapper truckMapper) {
        this.truckRepository = truckRepository;
        this.truckMapper = truckMapper;
    }

    @Override
    public TruckDTO save(TruckDTO truckDTO) {
        log.debug("Request to save Truck : {}", truckDTO);
        Truck truck = truckMapper.toEntity(truckDTO);
        truck = truckRepository.save(truck);
        return truckMapper.toDto(truck);
    }

    @Override
    public TruckDTO update(TruckDTO truckDTO) {
        log.debug("Request to update Truck : {}", truckDTO);
        Truck truck = truckMapper.toEntity(truckDTO);
        truck = truckRepository.save(truck);
        return truckMapper.toDto(truck);
    }

    @Override
    public Optional<TruckDTO> partialUpdate(TruckDTO truckDTO) {
        log.debug("Request to partially update Truck : {}", truckDTO);

        return truckRepository
            .findById(truckDTO.getId())
            .map(existingTruck -> {
                truckMapper.partialUpdate(existingTruck, truckDTO);

                return existingTruck;
            })
            .map(truckRepository::save)
            .map(truckMapper::toDto);
    }

    @Override
    public Page<TruckDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Trucks");
        return truckRepository.findAll(pageable).map(truckMapper::toDto);
    }

    @Override
    public Optional<TruckDTO> findOne(UUID id) {
        log.debug("Request to get Truck : {}", id);
        return truckRepository.findById(id).map(truckMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Truck : {}", id);
        truckRepository.deleteById(id);
    }
}
