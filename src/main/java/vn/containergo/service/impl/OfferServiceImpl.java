package vn.containergo.service.impl;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.containergo.domain.Offer;
import vn.containergo.repository.OfferRepository;
import vn.containergo.service.OfferService;
import vn.containergo.service.dto.OfferDTO;
import vn.containergo.service.mapper.OfferMapper;

/**
 * Service Implementation for managing {@link vn.containergo.domain.Offer}.
 */
@Service
public class OfferServiceImpl implements OfferService {

    private final Logger log = LoggerFactory.getLogger(OfferServiceImpl.class);

    private final OfferRepository offerRepository;

    private final OfferMapper offerMapper;

    public OfferServiceImpl(OfferRepository offerRepository, OfferMapper offerMapper) {
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
    }

    @Override
    public OfferDTO save(OfferDTO offerDTO) {
        log.debug("Request to save Offer : {}", offerDTO);
        Offer offer = offerMapper.toEntity(offerDTO);
        offer = offerRepository.save(offer);
        return offerMapper.toDto(offer);
    }

    @Override
    public OfferDTO update(OfferDTO offerDTO) {
        log.debug("Request to update Offer : {}", offerDTO);
        Offer offer = offerMapper.toEntity(offerDTO);
        offer = offerRepository.save(offer);
        return offerMapper.toDto(offer);
    }

    @Override
    public Optional<OfferDTO> partialUpdate(OfferDTO offerDTO) {
        log.debug("Request to partially update Offer : {}", offerDTO);

        return offerRepository
            .findById(offerDTO.getId())
            .map(existingOffer -> {
                offerMapper.partialUpdate(existingOffer, offerDTO);

                return existingOffer;
            })
            .map(offerRepository::save)
            .map(offerMapper::toDto);
    }

    @Override
    public Page<OfferDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Offers");
        return offerRepository.findAll(pageable).map(offerMapper::toDto);
    }

    @Override
    public Optional<OfferDTO> findOne(UUID id) {
        log.debug("Request to get Offer : {}", id);
        return offerRepository.findById(id).map(offerMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete Offer : {}", id);
        offerRepository.deleteById(id);
    }
}
