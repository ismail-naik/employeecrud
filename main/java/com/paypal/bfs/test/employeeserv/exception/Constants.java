package com.paypal.bfs.test.employeeserv.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

public class Constants {
	private static Log log = LogFactory.getLog(Constants.class);
	/*
	 * private static ResourceBundle RESOURCE_BUNDLE = null; public static final
	 * String COUNTRY = "en"; public static final String LANGUAGE = "US";
	 * 
	 * public static String getProperty(String key) { if (RESOURCE_BUNDLE == null) {
	 * RESOURCE_BUNDLE =
	 * ResourceBundle.getBundle("com/geco/resources/partnerintegration"); } return
	 * RESOURCE_BUNDLE.getString(key); }
	 */

	public static String generateResJson(int type, String errorMsg) throws Exception {
		JSONObject obj = new JSONObject();
		try {
			switch (type) {
			case 0:
				obj.put("ErrorCode", "64004");
				obj.put("ErrorMsg", "Service Temporarily Unavailable " + errorMsg);
				break;
			case 1:
				obj.put("ErrorCode", "64003");
				obj.put("ErrorMsg", errorMsg);
				break;
			case 2:
				obj.put("ErrorCode", "64002");
				obj.put("ErrorMsg", errorMsg);
				break;
			case 3:
				obj.put("ErrorCode", "64005");
				obj.put("ErrorMsg", errorMsg);
				break;
			case 4:
				obj.put("ErrorCode", "64006");
				obj.put("ErrorMsg", errorMsg);
			}
		} catch (JSONException e) {
			log.info(e.getCause() + " :: " + e.getMessage());
			return "Service Temporarily Unavailable";
		}
		return generateErrorResponse(obj);
	}

	public static String generateErrorResponse(JSONObject error) throws Exception {
		JSONObject dummyObj = new JSONObject();
		JSONObject errorObj = new JSONObject();
		errorObj.put("Error", error);
		JSONObject responseObj = new JSONObject();
		responseObj.put("Data", dummyObj);
		responseObj.put("Warning", dummyObj);
		responseObj.put("Errors", error);
		return responseObj.toString();
	}

	public static String generateResponse(HttpStatus statusCode, String message) throws Exception {
		JSONObject validateObject = new JSONObject();
		validateObject.put("status",statusCode.value());
		validateObject.put("message",message);	
		
		
		return validateObject.toString();
	}
	
	public static String generateResponse(HttpStatus statusCode, HttpStatus message,String error) throws Exception {
		JSONObject validateObject = new JSONObject();
		validateObject.put("status",statusCode.value());
		validateObject.put("message",error);	
		validateObject.put("error",message);
		
		
		return validateObject.toString();
	}

	
}
