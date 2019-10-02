package io.github.prepayments.app.messaging;

import com.google.common.collect.ImmutableList;

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

    default List<DTO> toDTO(List<EVM> evmList) {
        return evmList.stream().map(this::toDTO).collect(ImmutableList.toImmutableList());
    }

    default List<EVM> toEVM(List<DTO> entityList) {
        return entityList.stream().map(this::toEVM).collect(ImmutableList.toImmutableList());
    }

}
