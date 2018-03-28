package ul.jack.fyp.model;

import java.util.ArrayList;

public class ModulesDao {	
	/*public List<Modules> findAll(String email) {
		 	List<Modules> moduleList = new ArrayList<Modules>();
		 	Connection c = null;
	    	String sql = "SELECT * FROM modules WHERE email = " +"'" +email+"'";
	        try {
	        	c = DBUtils.getTestConnection();
	            PreparedStatement ps = c.prepareStatement(sql);
	            ResultSet rs = ps.executeQuery(sql);
	            while (rs.next()) {
	                moduleList.add(processRow(rs));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new RuntimeException(e);
			} finally {
				DBUtils.close(c);
			}
		 	
	        return moduleList;
	    }*/
	 /*protected Modules processRow(ResultSet rs) throws SQLException {
	        Modules modules = new Modules();
	        //modules.setEmail(rs.getString("email"));
	        modules.setModule1(rs.getString("module1"));
	        modules.setModule2(rs.getString("module2"));
	        modules.setModule3(rs.getString("module3"));
	        modules.setModule4(rs.getString("module4"));
	        modules.setModule5(rs.getString("module5"));
	        modules.setModule6(rs.getString("module6"));
	        modules.setModule7(rs.getString("module7"));
	        modules.setModule8(rs.getString("module8"));
	        modules.setModule9(rs.getString("module9"));
	        modules.setModule10(rs.getString("module10"));
	        return modules;
	    }*/
}