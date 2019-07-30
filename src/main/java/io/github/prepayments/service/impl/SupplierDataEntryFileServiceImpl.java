package io.github.prepayments.service.impl;

import io.github.prepayments.service.SupplierDataEntryFileService;
import io.github.prepayments.domain.SupplierDataEntryFile;
import io.github.prepayments.repository.SupplierDataEntryFileRepository;
import io.github.prepayments.repository.search.SupplierDataEntryFileSearchRepository;
import io.github.prepayments.service.dto.SupplierDataEntryFileDTO;
import io.github.prepayments.service.mapper.SupplierDataEntryFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link SupplierDataEntryFile}.
 */
@Service
@Transactional
public class SupplierDataEntryFileServiceImpl implements SupplierDataEntryFileService {

    private final Logger log = LoggerFactory.getLogger(SupplierDataEntryFileServiceImpl.class);

    private final SupplierDataEntryFileRepository supplierDataEntryFileRepository;

    private final SupplierDataEntryFileMapper supplierDataEntryFileMapper;

    private final SupplierDataEntryFileSearchRepository supplierDataEntryFileSearchRepository;

    public SupplierDataEntryFileServiceImpl(SupplierDataEntryFileRepository supplierDataEntryFileRepository, SupplierDataEntryFileMapper supplierDataEntryFileMapper, SupplierDataEntryFileSearchRepository supplierDataEntryFileSearchRepository) {
        this.supplierDataEntryFileRepository = supplierDataEntryFileRepository;
        this.supplierDataEntryFileMapper = supplierDataEntryFileMapper;
        this.supplierDataEntryFileSearchRepository = supplierDataEntryFileSearchRepository;
    }

    /**
     * Save a supplierDataEntryFile.
     *
     * @param supplierDataEntryFileDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SupplierDataEntryFileDTO save(SupplierDataEntryFileDTO supplierDataEntryFileDTO) {
        log.debug("Request to save SupplierDataEntryFile : {}", supplierDataEntryFileDTO);
        SupplierDataEntryFile supplierDataEntryFile = supplierDataEntryFileMapper.toEntity(supplierDataEntryFileDTO);
        supplierDataEntryFile = supplierDataEntryFileRepository.save(supplierDataEntryFile);
        SupplierDataEntryFileDTO result = supplierDataEntryFileMapper.toDto(supplierDataEntryFile);
        supplierDataEntryFileSearchRepository.save(supplierDataEntryFile);
        return result;
    }

    /**
     * Get all the supplierDataEntryFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SupplierDataEntryFileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SupplierDataEntryFiles");
        return supplierDataEntryFileRepository.findAll(pageable)
            .map(supplierDataEntryFileMapper::toDto);
    }


    /**
     * Get one supplierDataEntryFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SupplierDataEntryFileDTO> findOne(Long id) {
        log.debug("Request to get SupplierDataEntryFile : {}", id);
        return supplierDataEntryFileRepository.findById(id)
            .map(supplierDataEntryFileMapper::toDto);
    }

    /**
     * Delete the supplierDataEntryFile by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SupplierDataEntryFile : {}", id);
        supplierDataEntryFileRepository.deleteById(id);
        supplierDataEntryFileSearchRepository.deleteById(id);
    }

    /**
     * Search for the supplierDataEntryFile corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SupplierDataEntryFileDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SupplierDataEntryFiles for query {}", query);
        return supplierDataEntryFileSearchRepository.search(queryStringQuery(query), pageable)
            .map(supplierDataEntryFileMapper::toDto);
    }
}
