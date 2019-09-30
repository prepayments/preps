package io.github.prepayments.app.decoratedResource;

import io.github.prepayments.app.messaging.MessageService;
import io.github.prepayments.app.messaging.notifications.dto.AmortizationUpdateFileUploadNotification;
import io.github.prepayments.app.messaging.notifications.dto.FileUploadNotification;
import io.github.prepayments.app.token.ExcelFileTokenProvider;
import io.github.prepayments.service.dto.AmortizationUpdateFileDTO;
import io.github.prepayments.web.rest.AmortizationUpdateFileResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * REST controller for managing {@link io.github.prepayments.domain.AmortizationUpdateFile}.
 *
 * This controller contains notifiers to send message to the queue about uploaded updated or deleted files
 */
@RestController
@RequestMapping("/api/notified")
public class NotifiedAmortizationUpdateFileResource extends AmortizationUpdateFileResourceDecorator implements IAmortizationUpdateFileResource {

    private final ExcelFileTokenProvider excelFileTokenProvider;
    private final MessageService<FileUploadNotification> amortizationUpdateFileMessageService;

    public NotifiedAmortizationUpdateFileResource(final AmortizationUpdateFileResource amortizationUpdateFileResource, final ExcelFileTokenProvider excelFileTokenProvider,
                                                  final MessageService<FileUploadNotification> amortizationUpdateFileMessageService) {
        super(amortizationUpdateFileResource);
        this.excelFileTokenProvider = excelFileTokenProvider;
        this.amortizationUpdateFileMessageService = amortizationUpdateFileMessageService;
    }

    /**
     * {@code POST  /amortization-update-files} : Create a new amortizationUpdateFile.
     *
     * @param amortizationUpdateFileDTO the amortizationUpdateFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new amortizationUpdateFileDTO, or with status {@code 400 (Bad Request)} if the amortizationUpdateFile has
     * already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Override
    @PostMapping("/amortization-update-files")
    public ResponseEntity<AmortizationUpdateFileDTO> createAmortizationUpdateFile(@Valid @RequestBody final AmortizationUpdateFileDTO amortizationUpdateFileDTO) throws URISyntaxException {

        amortizationUpdateFileDTO.setFileToken(excelFileTokenProvider.getFileToken(amortizationUpdateFileDTO));

        ResponseEntity<AmortizationUpdateFileDTO> responseEntity = super.createAmortizationUpdateFile(amortizationUpdateFileDTO);

        // Send notification
        amortizationUpdateFileMessageService.sendMessage(AmortizationUpdateFileUploadNotification.builder()
                                                                                                .id(Objects.requireNonNull(responseEntity.getBody()).getId())
                                                                                                .timeStamp(System.currentTimeMillis())
                                                                                                .fileUpload(responseEntity.getBody().getDataEntryFile())
                                                                                                .fileToken(responseEntity.getBody().getFileToken())
                                                                                                .build());

        return responseEntity;

    }
}
