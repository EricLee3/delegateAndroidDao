package com.service;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.service.command.connection.DataBaseManager;
import com.service.command.log.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONException;

public class sampleLogin {
	
	private static sampleLogin instance = new sampleLogin();

	JSONArray jsArray;
	public static sampleLogin getInstance() {
		return instance;
	}
   
	private sampleLogin() {
		
	} 
	
//	public List<Object> selectBrand() throws IOException {
	public String validateID(String email, String password) throws IOException {
		String methodName ="com.service.sampleLogin()";
		String result = null;
		Logger.debug(methodName);
		
		Connection 			conn	= null;
		PreparedStatement	pstmt	= null;
		ResultSet			rs		= null;
		StringBuffer   	sqlBuffer  	= new StringBuffer(500);	//쿼리문
		
		HashMap hm 			= new HashMap();
		List	vendorList 	= new ArrayList();
		
		int cnt 	= 0;
		
		try
		{ 
			conn = DataBaseManager.getConnection();
			
			sqlBuffer.append(" SELECT  *            ");  
			sqlBuffer.append(" FROM    CSUSER		");
			sqlBuffer.append(" where   user_id = ? and user_pwd = ? ");
			
			
			pstmt = conn.prepareStatement(sqlBuffer.toString());
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			
			if (rs.next())  {
				result = "valid user id";
			} else  {
				result = "invalid user id or password";
			}
		
		} catch(Exception e) {
			Logger.debug("###Error###:"+ methodName +" Error :"+ e.toString());
		} finally {
			try
			{
				if( rs !=null ) try{ rs.close(); rs = null; }catch(Exception e){}finally{rs = null;}
				if( pstmt != null ) try{ pstmt.close(); pstmt = null; }catch(Exception e){}finally{pstmt = null;}
				
				DataBaseManager.close(conn);
				if( conn!= null ) try{conn.close(); conn = null; }catch(Exception e){}finally{conn = null;}
			}
			catch (Exception e)
			{
				Logger.debug("###Error###:"+ methodName +" Error :"+ e.toString());
			}
		}
		
		return result;
	}
}
