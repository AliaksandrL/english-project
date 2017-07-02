package translator.dao;

import translator.DataLayer.DbEntities.DbUser;
import translator.util.JDBCFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Lenovo on 08.06.2017.
 */
public abstract class AbstractDAO<T,T1> {
	protected Connection connection = JDBCFactory.getConnection();

	public abstract boolean save(T entity);

	public abstract T find(long id);

	public abstract Iterable<T> getAll();

	//public abstract T findByField(T1 fieldName);

	public abstract Iterable<T> getByField(T1 fieldName);
}
