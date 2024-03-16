package vn.containergo.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.containergo.domain.CarrierPerson;
import vn.containergo.repository.CarrierPersonRepository;
import vn.containergo.service.CarrierPersonService;
import vn.containergo.service.dto.CarrierPersonDTO;
import vn.containergo.service.mapper.CarrierPersonMapper;

/**
 * Service Implementation for managing {@link vn.containergo.domain.CarrierPerson}.
 */
@Service
public class CarrierPersonServiceImpl implements CarrierPersonService {

    private final Logger log = LoggerFactory.getLogger(CarrierPersonServiceImpl.class);

    private final CarrierPersonRepository carrierPersonRepository;

    private final CarrierPersonMapper carrierPersonMapper;

    public CarrierPersonServiceImpl(CarrierPersonRepository carrierPersonRepository, CarrierPersonMapper carrierPersonMapper) {
        this.carrierPersonRepository = carrierPersonRepository;
        this.carrierPersonMapper = carrierPersonMapper;
    }

    @Override
    public CarrierPersonDTO save(CarrierPersonDTO carrierPersonDTO) {
        log.debug("Request to save CarrierPerson : {}", carrierPersonDTO);
        CarrierPerson carrierPerson = carrierPersonMapper.toEntity(carrierPersonDTO);
        carrierPerson = carrierPersonRepository.save(carrierPerson);
        return carrierPersonMapper.toDto(carrierPerson);
    }

    @Override
    public CarrierPersonDTO update(CarrierPersonDTO carrierPersonDTO) {
        log.debug("Request to update CarrierPerson : {}", carrierPersonDTO);
        CarrierPerson carrierPerson = carrierPersonMapper.toEntity(carrierPersonDTO);
        carrierPerson = carrierPersonRepository.save(carrierPerson);
        return carrierPersonMapper.toDto(carrierPerson);
    }

    @Override
    public Optional<CarrierPersonDTO> partialUpdate(CarrierPersonDTO carrierPersonDTO) {
        log.debug("Request to partially update CarrierPerson : {}", carrierPersonDTO);

        return carrierPersonRepository
            .findById(carrierPersonDTO.getId())
            .map(existingCarrierPerson -> {
                carrierPersonMapper.partialUpdate(existingCarrierPerson, carrierPersonDTO);

                return existingCarrierPerson;
            })
            .map(carrierPersonRepository::save)
            .map(carrierPersonMapper::toDto);
    }

    @Override
    public Page<CarrierPersonDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CarrierPeople");
        return carrierPersonRepository.findAll(pageable).map(carrierPersonMapper::toDto);
    }

    @Override
    public Optional<CarrierPersonDTO> findOne(Long id) {
        log.debug("Request to get CarrierPerson : {}", id);
        return carrierPersonRepository.findById(id).map(carrierPersonMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CarrierPerson : {}", id);
        carrierPersonRepository.deleteById(id);
    }
}
