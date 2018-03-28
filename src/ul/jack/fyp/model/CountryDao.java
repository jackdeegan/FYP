package ul.jack.fyp.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ul.jack.fyp.dao.DBUtils;
import ul.jack.fyp.model.Country;

public class CountryDao {
	 public List<Country> findAll() {
	        List<Country> list = new ArrayList<Country>();
	        Connection c = null;
	    	String sql = "SELECT * FROM countries";
	        try {
	            c = DBUtils.getTestConnection();
	            Statement s = c.createStatement();
	            ResultSet rs = s.executeQuery(sql);
	            while (rs.next()) {
	                list.add(processRow(rs));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new RuntimeException(e);
			} finally {
				DBUtils.close(c);
			}
	        return list;
	    }

	 protected Country processRow(ResultSet rs) throws SQLException {
	        Country country = new Country();
	        country.setCountryName(rs.getString("countryName"));
	        country.setCountryID(rs.getInt("countryID"));
	        return country;
	    }
}

