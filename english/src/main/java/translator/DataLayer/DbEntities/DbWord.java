package translator.DataLayer.DbEntities;

import java.io.Serializable;

/**
 * Created by Администратор on 23.06.2017.
 */
public class DbWord implements Serializable {
    public int wordId;
    public int topicId;
    public String englishWord;
    public String russianWord;

    @Override
    public String toString() {
        return "word{" +
                "wordId=" + wordId +
                ", topicId='" + topicId + '\'' +
                ", englishWord=" + englishWord +
                ", russianWord='" + russianWord+
                '}';
    }
}
