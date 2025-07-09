package service;

import dao.FlightDao;
import dto.FlightDto;

import java.util.List;
import java.util.stream.Collectors;

public class FlightService {
    private static final FlightService INSTANCE = new FlightService();
    private final FlightDao flightDao = FlightDao.getInstance();

    private FlightService() {
    }

    public static FlightService getInstance() {
        return INSTANCE;
    }

    public List<FlightDto> findAll() {
        return flightDao.findAll().stream()
                .map(flight -> {
                    String description = """
                            %s -  %s -  %s 
                            """.formatted(flight.getDeparture_airport_code(), flight.getArrival_airport_code(), flight.getStatus().toString());
                    System.out.println("Flight description: " + description);  // Логируем описание
                    return FlightDto.builder().id(flight.getId()).description(description).build();
                })
                .collect(Collectors.toList());

    }

}
