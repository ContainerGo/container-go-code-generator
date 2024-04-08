package vn.containergo.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.containergo.domain.CarrierPersonGroup;
import vn.containergo.repository.CarrierPersonGroupRepository;
import vn.containergo.service.CarrierPersonGroupService;
import vn.containergo.service.dto.CarrierPersonGroupDTO;
import vn.containergo.service.mapper.CarrierPersonGroupMapper;

/**
 * Service Implementation for managing {@link vn.containergo.domain.CarrierPersonGroup}.
 */
@Service
public class CarrierPersonGroupServiceImpl implements CarrierPersonGroupService {

    private final Logger log = LoggerFactory.getLogger(CarrierPersonGroupServiceImpl.class);

    private final CarrierPersonGroupRepository carrierPersonGroupRepository;

    private final CarrierPersonGroupMapper carrierPersonGroupMapper;

    public CarrierPersonGroupServiceImpl(
        CarrierPersonGroupRepository carrierPersonGroupRepository,
        CarrierPersonGroupMapper carrierPersonGroupMapper
    ) {
        this.carrierPersonGroupRepository = carrierPersonGroupRepository;
        this.carrierPersonGroupMapper = carrierPersonGroupMapper;
    }

    @Override
    public CarrierPersonGroupDTO save(CarrierPersonGroupDTO carrierPersonGroupDTO) {
        log.debug("Request to save CarrierPersonGroup : {}", carrierPersonGroupDTO);
        CarrierPersonGroup carrierPersonGroup = carrierPersonGroupMapper.toEntity(carrierPersonGroupDTO);
        carrierPersonGroup = carrierPersonGroupRepository.save(carrierPersonGroup);
        return carrierPersonGroupMapper.toDto(carrierPersonGroup);
    }

    @Override
    public CarrierPersonGroupDTO update(CarrierPersonGroupDTO carrierPersonGroupDTO) {
        log.debug("Request to update CarrierPersonGroup : {}", carrierPersonGroupDTO);
        CarrierPersonGroup carrierPersonGroup = carrierPersonGroupMapper.toEntity(carrierPersonGroupDTO);
        carrierPersonGroup = carrierPersonGroupRepository.save(carrierPersonGroup);
        return carrierPersonGroupMapper.toDto(carrierPersonGroup);
    }

    @Override
    public Optional<CarrierPersonGroupDTO> partialUpdate(CarrierPersonGroupDTO carrierPersonGroupDTO) {
        log.debug("Request to partially update CarrierPersonGroup : {}", carrierPersonGroupDTO);

        return carrierPersonGroupRepository
            .findById(carrierPersonGroupDTO.getId())
            .map(existingCarrierPersonGroup -> {
                carrierPersonGroupMapper.partialUpdate(existingCarrierPersonGroup, carrierPersonGroupDTO);

                return existingCarrierPersonGroup;
            })
            .map(carrierPersonGroupRepository::save)
            .map(carrierPersonGroupMapper::toDto);
    }

    @Override
    public Page<CarrierPersonGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CarrierPersonGroups");
        return carrierPersonGroupRepository.findAll(pageable).map(carrierPersonGroupMapper::toDto);
    }

    @Override
    public Optional<CarrierPersonGroupDTO> findOne(UUID id) {
        log.debug("Request to get CarrierPersonGroup : {}", id);
        return carrierPersonGroupRepository.findById(id).map(carrierPersonGroupMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete CarrierPersonGroup : {}", id);
        carrierPersonGroupRepository.deleteById(id);
    }
}
