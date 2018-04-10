package ul.jack.fyp.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ul.jack.fyp.model.Algorithm;
import ul.jack.fyp.model.Modules;

 
@Path("REST")	// name of path for REST API
public class RestService {
	
	@POST	// type of method
	@Path("/confirmModules")	// name of path
	@Consumes(MediaType.APPLICATION_JSON)	// what method consumes
	@Produces(MediaType.APPLICATION_JSON)	// what method produces
	public String[][] confirmModules(Modules userModules) throws IOException {
			Algorithm mod = new Algorithm(); 	// initialize algorithm object
			ArrayList<List<String>> details = mod.moduleDetails(userModules.getModules());
			// pass users modules codes into algorithm object method and set module details array list with value returned
			String[][] timetable = mod.generateTimetable(details);
			// pass module details into algorithm object method and set timetable array as value returned
			return timetable;	// return timetable
	}	
	
	@POST	//type of method
	@Path("/returnConflicts") // name of path
	@Consumes(MediaType.APPLICATION_JSON)	// what the method consumes
	@Produces(MediaType.APPLICATION_JSON)	// what the method produces
	public ArrayList<String> returnConflicts(Modules userModules) throws IOException {
			Algorithm mod = new Algorithm();	// initialize algorithm object
			ArrayList<List<String>> details = mod.moduleDetails(userModules.getModules());
			// pass users modules codes into algorithm object method and set module details array list with value returned
			ArrayList<String> conflicts = mod.conflicts(details);
			// pass module details into algorithm object method and set conflicts array list with values returned
			return conflicts;	// return conflicts
	}	
}