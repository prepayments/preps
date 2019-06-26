package io.github.prepayments.service.impl;

import io.github.prepayments.domain.RegisteredSupplier;
import io.github.prepayments.repository.RegisteredSupplierRepository;
import io.github.prepayments.repository.search.RegisteredSupplierSearchRepository;
import io.github.prepayments.service.RegisteredSupplierService;
import io.github.prepayments.service.dto.RegisteredSupplierDTO;
import io.github.prepayments.service.mapper.RegisteredSupplierMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link RegisteredSupplier}.
 */
@Service
@Transactional
public class RegisteredSupplierServiceImpl implements RegisteredSupplierService {

    private final Logger log = LoggerFactory.getLogger(RegisteredSupplierServiceImpl.class);

    private final RegisteredSupplierRepository registeredSupplierRepository;

    private final RegisteredSupplierMapper registeredSupplierMapper;

    private final RegisteredSupplierSearchRepository registeredSupplierSearchRepository;

    public RegisteredSupplierServiceImpl(RegisteredSupplierRepository registeredSupplierRepository, RegisteredSupplierMapper registeredSupplierMapper,
                                         RegisteredSupplierSearchRepository registeredSupplierSearchRepository) {
        this.registeredSupplierRepository = registeredSupplierRepository;
        this.registeredSupplierMapper = registeredSupplierMapper;
        this.registeredSupplierSearchRepository = registeredSupplierSearchRepository;
    }

    /**
     * Save a registeredSupplier.
     *
     * @param registeredSupplierDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RegisteredSupplierDTO save(RegisteredSupplierDTO registeredSupplierDTO) {
        log.debug("Request to save RegisteredSupplier : {}", registeredSupplierDTO);
        RegisteredSupplier registeredSupplier = registeredSupplierMapper.toEntity(registeredSupplierDTO);
        registeredSupplier = registeredSupplierRepository.save(registeredSupplier);
        RegisteredSupplierDTO result = registeredSupplierMapper.toDto(registeredSupplier);
        registeredSupplierSearchRepository.save(registeredSupplier);
        return result;
    }

    /**
     * Get all the registeredSuppliers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RegisteredSupplierDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RegisteredSuppliers");
        return registeredSupplierRepository.findAll(pageable).map(registeredSupplierMapper::toDto);
    }


    /**
     * Get one registeredSupplier by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RegisteredSupplierDTO> findOne(Long id) {
        log.debug("Request to get RegisteredSupplier : {}", id);
        return registeredSupplierRepository.findById(id).map(registeredSupplierMapper::toDto);
    }

    /**
     * Delete the registeredSupplier by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RegisteredSupplier : {}", id);
        registeredSupplierRepository.deleteById(id);
        registeredSupplierSearchRepository.deleteById(id);
    }

    /**
     * Search for the registeredSupplier corresponding to the query.
     *
     * @param query    the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RegisteredSupplierDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RegisteredSuppliers for query {}", query);
        return registeredSupplierSearchRepository.search(queryStringQuery(query), pageable).map(registeredSupplierMapper::toDto);
    }
}
