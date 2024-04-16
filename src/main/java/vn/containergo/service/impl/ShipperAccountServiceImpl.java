package vn.containergo.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.containergo.domain.ShipperAccount;
import vn.containergo.repository.ShipperAccountRepository;
import vn.containergo.service.ShipperAccountService;
import vn.containergo.service.dto.ShipperAccountDTO;
import vn.containergo.service.mapper.ShipperAccountMapper;

/**
 * Service Implementation for managing {@link vn.containergo.domain.ShipperAccount}.
 */
@Service
public class ShipperAccountServiceImpl implements ShipperAccountService {

    private final Logger log = LoggerFactory.getLogger(ShipperAccountServiceImpl.class);

    private final ShipperAccountRepository shipperAccountRepository;

    private final ShipperAccountMapper shipperAccountMapper;

    public ShipperAccountServiceImpl(ShipperAccountRepository shipperAccountRepository, ShipperAccountMapper shipperAccountMapper) {
        this.shipperAccountRepository = shipperAccountRepository;
        this.shipperAccountMapper = shipperAccountMapper;
    }

    @Override
    public ShipperAccountDTO save(ShipperAccountDTO shipperAccountDTO) {
        log.debug("Request to save ShipperAccount : {}", shipperAccountDTO);
        ShipperAccount shipperAccount = shipperAccountMapper.toEntity(shipperAccountDTO);
        shipperAccount = shipperAccountRepository.save(shipperAccount);
        return shipperAccountMapper.toDto(shipperAccount);
    }

    @Override
    public ShipperAccountDTO update(ShipperAccountDTO shipperAccountDTO) {
        log.debug("Request to update ShipperAccount : {}", shipperAccountDTO);
        ShipperAccount shipperAccount = shipperAccountMapper.toEntity(shipperAccountDTO);
        shipperAccount = shipperAccountRepository.save(shipperAccount);
        return shipperAccountMapper.toDto(shipperAccount);
    }

    @Override
    public Optional<ShipperAccountDTO> partialUpdate(ShipperAccountDTO shipperAccountDTO) {
        log.debug("Request to partially update ShipperAccount : {}", shipperAccountDTO);

        return shipperAccountRepository
            .findById(shipperAccountDTO.getId())
            .map(existingShipperAccount -> {
                shipperAccountMapper.partialUpdate(existingShipperAccount, shipperAccountDTO);

                return existingShipperAccount;
            })
            .map(shipperAccountRepository::save)
            .map(shipperAccountMapper::toDto);
    }

    @Override
    public Page<ShipperAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShipperAccounts");
        return shipperAccountRepository.findAll(pageable).map(shipperAccountMapper::toDto);
    }

    @Override
    public Optional<ShipperAccountDTO> findOne(UUID id) {
        log.debug("Request to get ShipperAccount : {}", id);
        return shipperAccountRepository.findById(id).map(shipperAccountMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete ShipperAccount : {}", id);
        shipperAccountRepository.deleteById(id);
    }
}
