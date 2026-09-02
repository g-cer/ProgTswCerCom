package model.utente;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.DAOInterface;

public class UtenteDAO implements DAOInterface<UtenteBean, String> {
	private static DataSource ds;
	private static final String TABLE_NAME = "Utente";
	
	//Connessione al database attraverso datasource
	static {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			ds = (DataSource) envCtx.lookup("jdbc/keyItaly");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	//Metodi Interfaccia DAO
	public synchronized void doSave(UtenteBean utente) throws SQLException {
		String insertSQL = "INSERT INTO " + TABLE_NAME
				+ " (username, pwd, nome, cognome, email, dataNascita, nomeCarta, cognomeCarta, numCarta, dataScadenza, CVV, cap, via, citta, tipo) " 
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		//try-with-resources garantisce la chiusura delle risorse dopo la sua esecuzione
		try(Connection connection = ds.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)){
			settingUtenteStatement(utente, preparedStatement);
			preparedStatement.executeUpdate();
		}
	}
		
    public synchronized void doUpdate(UtenteBean utente) throws SQLException {
        String query =  "UPDATE " + TABLE_NAME +
                        " SET pwd = ?, nome = ?, cognome = ?,"+
                        " email = ?, dataNascita = ?, numCarta = ?," +
                        " dataScadenza = ?, CVV = ?, nomeCarta = ?, cognomeCarta = ?, cap = ?, via = ?," +
                        " citta = ?, tipo = ?" +
                        " WHERE username = ?";

        try (Connection connection = ds.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
           preparedStatement.setString(1, utente.getPwd());
           preparedStatement.setString(2, utente.getNome());
           preparedStatement.setString(3, utente.getCognome());
           preparedStatement.setString(4, utente.getEmail());
           preparedStatement.setDate(5, Date.valueOf(utente.getDataNascita()));
           preparedStatement.setString(6, utente.getNumCarta());
           if(utente.getDataScadenza() == null){
               preparedStatement.setDate(7, null);
           }
           else{
               preparedStatement.setDate(7, Date.valueOf(utente.getDataScadenza()));
           }
           preparedStatement.setString(8, utente.getCvv());
           preparedStatement.setString(9, utente.getNomeCarta());
           preparedStatement.setString(10, utente.getCognomeCarta());
           preparedStatement.setString(11, utente.getCap());
           preparedStatement.setString(12, utente.getVia());
           preparedStatement.setString(13, utente.getCitta());
           preparedStatement.setString(14, utente.getTipo());
           preparedStatement.setString(15, utente.getUsername());

           preparedStatement.executeUpdate();
        }
    }

	public synchronized boolean doDelete(String key) throws SQLException {
		int result;
        String query = "DELETE FROM " + TABLE_NAME + " WHERE username = ?";

        try (Connection connection = ds.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, key);
            result = preparedStatement.executeUpdate();
        }
        return result != 0;
	}

	public synchronized UtenteBean doRetrieveByKey(String key) throws SQLException {
		UtenteBean utenteBean = new UtenteBean();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";
        
        return getUtenteBean(key, utenteBean, query);
	}
	
	public synchronized UtenteBean doRetrieveByEmail(String email) throws SQLException {
        UtenteBean utenteBean = new UtenteBean();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE email = ?";
        
        return getUtenteBean(email, utenteBean, query);
    }
	
	public synchronized Collection<UtenteBean> doRetrieveAll(String order) throws SQLException {
        Collection<UtenteBean> utenti = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        try (Connection connection = ds.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query);){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                UtenteBean utenteBean = new UtenteBean();
                settingUtente(resultSet, utenteBean);
                utenti.add(utenteBean);
            }
        }
        return utenti;
	}
	
	//Metodi di utilita'
	private UtenteBean getUtenteBean(String code, UtenteBean utenteBean, String query) throws SQLException {
        try (Connection connection = ds.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst())
                return null;
            
            resultSet.next();
            settingUtente(resultSet, utenteBean);
        }
        return utenteBean;
    }

	private void settingUtenteStatement(UtenteBean utente, PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setString(1, utente.getUsername());
		preparedStatement.setString(2, utente.getPwd());
        preparedStatement.setString(3, utente.getNome());
        preparedStatement.setString(4, utente.getCognome());
        preparedStatement.setString(5, utente.getEmail());
        preparedStatement.setDate(6, Date.valueOf(utente.getDataNascita()));
        preparedStatement.setString(7, utente.getNomeCarta());
        preparedStatement.setString(8, utente.getCognomeCarta());
        preparedStatement.setString(9, utente.getNumCarta());
        if(utente.getDataScadenza() == null)
            preparedStatement.setDate(10, null);
        else
            preparedStatement.setDate(10, Date.valueOf(utente.getDataScadenza()));
        preparedStatement.setString(11, utente.getCvv());
        preparedStatement.setString(12, utente.getCap());
        preparedStatement.setString(13, utente.getVia());
        preparedStatement.setString(14, utente.getCitta());
        preparedStatement.setString(15, utente.getTipo());
    }
	
	private void settingUtente(ResultSet resultSet, UtenteBean utenteBean) throws SQLException {
        utenteBean.setUsername(resultSet.getString("username"));
        utenteBean.setPwd(resultSet.getString("pwd"));
        utenteBean.setNome(resultSet.getString("nome"));
        utenteBean.setCognome(resultSet.getString("cognome"));
        utenteBean.setEmail(resultSet.getString("email"));
        utenteBean.setDataNascita(resultSet.getDate("dataNascita").toLocalDate());
        utenteBean.setNomeCarta(resultSet.getString("nomeCarta"));
        utenteBean.setCognomeCarta(resultSet.getString("cognomeCarta"));
        utenteBean.setNumCarta(resultSet.getString("numCarta"));
        if (resultSet.getDate("dataScadenza") == null)
            utenteBean.setDataScadenza(null);
        else
            utenteBean.setDataScadenza((resultSet.getDate("dataScadenza").toLocalDate()));
        utenteBean.setCvv(resultSet.getString("CVV"));
        utenteBean.setCap(resultSet.getString("cap"));
        utenteBean.setVia(resultSet.getString("via"));
        utenteBean.setCitta(resultSet.getString("citta"));
        utenteBean.setTipo(resultSet.getString("tipo"));
    }
}
