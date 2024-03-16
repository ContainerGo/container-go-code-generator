package vn.containergo.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.containergo.domain.ShipperPerson;
import vn.containergo.repository.ShipperPersonRepository;
import vn.containergo.service.ShipperPersonService;
import vn.containergo.service.dto.ShipperPersonDTO;
import vn.containergo.service.mapper.ShipperPersonMapper;

/**
 * Service Implementation for managing {@link vn.containergo.domain.ShipperPerson}.
 */
@Service
public class ShipperPersonServiceImpl implements ShipperPersonService {

    private final Logger log = LoggerFactory.getLogger(ShipperPersonServiceImpl.class);

    private final ShipperPersonRepository shipperPersonRepository;

    private final ShipperPersonMapper shipperPersonMapper;

    public ShipperPersonServiceImpl(ShipperPersonRepository shipperPersonRepository, ShipperPersonMapper shipperPersonMapper) {
        this.shipperPersonRepository = shipperPersonRepository;
        this.shipperPersonMapper = shipperPersonMapper;
    }

    @Override
    public ShipperPersonDTO save(ShipperPersonDTO shipperPersonDTO) {
        log.debug("Request to save ShipperPerson : {}", shipperPersonDTO);
        ShipperPerson shipperPerson = shipperPersonMapper.toEntity(shipperPersonDTO);
        shipperPerson = shipperPersonRepository.save(shipperPerson);
        return shipperPersonMapper.toDto(shipperPerson);
    }

    @Override
    public ShipperPersonDTO update(ShipperPersonDTO shipperPersonDTO) {
        log.debug("Request to update ShipperPerson : {}", shipperPersonDTO);
        ShipperPerson shipperPerson = shipperPersonMapper.toEntity(shipperPersonDTO);
        shipperPerson = shipperPersonRepository.save(shipperPerson);
        return shipperPersonMapper.toDto(shipperPerson);
    }

    @Override
    public Optional<ShipperPersonDTO> partialUpdate(ShipperPersonDTO shipperPersonDTO) {
        log.debug("Request to partially update ShipperPerson : {}", shipperPersonDTO);

        return shipperPersonRepository
            .findById(shipperPersonDTO.getId())
            .map(existingShipperPerson -> {
                shipperPersonMapper.partialUpdate(existingShipperPerson, shipperPersonDTO);

                return existingShipperPerson;
            })
            .map(shipperPersonRepository::save)
            .map(shipperPersonMapper::toDto);
    }

    @Override
    public Page<ShipperPersonDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShipperPeople");
        return shipperPersonRepository.findAll(pageable).map(shipperPersonMapper::toDto);
    }

    @Override
    public Optional<ShipperPersonDTO> findOne(Long id) {
        log.debug("Request to get ShipperPerson : {}", id);
        return shipperPersonRepository.findById(id).map(shipperPersonMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShipperPerson : {}", id);
        shipperPersonRepository.deleteById(id);
    }
}
