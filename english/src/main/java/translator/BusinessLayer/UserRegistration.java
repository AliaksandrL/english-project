package translator.web;

import translator.DataLayer.DataRetrievers.UserRetriever;
import translator.DataLayer.DbEntities.DbUser;

import java.util.Scanner;

/**
 * Created by Администратор on 23.06.2017.
 */
public class  UserRegistration {
    private String errorMsg = null;
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
            for (DbUser curUser : userRetriever.getAll()) {
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
        boolean saveResult = userRetriever.save(newUser);
        if(!saveResult)
            errorMsg = String.format("Произошла ошибка регистации пользователя '%s'! Попрбуйте снова.", newUser.userName);
    }
}
