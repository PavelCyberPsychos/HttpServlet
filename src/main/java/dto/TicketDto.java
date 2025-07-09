package dto;

import lombok.Builder;
import lombok.Value;

import java.util.Objects;

@Value
@Builder
public class TicketDto {
    Long id;
    Long flight_id;
    Long seat_no;


}
