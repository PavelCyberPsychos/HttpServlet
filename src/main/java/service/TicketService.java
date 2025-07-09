package service;

import dao.TicketDao;
import dto.TicketDto;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


public class TicketService {
    private static final TicketService INSTANCE = new TicketService();
    private final TicketDao ticketDao = TicketDao.getINSTANCE();

    private TicketService() {
    }

    public static TicketService getINSTANCE() {
        return INSTANCE;
    }

    public List<TicketDto> findAllByFlightId(Long flight_id) {
        return ticketDao.findAllByFlightId(flight_id).stream()
                .map(ticket -> TicketDto.builder().id(ticket.getId())
                        .flight_id(ticket.getFlight_id())
                        .seat_no(ticket.getSeat_no()).build()
                ).collect(toList());
    }

}
