package ul.jack.fyp.constants;

public enum Constants {

	DB_CLASS_MARIADB("org.mariadb.jdbc.Driver");
	
	private String value;
	
	private Constants(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
}
