package translator.web;

import translator.util.StaticValues;

import java.util.Scanner;

/**
 * Created by Администратор on 02.07.2017.
 */
public class LoginMenu {
    private boolean repeat = true;
    public void ViewLoginMenu() {
        UserRegistration userRegistration = new UserRegistration();
        UserLogin userLogin = new UserLogin();
        Scanner scanner = new Scanner(System.in);
        byte n;
        for(;repeat;) {
            System.out.println("    *** Выберите действие которое хотите совершить: ***");
            System.out.println("    ==================================================");
            System.out.println("        1. Войти");
            System.out.println(" ");
            System.out.println("        2. Регистрация");
            System.out.println(" ");
            System.out.println("        3. Выход");
            System.out.println("    ==================================================");
            n = scanner.nextByte();

            switch (n) {
                case 1:
                    userLogin.ViewUserLogin();
                    if (userLogin.getErrorMsg() != null) {
                        System.out.println(userLogin.getErrorMsg());
                        break;
                    }
                    StaticValues.setAuthenticatedUserId(userLogin.user.id);
                    this.repeat=false;
                    break;
                case 2:
                    userRegistration.ViewUserRegistration();
                    if (userRegistration.getErrorMsg() != null) {
                        System.out.println(userLogin.getErrorMsg());
                        break;
                    }
                    StaticValues.setAuthenticatedUserId(userRegistration.newUser.id);
                    this.repeat=false;
                    break;
                case 3:
                    System.out.println("До свидания!");
                    break;
                default:
                    System.out.println("!!!Попробуйте еще раз!!! =)");
                    System.out.println("");
            }
        }
    }
}
