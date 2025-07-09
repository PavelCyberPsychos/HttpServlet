package dao;

import entity.Flight;
import entity.FlightStatus;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDao implements Dao<Long, Flight> {
    private static final FlightDao INSTANSE = new FlightDao();

    private FlightDao() {
    }

    public static FlightDao getInstance() {
        return INSTANSE;
    }

    private final static String SQL_findAll = """
              SELECT id,flight_no,departure_date,departure_airport_code,arrival_date,
                        arrival_airport_code,aircraft_id,status FROM flight
            """;
    private final static String SQL_findById = """
                    SELECT * FROM flight
            WHERE id = ?
            """;
    private final static String SQL_delete = """
                        DELETE  FROM flight 
            WHERE id=?
            """;
    private final static String SQL_save = """
                    INSERT INTO flight (aircraft_id,arrival_airport_code,arrival_date,departure_airport_code,departure_date,flight_no,status)
            VALUES(?,?,?,?,?,?,?)
            """;
    private final static String SQL_update = """
                       UPDATE flight set 
                        aircraft_id=?,
                        arrival_airport_code=?,
                        arrival_date=?,
                        departure_airport_code=?,
                        departure_date=?,
                        flight_no=?
                        status = ? 
            """;

    private Flight getFlight(ResultSet resultSet) {
        try {
            Long id = resultSet.getLong("id");
            Long aircraft_id = resultSet.getLong("aircraft_id"); // Используйте getLong
            String arrival_airport_code = resultSet.getString("arrival_airport_code");
            String departure_airport_code = resultSet.getString("departure_airport_code");
            String flight_no = resultSet.getString("flight_no");

            // LocalDateTime (обработка NULL)
            LocalDateTime arrival_date = null;
            Timestamp arrivalTimestamp = resultSet.getTimestamp("arrival_date");
            if (arrivalTimestamp != null) {
                arrival_date = arrivalTimestamp.toLocalDateTime();
            }

            LocalDateTime departure_date = null;
            Timestamp departureTimestamp = resultSet.getTimestamp("departure_date");
            if (departureTimestamp != null) {
                departure_date = departureTimestamp.toLocalDateTime();
            }

            FlightStatus status = null; // Значение по умолчанию
            try {
                String statusString = resultSet.getString("status");
                if (statusString != null) {
                    status = FlightStatus.valueOf(statusString.toUpperCase());
                } else {
                    System.err.println("Status is NULL in DB for flight id: " + id + ". Using default UNKNOWN.");
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid status value in DB for flight id: " + id + ": " + resultSet.getString("status") + ". Using default UNKNOWN.");
                // Логирование или обработка ошибки. Например, можно присвоить статус по умолчанию.
            }

            Flight flight = new Flight(id, aircraft_id, arrival_airport_code, arrival_date, departure_airport_code, departure_date, flight_no, status);
            System.out.println("Flight object created: " + flight); // Логируем объект

            return flight;

        } catch (SQLException e) {
            System.err.println("SQLException in getFlight(): " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Flight> findAll() {
        try (var connection = ConnectionManager.get(); var statement = connection.prepareStatement(SQL_findAll)) {
            List<Flight> list = new ArrayList<>();
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(getFlight(resultSet));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Flight> findById(Long id) {
        try (var connection = ConnectionManager.get(); var statement = connection.prepareStatement(SQL_findById)) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            Flight flight = null;
            while (resultSet.next()) {
                flight = getFlight(resultSet);
            }
            return Optional.ofNullable(flight);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get(); var statement = connection.prepareStatement(SQL_delete)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Flight entity) {
        try (var connection = ConnectionManager.get(); var statement = connection.prepareStatement(SQL_update)) {
            statement.setObject(1, entity.getAircraft_id());
            statement.setObject(2, entity.getArrival_airport_code());
            statement.setObject(3, entity.getArrival_date());
            statement.setObject(4, entity.getDeparture_airport_code());
            statement.setObject(5, entity.getDeparture_date());
            statement.setObject(6, entity.getFlight_no());
            statement.setObject(7, entity.getStatus());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Flight save(Flight entity) {
        try (var connection = ConnectionManager.get(); var statement = connection.prepareStatement(SQL_save, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getFlight_no());
            statement.setTimestamp(2, Timestamp.valueOf(entity.getDeparture_date()));
            statement.setString(3, entity.getDeparture_airport_code());
            statement.setTimestamp(4, Timestamp.valueOf(entity.getArrival_date()));
            statement.setString(5, entity.getArrival_airport_code());
            statement.setLong(6, entity.getAircraft_id());
            statement.setString(7, entity.getStatus().toString());
            statement.executeUpdate();
            var KEY = statement.getGeneratedKeys();
            if (KEY.next()) {
                entity.setId(KEY.getLong("id"));
            }
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}

