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

	public String selectStock(String strBrandNm) throws IOException {
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
			
			sqlBuffer.append(" SELECT  CENTER_CD, ITEM_CD, ITEM_STATE, SUM(STOCK_QTY) AS STOCK_QTY FROM LS010NM A ");  
			sqlBuffer.append(" join CMBRAND B ON A.BRAND_CD = B.BRAND_CD  ");  
			sqlBuffer.append(" WHERE B.BRAND_NM = ?			 ");
//			sqlBuffer.append(" and rownum < 100			 ");
			sqlBuffer.append(" GROUP BY CENTER_CD, ITEM_CD, ITEM_STATE			 ");
			
			pstmt = conn.prepareStatement(sqlBuffer.toString());
			pstmt.setString(1, strBrandNm);
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
	
	public String selectOutbound(String strBrandNm) throws IOException {
		String methodName ="com.service.sampleSelect.selectOutbound()";
		Logger.debug(methodName);
		
		Connection 			conn	= null;
		PreparedStatement	pstmt	= null;
		PreparedStatement	pstmt1	= null;
		ResultSet			rs		= null;
		ResultSet			rs1		= null;
		StringBuffer   	sqlBuffer  	= new StringBuffer(500);	//쿼리문
		StringBuffer   	sqlBuffer1  = new StringBuffer(500);	//쿼리문
		
		HashMap hm 			= new HashMap();
		List	vendorList 	= new ArrayList();
		
		int cnt 	= 0;
		
		try
		{ 
			conn = DataBaseManager.getConnection();
			
			sqlBuffer.append(" select center_cd, order_no, ord_nm from lo010nm  ");  
			sqlBuffer.append(" where outbound_state = 10 and brand_cd = '6101'		 ");
			sqlBuffer.append(" and inout_cd = 'D10' and order_date = '15/08/01'	 ");
			
			pstmt = conn.prepareStatement(sqlBuffer.toString());
			//pstmt.setString(1, strBrandNm);
			rs = pstmt.executeQuery();
			
			jsArray = new JSONArray();
			while(rs.next())  {
				// to separate order and detail
				JSONObject jsObj = new JSONObject();
				jsObj.put("CENTER_CD", rs.getString("center_cd"));
				jsObj.put("ORDER_NO", rs.getString("order_no"));
				jsObj.put("ORD_NM", rs.getString("ord_nm"));
				
				sqlBuffer1.append(" select item_cd, order_qty, brand_no from lo010nd ");
				sqlBuffer1.append(" where center_cd = 'A1' and brand_cd = '6101' and order_date = '15/08/01' and order_no = ? ");
				pstmt1 = conn.prepareStatement(sqlBuffer1.toString());
				pstmt1.setString(1,  rs.getString("order_no"));
				rs1 = pstmt1.executeQuery();
				
				JSONObject jsObj1 = new JSONObject();
				JSONArray jsArray1 = new JSONArray();
				while(rs1.next())  {
					jsObj1.put("ITEM_CD", rs1.getString("item_cd"));
					jsObj1.put("ORDER_QTY", rs1.getInt("order_qty"));
					jsObj1.put("BRAND_NO", rs1.getString("brand_no"));
					jsArray1.add(jsObj1);
				}
				jsObj.put("orderList", jsArray1);
				jsArray.add(jsObj);
				
				rs1 = null; sqlBuffer1.setLength(0); pstmt1 = null;
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
