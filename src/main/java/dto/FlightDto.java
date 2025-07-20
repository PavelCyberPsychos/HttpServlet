package dto;

import lombok.Builder;
import lombok.Value;

import java.util.Objects;

@Value
@Builder
public class FlightDto {
    Long id;
    String description;

}
