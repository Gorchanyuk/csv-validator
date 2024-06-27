package ru.gorchanyuk.csvvalidator.controller;

import ru.gorchanyuk.csvvalidator.dto.ErrorResponseDTO;
import ru.gorchanyuk.csvvalidator.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ResourceBundle;

@Controller
@RequiredArgsConstructor
@RequestMapping("/csv/validator")
@Tag(name = "Загрузка файла", description = "Контроллер для загрузки файла")
@ApiResponses({
        @ApiResponse(responseCode = "200",
        content = @Content(schema = @Schema(implementation = String.class)),
        description = "Файл прошел валидацию и сохранен на сервер."),
        @ApiResponse(responseCode = "412",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)),
                description = "Недопустимое название файла."),
        @ApiResponse(responseCode = "406",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)),
                description = "Недопустимое содержимое файла (количество столбцов, формат даты)."),
        @ApiResponse(responseCode = "422",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)),
                description = "Недопустимое значение в поле файла."),
        @ApiResponse(responseCode = "409",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)),
                description = "Проблема с файлом (битый)."),
        @ApiResponse(responseCode = "429",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)),
                description = "Файл с таким именем уже существует."),
        @ApiResponse(responseCode = "500",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)),
                description = "Ошибка на стороне сервера. Не удалось сохранить файл")
})
public class UploadFileController {

    private final FileService fileService;
    private final ResourceBundle resourceBundle;

    @Operation(summary = "Загрузить файл",
            description = "Сохраняет файл на сервер. Перед сохранением файл проходит проверку на соответствие заданным правилам.")
    @PostMapping(value = "/upload",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<String> uploadFile(@Parameter(description = "Файл с именем в формате car_dd.mm.yyyy.csv или train_dd.mm.yyyy.csv. Регистр допускается любой.")
                                             @RequestParam("file") MultipartFile file) {

        fileService.saveAndValidateFile(file);
        return ResponseEntity.ok(resourceBundle.getString("FILE_SAVED"));
    }

    @GetMapping()
    public String getIndex(){
        return "index";
    }
}
