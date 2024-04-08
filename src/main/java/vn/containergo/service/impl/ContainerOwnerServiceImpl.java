package vn.containergo.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.containergo.domain.ContainerOwner;
import vn.containergo.repository.ContainerOwnerRepository;
import vn.containergo.service.ContainerOwnerService;
import vn.containergo.service.dto.ContainerOwnerDTO;
import vn.containergo.service.mapper.ContainerOwnerMapper;

/**
 * Service Implementation for managing {@link vn.containergo.domain.ContainerOwner}.
 */
@Service
public class ContainerOwnerServiceImpl implements ContainerOwnerService {

    private final Logger log = LoggerFactory.getLogger(ContainerOwnerServiceImpl.class);

    private final ContainerOwnerRepository containerOwnerRepository;

    private final ContainerOwnerMapper containerOwnerMapper;

    public ContainerOwnerServiceImpl(ContainerOwnerRepository containerOwnerRepository, ContainerOwnerMapper containerOwnerMapper) {
        this.containerOwnerRepository = containerOwnerRepository;
        this.containerOwnerMapper = containerOwnerMapper;
    }

    @Override
    public ContainerOwnerDTO save(ContainerOwnerDTO containerOwnerDTO) {
        log.debug("Request to save ContainerOwner : {}", containerOwnerDTO);
        ContainerOwner containerOwner = containerOwnerMapper.toEntity(containerOwnerDTO);
        containerOwner = containerOwnerRepository.save(containerOwner);
        return containerOwnerMapper.toDto(containerOwner);
    }

    @Override
    public ContainerOwnerDTO update(ContainerOwnerDTO containerOwnerDTO) {
        log.debug("Request to update ContainerOwner : {}", containerOwnerDTO);
        ContainerOwner containerOwner = containerOwnerMapper.toEntity(containerOwnerDTO);
        containerOwner = containerOwnerRepository.save(containerOwner);
        return containerOwnerMapper.toDto(containerOwner);
    }

    @Override
    public Optional<ContainerOwnerDTO> partialUpdate(ContainerOwnerDTO containerOwnerDTO) {
        log.debug("Request to partially update ContainerOwner : {}", containerOwnerDTO);

        return containerOwnerRepository
            .findById(containerOwnerDTO.getId())
            .map(existingContainerOwner -> {
                containerOwnerMapper.partialUpdate(existingContainerOwner, containerOwnerDTO);

                return existingContainerOwner;
            })
            .map(containerOwnerRepository::save)
            .map(containerOwnerMapper::toDto);
    }

    @Override
    public Page<ContainerOwnerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContainerOwners");
        return containerOwnerRepository.findAll(pageable).map(containerOwnerMapper::toDto);
    }

    @Override
    public Optional<ContainerOwnerDTO> findOne(UUID id) {
        log.debug("Request to get ContainerOwner : {}", id);
        return containerOwnerRepository.findById(id).map(containerOwnerMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete ContainerOwner : {}", id);
        containerOwnerRepository.deleteById(id);
    }
}
