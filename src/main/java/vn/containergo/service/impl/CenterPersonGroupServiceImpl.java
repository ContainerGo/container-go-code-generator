package vn.containergo.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.containergo.domain.CenterPersonGroup;
import vn.containergo.repository.CenterPersonGroupRepository;
import vn.containergo.service.CenterPersonGroupService;
import vn.containergo.service.dto.CenterPersonGroupDTO;
import vn.containergo.service.mapper.CenterPersonGroupMapper;

/**
 * Service Implementation for managing {@link vn.containergo.domain.CenterPersonGroup}.
 */
@Service
public class CenterPersonGroupServiceImpl implements CenterPersonGroupService {

    private final Logger log = LoggerFactory.getLogger(CenterPersonGroupServiceImpl.class);

    private final CenterPersonGroupRepository centerPersonGroupRepository;

    private final CenterPersonGroupMapper centerPersonGroupMapper;

    public CenterPersonGroupServiceImpl(
        CenterPersonGroupRepository centerPersonGroupRepository,
        CenterPersonGroupMapper centerPersonGroupMapper
    ) {
        this.centerPersonGroupRepository = centerPersonGroupRepository;
        this.centerPersonGroupMapper = centerPersonGroupMapper;
    }

    @Override
    public CenterPersonGroupDTO save(CenterPersonGroupDTO centerPersonGroupDTO) {
        log.debug("Request to save CenterPersonGroup : {}", centerPersonGroupDTO);
        CenterPersonGroup centerPersonGroup = centerPersonGroupMapper.toEntity(centerPersonGroupDTO);
        centerPersonGroup = centerPersonGroupRepository.save(centerPersonGroup);
        return centerPersonGroupMapper.toDto(centerPersonGroup);
    }

    @Override
    public CenterPersonGroupDTO update(CenterPersonGroupDTO centerPersonGroupDTO) {
        log.debug("Request to update CenterPersonGroup : {}", centerPersonGroupDTO);
        CenterPersonGroup centerPersonGroup = centerPersonGroupMapper.toEntity(centerPersonGroupDTO);
        centerPersonGroup = centerPersonGroupRepository.save(centerPersonGroup);
        return centerPersonGroupMapper.toDto(centerPersonGroup);
    }

    @Override
    public Optional<CenterPersonGroupDTO> partialUpdate(CenterPersonGroupDTO centerPersonGroupDTO) {
        log.debug("Request to partially update CenterPersonGroup : {}", centerPersonGroupDTO);

        return centerPersonGroupRepository
            .findById(centerPersonGroupDTO.getId())
            .map(existingCenterPersonGroup -> {
                centerPersonGroupMapper.partialUpdate(existingCenterPersonGroup, centerPersonGroupDTO);

                return existingCenterPersonGroup;
            })
            .map(centerPersonGroupRepository::save)
            .map(centerPersonGroupMapper::toDto);
    }

    @Override
    public Page<CenterPersonGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CenterPersonGroups");
        return centerPersonGroupRepository.findAll(pageable).map(centerPersonGroupMapper::toDto);
    }

    @Override
    public Optional<CenterPersonGroupDTO> findOne(UUID id) {
        log.debug("Request to get CenterPersonGroup : {}", id);
        return centerPersonGroupRepository.findById(id).map(centerPersonGroupMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete CenterPersonGroup : {}", id);
        centerPersonGroupRepository.deleteById(id);
    }
}
