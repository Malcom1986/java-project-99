package hexlet.code.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import org.openapitools.jackson.nullable.JsonNullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusDTO {

    private Long id;

    @NotBlank
    private JsonNullable<String> name;

    @NotBlank
    private JsonNullable<String> slug;

    private LocalDate createdAt;
}
