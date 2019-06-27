package io.github.prepayments.app.messaging.data_entry.service;

import io.github.prepayments.domain.PrepaymentEntry;
import io.github.prepayments.repository.PrepaymentEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * The prepayment model is designed unique by both prepaymentDate and prepaymentId. This class uses its repository to implement a method of finding a prepayment by those two fields
 */
@Transactional
@Service("prepaymentIdService")
@Slf4j
public class PrepaymentEntryIdService implements IPrepaymentEntryIdService {

    private final PrepaymentEntryRepository prepaymentEntryRepository;

    public PrepaymentEntryIdService(PrepaymentEntryRepository prepaymentEntryRepository) {
        this.prepaymentEntryRepository = prepaymentEntryRepository;
    }


    /**
     * This method return the DB domain Id whose date and business Id is as given
     */
    @Override
    @Cacheable("prepaymentByIdAndDate")
    public Long findByIdAndDate(final String prepaymentEntryId, final String prepaymentEntryDate) {

        Long findByIdAndDate;

        log.debug("Finding prepayment with the Id : {} dated : {}", prepaymentEntryId, prepaymentEntryDate);

        //TODO Convert this using system-wide converter
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        PrepaymentEntry found = prepaymentEntryRepository.findFirstByPrepaymentIdAndPrepaymentDate(prepaymentEntryId, LocalDate.parse(prepaymentEntryDate, dtf));

        if(found == null){
            log.debug("Prepayment Not found: Prepayment Id : {}, dated : {}", prepaymentEntryId, prepaymentEntryDate);
            return 1L;
        }
        findByIdAndDate = found.getId();

        return findByIdAndDate;
    }
}
