package ru.gorchanyuk.csvvalidator.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"vehicleId", "registrationDate", "expirationDate", "licensePlate", "owner"})
public class Content {

    @NotNull(message = "'Идентификатор транспортного средства' обязательное поле, не может быть пустым.")
    private String vehicleId; // Идентификатор транспортного средства (например, VIN для автомобилей)

    @NotNull(message = "'Дата регистрации' обязательное поле, не может быть пустым.")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate registrationDate; // Дата регистрации транспортного средства

    @NotNull(message = "'Дата окончания действия регистрации' обязательное поле, не может быть пустым.")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate expirationDate; // Дата окончания действия регистрации

    @NotNull(message = "'Государственный номер' обязательное поле, не может быть пустым.")
    @Size(min = 1, max = 10, message = "'Государственный номер' должен содержать от 1 до 10 символов.")
    private String licensePlate; // Государственный номер транспортного средства

    @Nullable
    @Size(min = 1, max = 50, message = "'Хозяин транспортного средства' должно содержать от 1 до 50 символов.")
    private String owner; // Хозяин транспортного средства
}
