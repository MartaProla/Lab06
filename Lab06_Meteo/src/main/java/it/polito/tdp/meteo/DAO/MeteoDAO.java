package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.meteo.model.Rilevamento;

public class MeteoDAO {
	
	public Map<String, Double>mappaUmidita;
	
	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();
			

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {

		return null;
	}

	public Map<String, Double>getUmidtàMedia(int mese) {
		final String sql = "SELECT localita,  AVG(umidita) AS media " + 
							"FROM situazione s " + 
							"WHERE MONTH(`Data`)=? " + 
							"GROUP BY localita ";
		this.mappaUmidita=new HashMap<String,Double>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);
			ResultSet rs = st.executeQuery();
			

			while (rs.next()) {

				mappaUmidita.put(rs.getString("localita"), rs.getDouble("media"));
			}

			conn.close();
			return mappaUmidita;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	
	

	


}
