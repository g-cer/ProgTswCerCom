package model.acquisto;

import model.DAOInterface;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class AcquistoDAO implements DAOInterface<AcquistoBean, Integer> {
    private static final String TABLE_NAME = "Acquisto";
    private static DataSource ds;

    //Connessione al database attraverso datasource
    static {
        try {
            Context init = new InitialContext();
            Context env = (Context) init.lookup("java:comp/env");
            ds = (DataSource) env.lookup("jdbc/keyItaly");
        } catch (NamingException e) {
        	e.printStackTrace();
        }
    }

    //Metodi Interfaccia DAO
    public AcquistoBean doRetrieveByKey(Integer code) throws SQLException {
        AcquistoBean acquistoBean = new AcquistoBean();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE IDAcquisto = ?";

        try (Connection connection = ds.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            setAcquisto(resultSet, acquistoBean);
        }
        return acquistoBean;
    }

    //Restituisce la Collection di acquisti compresi in un ordine
    public Collection<AcquistoBean> doRetrieveByOrdine(Integer ordine) throws SQLException {
        Collection<AcquistoBean> acquisti = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE IDOrdine = ?";

        try (Connection connection = ds.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, ordine);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                AcquistoBean acquistoBean = new AcquistoBean();
                setAcquisto(resultSet, acquistoBean);
                acquisti.add(acquistoBean);
            }
        } 
        return acquisti;
    }

    //doRetrieveAll non necessario per gli acquisti
    public Collection<AcquistoBean> doRetrieveAll(String order) {
        return null;
    }

    public void doSave(AcquistoBean acquistoBean) throws SQLException {
        String query =  "INSERT INTO " + TABLE_NAME + " (IDOrdine, IDProdotto, quantita, immagine, prezzoAq, ivaAq) "+
                        "VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection connection = ds.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setAcquistoStatement(acquistoBean, preparedStatement);
            preparedStatement.executeUpdate();
        } 
    }

    //Non si può aggiornare un ordine compiuto
    public void doUpdate(AcquistoBean product) {
    }
    
    //Non si può cancellare un ordine compiuto
    public boolean doDelete(Integer code) {
        return false;
    }
    
    //Metodi di utilità
    
    //Imposta le proprietà del Bean in unico metodo
    private void setAcquisto(ResultSet resultSet, AcquistoBean acquistoBean) throws SQLException {
        acquistoBean.setIDAcquisto(resultSet.getInt("IDAcquisto"));
        acquistoBean.setIDOrdine(resultSet.getInt("IDOrdine"));
        acquistoBean.setIDProdotto(resultSet.getInt("IDProdotto"));
        acquistoBean.setQuantita(resultSet.getInt("quantita"));
        acquistoBean.setImmagine(resultSet.getString("immagine"));
        acquistoBean.setPrezzoAq(resultSet.getBigDecimal("prezzoAq"));
        acquistoBean.setIvaAq(resultSet.getInt("ivaAq"));
    }
    
    //Imposta i parametri del PreparedStatement in un unico metodo
    private void setAcquistoStatement(AcquistoBean acquistoBean, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, acquistoBean.getIDOrdine());
        preparedStatement.setInt(2, acquistoBean.getIDProdotto());
        preparedStatement.setInt(3, acquistoBean.getQuantita());
        preparedStatement.setString(4, acquistoBean.getImmagine());
        preparedStatement.setBigDecimal(5, acquistoBean.getPrezzoAq());
        preparedStatement.setInt(6, acquistoBean.getIvaAq());
    }
}
