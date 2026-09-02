package model.prodotto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.DAOInterface;

public class ProdottoDAO implements DAOInterface<ProdottoBean, Integer> {
	private static DataSource ds;
	private static final String TABLE_NAME = "Prodotto";
	private static final List<String> ORDERS = new ArrayList<>(Arrays.asList("nome", "prezzo", "tipo"));
	
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
	public synchronized void doSave(ProdottoBean prodotto) throws SQLException {
		String insertSQL = "INSERT INTO " + TABLE_NAME
				+ " (nome, prezzo, IVA, tipo, grafica, descrizione, quantitaInStock) " 
				+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		try (Connection connection = ds.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
			settingProdottoStatement(prodotto, preparedStatement);
			preparedStatement.executeUpdate();
		}
	}
		
	public synchronized void doUpdate(ProdottoBean prodotto) throws SQLException {
		String query = "UPDATE " + TABLE_NAME +
	            " SET nome = ?, prezzo = ?, IVA = ?, tipo = ?, grafica = ?, descrizione = ?, quantitaInStock = ? " +
	            " WHERE ID = ?";
		
		try (Connection connection = ds.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		     settingProdottoStatement(prodotto, preparedStatement);
		     preparedStatement.setInt(8, prodotto.getID());
		     preparedStatement.executeUpdate();
		}
	}

	public synchronized boolean doDelete(Integer key) throws SQLException {
		int result;
        String query = "DELETE FROM " + TABLE_NAME + " WHERE ID = ?";
        
        try (Connection connection = ds.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, key);
            result = preparedStatement.executeUpdate();
        }
        return result != 0;
	}

	public synchronized Collection<ProdottoBean> doRetrieveByTipo(String tipo) throws SQLException {
        Collection<ProdottoBean> prodottiTipo = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE tipo = ?";

        try (Connection connection = ds.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, tipo);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                ProdottoBean prodottoBean = new ProdottoBean();
                settingProdotto(resultSet, prodottoBean);
                prodottiTipo.add(prodottoBean);
            }
        }
        return prodottiTipo;
    }
	
	public synchronized ProdottoBean doRetrieveByKey(Integer key) throws SQLException {
		ProdottoBean prodottoBean = new ProdottoBean();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE ID = ?";

        try (Connection connection = ds.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            resultSet.next();
            settingProdotto(resultSet, prodottoBean);
        }
        return prodottoBean;
	}
	
	public synchronized Collection<ProdottoBean> doRetrieveAll() throws SQLException {
		return doRetrieveAll("");
	}
	
	public synchronized Collection<ProdottoBean> doRetrieveAll(String order) throws SQLException {
        Collection<ProdottoBean> prodotti = new ArrayList<>();

        StringBuilder query = new StringBuilder("SELECT * FROM " + TABLE_NAME + " WHERE Tipo <> 'Eliminato'");

        try (Connection connection = ds.getConnection()) {

            for (String s: ORDERS)
                if (s.equals(order))
                    query.append(" ORDER BY ").append(s);
            PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ProdottoBean ProdottoBean = new ProdottoBean();
                settingProdotto(resultSet, ProdottoBean);
                prodotti.add(ProdottoBean);
            }
        }
        return prodotti;
	}

	//Metodi di utilita'
	
	//Aggiorna il tipo di un prodtto in 'Eliminato'
	public boolean deleteProdotto(Integer key) throws SQLException {
        int result = 0;
        String query = "UPDATE " + TABLE_NAME + " SET Tipo = 'Eliminato' WHERE ID = ?";

        try (Connection connection = ds.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, key);
            result = preparedStatement.executeUpdate();
        }
        return result != 0;
    } 
	
	public int getMaxID() throws SQLException {
		//Disabilita la cache delle statistiche di information_schema per la sessione corrente
        String sessionCacheQuery = "SET @@SESSION.information_schema_stats_expiry = 0;";
        //Seleziona il valore di AUTO_INCREMENT dalla tabella information_schema.tables (Metadati dello schema)
        String query = "SELECT AUTO_INCREMENT " +
                       "FROM information_schema.tables WHERE table_name = '" + TABLE_NAME +
                       "' AND table_schema = DATABASE()";

        int ID = 0;
        try (Connection connection = ds.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            connection.createStatement().execute(sessionCacheQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            ID = resultSet.getInt("AUTO_INCREMENT");
        }
        return ID;
    }
	
	//Utilizzato per la barra di ricerca
	public synchronized Collection<ProdottoBean> doSearchByName(String searchQuery, int limit) throws SQLException {
        Collection<ProdottoBean> prodotti = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE nome LIKE ? AND Tipo <> 'Eliminato'";
        if (limit > 0) query += " LIMIT " + limit;

        try(Connection connection = ds.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + searchQuery + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ProdottoBean ProdottoBean = new ProdottoBean();
                settingProdotto(resultSet, ProdottoBean);
                prodotti.add(ProdottoBean);
            }
        }
        return prodotti;
	}
	
	//Settings
	private void settingProdottoStatement(ProdottoBean prodotto, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, prodotto.getNome());
        preparedStatement.setBigDecimal(2, prodotto.getPrezzo());
        preparedStatement.setInt(3, prodotto.getIVA());
        preparedStatement.setString(4, prodotto.getTipo());
        preparedStatement.setString(5, prodotto.getGrafica());
        preparedStatement.setString(6, prodotto.getDescrizione());
        preparedStatement.setInt(7, prodotto.getQuantitaInStock());
    }
	
	private void settingProdotto(ResultSet resultSet, ProdottoBean prodottoBean) throws SQLException {
        prodottoBean.setID(resultSet.getInt("ID"));
        prodottoBean.setNome(resultSet.getString("nome"));
        prodottoBean.setPrezzo(resultSet.getBigDecimal("prezzo"));
        prodottoBean.setIVA(resultSet.getInt("IVA"));
        prodottoBean.setTipo(resultSet.getString("tipo"));
        prodottoBean.setGrafica(resultSet.getString("grafica"));
        prodottoBean.setDescrizione(resultSet.getString("descrizione"));
        prodottoBean.setQuantitaInStock(resultSet.getInt("quantitaInStock"));
    }
}
