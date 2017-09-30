package ul.jack.fyp.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ul.jack.fyp.dao.DBUtils;

public class DatabaseConnectionTest {

	public static void main(String[] args) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try (Connection conn = DBUtils.getTestConnection()) {
			String strSql = "select * from users where email = ?";
			ps = conn.prepareStatement(strSql);
			ps.setString(1, "jack.deegan@gmail.com");
			rs = ps.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getString("email"));
			}

		} catch(SQLException e) {
			System.out.println(e);
		}
	}
	
}
