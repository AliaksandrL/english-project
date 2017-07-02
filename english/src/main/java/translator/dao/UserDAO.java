package translator.dao;

import translator.DataLayer.DbEntities.DbUser;
import translator.entity.User;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Lenovo on 08.06.2017.
 */
public class UserDAO extends AbstractDAO<User,String> {
	private Logger logger = Logger.getLogger(this.getClass());
	private String SAVE_QUERY = "INSERT INTO `users` (`name`, `age`) VALUES (?, ?)";
	private String SELECT_BY_ID_QUERY = "SELECT Id, UserName, Password FROM users WHERE Id=?";

	public boolean save(User user) {
		try (PreparedStatement st = connection.prepareStatement(SAVE_QUERY)) {
			st.setString(1, user.getName());
			st.setInt(2, user.getAge());

			st.executeUpdate();
			return true;
		} catch (SQLException e) {
			logger.error("Can't save user with name: " + user.getName());
			return false;
		}
	}

	public User find(long id) {
		User user = new User();

		try (PreparedStatement st = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
			st.setLong(1, id);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				user.setId(rs.getLong(1));
				user.setName(rs.getString(2));
				user.setAge(rs.getInt(3));
			}
			return user;
		} catch (SQLException e) {
			logger.error("Can't find user with id: " + id);
			return null;
		}
	}


	public Iterable<User> getAll(){
		return new ArrayList<User>();
	}

	public Iterable<User> getByField(String userName) {
		return new ArrayList<User>();
	}
}
