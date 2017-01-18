package com.service.command.connection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;


import org.apache.commons.dbutils.DbUtils;
import com.service.command.log.Logger;


/**
 * @author sylyu
 * @version 1.0
 */
public class DataBaseManager{
	public DataBaseManager() {
		
	}
  	/**
	 * Log4j Object.
	 */
	public synchronized static Connection getConnection() throws SQLException,Exception {
		ConnectInfo connectInfo = null;
		Connection con = null;
		
		try {
			connectInfo = new ConnectInfo();
			
			Class.forName(connectInfo.getDbDriver()).newInstance();
			connectInfo.setDbConnect("jdbc:oracle:thin:@220.117.243.55:1521:WMS");
			connectInfo.setDbAccount("WMS_USER");
			connectInfo.setDbPassword("WMS_USER");
			
			con = java.sql.DriverManager.getConnection(connectInfo.getDbConnect(), connectInfo.getDbAccount(), connectInfo.getDbPassword());
			Logger.debug("DB Connect ");
			connectInfo = null;
		} catch (Exception e) {	
			Logger.error(e);
		}
		return con;
	}
	
	
	/**
	 * Stored Procedure를 실행하기위해 prepareCall합니다.
	 *
	 * @param procedure
	 * @return boolean
	 */
	public static void prepareCall(Connection conn, String procedure) throws SQLException
	{
		@SuppressWarnings("unused")
		CallableStatement stmt = null;
		
		try
		{
			//Logger.info("call procedure:"+procedure);
			stmt = conn.prepareCall(procedure);
			stmt.execute();
		}
		catch (SQLException ex)
		{
			Logger.error(ex);
			throw ex;
		}
	}
 
	
	public static void close(Connection conn) {
		try {
			Logger.debug("DB Connect out ");
			conn.setAutoCommit(true);
			DbUtils.closeQuietly(conn);
		} catch (Exception e) {
			Logger.error(e);
		}
	}
}
