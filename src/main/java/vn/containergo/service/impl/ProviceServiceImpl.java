package vn.containergo.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.containergo.domain.Provice;
import vn.containergo.repository.ProviceRepository;
import vn.containergo.service.ProviceService;
import vn.containergo.service.dto.ProviceDTO;
import vn.containergo.service.mapper.ProviceMapper;

/**
 * Service Implementation for managing {@link vn.containergo.domain.Provice}.
 */
@Service
public class ProviceServiceImpl implements ProviceService {

    private final Logger log = LoggerFactory.getLogger(ProviceServiceImpl.class);

    private final ProviceRepository proviceRepository;

    private final ProviceMapper proviceMapper;

    public ProviceServiceImpl(ProviceRepository proviceRepository, ProviceMapper proviceMapper) {
        this.proviceRepository = proviceRepository;
        this.proviceMapper = proviceMapper;
    }

    @Override
    public ProviceDTO save(ProviceDTO proviceDTO) {
        log.debug("Request to save Provice : {}", proviceDTO);
        Provice provice = proviceMapper.toEntity(proviceDTO);
        provice = proviceRepository.save(provice);
        return proviceMapper.toDto(provice);
    }

    @Override
    public ProviceDTO update(ProviceDTO proviceDTO) {
        log.debug("Request to update Provice : {}", proviceDTO);
        Provice provice = proviceMapper.toEntity(proviceDTO);
        provice = proviceRepository.save(provice);
        return proviceMapper.toDto(provice);
    }

    @Override
    public Optional<ProviceDTO> partialUpdate(ProviceDTO proviceDTO) {
        log.debug("Request to partially update Provice : {}", proviceDTO);

        return proviceRepository
            .findById(proviceDTO.getId())
            .map(existingProvice -> {
                proviceMapper.partialUpdate(existingProvice, proviceDTO);

                return existingProvice;
            })
            .map(proviceRepository::save)
            .map(proviceMapper::toDto);
    }

    @Override
    public Page<ProviceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Provices");
        return proviceRepository.findAll(pageable).map(proviceMapper::toDto);
    }

    @Override
    public Optional<ProviceDTO> findOne(UUID id) {
        log.debug("Request to get Provice : {}", id);
        return proviceRepository.findById(id).map(proviceMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Provice : {}", id);
        proviceRepository.deleteById(id);
    }
}
