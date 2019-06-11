package io.github.prepayments.app.services;

import io.github.prepayments.repository.TransactionAccountRepository;
import io.github.prepayments.service.dto.TransactionAccountDTO;
import io.github.prepayments.service.mapper.TransactionAccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class TransactionAccountsReportService {

    private final TransactionAccountMapper transactionAccountMapper;
    private final TransactionAccountRepository transactionAccountRepository;

    public TransactionAccountsReportService(final TransactionAccountMapper transactionAccountMapper, final TransactionAccountRepository transactionAccountRepository) {
        this.transactionAccountMapper = transactionAccountMapper;
        this.transactionAccountRepository = transactionAccountRepository;
    }

    @Transactional(readOnly = true)
    public List<TransactionAccountDTO> findAll() {
        log.debug("Request to get all transaction accounts for reporting");
        return transactionAccountMapper.toDto(transactionAccountRepository.findAll());
    }
}
