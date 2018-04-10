package ul.jack.fyp.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Algorithm {

	// method used by the following method for reading data as string
	private static String readAll(Reader rd) throws IOException {
	  StringBuilder sb = new StringBuilder();
	  int cp;
	  while ((cp = rd.read()) != -1) {
		  sb.append((char) cp);
	  }
	  return sb.toString();
  }
  
  // method used to read the JSON data from an URL
  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException { // method to read JSON data from  an URL
	  InputStream is = new URL(url).openStream();
	  try {
		  BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		  String jsonText = readAll(rd);
		  JSONObject json = new JSONObject(jsonText);
		  return json;
	  } finally {
		  is.close();
	  }
  }
  
  // method that gets all module details from the module codes
  public ArrayList<List<String>> moduleDetails(String [] moduleCodes) throws IOException, JSONException {
	  
	  List<String> temp = new ArrayList<String>(Arrays.asList(moduleCodes)); // stores module codes in a temporary list
	  temp.removeAll(Arrays.asList("", null));	// removes all empty strings in list
	  String[] modCodes = new String[temp.size()];	// initialize array the size of temp
	  modCodes = temp.toArray(modCodes); //insert temp data into string array
	  String day, time, startTime,endTime,type, room; // declare string variables
	  String[] times = new String[1];	// initialize array for times
	  ArrayList<List<String>> modDetails = new ArrayList<List<String>>(); // initialize multidimensional array list
	  for(int i = 0; i < modCodes.length; i++) {	// iterate through the modual codes
		  JSONObject json = readJsonFromUrl("http://35.197.202.71/module-timetable.php?module=" +moduleCodes[i]); //get JSON Object from URL and module code [i]
		  JSONArray classes = json.getJSONArray("classes"); // get all classes for module
		  int count = classes.length(); // get number of classes
		  for(int j = 0; j < count; j++) {	// iterate through classes
			  JSONObject jsonObject = classes.getJSONObject(j);	// get each class
			  day = Integer.toString(jsonObject.getInt("day"));	// set day of class
			  if(day.equals("1"))	// changing int to string for day
				  day = "MON";
			  if(day.equals("2"))
				  day = "TUE";
			  if(day.equals("3"))
				  day = "WED";
			  if(day.equals("4"))
				  day = "THU";
			  if(day.equals("5"))
				  day = "FRI";
			  time = jsonObject.getString("time"); //get times of class
			  times = time.split("-");	// split time into 2 times
			  startTime = times[0];	// start time class
			  endTime = times[1];	// end time of class
			  type = jsonObject.getString("type"); // get type of class
			  room = jsonObject.getString("room");	// get room of class
			  modDetails.add(Arrays.asList(modCodes[i],day,startTime,endTime,type,room)); // add all details of class to modDetails
		  }
	  }
	  return modDetails; // return module details 
  } 
  
  	// method that gets all conflicts between the different modules
  	public ArrayList<String> conflicts(ArrayList<List<String>>details) throws IOException{
	  String t1[] = new String[1];	//
	  String t2[] = new String[1];	//
	  String t3[] = new String[1];	//	- all different variables to deal with time in this method
	  String t4[] = new String[1];	//
	  int tm1,tm2,tm3,tm4;			//
	  ArrayList<String> allConflicts = new ArrayList<String>();	// initialize array list to store all conflicts
	  int conflicts = 0;
	  for (int i = 0; i < details.size(); i++)	// iterate through module details 
	  {	
		  for (int j = 0; j < details.size(); j++)	// iterate through module details again
	      {
			  if(!((details.get(i).get(0)).equals(details.get(j).get(0)))) //if module codes are different
    		  {
	    		  if((details.get(i).get(1)).equals(details.get(j).get(1))) //if days are equals
	    		  {
	    			  t1 = details.get(i).get(2).split(":");	// gets start time of class i
	    			  t2 = details.get(i).get(3).split(":");	// gets end time of class i
	    			  t3 = details.get(j).get(2).split(":");	// gets start time of class j
	    			  t4 = details.get(j).get(3).split(":");;	// gets end time of class j
	    			  tm1 = Integer.parseInt(t1[0]);	// converts each time to an int
	    			  tm2 = Integer.parseInt(t2[0]);
	    			  tm3 = Integer.parseInt(t3[0]);
	    			  tm4 = Integer.parseInt(t4[0]);
	    			  
	    			  if((details.get(i).get(2)).equals(details.get(j).get(2)) || (details.get(i).get(3)).equals(details.get(j).get(3))
	    					  || (tm3>tm1 && tm3<tm2) || (tm4>tm1 && tm4<tm2)) //if times are equal or conflict
	    			  {
	    				  conflicts++;	// add 1 to number of conflicts
	    				  String temp;	// declare temporary string
	    				  temp = details.get(i) + " CONFLICTS WITH " + details.get(j);	// set temp with conflict here
	    				  allConflicts.add(temp);	// add temp to all conflicts
	    				  for(int k = 0; k<allConflicts.size();k++) {	// iterate through all conflicts
	    					  if(allConflicts.get(k).equals(details.get(j) + " CONFLICTS WITH " + details.get(i)))	// if conflicts are the same
	    						  allConflicts.remove(k); 	// remove duplicates
	    				  }
	    			  }
	    		  }
    		  }
	      } 
	  }
	  conflicts = conflicts/2;	// divide number of conflicts by two as we have removed duplicates
	  return allConflicts;	// return all conflicts
  }
  	
  	// method that generates the timetable in a 2D array format
  	public String[][] generateTimetable(ArrayList<List<String>>details){
  		int day;	// declare int day
  		String [][]timetable = new String[9][5]; // initialize timetable with 9 hours, 5 days
  		for(int g = 0; g < timetable.length; g++) {	// iterate through rows in timetable
  			for(int h = 0; h< timetable[g].length; h++) {	// iterate through columns in timetable
  				timetable[g][h] = "";	// insert empty string in timetable cells
  	  		}
  		}
  		
  		for(int i = 0; i < details.size();i++) // iterate through module details
  		{
  			if(details.get(i).get(1).equals("MON")) {	// if day equals Monday
  				day = 0; //set day to 0		//start time			//end time			   //module code		  //type				//room
  				addData(day, timetable, details.get(i).get(2), details.get(i).get(3), details.get(i).get(0), details.get(i).get(4), details.get(i).get(5));
  			}
  			else if(details.get(i).get(1).equals("TUE")) {	// if day equals Tuesday
  				day = 1; //set day to 1
  				addData(day, timetable, details.get(i).get(2), details.get(i).get(3), details.get(i).get(0), details.get(i).get(4), details.get(i).get(5));
  			}
  			else if(details.get(i).get(1).equals("WED")) {	// if day equals Wednesday
  				day = 2; //set day to 2
  				addData(day, timetable, details.get(i).get(2), details.get(i).get(3), details.get(i).get(0), details.get(i).get(4), details.get(i).get(5));
  			}
  			else if(details.get(i).get(1).equals("THU")) {	// if day equals Thursday
  				day = 3; //set day to 3
  				addData(day, timetable, details.get(i).get(2), details.get(i).get(3), details.get(i).get(0), details.get(i).get(4), details.get(i).get(5));
  			}	  			
  			else if(details.get(i).get(1).equals("FRI")) {	// if day equals Friday
  				day = 4; //set day to 4
  				addData(day, timetable, details.get(i).get(2), details.get(i).get(3), details.get(i).get(0), details.get(i).get(4), details.get(i).get(5));
  			}
  		}
  		
  		timetable = removeDuplicates(timetable);	// call method to remove any duplicates
  		return timetable;	// return timetable
  	}
  	
  	//method which inserts class details into an array as a timetable format
  	public void addData(int day, String[][]timetable, String t, String e, String code, String type, String room) {
  			if(t.equals("09:00")) {	// if time of class starts at 9:00
  				if(type.equals("LEC-")) {	// if type of class is a lecture
  					if(timetable[0][day].equals("")) {	// if cell in timetable is empty
  						timetable[0][day] = code + "-" + type + room;	// set value of this cell with class details
  						if(e.equals("11:00")) // if end of class is at 11:00 (IE if class is a double)
  							timetable[1][day] = code + "-" + type + room; // set value of this cell with class details
  					}
  					else if(!(timetable[0][day].equals("")) && (timetable[0][day].contains("TUT") || timetable[0][day].contains("LAB"))) {
  						// if cell contains a tutorial or lab
  						timetable[0][day] = code + "-" + type + room; // overwrite timetable cell with lecture class (IE lectures have priority of a tutorial or lab)
  						if(e.equals("11:00")) // if end of class is at 11:00 (IE if class is a double)
  							timetable[1][day] = code + "-" + type + room; // set value of this cell with class details
  					}
  					else {	// if cell contains a lecture
  						timetable[0][day] += " OR " + code + "-" + type + room; // add class details to existing value of cell
  						if(e.equals("11:00")) // if end of class is at 11:00 (IE if class is a double)
  							timetable[1][day] += " OR " +code + "-" + type + room; // add class details to existing value of cell
  					}
  				}
  				else { //if class is a tutorial or lab
  					if(timetable[0][day].equals("")) {	// if cell in timetable is empty
  						timetable[0][day] = code + "-" + type + "-" + room;	// set value of this cell with class details
  						if(e.equals("11:00")) 	// if end of class is at 11:00 (IE if class is a double)
  							timetable[1][day] = code + "-" + type + "-" + room;	// set value of this cell with class details
  					}
  				}
  			}
  			
  			// comments for all if statements, only thing different is time
			if(t.equals("10:00")) {
				if(type.equals("LEC-")) {
					if(timetable[1][day].equals("")) {
						timetable[1][day] = code + "-" + type + room;
						if(e.equals("12:00")) 
							timetable[2][day] = code + "-" + type + room;
					}
					else if(!(timetable[1][day].equals("")) && (timetable[1][day].contains("TUT") || timetable[1][day].contains("LAB")))  {
  						timetable[1][day] = code + "-" + type + room;
  						if(e.equals("12:00")) 
  							timetable[2][day] = code + "-" + type + room;
  					}
					else {
  						timetable[1][day] += " OR " + code + "-" + type + room;
  						if(e.equals("12:00")) 
  							timetable[2][day] += " OR " +code + "-" + type + room;
  					}
				}
				else {
					if(timetable[1][day].equals("")) {
						timetable[1][day] = code + "-" + type + "-" + room;
						if(e.equals("12:00"))
							timetable[2][day] = code + "-" + type + "-" + room;
					}
				}
			}
			
			if(t.equals("11:00")) { 
				if(type.equals("LEC-")) {
					if(timetable[2][day].equals("")) {
						timetable[2][day] = code + "-" + type + room;
						if(e.equals("13:00")) 
							timetable[3][day] = code + "-" + type + room;
					}
					else if(!(timetable[2][day].equals("")) && (timetable[2][day].contains("TUT") || timetable[2][day].contains("LAB")))  {
  						timetable[2][day] = code + "-" + type + room;
  						if(e.equals("13:00")) 
  							timetable[3][day] = code + "-" + type + room;
  					}
					else {
						timetable[2][day] += " OR " + code + "-" + type + room;
						if(e.equals("13:00")) 
							timetable[3][day] += " OR " + code + "-" + type + room;
					}
				}
				else {
					if(timetable[2][day].equals("")) {
						timetable[2][day] = code + "-" + type + "-" + room;
						if(e.equals("13:00"))
							timetable[2][day] = code + "-" + type + "-" + room;
					}
				}
			}
			
			if(t.equals("12:00")) { 
				if(type.equals("LEC-")) {
					if(timetable[3][day].equals("")) {
						timetable[3][day] = code + "-" + type + room;
						if(e.equals("14:00")) 
							timetable[4][day] = code + "-" + type + room;
					}
					else if(!(timetable[3][day].equals("")) && (timetable[3][day].contains("TUT") || timetable[3][day].contains("LAB")))  {
  						timetable[3][day] = code + "-" + type + room;
  						if(e.equals("14:00")) 
  							timetable[4][day] = code + "-" + type + room;
  					}		
					else	
						timetable[3][day] += " OR " + code + "-" + type + room;
						if(e.equals("14:00"))
							timetable[4][day] += " OR " + code + "-" + type + room;
					
				}
				else {
					if(timetable[3][day].equals("")) {
						timetable[3][day] = code + "-" + type + "-" + room;
						if(e.equals("14:00"))
							timetable[4][day] = code + "-" + type + "-" + room;
					}
				}
			}
			
			if(t.equals("13:00")) { 
				if(type.equals("LEC-")) {
					if(timetable[4][day].equals("")) {
						timetable[4][day] = code + "-" + type + room;
						if(e.equals("15:00")) 
							timetable[5][day] = code + "-" + type + room;
					}
					else if(!(timetable[4][day].equals("")) && (timetable[4][day].contains("TUT") || timetable[4][day].contains("LAB")))  {
  						timetable[4][day] = code + "-" + type + room;
  						if(e.equals("15:00")) 
  							timetable[5][day] = code + "-" + type + room;
  					}
					else {
						timetable[4][day] += " OR " + code + "-" + type + room;
						if(e.equals("15:00")) 
							timetable[5][day] += " OR " + code + "-" + type + room;
					}
				}
				else {
					if(timetable[4][day].equals("")) {
						timetable[4][day] = code + "-" + type + "-" + room;
						if(e.equals("15:00"))
							timetable[5][day] = code + "-" + type + "-" + room;
					}
				}
			}
			
			if(t.equals("14:00")) { 
				if(type.equals("LEC-")) {
					if(timetable[5][day].equals("")) {
						timetable[5][day] = code + "-" + type + room;
						if(e.equals("16:00")) 
							timetable[6][day] = code + "-" + type + room;
					}
					else if(!(timetable[5][day].equals("")) && (timetable[5][day].contains("TUT") || timetable[5][day].contains("LAB")))  {
  						timetable[5][day] = code + "-" + type + room;
  						if(e.equals("16:00")) 
  							timetable[6][day] = code + "-" + type + room;
  					}
					else {
						timetable[5][day] += " OR " + code + "-" + type + room;
						if(e.equals("16:00")) 
							timetable[6][day] += " OR " + code + "-" + type + room;
					}
				}
				else {
					if(timetable[5][day].equals("")) {
						timetable[5][day] = code + "-" + type + "-" + room;
						if(e.equals("16:00"))
							timetable[6][day] = code + "-" + type + "-" + room;
					}
				}
			}
			
			if(t.equals("15:00")) { 
				if(type.equals("LEC-")) {
					if(timetable[6][day].equals("")) {
						timetable[6][day] = code + "-" + type + room;
						if(e.equals("17:00")) 
							timetable[7][day] = code + "-" + type + room;
					}
					else if(!(timetable[6][day].equals("")) && (timetable[6][day].contains("TUT") || timetable[6][day].contains("LAB")))  {
  						timetable[6][day] = code + "-" + type + room;
  						if(e.equals("17:00")) 
  							timetable[7][day] = code + "-" + type + room;
  					}
					else {
						timetable[6][day] += " OR " + code + "-" + type + room;
						if(e.equals("17:00")) 
							timetable[7][day] += " OR " + code + "-" + type + room;
					}
				}
				else {
					if(timetable[6][day].equals("")) {
						timetable[6][day] = code + "-" + type + "-" + room;
						if(e.equals("17:00"))
							timetable[7][day] = code + "-" + type + "-" + room;
					}
				}
			}
			
			if(t.equals("16:00")) { 
				if(type.equals("LEC-")) {
					if(timetable[7][day].equals("")) {
						timetable[7][day] = code + "-" + type + room;
						if(e.equals("18:00")) 
							timetable[8][day] = code + "-" + type + room;
					}
					else if(!(timetable[7][day].equals("")) && (timetable[7][day].contains("TUT") || timetable[7][day].contains("LAB")))  {
  						timetable[7][day] = code + "-" + type + room;
  						if(e.equals("18:00")) 
  							timetable[8][day] = code + "-" + type + room;
  					}
					else {
						timetable[7][day] += " OR " + code + "-" + type + room;
						if(e.equals("18:00")) 
							timetable[8][day] += " OR " + code + "-" + type + room;
					}
				}
				else {
					if(timetable[7][day].equals("")) {
						timetable[7][day] = code + "-" + type + "-" + room;
						if(e.equals("18:00"))
							timetable[8][day] = code + "-" + type + "-" + room;
					}
				}
			}
			
			if(t.equals("17:00")) {
				if(type.equals("LEC-")) {
					if(timetable[8][day].equals(""))
						timetable[8][day] = code + "-" + type + room;
					else if(!(timetable[8][day].equals("")) && (timetable[8][day].contains("TUT") || timetable[8][day].contains("LAB")))
						timetable[8][day] = code + "-" + type + room;
					else
						timetable[8][day] += " OR " + code + "-" + type + room;
				}
				else {
					if(timetable[8][day].equals(""))
						timetable[8][day] = code + "-" + type + "-" + room;
				}
			}
  	}
  	
  	// method with removes duplicates of classes from timetable array
  	// only removing duplicate tutorials or labs as have a choice of which one to go to
  	// cannot remove duplicate lectures as we only have one of each lecture
  	public static String[][] removeDuplicates(String[][] timetable) {
  		String mCode = "";	// empty string variable
  		for(int i =0; i < timetable.length; i++) {	// iterate through rows in timetable
  			for(int j = 0; j < timetable[i].length; j++) {	// iterate through columns in timetable
  				if(timetable[i][j].contains("LAB")) {	// if class in timetable is a lab
  					mCode = timetable[i][j].substring(0, 6); // get the module code of class
  					for(int x =0; x < timetable.length; x++) {	// iterate through timetable again
  			  			for(int y = 0; y < timetable[x].length; y++) {
  			  				if(timetable[x][y].contains("LAB") && timetable[x][y].contains(mCode) && !(timetable[x][y].equals(timetable[i][j]))) {
  			  				// if there are duplicates in timetable	
  			  					timetable[x][y] = "";	// remove the duplicates
  			  				}
  			  			}
  			  		}
  				}
  				
  				//same process for tutorials
  				else if(timetable[i][j].contains("TUT")) {
  					mCode = timetable[i][j].substring(0, 6);
  					for(int x =0; x < timetable.length; x++) {
  			  			for(int y = 0; y < timetable[x].length; y++) {
  			  				if(timetable[x][y].contains("TUT") && timetable[x][y].contains(mCode) && !(timetable[x][y].equals(timetable[i][j]))) {
  			  					timetable[x][y] = "";
  			  				}
  			  			}
  			  		}
  				}
  				
  			}
  		}
  		return timetable; // return new timetable with no duplicates
  	}
}