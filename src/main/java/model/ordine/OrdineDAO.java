package model.ordine;

import model.DAOInterface;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class OrdineDAO implements DAOInterface<OrdineBean, Integer> {
    private static final String TABLE_NAME = "Ordine";
    private static DataSource ds;
    private static final List<String> ORDERS = new ArrayList<>(Arrays.asList("username", "dataOrdine"));

    //Connessione al database attraverso datasource
    static {
        try {
            Context initCtx = new InitialContext();
            Context env = (Context) initCtx.lookup("java:comp/env");
            ds = (DataSource) env.lookup("jdbc/keyItaly");
        } catch (NamingException e) {
        	e.printStackTrace();
        }
    }

    //Metodi Interfaccia DAO
    public List<OrdineBean> getFilteredOrders(String username, String dataInizio, String dataFine) throws SQLException {
        List<OrdineBean> ordini = new ArrayList<>();
        String sql = "SELECT * FROM Ordine WHERE 1=1";

        if (username != null && !username.isEmpty()) {
            sql += " AND username = ?";
        }
        if (dataInizio != null && !dataInizio.isEmpty()) {
            sql += " AND dataOrdine >= ?";
        }
        if (dataFine != null && !dataFine.isEmpty()) {
            sql += " AND dataOrdine <= ?";
        }

        try (Connection conn = ds.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            //L'indice avanza solo per le clausole effettivamente aggiunte alla query
            int indice = 1;
            if (username != null && !username.isEmpty()) {
                ps.setString(indice++, username);
            }
            if (dataInizio != null && !dataInizio.isEmpty()) {
                ps.setDate(indice++, Date.valueOf(LocalDate.parse(dataInizio)));
            }
            if (dataFine != null && !dataFine.isEmpty()) {
                ps.setDate(indice++, Date.valueOf(LocalDate.parse(dataFine)));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrdineBean ordine = new OrdineBean();
                    setOrders(rs, ordine);
                    ordini.add(ordine);
                }
            }
        }
        return ordini;
    }
    
    public OrdineBean doRetrieveByKey(Integer code) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE ID = ?";

        try(Connection connection = ds.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, code);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }
                OrdineBean ordineBean = new OrdineBean();
                setOrders(resultSet, ordineBean);
                return ordineBean;
            }
    	}
    }

    //Restituisce la Collection di ordini eseguiti da un user
    public Collection<OrdineBean> doRetrieveByUsername(String username) throws SQLException {
        Collection<OrdineBean> ordini = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";

        try (Connection connection = ds.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                OrdineBean ordineBean = new OrdineBean();
                setOrders(resultSet, ordineBean);
                ordini.add(ordineBean);
            }
        } 
        return ordini;
    }

    public Collection<OrdineBean> doRetrieveAll(String order) throws SQLException {
        Collection<OrdineBean> ordini = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM " + TABLE_NAME);
        
        for (String s: ORDERS)
            if (s.equals(order))
                query.append(" ORDER BY ").append(s);

        try(Connection connection = ds.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query.toString())){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                OrdineBean ordineBean = new OrdineBean();
                setOrders(resultSet,ordineBean);
                ordini.add(ordineBean);
            }
        }
        return ordini;
    }

    public void doSave(OrdineBean ordineBean) throws SQLException {
        String query = "INSERT INTO " + TABLE_NAME + " (username, prezzoTotale, dataConsegna, dataOrdine, nomeConsegna, cognomeConsegna, cap, via, citta) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = ds.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            setOrdineStatement(ordineBean, preparedStatement);
            preparedStatement.executeUpdate();

            //Riporta sul bean la chiave generata dal database, necessaria per
            //collegare le righe di Acquisto all'ordine appena inserito
            try (ResultSet chiavi = preparedStatement.getGeneratedKeys()) {
                if (chiavi.next()) {
                    ordineBean.setID(chiavi.getInt(1));
                }
            }
        }
    }

    //Non si può aggiornare un ordine compiuto
    public void doUpdate(OrdineBean product) {
    }
    
    //Non si può cancellare un ordine compiuto
    public boolean doDelete(Integer code) {
        return false;
    }

    //Metodi di utilità
    
    //Restituisci l'ID dell'ultimo ordine inserito
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

    //Imposta le proprietà del Bean in unico metodo
    private void setOrders(ResultSet resultSet, OrdineBean ordineBean) throws SQLException {
        ordineBean.setID(resultSet.getInt("ID"));
        ordineBean.setUsername(resultSet.getString("username"));
        ordineBean.setPrezzoTotale(resultSet.getBigDecimal("prezzoTotale"));
        ordineBean.setDataConsegna(resultSet.getDate("dataConsegna").toLocalDate());
        ordineBean.setDataOrdine(resultSet.getDate("dataOrdine").toLocalDate());
        ordineBean.setNomeConsegna(resultSet.getString("nomeConsegna"));
        ordineBean.setCognomeConsegna(resultSet.getString("cognomeConsegna"));
        ordineBean.setCap(resultSet.getString("cap"));
        ordineBean.setVia(resultSet.getString("via"));
        ordineBean.setCitta(resultSet.getString("citta"));
    }

    //Imposta i parametri del PreparedStatement in un unico metodo
    private void setOrdineStatement(OrdineBean ordineBean, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, ordineBean.getUsername());
        preparedStatement.setBigDecimal(2, ordineBean.getPrezzoTotale());
        preparedStatement.setDate(3, Date.valueOf(LocalDate.now().plusDays(15)));
        preparedStatement.setDate(4, Date.valueOf(LocalDate.now()));
        preparedStatement.setString(5, ordineBean.getNomeConsegna());
        preparedStatement.setString(6, ordineBean.getCognomeConsegna());
        preparedStatement.setString(7, ordineBean.getCap());
        preparedStatement.setString(8, ordineBean.getVia());
        preparedStatement.setString(9, ordineBean.getCitta());
    }
}
