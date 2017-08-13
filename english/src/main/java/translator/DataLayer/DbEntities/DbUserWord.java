package translator.DataLayer.DbEntities;

import java.io.Serializable;

/**
 * Created by Администратор on 23.06.2017.
 */
public class DbUserWord implements Serializable {
    public int userWordId;
    public int userId;
    public int wordId;
    public int countCorrect;

    @Override
    public String toString() {
        return "userword{" +
                "userWordId=" + userWordId +
                ", userId='" + userId + '\'' +
                ", wordId=" + wordId +
                ", countCorrect='" + countCorrect+
                '}';
    }
}
