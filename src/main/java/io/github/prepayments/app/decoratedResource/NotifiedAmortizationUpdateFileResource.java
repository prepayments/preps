package io.github.prepayments.app.decoratedResource;

import io.github.prepayments.service.dto.AmortizationUpdateFileDTO;
import io.github.prepayments.web.rest.AmortizationUpdateFileResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URISyntaxException;

/**
 * REST controller for managing {@link io.github.prepayments.domain.AmortizationUpdateFile}.
 */
@RestController
@RequestMapping("/api/notified")
public class NotifiedAmortizationUpdateFileResource extends AmortizationUpdateFileResourceDecorator implements IAmortizationUpdateFileResource {

    private final AmortizationUpdateFileResource resource;

    public NotifiedAmortizationUpdateFileResource(final AmortizationUpdateFileResource resource, final AmortizationUpdateFileResource resource1) {
        super(resource);
        this.resource = resource1;
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

        // Send notification

        return super.createAmortizationUpdateFile(amortizationUpdateFileDTO);
    }
}
