package ul.jack.fyp.rest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import ul.jack.fyp.dao.DBUtils;
import ul.jack.fyp.model.Result;
import ul.jack.fyp.model.User;
import ul.jack.fyp.model.UsersDao;
 
@Path("REST")
public class RestService {
	
	@GET
	@Produces("application/json")
	@Path("/authUser/{id}")
	public String authUser(@PathParam("id") String id, @QueryParam("pwd") String password) {
		Result result = new Result();
		if(id == null || id.equals("")) {
			result.setCode("410");
			result.setMessage("Email is mandatory");
			Gson g = new Gson();
			return g.toJson(result);
		}
		if(password == null || password.equals("")) {
			result.setCode("411");
			result.setMessage("Password is mandatory");
			Gson g = new Gson();
			return g.toJson(result);
		}
		Connection conn = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		try {
			conn = DBUtils.getTestConnection();
			String strSql = "select * from users where email = ? AND password = ?";
			ps1 = conn.prepareStatement(strSql);
			ps1.setString(1, id);
			ps1.setString(2,  password);
			rs1 = ps1.executeQuery();
			
			if(rs1.next()) {
				result.setCode("200");
				result.setMessage("Valid Login");
				Gson g = new Gson();
				return g.toJson(result);
			} else {
				result.setCode("404");
				result.setMessage("Invalid Login");
				Gson g = new Gson();
				return g.toJson(result);
			}
		} catch(SQLException e) {
			System.out.println(e);
		} finally {
			DBUtils.close(rs1);
			DBUtils.close(ps1);
			DBUtils.close(conn);
		}	
		return "";
	}
	
	@GET
	@Produces("application/json")
	@Path("/getUserList")
	public String getUserList() {
		UsersDao userDao = new UsersDao();
		List<User> userList = userDao.findAll();
		Gson g = new Gson();
		return g.toJson(userList);
	}
	
	@DELETE
	@Path("/deleteUser/{email}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Result deleteUser(@PathParam("email") String email) {
		Result result = null;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtils.getTestConnection();
			String sql = "DELETE FROM users WHERE email = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			if(ps.execute()) {
				result = new Result();
				result.setCode("200");
				result.setMessage("Success");
				//Gson g = new Gson();
				//return g.toJson(result);
			}
			else {
				result = new Result();
				result.setCode("401");
				result.setMessage("Something went wrong");
				//Gson g = new Gson();
				//return g.toJson(result);
				}
			}
			catch(SQLException e) {
				result = new Result();
				result.setCode("401");
				result.setMessage("Something went wrong");
				//Gson g = new Gson();
				//return g.toJson(result);
			} finally {
				DBUtils.close(ps);
				DBUtils.close(conn);
		}
		return result;
}
	
	@POST
	@Path("/regUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Result regUser(User user) {
		Result result = null;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtils.getTestConnection();
			String sql = "INSERT INTO users (email, password, fname, lname, studentNum, countryID, address) VALUES (?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getEmail());
			ps.setString(2,  user.getPassword());
			ps.setString(3,  user.getFname());
			ps.setString(4,  user.getLname());
			ps.setInt(5,  user.getStudentNum());
			ps.setInt(6, 94);
			ps.setString(7,  user.getAddress());
			ps.execute();
			result = new Result();
			result.setCode("200");
			result.setMessage("Success");
		} catch(SQLException e) {
			result = new Result();
			result.setCode("401");
				result.setMessage("Something went wrong");
		} finally {
			DBUtils.close(ps);
			DBUtils.close(conn);
		}
		return result;
	}
}