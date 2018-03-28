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

public class Module {

  private static String readAll(Reader rd) throws IOException {
	  StringBuilder sb = new StringBuilder();
	  int cp;
	  while ((cp = rd.read()) != -1) {
		  sb.append((char) cp);
	  }
	  return sb.toString();
  }

  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
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
  
  public ArrayList<List<String>> moduleDetails(String [] moduleCodes) throws IOException, JSONException {
	  
	  List<String> temp = new ArrayList<String>(Arrays.asList(moduleCodes));
	  temp.removeAll(Arrays.asList("", null));
	  String[] modCodes = new String[temp.size()];
	  modCodes = temp.toArray(modCodes);
	  String day, time, startTime,endTime,type, room;
	  String[] times = new String[1];
	  ArrayList<List<String>> modDetails = new ArrayList<List<String>>();
	  for(int i = 0; i < modCodes.length; i++) {
		  JSONObject json = readJsonFromUrl("http://35.197.202.71/module-timetable.php?module=" +moduleCodes[i]);
		  JSONArray classes = json.getJSONArray("classes");
		  int count = classes.length();
		  for(int j = 0; j < count; j++) {
			  JSONObject jsonObject = classes.getJSONObject(j);
			  day = Integer.toString(jsonObject.getInt("day"));
			  time = jsonObject.getString("time");
			  times = time.split("-");
			  startTime = times[0];
			  endTime = times[1];
			  type = jsonObject.getString("type");
			  room = jsonObject.getString("room");
			  modDetails.add(Arrays.asList(modCodes[i],day,startTime,endTime,type,room));
		  }
	  }
	  return modDetails;
  } 
  
 
  	public ArrayList<String> conflicts(ArrayList<List<String>>details) throws IOException{
	  String t1[] = new String[1];
	  String t2[] = new String[1];
	  String t3[] = new String[1];
	  String t4[] = new String[1];
	  int tm1,tm2,tm3,tm4;
	  ArrayList<String> allConflicts = new ArrayList<String>();
	  int conflicts = 0;
	  for (int i = 0; i < details.size(); i++)
	  {
		  for (int j = 0; j < details.size(); j++)
	      {
			  if(!((details.get(i).get(0)).equals(details.get(j).get(0)))) //if module codes are different
    		  {
	    		  if((details.get(i).get(1)).equals(details.get(j).get(1))) //if days are equals
	    		  {
	    			  t1 = details.get(i).get(2).split(":");
	    			  t2 = details.get(i).get(3).split(":");
	    			  t3 = details.get(j).get(2).split(":");
	    			  t4 = details.get(j).get(3).split(":");;
	    			  tm1 = Integer.parseInt(t1[0]);
	    			  tm2 = Integer.parseInt(t2[0]);
	    			  tm3 = Integer.parseInt(t3[0]);
	    			  tm4 = Integer.parseInt(t4[0]);
	    			  
	    			  if((details.get(i).get(2)).equals(details.get(j).get(2)) || (details.get(i).get(3)).equals(details.get(j).get(3))
	    					  || (tm3>tm1 && tm3<tm2) || (tm4>tm1 && tm4<tm2)) //if times are equal or conflict
	    			  {
	    				  conflicts++;
	    				  String temp;
	    				  temp = details.get(i) + " CONFLICTS WITH " + details.get(j);
	    				  allConflicts.add(temp);
	    				  for(int k = 0; k<allConflicts.size();k++) {
	    					  if(allConflicts.get(k).equals(details.get(j) + " CONFLICTS WITH " + details.get(i)))
	    						  allConflicts.remove(k);
	    				  }
	    			  }
	    		  }
    		  }
	      } 
	  }
	  
	  conflicts = conflicts/2;
	  return allConflicts;
  }
  	
  	public String[][] generateTimetable(ArrayList<List<String>>details){
  		int day;
  		String [][]timetable = new String[9][5]; //9 hours - 5 days
  		for(int g = 0; g < timetable.length; g++) {
  			for(int h = 0; h< timetable[g].length; h++) {
  				timetable[g][h] = "";
  	  		}
  		}
  		
  		for(int i = 0; i < details.size();i++) 
  		{
  			if(details.get(i).get(1).equals("1")) {
  				day = 0;				//start time			//end time			   //module code		  //type				//room
  				addData(day, timetable, details.get(i).get(2), details.get(i).get(3), details.get(i).get(0), details.get(i).get(4), details.get(i).get(5));
  			}
  			else if(details.get(i).get(1).equals("2")) {
  				day = 1;
  				addData(day, timetable, details.get(i).get(2), details.get(i).get(3), details.get(i).get(0), details.get(i).get(4), details.get(i).get(5));
  			}
  			else if(details.get(i).get(1).equals("3")) {
  				day = 2;
  				addData(day, timetable, details.get(i).get(2), details.get(i).get(3), details.get(i).get(0), details.get(i).get(4), details.get(i).get(5));
  			}
  			else if(details.get(i).get(1).equals("4")) {
  				day = 3;
  				addData(day, timetable, details.get(i).get(2), details.get(i).get(3), details.get(i).get(0), details.get(i).get(4), details.get(i).get(5));
  			}	  			
  			else if(details.get(i).get(1).equals("5")) {
  				day = 4;
  				addData(day, timetable, details.get(i).get(2), details.get(i).get(3), details.get(i).get(0), details.get(i).get(4), details.get(i).get(5));
  			}
  		}
  		
  		timetable = removeDuplicates(timetable);
  		return timetable;
  	}
  	
  	public void addData(int day, String[][]timetable, String t, String e, String code, String type, String room) {
  			if(t.equals("09:00")) {
  				if(type.equals("LEC-")) {
  					if(timetable[0][day].equals("")) {
  						timetable[0][day] = code + "-" + type + room;
  						if(e.equals("11:00")) 
  							timetable[1][day] = code + "-" + type + room;
  					}
  					else if(!(timetable[0][day].equals("")) && (timetable[0][day].contains("TUT") || timetable[0][day].contains("LAB")))  {
  						timetable[0][day] = code + "-" + type + room;
  						if(e.equals("11:00")) 
  							timetable[1][day] = code + "-" + type + room;
  					}
  					else {
  						timetable[0][day] += " OR " + code + "-" + type + room;
  						if(e.equals("11:00")) 
  							timetable[1][day] += " OR " +code + "-" + type + room;
  					}
  				}
  				else { //if tutorial or lab
  					if(timetable[0][day].equals("")) {
  						timetable[0][day] = code + "-" + type + "-" + room;
  						if(e.equals("11:00")) 
  							timetable[1][day] = code + "-" + type + "-" + room;
  					}
  				}
  			}
  			
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
  	
  	public static String[][] removeDuplicates(String[][] timetable) {
  		String mCode = "";
  		for(int i =0; i < timetable.length; i++) {
  			for(int j = 0; j < timetable[i].length; j++) {
  				if(timetable[i][j].contains("LAB")) {
  					mCode = timetable[i][j].substring(0, 6);
  					for(int x =0; x < timetable.length; x++) {
  			  			for(int y = 0; y < timetable[x].length; y++) {
  			  				if(timetable[x][y].contains("LAB") && timetable[x][y].contains(mCode) && !(timetable[x][y].equals(timetable[i][j]))) {
  			  					timetable[x][y] = "";
  			  				}
  			  			}
  			  		}
  				}
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
  		return timetable;
  	}
}