package entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Ticket {
    private Long id;
    private BigDecimal cost;
    private Long flight_id;
    private String passenger_name;
    private String passport_no;
    private Long seat_no;

    public Ticket(Long id, BigDecimal cost, Long flight_id, String passenger_name, String passport_no, Long seat_no) {
        this.id = id;
        this.cost = cost;
        this.flight_id = flight_id;
        this.passenger_name = passenger_name;
        this.passport_no = passport_no;
        this.seat_no = seat_no;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Long getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(Long flight_id) {
        this.flight_id = flight_id;
    }

    public String getPassenger_name() {
        return passenger_name;
    }

    public void setPassenger_name(String passenger_name) {
        this.passenger_name = passenger_name;
    }

    public String getPassport_no() {
        return passport_no;
    }

    public void setPassport_no(String passport_no) {
        this.passport_no = passport_no;
    }

    public Long getSeat_no() {
        return seat_no;
    }

    public void setSeat_no(Long seat_no) {
        this.seat_no = seat_no;
    }

    @Override
    public String toString() {
        return "Ticket{" +
               "id=" + id +
               ", cost=" + cost +
               ", flight_id=" + flight_id +
               ", passenger_name='" + passenger_name + '\'' +
               ", passport_no='" + passport_no + '\'' +
               ", seat_no=" + seat_no +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) && Objects.equals(cost, ticket.cost) && Objects.equals(flight_id, ticket.flight_id) && Objects.equals(passenger_name, ticket.passenger_name) && Objects.equals(passport_no, ticket.passport_no) && Objects.equals(seat_no, ticket.seat_no);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cost, flight_id, passenger_name, passport_no, seat_no);
    }
}
