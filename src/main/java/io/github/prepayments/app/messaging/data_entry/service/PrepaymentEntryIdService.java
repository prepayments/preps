package io.github.prepayments.app.messaging.data_entry.service;

import io.github.prepayments.app.decorators.PrepaymentEntryRepositoryDecorator;
import io.github.prepayments.domain.PrepaymentEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The prepayment model is designed unique by both prepaymentDate and prepaymentId. This class uses its repository to implement a method of finding a prepayment by those two fields
 */
// TODO may be decorate this...
@Transactional
@Service("prepaymentIdService")
@Slf4j
public class PrepaymentEntryIdService implements IPrepaymentEntryIdService {

    private final PrepaymentEntryRepositoryDecorator prepaymentEntryRepositoryDecorator;

    public PrepaymentEntryIdService(@Qualifier("prepaymentEntryRepositoryDecorator") PrepaymentEntryRepositoryDecorator prepaymentEntryRepositoryDecorator) {
        this.prepaymentEntryRepositoryDecorator = prepaymentEntryRepositoryDecorator;
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

        PrepaymentEntry found = prepaymentEntryRepositoryDecorator.findFirstByPrepaymentIdAndPrepaymentDate(prepaymentEntryId, LocalDate.parse(prepaymentEntryDate, dtf));

        if(found == null){
            log.debug("Prepayment Not found: Prepayment Id : {}, dated : {}", prepaymentEntryId, prepaymentEntryDate);
            return 1L;
        }
        findByIdAndDate = found.getId();

        return findByIdAndDate;
    }
}
