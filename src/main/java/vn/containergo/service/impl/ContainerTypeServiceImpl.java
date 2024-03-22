package vn.containergo.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.containergo.domain.ContainerType;
import vn.containergo.repository.ContainerTypeRepository;
import vn.containergo.service.ContainerTypeService;
import vn.containergo.service.dto.ContainerTypeDTO;
import vn.containergo.service.mapper.ContainerTypeMapper;

/**
 * Service Implementation for managing {@link vn.containergo.domain.ContainerType}.
 */
@Service
public class ContainerTypeServiceImpl implements ContainerTypeService {

    private final Logger log = LoggerFactory.getLogger(ContainerTypeServiceImpl.class);

    private final ContainerTypeRepository containerTypeRepository;

    private final ContainerTypeMapper containerTypeMapper;

    public ContainerTypeServiceImpl(ContainerTypeRepository containerTypeRepository, ContainerTypeMapper containerTypeMapper) {
        this.containerTypeRepository = containerTypeRepository;
        this.containerTypeMapper = containerTypeMapper;
    }

    @Override
    public ContainerTypeDTO save(ContainerTypeDTO containerTypeDTO) {
        log.debug("Request to save ContainerType : {}", containerTypeDTO);
        ContainerType containerType = containerTypeMapper.toEntity(containerTypeDTO);
        containerType = containerTypeRepository.save(containerType);
        return containerTypeMapper.toDto(containerType);
    }

    @Override
    public ContainerTypeDTO update(ContainerTypeDTO containerTypeDTO) {
        log.debug("Request to update ContainerType : {}", containerTypeDTO);
        ContainerType containerType = containerTypeMapper.toEntity(containerTypeDTO);
        containerType = containerTypeRepository.save(containerType);
        return containerTypeMapper.toDto(containerType);
    }

    @Override
    public Optional<ContainerTypeDTO> partialUpdate(ContainerTypeDTO containerTypeDTO) {
        log.debug("Request to partially update ContainerType : {}", containerTypeDTO);

        return containerTypeRepository
            .findById(containerTypeDTO.getId())
            .map(existingContainerType -> {
                containerTypeMapper.partialUpdate(existingContainerType, containerTypeDTO);

                return existingContainerType;
            })
            .map(containerTypeRepository::save)
            .map(containerTypeMapper::toDto);
    }

    @Override
    public Page<ContainerTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContainerTypes");
        return containerTypeRepository.findAll(pageable).map(containerTypeMapper::toDto);
    }

    @Override
    public Optional<ContainerTypeDTO> findOne(Long id) {
        log.debug("Request to get ContainerType : {}", id);
        return containerTypeRepository.findById(id).map(containerTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContainerType : {}", id);
        containerTypeRepository.deleteById(id);
    }
}
