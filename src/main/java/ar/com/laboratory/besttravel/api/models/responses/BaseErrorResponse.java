package ar.com.laboratory.besttravel.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class BaseErrorResponse {

    private String status;
    private Integer code;
}
