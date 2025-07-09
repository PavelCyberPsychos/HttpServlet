package dao;

import entity.Ticket;
import util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketDao implements Dao<Long, Ticket> {
    private final static TicketDao INSTANCE = new TicketDao();
    private final static FlightDao flightDao = FlightDao.getInstance();

    private final static String SAVE_SQL = """
                    INSERT INTO ticket(cost,flight_id,passenger_name,passport_no,seat_no)
            VALUES (?,?,?,?,?)
            """;
    private final static String DELETE_SQL = """
                    DELETE FROM ticket WHERE id=?
            """;
    private final static String FIND_ALL_SQL = """
                                    SELECT t.id,cost,t.flight_id,t.passenger_name,t.passport_no,t.seat_no,
                                    f.flight_no, f.departure_date, f.departure_airport_code, f.arrival_date,
                                     f.arrival_airport_code, f.aircraft_id, f.status
                        FROM ticket t
            JOIN flight f on f.id=t.flight_id
            """;
    private final static String FIND_SQL = FIND_ALL_SQL + """
            WHERE t.id=?
            """;
    private final static String UPDATE_SQL = """
                    UPDATE ticket SET 
             cost=?,
             flight_id=?,
             passenger_name=?,
             passport_no=?,
             seat_no=?
            WHERE id=?
            """;
    private final static String FIND_ALL_BY_FLIGHT_ID = """
                        SELECT * FROM ticket
            WHERE flight_id = ?
            """;

    public List<Ticket> findAllByFlightId(Long flight_id) {
        try (var connection = ConnectionManager.get(); var statement = connection.prepareStatement(FIND_ALL_BY_FLIGHT_ID)) {
            statement.setLong(1, flight_id);
            List<Ticket> tickets = new ArrayList<>();
            var result = statement.executeQuery();

            while (result.next()) {
                tickets.add(getTicket(result));
            }
            return tickets;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Ticket ticket) {
        try (var connection = ConnectionManager.get(); var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setBigDecimal(1, ticket.getCost());
            statement.setLong(2, ticket.getFlight_id());
            statement.setString(3, ticket.getPassenger_name());
            statement.setString(4, ticket.getPassport_no());
            statement.setLong(5, ticket.getSeat_no());
            statement.setLong(6, ticket.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public Ticket save(Ticket ticket) {
        try (var connection = ConnectionManager.get(); var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setBigDecimal(1, ticket.getCost());
            statement.setLong(2, ticket.getFlight_id());
            statement.setString(3, ticket.getPassenger_name());
            statement.setString(4, ticket.getPassport_no());
            statement.setLong(5, ticket.getSeat_no());

            statement.executeUpdate();
            var KEY = statement.getGeneratedKeys();
            if (KEY.next())
                ticket.setId(KEY.getLong("id"));
            return ticket;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get(); var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ticket> findAll() {
        try (var connection = ConnectionManager.get(); var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Ticket> tickets = new ArrayList<>();
            var result = statement.executeQuery();

            while (result.next()) {
                tickets.add(
                        getTicket(result)
                );
            }

            return tickets;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private static Ticket getTicket(ResultSet result) throws SQLException {

        return new Ticket(result.getLong("id"),
                result.getBigDecimal("cost"),
                result.getLong("flight_id"),
                result.getString("passenger_name"),
                result.getString("passport_no"),
                result.getLong("seat_no"));
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        try (var connection = ConnectionManager.get(); var statement = connection.prepareStatement(FIND_SQL)) {
            statement.setLong(1, id);
            var result = statement.executeQuery();
            Ticket ticket = null;
            if (result.next()) {
                ticket = getTicket(result);
            }
            return Optional.ofNullable(ticket);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static TicketDao getINSTANCE() {
        return INSTANCE;
    }

    private TicketDao() {

    }
}

