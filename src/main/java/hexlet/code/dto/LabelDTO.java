package hexlet.code.dto;

import java.time.LocalDate;

import org.openapitools.jackson.nullable.JsonNullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabelDTO {

    private Long id;

    private JsonNullable<String> name;

    private LocalDate createdAt;
}
