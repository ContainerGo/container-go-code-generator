package vn.containergo.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.containergo.domain.CenterPerson;
import vn.containergo.repository.CenterPersonRepository;
import vn.containergo.service.CenterPersonService;
import vn.containergo.service.dto.CenterPersonDTO;
import vn.containergo.service.mapper.CenterPersonMapper;

/**
 * Service Implementation for managing {@link vn.containergo.domain.CenterPerson}.
 */
@Service
public class CenterPersonServiceImpl implements CenterPersonService {

    private final Logger log = LoggerFactory.getLogger(CenterPersonServiceImpl.class);

    private final CenterPersonRepository centerPersonRepository;

    private final CenterPersonMapper centerPersonMapper;

    public CenterPersonServiceImpl(CenterPersonRepository centerPersonRepository, CenterPersonMapper centerPersonMapper) {
        this.centerPersonRepository = centerPersonRepository;
        this.centerPersonMapper = centerPersonMapper;
    }

    @Override
    public CenterPersonDTO save(CenterPersonDTO centerPersonDTO) {
        log.debug("Request to save CenterPerson : {}", centerPersonDTO);
        CenterPerson centerPerson = centerPersonMapper.toEntity(centerPersonDTO);
        centerPerson = centerPersonRepository.save(centerPerson);
        return centerPersonMapper.toDto(centerPerson);
    }

    @Override
    public CenterPersonDTO update(CenterPersonDTO centerPersonDTO) {
        log.debug("Request to update CenterPerson : {}", centerPersonDTO);
        CenterPerson centerPerson = centerPersonMapper.toEntity(centerPersonDTO);
        centerPerson = centerPersonRepository.save(centerPerson);
        return centerPersonMapper.toDto(centerPerson);
    }

    @Override
    public Optional<CenterPersonDTO> partialUpdate(CenterPersonDTO centerPersonDTO) {
        log.debug("Request to partially update CenterPerson : {}", centerPersonDTO);

        return centerPersonRepository
            .findById(centerPersonDTO.getId())
            .map(existingCenterPerson -> {
                centerPersonMapper.partialUpdate(existingCenterPerson, centerPersonDTO);

                return existingCenterPerson;
            })
            .map(centerPersonRepository::save)
            .map(centerPersonMapper::toDto);
    }

    @Override
    public Page<CenterPersonDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CenterPeople");
        return centerPersonRepository.findAll(pageable).map(centerPersonMapper::toDto);
    }

    public Page<CenterPersonDTO> findAllWithEagerRelationships(Pageable pageable) {
        return centerPersonRepository.findAllWithEagerRelationships(pageable).map(centerPersonMapper::toDto);
    }

    @Override
    public Optional<CenterPersonDTO> findOne(UUID id) {
        log.debug("Request to get CenterPerson : {}", id);
        return centerPersonRepository.findOneWithEagerRelationships(id).map(centerPersonMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete CenterPerson : {}", id);
        centerPersonRepository.deleteById(id);
    }
}
