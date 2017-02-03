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

public class sampleSelect {
	
	private static sampleSelect instance = new sampleSelect();

	JSONArray jsArray;
	public static sampleSelect getInstance() {
		return instance;
	}
   
	private sampleSelect() {
		
	} 
	
//	public List<Object> selectBrand() throws IOException {
	public String selectBrand() throws IOException {
		String methodName ="com.service.sampleSelect.selectBrand()";
		Logger.debug(methodName);
		
		Connection 			conn	= null;
		PreparedStatement	pstmt	= null;
		ResultSet			rs		= null;
		StringBuffer   	sqlBuffer  	= new StringBuffer(500);	//쿼리문
		
		HashMap hm 			= new HashMap();
		List	vendorList 	= new ArrayList();
		
		int cnt 	= 0;
		
		try { 
			conn = DataBaseManager.getConnection();
			
			sqlBuffer.append(" SELECT  BRAND_NM, BRAND_CD          ");  
			sqlBuffer.append(" FROM    CMBRAND			 ");
			pstmt = conn.prepareStatement(sqlBuffer.toString());
			rs = pstmt.executeQuery();
			
			jsArray = new JSONArray();
			while(rs.next()) {
				JSONObject jsObj = new JSONObject();
				jsObj.put("BRAND_NM", rs.getString("BRAND_NM"));
				jsObj.put("BRAND_CD", rs.getString("BRAND_CD"));
				jsArray.add(jsObj);
//					hm = new HashMap();
//					hm.put("BRAND_NM", rs.getString("BRAND_NM"));
//					vendorList.add(hm);
			}
		} catch(Exception e) {
			Logger.debug("###Error###:"+ methodName +" Error :"+ e.toString());
		} finally  {
			try  {
				if( rs !=null ) try{ rs.close(); rs = null; }catch(Exception e){}finally{rs = null;}
				if( pstmt != null ) try{ pstmt.close(); pstmt = null; }catch(Exception e){}finally{pstmt = null;}
				
				DataBaseManager.close(conn);
				if( conn!= null ) try{conn.close(); conn = null; }catch(Exception e){}finally{conn = null;}
			} catch (Exception e)  {
				Logger.debug("###Error###:"+ methodName +" Error :"+ e.toString());
			}
		}
		return jsArray.toString();
	}

	public String selectStock() throws IOException {
		String methodName ="com.service.sampleSelect.selectBrand()";
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
			
			sqlBuffer.append(" SELECT  CENTER_CD, ITEM_CD, ITEM_STATE, SUM(STOCK_QTY) AS STOCK_QTY FROM LS010NM ");  
			sqlBuffer.append(" WHERE BRAND_CD = ?			 ");
			sqlBuffer.append(" GROUP BY CENTER_CD, ITEM_CD, ITEM_STATE			 ");
			
			pstmt = conn.prepareStatement(sqlBuffer.toString());
			pstmt.setString(1, "6544");
			rs = pstmt.executeQuery();
			
			jsArray = new JSONArray();
				while(rs.next())
				{
					JSONObject jsObj = new JSONObject();
					jsObj.put("CENTER_CD", rs.getString("CENTER_CD"));
					jsObj.put("ITEM_CD", rs.getString("ITEM_CD"));
					jsObj.put("ITEM_STATE", rs.getString("ITEM_STATE"));
					jsObj.put("STOCK_QTY", rs.getString("STOCK_QTY"));
					jsArray.add(jsObj);
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
		
		return jsArray.toString();
	}

}
