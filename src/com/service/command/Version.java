package com.service.command;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Version {
	public static void main(String[] args)  {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		String url = "jdbc:postgresql://localhost/postgres";
		String user = "postgres";
		String password = "isec0902!@";
		
		try {

			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return;

		}
		
		try  {
			con = DriverManager.getConnection(url, user, password);
			st = con.createStatement();
			rs = st.executeQuery(" SELECT * from  VERSION() ");
			if (rs.next())  {
				System.out.println(rs.getString(1));
			} 
			rs = st.executeQuery(" select * from weather ");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCnt = rsmd.getColumnCount();
			if (rs.next())  {
				System.out.println(rs.getString(1));
				for (int i=1; i <= columnCnt; i++)  {
					System.out.println(rsmd.getColumnName(i)+", "+rs.getString(rsmd.getColumnName(i)));
				}
			}
			
		} catch (SQLException ex)  {
			Logger lgr = Logger.getLogger(Version.class.getName()); 
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		} finally { try { if (rs != null) { rs.close(); } if (st != null) { st.close(); } if (con != null) { con.close(); } } catch (SQLException ex) { Logger lgr = Logger.getLogger(Version.class.getName()); lgr.log(Level.WARNING, ex.getMessage(), ex); } }
	}
}
