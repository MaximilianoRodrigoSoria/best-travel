package ar.com.laboratory.besttravel.api.models.responses;

import ar.com.laboratory.besttravel.util.AeroLine;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FlyResponse  implements Serializable {

    private Long id;
    private Double originLat;
    private Double originLng;
    private Double destinyLat;
    private Double destinyLng;
    private String originName;
    private String destinyName;
    private BigDecimal price;
    private AeroLine aeroLine;
}
