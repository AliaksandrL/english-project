package translator.dao;

import translator.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Lenovo on 08.06.2017.
 */
public class UserDAOTest {

	private UserDAO dao;

	@Before
	public void init() {
		dao = new UserDAO();
	}
	@Test
	public void shouldReturnTrue() throws Exception {
		User user = new User();
		user.setAge(12);
		user.setName("Petya");

		boolean result = dao.save(user);

		Assert.assertTrue(result);
	}

	@Test
	public void shouldReturnUserById() {
		User actual = dao.find(1L);

		Assert.assertEquals("Petya", actual.getName());
		Assert.assertEquals(new Integer(12), actual.getAge());
	}

}