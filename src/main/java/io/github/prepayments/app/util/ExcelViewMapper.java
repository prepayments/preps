package io.github.prepayments.app.util;

import java.time.format.DateTimeFormatter;
import java.util.List;

public interface ExcelViewMapper<ExcelView, DTO> {

    ExcelView toExcelView(DTO dto, DateTimeFormatter dtf);

    DTO toDto(ExcelView excelView, DateTimeFormatter dtf);

    List<ExcelView> toExcelView(List<DTO> dtoList, DateTimeFormatter dtf);

    List<DTO> toDto(List<ExcelView> excelViews, DateTimeFormatter dtf);
}
