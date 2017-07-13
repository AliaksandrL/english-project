package translator.DataLayer.DataRetrievers;

import org.apache.log4j.Logger;
import translator.DataLayer.DbEntities.DbWord;
import translator.dao.AbstractDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Created by Администратор on 29.06.2017.
 */
public class WordRetriever extends AbstractDAO<DbWord, Integer> {
    private Logger logger = Logger.getLogger(this.getClass());
    private String SAVE_QUERY = "INSERT INTO words (TopicId, EnglishWord, RussianWord) VALUES (?, ?, ?)";
    private String UPDATE_QUERY = "UPDATE words SET TopicId=?, UserWord=?, RussianWord=? WHERE WordId=?";
    private String SELECT_BY_ID_QUERY = "SELECT WordId, TopicId, EnglishWord, RussianWord FROM words WHERE WordId=?";
    private String SELECT_ALL_QUERY = "SELECT * FROM words";
    private String SELECT_BY_TOPICID = "SELECT * FROM words WHERE TopicId=?";

    public boolean save(DbWord word) {
        try (PreparedStatement st = connection.prepareStatement(SAVE_QUERY)) {
            st.setInt(1, word.topicId);
            st.setString(2, word.englishWord);
            st.setString(3, word.russianWord);

            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Can't save user with name: " + word.englishWord);
            return false;
        }
    }

    public boolean update(DbWord dbWord) {
        try (PreparedStatement st = connection.prepareStatement(UPDATE_QUERY)) {
            st.setInt(1, dbWord.topicId);
            st.setString(2, dbWord.englishWord);
            st.setString(3, dbWord.russianWord);
            st.setInt(4, dbWord.wordId);

            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Can't update word with user id: " + dbWord.wordId);
            return false;
        }
    }

    public DbWord find(long id) {
        DbWord word = new DbWord();

        try (PreparedStatement st = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            st.setLong(1, id);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                word.wordId=rs.getInt(1);
                word.topicId=rs.getInt(2);
                word.englishWord=rs.getString(3);
                word.russianWord=rs.getString(4);
            }
            return word;
        } catch (SQLException e) {
            logger.error("Can't find user with id: " + id);
            return null;
        }
    }

    public Iterable<DbWord> getAll (){
        ArrayList<DbWord> words = new ArrayList<DbWord>();
        DbWord word;

        try (PreparedStatement st = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                word = new DbWord();
                word.wordId=rs.getInt(1);
                word.topicId=rs.getInt(2);
                word.englishWord=rs.getString(3);
                word.russianWord=rs.getString(4);
                words.add(word);
            }
            return words;
        } catch (SQLException e) {
            logger.error("Can't get all users, scuko");
            return null;
        }
    }

    public Iterable<DbWord> getByField(Integer topicId){
        ArrayList<DbWord> words = new ArrayList<DbWord>();
        DbWord word;

        try (PreparedStatement st = connection.prepareStatement(SELECT_BY_TOPICID)) {
            st.setInt(1, topicId);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                word = new DbWord();
                word.wordId=rs.getInt(1);
                word.topicId=rs.getInt(2);
                word.englishWord=rs.getString(3);
                word.russianWord=rs.getString(4);
                words.add(word);
            }
            return words;
        } catch (SQLException e) {
            logger.error("Can't find user with topicId: " + topicId);
            return null;
        }
    }
}

