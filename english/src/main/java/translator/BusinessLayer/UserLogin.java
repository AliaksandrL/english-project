package translator.BusinessLayer;

import translator.DataLayer.DataRetrievers.UserRetriever;
import translator.DataLayer.DbEntities.DbUser;
import translator.web.Dispatcher;
import translator.web.HttpMethod;
import translator.web.ModelAndView;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Администратор on 08.07.2017.
 */
public class UserLogin {


    private int blockTimeInMinutes = 2;
    private String errorMsg=null;
    private Dispatcher dispatcher = Dispatcher.getInstance();
    private Map<String, String[]> paramMap = new HashMap<String, String[]>();

    public DbUser user;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void ViewUserLogin() {
        Scanner scanner = new Scanner(System.in);
        String username, password;
        int attempts;

        System.out.println("    *** Для входа введите логин и пароль. ***");
        do {
            errorMsg = null;
            attempts = 0;
            System.out.println("    ==================================================");
            System.out.println("        Логин: ");
            username = scanner.next();

            paramMap.put("arg0", new String[]{username});
            user = (DbUser)dispatcher.dispatch("/users/find", HttpMethod.GET, paramMap).getParameter("user");
            paramMap.clear();

            if (user == null) {
                errorMsg = String.format("Пользователя '%s' нет в системе!", username);
                System.out.println(errorMsg);
                return;
            }
            Calendar calendar = Calendar.getInstance();
            Date nowDateTime = new Date(calendar.getTime().getTime());
            if(user.blockTime != null && !nowDateTime.after(user.blockTime)) {
                errorMsg = String.format("Пользователь '%s' заблокирован. Блокировка закончится ", username) + user.blockTime;
                System.out.println(errorMsg);
            }
        }
        while (errorMsg != null);

        do {
            if(attempts>3) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MINUTE, blockTimeInMinutes);
                user.blockTime = new Timestamp(calendar.getTime().getTime());
                if(dispatcher.dispatchGeneric("/users/update", HttpMethod.UPDATE, user).getParameter("user") != null){
                    errorMsg = String.format("Превышено максимальное количество попыток входа. Аккаунт заблокирован на %d минут", blockTimeInMinutes);
                    break;
                }
            }
            errorMsg = null;
            System.out.println(" ");
            System.out.println("        Пароль: ");
            password = (scanner.next());
            System.out.println("    ==================================================");
            if (!user.password.equals(password)) {
                errorMsg = String.format("Неверный пароль для пользователя '%s'", username);
                System.out.println(errorMsg);
            }
            attempts++;
        }
        while (errorMsg != null);
    }
}
