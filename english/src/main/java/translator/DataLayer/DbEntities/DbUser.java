package translator.DataLayer.DbEntities;

/**
 * Created by Администратор on 23.06.2017.
 */
public class DbUser {
    public int id;
    public String userName;
    public String password;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + userName + '\'' +
                ", password=" + password +
                '}';
    }
}
