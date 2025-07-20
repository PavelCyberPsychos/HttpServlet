package locale;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleRunner {
    public static void main(String[] args) {
        Locale locale = new Locale("ru","RU");

        System.out.println(locale.US);
        System.out.println(locale.getDefault());

        var rb = ResourceBundle.getBundle("translations");
        System.out.println(rb.getString("page.login.password"));
    }





}
