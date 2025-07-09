package util;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtil {
    private static final Properties properties = new Properties();

    static {
        load();
    }

    private PropertiesUtil() {
    }

    public static void load() {

        try (var propertiesStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(propertiesStream);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
