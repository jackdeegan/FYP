package ul.jack.fyp.model;

//import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
//@XmlRootElement
public class Modules {
	private String [] modules;
	
	public String [] getModules() {
		return modules;
	}
	public void setModules(String [] modules) {
		this.modules = modules;
	}
	
}