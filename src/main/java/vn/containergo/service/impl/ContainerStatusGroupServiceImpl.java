package vn.containergo.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.containergo.domain.ContainerStatusGroup;
import vn.containergo.repository.ContainerStatusGroupRepository;
import vn.containergo.service.ContainerStatusGroupService;
import vn.containergo.service.dto.ContainerStatusGroupDTO;
import vn.containergo.service.mapper.ContainerStatusGroupMapper;

/**
 * Service Implementation for managing {@link vn.containergo.domain.ContainerStatusGroup}.
 */
@Service
public class ContainerStatusGroupServiceImpl implements ContainerStatusGroupService {

    private final Logger log = LoggerFactory.getLogger(ContainerStatusGroupServiceImpl.class);

    private final ContainerStatusGroupRepository containerStatusGroupRepository;

    private final ContainerStatusGroupMapper containerStatusGroupMapper;

    public ContainerStatusGroupServiceImpl(
        ContainerStatusGroupRepository containerStatusGroupRepository,
        ContainerStatusGroupMapper containerStatusGroupMapper
    ) {
        this.containerStatusGroupRepository = containerStatusGroupRepository;
        this.containerStatusGroupMapper = containerStatusGroupMapper;
    }

    @Override
    public ContainerStatusGroupDTO save(ContainerStatusGroupDTO containerStatusGroupDTO) {
        log.debug("Request to save ContainerStatusGroup : {}", containerStatusGroupDTO);
        ContainerStatusGroup containerStatusGroup = containerStatusGroupMapper.toEntity(containerStatusGroupDTO);
        containerStatusGroup = containerStatusGroupRepository.save(containerStatusGroup);
        return containerStatusGroupMapper.toDto(containerStatusGroup);
    }

    @Override
    public ContainerStatusGroupDTO update(ContainerStatusGroupDTO containerStatusGroupDTO) {
        log.debug("Request to update ContainerStatusGroup : {}", containerStatusGroupDTO);
        ContainerStatusGroup containerStatusGroup = containerStatusGroupMapper.toEntity(containerStatusGroupDTO);
        containerStatusGroup = containerStatusGroupRepository.save(containerStatusGroup);
        return containerStatusGroupMapper.toDto(containerStatusGroup);
    }

    @Override
    public Optional<ContainerStatusGroupDTO> partialUpdate(ContainerStatusGroupDTO containerStatusGroupDTO) {
        log.debug("Request to partially update ContainerStatusGroup : {}", containerStatusGroupDTO);

        return containerStatusGroupRepository
            .findById(containerStatusGroupDTO.getId())
            .map(existingContainerStatusGroup -> {
                containerStatusGroupMapper.partialUpdate(existingContainerStatusGroup, containerStatusGroupDTO);

                return existingContainerStatusGroup;
            })
            .map(containerStatusGroupRepository::save)
            .map(containerStatusGroupMapper::toDto);
    }

    @Override
    public Page<ContainerStatusGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContainerStatusGroups");
        return containerStatusGroupRepository.findAll(pageable).map(containerStatusGroupMapper::toDto);
    }

    @Override
    public Optional<ContainerStatusGroupDTO> findOne(UUID id) {
        log.debug("Request to get ContainerStatusGroup : {}", id);
        return containerStatusGroupRepository.findById(id).map(containerStatusGroupMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete ContainerStatusGroup : {}", id);
        containerStatusGroupRepository.deleteById(id);
    }
}
