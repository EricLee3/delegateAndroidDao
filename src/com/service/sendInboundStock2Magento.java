package com.service;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import com.service.command.connection.DataBaseManager;
import com.service.command.log.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.command.log.Logger;


public class sendInboundStock2Magento extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		String methodName ="com.service.sendInboundStock2Magento()";
		Connection 			conn	= null;
		PreparedStatement	pstmt	= null;
		ResultSet			rs		= null;
		StringBuffer   	sqlBuffer  	= new StringBuffer(500);	//쿼리문
		
		String result = "";
		
		JSONObject jObj = new JSONObject();
		JSONObject jHeader = new JSONObject();
		JSONArray jBody = new JSONArray();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String msg = "";
		
       	jHeader.put("bizUserId", "pumatest");
    	jHeader.put("bizUserPw", "!pumatest66t");
    	jHeader.put("callId", "sendUpdInventory");
    	jHeader.put("encType", "utf8");
		
		
		try {
			request.setCharacterEncoding("UTF-8");
			
			if(request.getParameter("msg")!=null) {
				msg = request.getParameter("msg");
			}
			
			if(msg.length() < 1) {
				return;
			} else {
				conn = DataBaseManager.getConnection();
				if (request.getParameter("msg") != null)  {
					sqlBuffer.append(request.getParameter("msg")); 
				} else  {
					return;
				}
				pstmt = conn.prepareStatement(request.getParameter("msg"));
				rs = pstmt.executeQuery();
				
				while(rs.next())
				{
					JSONObject jsObj = new JSONObject();
					jsObj.put("centerCd", rs.getString("CENTER_CD"));
					jsObj.put("brandCd", rs.getString("BRAND_CD"));
					jsObj.put("itemCd", rs.getString("ITEM_CD"));
					jsObj.put("itemState", rs.getString("ITEM_STATE"));
					jsObj.put("confirmQty", "+"+rs.getString("CONFIRM_QTY"));
					jBody.add(jsObj);
				}
				//System.out.println(jsArray.toString());
				jObj.put("body", jBody);
				
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
		params.add(new BasicNameValuePair("msg", jObj.toString()));
		result = callAPI("https://wmsapi.isecommerce.co.kr:3133/recvApi", params);
	}	
			
	private String callAPI(String url, List<NameValuePair> params ) {
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
		httpClient.getParams().setParameter("http.protocol.content-charset", "UTF-8");
		
		HttpParams httpParams = httpClient.getParams();
		httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
		httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
		
		HttpPost request = new HttpPost(url);
		HttpResponse response = null;
		String result = "";
		try {
			
			request.addHeader("content-type", "application/x-www-form-urlencoded");
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			
			response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			
			InputStream respIS = entity.getContent();
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			
			byte[] buffer = new byte[1024];
			int line = -1;
			while((line=respIS.read(buffer))!= -1) {
				arrayOutputStream.write(buffer, 0, line);
			}
			arrayOutputStream.flush();
			arrayOutputStream.close();
			
			byte[] respByte = arrayOutputStream.toByteArray();
			result = new String(respByte, 0, respByte.length); 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
			doPost(request, response);
	}
}
