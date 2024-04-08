package vn.containergo.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.containergo.domain.ContainerStatus;
import vn.containergo.repository.ContainerStatusRepository;
import vn.containergo.service.ContainerStatusService;
import vn.containergo.service.dto.ContainerStatusDTO;
import vn.containergo.service.mapper.ContainerStatusMapper;

/**
 * Service Implementation for managing {@link vn.containergo.domain.ContainerStatus}.
 */
@Service
public class ContainerStatusServiceImpl implements ContainerStatusService {

    private final Logger log = LoggerFactory.getLogger(ContainerStatusServiceImpl.class);

    private final ContainerStatusRepository containerStatusRepository;

    private final ContainerStatusMapper containerStatusMapper;

    public ContainerStatusServiceImpl(ContainerStatusRepository containerStatusRepository, ContainerStatusMapper containerStatusMapper) {
        this.containerStatusRepository = containerStatusRepository;
        this.containerStatusMapper = containerStatusMapper;
    }

    @Override
    public ContainerStatusDTO save(ContainerStatusDTO containerStatusDTO) {
        log.debug("Request to save ContainerStatus : {}", containerStatusDTO);
        ContainerStatus containerStatus = containerStatusMapper.toEntity(containerStatusDTO);
        containerStatus = containerStatusRepository.save(containerStatus);
        return containerStatusMapper.toDto(containerStatus);
    }

    @Override
    public ContainerStatusDTO update(ContainerStatusDTO containerStatusDTO) {
        log.debug("Request to update ContainerStatus : {}", containerStatusDTO);
        ContainerStatus containerStatus = containerStatusMapper.toEntity(containerStatusDTO);
        containerStatus = containerStatusRepository.save(containerStatus);
        return containerStatusMapper.toDto(containerStatus);
    }

    @Override
    public Optional<ContainerStatusDTO> partialUpdate(ContainerStatusDTO containerStatusDTO) {
        log.debug("Request to partially update ContainerStatus : {}", containerStatusDTO);

        return containerStatusRepository
            .findById(containerStatusDTO.getId())
            .map(existingContainerStatus -> {
                containerStatusMapper.partialUpdate(existingContainerStatus, containerStatusDTO);

                return existingContainerStatus;
            })
            .map(containerStatusRepository::save)
            .map(containerStatusMapper::toDto);
    }

    @Override
    public Page<ContainerStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContainerStatuses");
        return containerStatusRepository.findAll(pageable).map(containerStatusMapper::toDto);
    }

    @Override
    public Optional<ContainerStatusDTO> findOne(UUID id) {
        log.debug("Request to get ContainerStatus : {}", id);
        return containerStatusRepository.findById(id).map(containerStatusMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete ContainerStatus : {}", id);
        containerStatusRepository.deleteById(id);
    }
}
