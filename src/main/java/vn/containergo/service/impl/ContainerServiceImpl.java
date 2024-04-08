package vn.containergo.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.containergo.domain.Container;
import vn.containergo.repository.ContainerRepository;
import vn.containergo.service.ContainerService;
import vn.containergo.service.dto.ContainerDTO;
import vn.containergo.service.mapper.ContainerMapper;

/**
 * Service Implementation for managing {@link vn.containergo.domain.Container}.
 */
@Service
public class ContainerServiceImpl implements ContainerService {

    private final Logger log = LoggerFactory.getLogger(ContainerServiceImpl.class);

    private final ContainerRepository containerRepository;

    private final ContainerMapper containerMapper;

    public ContainerServiceImpl(ContainerRepository containerRepository, ContainerMapper containerMapper) {
        this.containerRepository = containerRepository;
        this.containerMapper = containerMapper;
    }

    @Override
    public ContainerDTO save(ContainerDTO containerDTO) {
        log.debug("Request to save Container : {}", containerDTO);
        Container container = containerMapper.toEntity(containerDTO);
        container = containerRepository.save(container);
        return containerMapper.toDto(container);
    }

    @Override
    public ContainerDTO update(ContainerDTO containerDTO) {
        log.debug("Request to update Container : {}", containerDTO);
        Container container = containerMapper.toEntity(containerDTO);
        container = containerRepository.save(container);
        return containerMapper.toDto(container);
    }

    @Override
    public Optional<ContainerDTO> partialUpdate(ContainerDTO containerDTO) {
        log.debug("Request to partially update Container : {}", containerDTO);

        return containerRepository
            .findById(containerDTO.getId())
            .map(existingContainer -> {
                containerMapper.partialUpdate(existingContainer, containerDTO);

                return existingContainer;
            })
            .map(containerRepository::save)
            .map(containerMapper::toDto);
    }

    @Override
    public Page<ContainerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Containers");
        return containerRepository.findAll(pageable).map(containerMapper::toDto);
    }

    @Override
    public Optional<ContainerDTO> findOne(UUID id) {
        log.debug("Request to get Container : {}", id);
        return containerRepository.findById(id).map(containerMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Container : {}", id);
        containerRepository.deleteById(id);
    }
}
