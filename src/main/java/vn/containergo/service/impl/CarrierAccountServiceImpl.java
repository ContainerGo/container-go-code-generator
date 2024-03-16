package vn.containergo.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.containergo.domain.CarrierAccount;
import vn.containergo.repository.CarrierAccountRepository;
import vn.containergo.service.CarrierAccountService;
import vn.containergo.service.dto.CarrierAccountDTO;
import vn.containergo.service.mapper.CarrierAccountMapper;

/**
 * Service Implementation for managing {@link vn.containergo.domain.CarrierAccount}.
 */
@Service
public class CarrierAccountServiceImpl implements CarrierAccountService {

    private final Logger log = LoggerFactory.getLogger(CarrierAccountServiceImpl.class);

    private final CarrierAccountRepository carrierAccountRepository;

    private final CarrierAccountMapper carrierAccountMapper;

    public CarrierAccountServiceImpl(CarrierAccountRepository carrierAccountRepository, CarrierAccountMapper carrierAccountMapper) {
        this.carrierAccountRepository = carrierAccountRepository;
        this.carrierAccountMapper = carrierAccountMapper;
    }

    @Override
    public CarrierAccountDTO save(CarrierAccountDTO carrierAccountDTO) {
        log.debug("Request to save CarrierAccount : {}", carrierAccountDTO);
        CarrierAccount carrierAccount = carrierAccountMapper.toEntity(carrierAccountDTO);
        carrierAccount = carrierAccountRepository.save(carrierAccount);
        return carrierAccountMapper.toDto(carrierAccount);
    }

    @Override
    public CarrierAccountDTO update(CarrierAccountDTO carrierAccountDTO) {
        log.debug("Request to update CarrierAccount : {}", carrierAccountDTO);
        CarrierAccount carrierAccount = carrierAccountMapper.toEntity(carrierAccountDTO);
        carrierAccount = carrierAccountRepository.save(carrierAccount);
        return carrierAccountMapper.toDto(carrierAccount);
    }

    @Override
    public Optional<CarrierAccountDTO> partialUpdate(CarrierAccountDTO carrierAccountDTO) {
        log.debug("Request to partially update CarrierAccount : {}", carrierAccountDTO);

        return carrierAccountRepository
            .findById(carrierAccountDTO.getId())
            .map(existingCarrierAccount -> {
                carrierAccountMapper.partialUpdate(existingCarrierAccount, carrierAccountDTO);

                return existingCarrierAccount;
            })
            .map(carrierAccountRepository::save)
            .map(carrierAccountMapper::toDto);
    }

    @Override
    public Page<CarrierAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CarrierAccounts");
        return carrierAccountRepository.findAll(pageable).map(carrierAccountMapper::toDto);
    }

    @Override
    public Optional<CarrierAccountDTO> findOne(Long id) {
        log.debug("Request to get CarrierAccount : {}", id);
        return carrierAccountRepository.findById(id).map(carrierAccountMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CarrierAccount : {}", id);
        carrierAccountRepository.deleteById(id);
    }
}
