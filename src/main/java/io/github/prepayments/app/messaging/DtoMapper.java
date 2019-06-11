package io.github.prepayments.app.messaging;

import java.util.List;

/**
 * Maps an Excel view model to a data transfer object
 *
 * @param <EVM> - EVM Type parameter
 * @param <DTO> - DTO Type parameter
 */
public interface DtoMapper<EVM, DTO> {

    DTO toDTO(EVM evm);

    EVM toEVM(DTO dto);

    List<DTO> toDTO(List<EVM> evmList);

    List<EVM> toEVM(List<DTO> entityList);

}
