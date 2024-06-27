package ru.gorchanyuk.csvvalidator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Модель для передачи сообщения об ошибке")
public class ErrorResponseDTO {

    @Schema(description = "Сообщение с информацией об ошибке")
    private String message;
}
