package translator.DataLayer.DbEntities;

import java.io.Serializable;

/**
 * Created by Администратор on 23.06.2017.
 */
public class DbTopic implements Serializable {
    public int topicId;
    public String topicName;

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + topicId +
                ", name='" + topicName+
                '}';
    }
}
