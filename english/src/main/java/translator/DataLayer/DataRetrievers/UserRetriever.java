package translator.DataLayer.DataRetrievers;

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.apache.log4j.Logger;
import translator.DataLayer.DbEntities.DbUser;
import translator.dao.AbstractDAO;

import java.io.Serializable;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Администратор on 23.06.2017.
 */
public class UserRetriever extends AbstractDAO<DbUser,String> {
    private Logger logger = Logger.getLogger(this.getClass());
    private String SAVE_QUERY = "INSERT INTO users (UserName, Password) VALUES (?, ?)";
    private String UPDATE_QUERY = "UPDATE users SET UserName=?, Password=?, BlockTime=? WHERE Id=?";
    private String SELECT_BY_ID_QUERY = "SELECT Id, UserName, Password, BlockTime FROM users WHERE Id=?";
    private String SELECT_ALL_QUERY = "SELECT * FROM USERS";
    private String SELECT_BY_USERNAME = "SELECT * FROM users WHERE UserName=?";

    public boolean save(DbUser user) {
        try (PreparedStatement st = connection.prepareStatement(SAVE_QUERY)) {
            Iterable<DbUser> allUsers = this.getAll();
            st.setString(1, user.userName);
            st.setString(2, user.password);

            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Can't save user with name: " + user.userName);
            return false;
        }
    }

    public boolean update(DbUser dbUser) {
        try (PreparedStatement st = connection.prepareStatement(UPDATE_QUERY)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            st.setString(1, dbUser.userName);
            st.setString(2, dbUser.password);
            st.setTimestamp(3, dbUser.blockTime);
            st.setInt(4, dbUser.id);

            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Can't update user with id: " + dbUser.id);
            return false;
        }
    }

    public DbUser find(long id) {
        DbUser user = new DbUser();

        try (PreparedStatement st = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            st.setLong(1, id);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                user.id=rs.getInt(1);
                user.userName=rs.getString(2);
                user.password=rs.getString(3);
                user.blockTime=rs.getTimestamp(4);
            }
            return user;
        } catch (SQLException e) {
            logger.error("Can't find user with id: " + id);
            return null;
        }
    }

    public Iterable<DbUser> getByField(String userName) {
        ArrayList<DbUser> users = new ArrayList<DbUser>();
        DbUser user;

        try (PreparedStatement st = connection.prepareStatement(SELECT_BY_USERNAME)) {
            st.setString(1, userName);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                user = new DbUser();
                user.id=rs.getInt(1);
                user.userName=rs.getString(2);
                user.password=rs.getString(3);
                user.blockTime=rs.getTimestamp(4);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            logger.error("Can't find user with userName: " + userName);
            return null;
        }
    }


    public Iterable<DbUser> getAll (){
        ArrayList<DbUser> users = new ArrayList<DbUser>();
        DbUser user;

        try (PreparedStatement st = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                user = new DbUser();
                user.id=rs.getInt(1);
                user.userName=rs.getString(2);
                user.password=rs.getString(3);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            logger.error("Can't get all users, scuko");
            return null;
        }
    }
}
