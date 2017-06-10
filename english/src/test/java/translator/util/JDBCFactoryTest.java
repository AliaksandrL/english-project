package translator.util;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

/**
 * Created by Lenovo on 08.06.2017.
 */
public class JDBCFactoryTest {

	@Test
	public void getConnection() throws Exception {
		Connection connection = JDBCFactory.getConnection();

		Assert.assertNotNull(connection);
	}

}