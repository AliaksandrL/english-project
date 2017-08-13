package translator.BusinessLayer;

import translator.DataLayer.DataRetrievers.UserRetriever;
import translator.DataLayer.DbEntities.DbUser;
import translator.web.Dispatcher;
import translator.web.HttpMethod;
import java.util.Scanner;

/**
 * Created by Администратор on 23.06.2017.
 */
public class  UserRegistration {
    private String errorMsg = null;
    private Dispatcher dispatcher = Dispatcher.getInstance();
    public DbUser newUser;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void ViewUserRegistration() {
        Scanner scanner = new Scanner(System.in);
        UserRetriever userRetriever = new UserRetriever();
        newUser = new DbUser();
        String username, password;
        boolean isUnique = true;
        System.out.println("    *** Для первого входа пройдите регистрацию. ***");

        do {
            errorMsg = null;
            isUnique = true;
            System.out.println("    ==================================================");
            System.out.println("        Логин: ");
            username = scanner.next();
            newUser.userName = username;
            for (DbUser curUser : (Iterable<DbUser>)dispatcher.dispatch("/users/all", HttpMethod.GET, null).getParameter("allUsers")) {
                if (curUser.userName.equals(newUser.userName)) {
                    errorMsg = String.format("Пользователь '%s' уже зарегестрирован в системе!", newUser.userName);
                    System.out.println(errorMsg);
                    isUnique = false;
                    break;
                }
            }
            if (isUnique == true)
                errorMsg = null;
        }
        while (errorMsg != null);

        System.out.println(" ");
        System.out.println("        Пароль: ");
        password = (scanner.next());
        newUser.password = password;
        System.out.println("    ==================================================");
        newUser = (DbUser) dispatcher.dispatchGeneric("/users/registernew", HttpMethod.POST, newUser).getParameter("user");
        if(newUser == null)
            errorMsg = String.format("Произошла ошибка регистации пользователя '%s'! Попрбуйте снова.", newUser.userName);
    }
}
