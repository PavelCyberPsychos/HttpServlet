package util;


import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@UtilityClass
public class ConnectionManager {
    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";
    private static final String DRIVER_KEY = "db.driver";
    private static final String SIZE_KEY = "db.size.pool";
    private static final int DEFAULT_SIZE_POOL = 10;
    private static BlockingQueue<Connection> pool;


    static {
        loadDriver();
        poolConnectionProxy();
    }

    private static void loadDriver() {
        try {
            String driver = PropertiesUtil.get(DRIVER_KEY);
            Class.forName(driver); // Загрузка драйвера
            System.out.println("JDBC driver loaded successfully: " + driver); // Логирование
        } catch (ClassNotFoundException e) {
            System.err.println("Не удалось загрузить драйвер JDBC: " + e.getMessage());
            throw new RuntimeException(e); // Критическая ошибка, выбрасываем исключение
        }
    }

    private static void poolConnectionProxy() {
        int size = PropertiesUtil.get(SIZE_KEY) == null ? DEFAULT_SIZE_POOL : Integer.parseInt(PropertiesUtil.get(SIZE_KEY));
        pool = new ArrayBlockingQueue<>(size);

        for (int i = 0; i < size; i++) {
            Connection connection = open();
            var newProxy = (Connection) Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(), new Class<?>[]{Connection.class},
                    (proxy, method, args) -> method.equals("close") ?
                            pool.add((Connection) proxy) : method.invoke(connection, args));

            pool.add(newProxy);
        }
    }

    @SneakyThrows
    public static Connection get() {
        return pool.take();
    }

    @SneakyThrows
    private static Connection open() {

        return DriverManager.getConnection(PropertiesUtil.get(URL_KEY),
                PropertiesUtil.get(USER_KEY),
                PropertiesUtil.get(PASSWORD_KEY));

    }

}
