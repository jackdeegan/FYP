//rest example

package ul.jack.fyp.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.google.gson.Gson;

import ul.jack.fyp.model.UsersDao;
 
@Path("/test")
public class RestService {
	@GET
	@Produces("application/json")
	public String restTest() {
		UsersDao u = new UsersDao();
		u.setFname("jack");
		u.setLname("deegan");
		u.setStudentNum(123);
		u.setEmail("jack@test.com");
		
		Gson g = new Gson();
		return g.toJson(u);

		
		
//		Double fahrenheit;
//		Double celsius = 36.8;
//		fahrenheit = ((celsius * 9) / 5) + 32;
// 
//		String result = "@Produces(\"application/xml\") Output: \n\nC to F Converter Output: \n\n" + fahrenheit;
//		return "<ctofservice>" + "<celsius>" + celsius + "</celsius>" + "<ctofoutput>" + result + "</ctofoutput>" + "</ctofservice>";
	}
 
//	@Path("{c}")
//	@GET
//	@Produces("application/xml")
//	public String convertCtoFfromInput(@PathParam("c") Double c) {
//		Double fahrenheit;
//		Double celsius = c;
//		fahrenheit = ((celsius * 9) / 5) + 32;
// 
//		String result = "@Produces(\"application/xml\") Output: \n\nC to F Converter Output: \n\n" + fahrenheit;
//		return "<ctofservice>" + "<celsius>" + celsius + "</celsius>" + "<ctofoutput>" + result + "</ctofoutput>" + "</ctofservice>";
//	}
	
//	@GET
//	@Path("/jason")
//	@Produces("application/json")
//	public String jsonTest() {
//		UsersDao u = new UsersDao();
//		u.setFname("jason");
//		u.setLname("lastname");
//		u.setStudentNum(123);
//		u.setEmail("jason@test.com");
//		
//		Gson g = new Gson();
//		return g.toJson(u);
//
//	}
}

//right methods for everything validate user etc