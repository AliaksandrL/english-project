package translator.DataLayer.DataRetrievers;

import org.apache.log4j.Logger;
import translator.DataLayer.DbEntities.DbUserWord;
import translator.dao.AbstractDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Created by Администратор on 29.06.2017.
 */
public class UsersWordsRetriever extends AbstractDAO<DbUserWord, Integer> {
    private Logger logger = Logger.getLogger(this.getClass());
    private String SAVE_QUERY = "INSERT INTO userswords (UserId, WordId, CurrentTranslateCount) VALUES (?, ?, ?)";
    private String UPDATE_QUERY = "UPDATE userswords SET CurrentTranslateCount=? WHERE UserId=? AND WordId=?";
    private String SELECT_BY_ID_QUERY = "SELECT userwordId, UserId, WordId, CurrentTranslateCount FROM userswords WHERE userwordId=?";
    private String SELECT_ALL_QUERY = "SELECT * FROM userswords";
    private String SELECT_BY_USERIDNAME = "SELECT * FROM userswords WHERE UserId=?";

    public boolean save(DbUserWord userword) {
        try (PreparedStatement st = connection.prepareStatement(SAVE_QUERY)) {
            st.setInt(1, userword.userId);
            st.setInt(2, userword.wordId);
            st.setInt(3, userword.countCorrect);

            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Can't save user word with user id: " + userword.userId);
            return false;
        }
    }

    public boolean update(DbUserWord userword) {
        try (PreparedStatement st = connection.prepareStatement(UPDATE_QUERY)) {
            st.setInt(1, userword.countCorrect);
            st.setInt(2, userword.userId);
            st.setInt(3, userword.wordId);

            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Can't update user word with user id: " + userword.userId);
            return false;
        }
    }

    public DbUserWord find(long id) {
        DbUserWord userword = new DbUserWord();

        try (PreparedStatement st = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            st.setLong(1, id);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                userword.userWordId=rs.getInt(1);
                userword.userId=rs.getInt(2);
                userword.wordId=rs.getInt(3);
                userword.countCorrect=rs.getInt(4);
            }
            return userword;
        } catch (SQLException e) {
            logger.error("Can't find user word with id: " + id);
            return null;
        }
    }

    public Iterable<DbUserWord> getAll (){
        ArrayList<DbUserWord> userWords = new ArrayList<DbUserWord>();
        DbUserWord userword;

        try (PreparedStatement st = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                userword = new DbUserWord();
                userword.userWordId=rs.getInt(1);
                userword.userId=rs.getInt(2);
                userword.wordId=rs.getInt(3);
                userword.countCorrect=rs.getInt(4);
                userWords.add(userword);
            }
            return userWords;
        } catch (SQLException e) {
            logger.error("Can't get all users words, scuko");
            return null;
        }
    }

    public Iterable<DbUserWord> getByField(Integer userId){
        ArrayList<DbUserWord> userwords = new ArrayList<DbUserWord>();
        DbUserWord userword;

        try (PreparedStatement st = connection.prepareStatement(SELECT_BY_USERIDNAME)) {
            st.setInt(1, userId);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                userword = new DbUserWord();
                userword.userWordId=rs.getInt(1);
                userword.countCorrect=rs.getInt(2);
                userword.userId=rs.getInt(3);
                userword.wordId=rs.getInt(4);
                userwords.add(userword);
            }
            return userwords;
        } catch (SQLException e) {
            logger.error("Can't find user words by user with id: " + userId);
            return null;
        }
    }
}

