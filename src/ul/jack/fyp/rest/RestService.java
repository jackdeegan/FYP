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

 
@Path("REST")
public class RestService {
	
	@POST
	@Path("/confirmModules")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String[][] confirmModules(Modules userModules) throws IOException {
			Algorithm mod = new Algorithm();
			ArrayList<List<String>> details = mod.moduleDetails(userModules.getModules());
			String[][] timetable = mod.generateTimetable(details);
			return timetable;
	}	
	
	@POST
	@Path("/returnConflicts")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> returnConflicts(Modules userModules) throws IOException {
			Algorithm mod = new Algorithm();
			ArrayList<List<String>> details = mod.moduleDetails(userModules.getModules());
			ArrayList<String> conflicts = mod.conflicts(details);
			return conflicts;
	}	
}