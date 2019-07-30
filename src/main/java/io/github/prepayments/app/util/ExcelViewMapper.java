package io.github.prepayments.app.util;

import java.util.List;

public interface ExcelViewMapper<ExcelView, DTO> {

    ExcelView toExcelView(DTO dto);

    DTO toDto(ExcelView entity);

    List<ExcelView> toEntity(List<DTO> dtoList);

    List<DTO> toDto(List<ExcelView> entityList);
}
