package ul.jack.fyp.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.Gson;

@XmlRootElement
public class User {
	private String email;
	private String password;
	private String fname;
	private String lname;
	private int studentNum;
	private int countryID;
	private String address;
	private Result result;
	
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public int getStudentNum() {
		return studentNum;
	}
	public void setStudentNum(int studentNum) {
		this.studentNum = studentNum;
	}
	public int getCountryID() {
		return countryID;
	}
	public void setCountryID(int countryID) {
		this.countryID = countryID;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public static void main (String[] args) {
		User u = new User();
		u.setEmail("test@test.com");
		u.setAddress("addr");
		u.setCountryID(1);
		u.setFname("post");
		u.setLname("test");
		u.setPassword("password");
		u.setStudentNum(123456);
		
		Gson g = new Gson();
		System.out.println(g.toJson(u));
	}
}
