package model;

import java.sql.SQLException;
import java.util.Collection;

public interface DAOInterface<T, K> {
	
	public T doRetrieveByKey(K key) throws SQLException;
    public Collection<T> doRetrieveAll(String order) throws SQLException;
    public void doSave(T product) throws SQLException;
    public void doUpdate(T product) throws SQLException;
    public boolean doDelete(K key) throws SQLException;
}
