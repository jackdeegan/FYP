package ul.jack.fyp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ul.jack.fyp.dao.DBUtils;
import ul.jack.fyp.model.User;

public class UsersDao {
	 public List<User> findAll() {
	        List<User> list = new ArrayList<User>();
	        Connection c = null;
	    	String sql = "SELECT * FROM users ORDER BY lname";
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

	 protected User processRow(ResultSet rs) throws SQLException {
	        User user = new User();
	        user.setEmail(rs.getString("email"));
	        user.setPassword(rs.getString("password"));
	        user.setFname(rs.getString("fname"));
	        user.setLname(rs.getString("lname"));
	        user.setStudentNum(rs.getInt("studentNum"));
	        user.setCountryID(rs.getInt("countryID"));
	        user.setAddress(rs.getString("address"));
	        return user;
	    }
	 
	 
	 
	    public User create(User user) {
	        Connection c = null;
	        PreparedStatement ps = null;
	        try {
	            c = DBUtils.getTestConnection();
	            ps = c.prepareStatement("INSERT INTO users (email, password, fname, lname studentNum, countryID, address) VALUES (?,?,?,?,?,?,?)",
	                new String[] { "EMAIL" });
	            ps.setString(1, user.getEmail());
				ps.setString(2,  user.getPassword());
				ps.setString(3,  user.getFname());
				ps.setString(4,  user.getLname());
				ps.setInt(5,  user.getStudentNum());
				ps.setInt(6,  0);
				ps.setString(7,  user.getAddress());
	            ps.executeUpdate();
	            ResultSet rs = ps.getGeneratedKeys();
	            rs.next();
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException(e);
			} finally {
				DBUtils.close(c);
			}
	        return user;
	    }
}