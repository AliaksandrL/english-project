package translator.service;

import translator.dao.UserDAO;
import translator.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnit44Runner;

import static org.junit.Assert.*;

/**
 * Created by Lenovo on 08.06.2017.
 */
@RunWith(MockitoJUnit44Runner.class)
public class UserServiceTest {
	@Mock
	private UserDAO userDAO;

	@InjectMocks
	private UserService userService;

	@Test
	public void findUserById() throws Exception {
		//when
		User user = new User();
		user.setId(100L);
		user.setName("1");
		user.setAge(12);

		Mockito.when(userDAO.find(100L)).thenReturn(user);

		//then
		User actual = userService.findUserById("100");

		//verify
		Assert.assertEquals(100L, actual.getId());
		Assert.assertEquals("1", actual.getName());
		Assert.assertEquals(new Integer(12), actual.getAge());
	}

	@Test
	public void saveUser() throws Exception {
	}

}