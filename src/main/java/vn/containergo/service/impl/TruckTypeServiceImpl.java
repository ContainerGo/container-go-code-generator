package vn.containergo.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.containergo.domain.TruckType;
import vn.containergo.repository.TruckTypeRepository;
import vn.containergo.service.TruckTypeService;
import vn.containergo.service.dto.TruckTypeDTO;
import vn.containergo.service.mapper.TruckTypeMapper;

/**
 * Service Implementation for managing {@link vn.containergo.domain.TruckType}.
 */
@Service
public class TruckTypeServiceImpl implements TruckTypeService {

    private final Logger log = LoggerFactory.getLogger(TruckTypeServiceImpl.class);

    private final TruckTypeRepository truckTypeRepository;

    private final TruckTypeMapper truckTypeMapper;

    public TruckTypeServiceImpl(TruckTypeRepository truckTypeRepository, TruckTypeMapper truckTypeMapper) {
        this.truckTypeRepository = truckTypeRepository;
        this.truckTypeMapper = truckTypeMapper;
    }

    @Override
    public TruckTypeDTO save(TruckTypeDTO truckTypeDTO) {
        log.debug("Request to save TruckType : {}", truckTypeDTO);
        TruckType truckType = truckTypeMapper.toEntity(truckTypeDTO);
        truckType = truckTypeRepository.save(truckType);
        return truckTypeMapper.toDto(truckType);
    }

    @Override
    public TruckTypeDTO update(TruckTypeDTO truckTypeDTO) {
        log.debug("Request to update TruckType : {}", truckTypeDTO);
        TruckType truckType = truckTypeMapper.toEntity(truckTypeDTO);
        truckType = truckTypeRepository.save(truckType);
        return truckTypeMapper.toDto(truckType);
    }

    @Override
    public Optional<TruckTypeDTO> partialUpdate(TruckTypeDTO truckTypeDTO) {
        log.debug("Request to partially update TruckType : {}", truckTypeDTO);

        return truckTypeRepository
            .findById(truckTypeDTO.getId())
            .map(existingTruckType -> {
                truckTypeMapper.partialUpdate(existingTruckType, truckTypeDTO);

                return existingTruckType;
            })
            .map(truckTypeRepository::save)
            .map(truckTypeMapper::toDto);
    }

    @Override
    public Page<TruckTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TruckTypes");
        return truckTypeRepository.findAll(pageable).map(truckTypeMapper::toDto);
    }

    @Override
    public Optional<TruckTypeDTO> findOne(UUID id) {
        log.debug("Request to get TruckType : {}", id);
        return truckTypeRepository.findById(id).map(truckTypeMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete TruckType : {}", id);
        truckTypeRepository.deleteById(id);
    }
}
