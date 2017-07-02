package translator.DataLayer.DataRetrievers;

import org.apache.log4j.Logger;
import translator.DataLayer.DbEntities.DbTopic;
import translator.dao.AbstractDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Created by Администратор on 29.06.2017.
 */
public class TopicRetriever extends AbstractDAO<DbTopic, String> {
    private Logger logger = Logger.getLogger(this.getClass());
    private String SAVE_QUERY = "INSERT INTO topics (TopicName) VALUES (?)";
    private String SELECT_BY_ID_QUERY = "SELECT TopicId, TopicName FROM topics WHERE TopicId=?";
    private String SELECT_ALL_QUERY = "SELECT * FROM topics";
    private String SELECT_BY_TOPICNAME = "SELECT * FROM topics WHERE TopicName=?";

    public boolean save(DbTopic topic) {
        try (PreparedStatement st = connection.prepareStatement(SAVE_QUERY)) {
            st.setString(1, topic.topicName);

            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Can't save topic with name: " + topic.topicName);
            return false;
        }
    }

    public DbTopic find(long id) {
        DbTopic topic = new DbTopic();

        try (PreparedStatement st = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            st.setLong(1, id);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                topic.topicId=rs.getInt(1);
                topic.topicName=rs.getString(2);
            }
            return topic;
        } catch (SQLException e) {
            logger.error("Can't find topic with id: " + id);
            return null;
        }
    }

    public Iterable<DbTopic> getAll (){
        ArrayList<DbTopic> topics = new ArrayList<DbTopic>();
        DbTopic topic;

        try (PreparedStatement st = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                topic = new DbTopic();
                topic.topicId=rs.getInt(1);
                topic.topicName=rs.getString(2);
                topics.add(topic);
            }
            return topics;
        } catch (SQLException e) {
            logger.error("Can't get all topics, scuko");
            return null;
        }
    }

    public Iterable<DbTopic> getByField(String topicName){
        ArrayList<DbTopic> topics = new ArrayList<DbTopic>();
        DbTopic topic;

        try (PreparedStatement st = connection.prepareStatement(SELECT_BY_TOPICNAME)) {
            st.setString(1, topicName);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                topic = new DbTopic();
                topic.topicId=rs.getInt(1);
                topic.topicName=rs.getString(2);
                topics.add(topic);
            }
            return topics;
        } catch (SQLException e) {
            logger.error("Can't find user with topic name: " + topicName);
            return null;
        }
    }
}

