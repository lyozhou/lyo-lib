package Support;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connect2Database {
	private Properties proper;
	private Connection con;
	private FileReader fr;

	public static void main(String[] arg) {
		new Connect2Database().loadDriver();
	}

	public void loadDriver() {
		// TODO Auto-generated method stub
		proper = new Properties();
		try {
			fr = new FileReader("src/Support/conData.properties");
			proper.load(fr);
			Class.forName(proper.getProperty("db.driverClass"));
			System.out.println("driver is successfully loaded");
			this.conData();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("file not found");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Cant found the database driver");
			e.printStackTrace();
		} 
//		finally {
//			try {
//				fr.close();
//				con.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}

	private void conData() {
		// TODO Auto-generated method stub
		try {
			con = DriverManager.getConnection(proper.getProperty("db.serverHost"),proper.getProperty("db.user"),proper.getProperty("db.password"));
			System.out.println("Successfully contect to database");  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Cant contect to the database");
			e.printStackTrace();
		}
		
	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}
	
}
