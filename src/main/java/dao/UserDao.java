package dao;

import entity.Gender;
import entity.Role;
import entity.User;
import lombok.SneakyThrows;
import util.ConnectionManager;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<Integer, User> {

    private static UserDao instance = new UserDao();


    private static final String SAVE_SQL = """
            INSERT INTO users (name,birthday,email,password,role,gender,image) VALUES (?,?,?,?,?,?,?)
            """;

    public static final String GET_BY_EMAIL_AND_PASSWORD_SQL = """
            SELECT * from users
            WHERE email = ?  AND  password = ?
            """;


    public static UserDao getInstance() {
        return instance;
    }

    @SneakyThrows
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (var connection = ConnectionManager.get(); var statement = connection.prepareStatement(GET_BY_EMAIL_AND_PASSWORD_SQL)) {
            statement.setString(1, email);
            statement.setString(2, password);
            var resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = buildEntity(resultSet);
            }
            return Optional.ofNullable(user);
        }
    }


    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public void update(User entity) {

    }

    @Override
    @SneakyThrows
    public User save(User entity) {
        try (var connection = ConnectionManager.get(); var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            statement.setObject(1, entity.getName());
            statement.setObject(2, entity.getBirthday());
            statement.setObject(3, entity.getEmail());
            statement.setObject(4, entity.getPassword());
            statement.setObject(5, entity.getRole().name());
            statement.setObject(6, entity.getGender().name());
            statement.setObject(7, entity.getImage());
            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getObject("id", Integer.class));
            }
            return entity;
        }
    }

    private User buildEntity(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getObject("id", Integer.class))
                .name(resultSet.getObject("name", String.class))
                .email(resultSet.getObject("email", String.class))
                .password(resultSet.getObject("password", String.class))
                .image(resultSet.getObject("image", String.class))
                .birthday(resultSet.getObject("birthday", Date.class).toLocalDate())
                .role(Role.find(resultSet.getObject("role", String.class)).orElse(null))
                .gender(Gender.valueOf(resultSet.getObject("gender", String.class)))
                .build();
    }
}
