//rest example

package ul.jack.fyp.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ul.jack.fyp.dao.DBUtils;

import com.google.gson.Gson;

import ul.jack.fyp.model.UsersDao;
 
@Path("/test")
public class RestService {
	@GET
	@Produces("application/json")
	public String restTest() {
		UsersDao u = new UsersDao();
		//u.setFname("jack");
		//u.setLname("deegan");
		//u.setStudentNum(123);
		//u.setEmail("jack@test.com");
		//u.setAddress("Ireland");
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		
		try (Connection conn = DBUtils.getTestConnection()) {
			String strSql = "select * from users";
			ps2 = conn.prepareStatement(strSql);
			rs2 = ps2.executeQuery();
			
			while(rs2.next()) {
				u.setEmail(rs2.getString("email"));
				u.setPassword(rs2.getString("password"));
				u.setFname(rs2.getString("fname"));
				u.setLname(rs2.getString("lname"));
				u.setStudentNum(rs2.getInt("studentNum"));
				u.setCountryID(rs2.getInt("countryID"));
				u.setAddress(rs2.getString("address"));
			}
		} catch(SQLException e) {
			System.out.println(e);
		}
		
		
		Gson g = new Gson();
		return g.toJson(u);
		
	}
}

//write methods for everything validate user etc