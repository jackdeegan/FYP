package ul.jack.fyp.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ul.jack.fyp.model.Module;
import ul.jack.fyp.model.Modules;

 
@Path("REST")
public class RestService {
	
	@POST
	@Path("/confirmModules")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String[][] confirmModules(Modules userModules) throws IOException {
			Module mod = new Module();
			ArrayList<List<String>> details = mod.moduleDetails(userModules.getModules());
			String[][] timetable = mod.generateTimetable(details);
			return timetable;
	}	
	
	@POST
	@Path("/returnConflicts")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> returnConflicts(Modules userModules) throws IOException {
			Module mod = new Module();
			ArrayList<List<String>> details = mod.moduleDetails(userModules.getModules());
			ArrayList<String> conflicts = mod.conflicts(details);
			return conflicts;
	}	
	
	/*
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
	@Path("/getUserList/{email}")
	public String getUserList() {
		UsersDao userDao = new UsersDao();
		List<User> userList = userDao.findAll();
		Gson g = new Gson();
		return g.toJson(userList);
	}
	
	@GET
	@Produces("application/json")
	@Path("/getCountryList")
	public String getCountryList() {
		CountryDao countryDao = new CountryDao();
		List<Country> countryList = countryDao.findAll();
		Gson g = new Gson();
		return g.toJson(countryList);
	}
	
	@GET
	@Produces("application/json")
	@Path("/getUserModules/{email}")
	public String getUserModules(@PathParam("email") String email) {
		ModulesDao modulesDao = new ModulesDao();
		List<Modules> moduleList = modulesDao.findAll(email);
		Gson g = new Gson();
		return g.toJson(moduleList);
	}
	
	@DELETE
	@Path("/deleteUser/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Result deleteUser(@PathParam("email") String email) {
		Result result = null;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtils.getTestConnection();
			String sql = "DELETE FROM users WHERE email =?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.execute();
			result = new Result();
			result.setCode("200");
			result.setMessage("Success");
			}
			catch(SQLException e) {
				result = new Result();
				result.setCode("401");
				result.setMessage("Something went wrong");
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
	
	
	@POST
	@Path("/confirmModules/{email}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Result confirmModule(@PathParam("email") String email, Modules module) {
		Result result = null;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtils.getTestConnection();
			String sql = "INSERT INTO modules (email, module1, module2, module3, module4, module5, module6, module7, module8, module9, module10) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2,  module.getModule1());
			ps.setString(3,  module.getModule2());
			ps.setString(4,  module.getModule3());
			ps.setString(5,  module.getModule4());
			ps.setString(6, module.getModule5());
			ps.setString(7,  module.getModule6());
			ps.setString(8,  module.getModule7());
			ps.setString(9,  module.getModule8());
			ps.setString(10,  module.getModule9());
			ps.setString(11,  module.getModule10());
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
	*/
}