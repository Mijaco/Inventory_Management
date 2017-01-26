package com.ibcs.desco.common.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.google.gson.Gson;
import com.ibcs.desco.inventory.constants.ContentType;

public abstract class Constrants {
	// constant value setup
	public static final String CS_RECEIVING_ITEMS = "CS_RECEIVING_ITEMS";
	public static final String LS_CS_REQUISITION = "LS_CS_REQUISITION";
	public static final String LS_PD_CS_REQUISITION = "LS_PD_CS_REQUISITION";
	public static final String LS_PD_SS_REQUISITION = "LS_PD_SS_REQUISITION";
	public static final String SS_CS_REQUISITION = "SS_CS_REQUISITION";
	public static final String OPEN = "OPEN";
	public static final String DONE = "DONE";
	public static final String BACK = "BACK";
	public static final String REJECT = "REJECT";
	public static final String REJECT_HEADER = "Reject History";
	public static final String AUCTION_DELIVERY = "AUCTION_DELIVERY";
	
	public static final String TESTING = "TESTING";
	public static final String RECEIVED = "RECEIVED";
	public static final String CS_STORE_TICKET = "CS_STORE_TICKET";
	public static final String CS_GATE_PASS = "CS_GATE_PASS";
	public static final String LS_CS_RETURN_SLIP = "LS_CS_RETURN_SLIP";
	public static final String LS_PD_CS_RETURN_SLIP = "LS_PD_CS_RETURN_SLIP";
	public static final String LS_PD_SS_RETURN_SLIP = "LS_PD_SS_RETURN_SLIP";
	public static final String SS_CS_RETURN_SLIP = "SS_CS_RETURN_SLIP";
	public static final String CN2_LS_RETURN_SLIP = "CN2_LS_RETURN_SLIP";
	public static final String LS_GATE_PASS = "LS_GATE_PASS";
	public static final String CN_PD_CS_REQUISITION = "CN_PD_CS_REQUISITION";
	public static final String CN_PD_SS_REQUISITION = "CN_PD_SS_REQUISITION";
	public static final String CN_PD_CS_RETURN = "CN_PD_CS_RETURN";
	public static final String CN_PD_SS_RETURN = "CN_PD_SS_RETURN";
	public static final String SND_PARENT_CODE = "520";
	public static final String PD_PARENT_CODE = "525";
	public static final String ASSENDING = "ASC";
	public static final String DECENDING = "DESC";
	public static final String WS_JOB_CARD = "WS_JOB_CARD";
	public static final String CN_WS_CS_REQUISITION = "CN_WS_CS_REQUISITION";
	public static final String CN_WS_SS_REQUISITION = "CN_WS_SS_REQUISITION";
	public static final String ROLE_PROJECT_DIRECTOR = "ROLE_PROJECT_DIRECTOR";
	public static final String ROLE_PROJECT_ = "ROLE_PROJECT_";
	
	public static final String SS_DEPT_ID = "510";
	public static final String CS_DEPT_ID = "505";

	// Added by Ashid || All Ledger Name should be added inside enum
	public enum LedgerBook {
		NEW_SERVICEABLE, RECOVERY_SERVICEABLE, UNSERVICEABLE, AUCTION
	}

	public enum LedgerBook1 {
		NEW_SERVICEABLE, RECOVERY_SERVICEABLE, UNSERVICEABLE
	}

	public static final String NEW_SERVICEABLE = LedgerBook.NEW_SERVICEABLE
			.toString();
	public static final String RECOVERY_SERVICEABLE = LedgerBook.RECOVERY_SERVICEABLE
			.toString();
	public static final String UNSERVICEABLE = LedgerBook.UNSERVICEABLE
			.toString();
	public static final String AUCTION = LedgerBook.AUCTION.toString();

	public static final String REVENUE = "SND";
	public static final String WORKSHOP = "WS";
	public static final String PROJECT = "PROJECT";

	// Setting constant value for this controller
	public static final String LS_SS_REQUISITION = "LS_SS_REQUISITION";
	public static final String SS_STORE_TICKET = "SS_STORE_TICKET";

	// Setting constant value for this controller
	public static final String LS_SS_RETURN_SLIP = "LS_SS_RETURN_SLIP";

	// Setting constant value for this controller
	public static final String LS_ISSUED_REQUISITION = "LS_ISSUED_REQUISITION";
	public static final String LS_STORE_TICKET = "LS_STORE_TICKET";
	public static final String LOCAL_REQUISITION = "LOCAL_REQUISITION";

	// All Constant Value
	public static final String CS_VEHICLE_PERMISSION = "CS_VEHICLE_PERMISSION";
	public static final String SS_VEHICLE_PERMISSION = "SS_VEHICLE_PERMISSION";

	// constant for CS khath transfer
	public static final String CS_KHATH_TRANSFER = "CS_KHATH_TRANSFER";
	public static final String CS_KHATH_TRANSFER_APPROVED = "1";

	// Roles added by taleb for Contractor representative
	public static final String ROLE_CN_PD_USER = "ROLE_CN_PD_USER";
	public static final String ROLE_SND_CN_USER = "ROLE_SND_CN_USER";
	public static final String ROLE_WO_CN_USER = "ROLE_WO_CN_USER";

	// Added by Nasrin
	public static final String CN_SS_REQUISITION = "CN_SS_REQUISITION";
	public static final String SS_GATE_PASS = "SS_GATE_PASS";
	public static final String CN_SS_RETURN_SLIP = "CN_SS_RETURN_SLIP";
	public static final String SS_RECEIVING_ITEMS = "SS_RECEIVING_ITEMS";
	public static final String PND_JOB_ESTIMATE = "PND_JOB_ESTIMATE";
	public static final String PD_AS_BUILT = "PD_AS_BUILT";
	public static final String WS_CS_REQUISITION = "WS_CS_REQUISITION";
	public static final String WS_CS_X_REQUISITION = "WS_CS_X_REQUISITION";
	public static final String WS_CS_X_RETURN = "WS_CS_X_RETURN";
	public static final String WS_CS_RETURN = "WS_CS_RETURN";
	public static final String X_FORMER_JOB_CARD = "X_FORMER_JOB_CARD";
	public static final String PND_NO_EDIT = "_1";
	public static final String CENTRAL_STORE = "CENTRAL STORE";
	public static final String LOCAL_STORE = "LOCAL STORE";
	public static final String SUB_STORE = "SUB STORE";
	public static final String WS_AS_BUILT = "WS_AS_BUILT";
	public static final String WS_LS_PRV_NOTE = "WS_LS_PRV_NOTE";
	public static final String DEPT_ID_520 = "520";
	public static final String WS_GATE_PASS = "WS_GATE_PASS";
	public static final String WS_INVENTORY_REPORT = "WS_INVENTORY_REPORT";
	public static final String JOB_ASSIGN_TO_PROJECT_CONTRACTOR = "JOB_ASSIGN_TO_PROJECT_CONTRACTOR";
	public static final String FA_REG_KEY_SEQ = "FA_REG_KEY_SEQ";

	public static final String NO = "no";
	public static final String GENERAL_ITEM = "G";
	public static final String FIXED_ASSET_SEQ = "FIXED_ASSET_SEQ";

	// constant for Transformer Type || Added by Ashid
	public static final String SINGLE_PHASE = "1-PHASE";
	public static final String THREE_PHASE = "3-PHASE";
	public static final String ONE = "1";
	public static final String TWO = "2";
	public static final String THREE = "3";
	
	// constant for Demand Type or Requirement Type || Added by Ashid
	public static final String CODED_ITEM = "1";
	public static final String NON_CODED_ITEM = "2";
	
	public static final String SYSTEM_MATERIALS = "C";
	public static final String GENERAL_ITEMS = "G";
	
	// constant for Final Confirm Status || Added by Ashid
	public static final String CONFIRMED = "1";

	// constant for Transformer Work Type || Added by Ashid
	public static final String REPAIR_WORKS = "REPAIR WORKS";
	public static final String PREVENTIVE_MAINTENANCE = "PREVENTIVE MAINTENANCE";
	
	// XFormer Test Report Process Name || Added by Ashid
	public static final String TRANSFORMER_TEST_REPORT_1P = "TRANSFORMER_TEST_REPORT_1P";
	public static final String TRANSFORMER_TEST_REPORT_3P = "TRANSFORMER_TEST_REPORT_3P";
	
	//procurement module
	public static final String SOURCE_OF_FUND = "SOURCE_OF_FUND";
	public static final String APPROVING_AUTH = "APPROVING_AUTH";
	public static final String PROCUREMENT_METHOD = "PROCUREMENT_METHOD";
	public static final String PROC_PACKAGE_TYPE = "PROC_PACKAGE_TYPE";
	public static final String PROC_PACKAGE_NAME = "PROC_PACKAGE_NAME";
	public static final String UOM = "UOM";
	public static final String PROC_MEDIUM = "PROC_MEDIUM";
	public static final String PROCUREMENT_PROCESS = "PROCUREMENT_PROCESS";
	public static final String CONTRACT_MANAGEMENT_PROCESS = "CONTRACT_MANAGEMENT_PROCESS";
	
	//procurement module: PROC_PACKAGE_TYPE  || Added by Ashid
	public static final String GOODS = "1";
	public static final String WORKS_SERVICES = "2";
	public static final String MISCELLANEOUS = "3";
	
	//All Committee Name || Added by Ashid	
	public static final String CONDEMN_COMMITTEE = "CONDEMN_COMMITTEE";
	public static final String AUCTION_COMMITTEE = "AUCTION_COMMITTEE";
	
	
	// Workshop/meter Testing @dd by Taleb
	public static final String NEW = "NEW";
	public static final String OLD = "OLD";
	public static final String HTCT = "HTCT";
	public static final String LTCT = "LTCT";
	
	// WC means Whole Current Meter New and Old
	public static final String WC = "WC";
	public static final String CT = "CT";
	public static final String PT = "PT";
	
	// added by taleb
	public static final String USER_TYPE_DESCO = "DESCO";
	public static final String USER_TYPE_CONTRACTOR = "CN";
	
	//added by galeb
	//budget module
	public static final String BUDGET_EXPENDITURE_CATEGORY = "BUDGET_EXPENDITURE_CATEGORY";
	public static final String BUDGET_TYPE = "BUDGET_TYPE";

	public boolean isJSONValid(String JSON_STRING) {

		Gson gson = new Gson();
		try {
			gson.fromJson(JSON_STRING, Object.class);
			return true;
		} catch (com.google.gson.JsonSyntaxException ex) {
			return false;
		}
	}
	
	public static String getXFormerType(String itemName){
		
		
		String[] s = itemName.split(" ");
		String transformerType = "";
		Double db=75d;

		if (Double.valueOf(s[0].toString()) <= db) {
			transformerType = ContentType.SINGLE_PHASE.toString();
		} else {
			transformerType = ContentType.THREE_PHASE.toString();
		}
		
		return transformerType;
	}
	public static final String DATE_FORMAT = "dd-MM-yyyy";
	
	public static String theMonth(int month){
	    String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	    return monthNames[month];
	}
	 public static String getCurrentmonth(String dateInString) throws ParseException{   
	        int month;
	        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	        //String dateInString = "22-01-2015";
	        Date date = sdf.parse(dateInString);

	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);     
	        month = calendar.get(Calendar.MONTH);
	        //month = month+1;
	       // System.out.println("Current month is  " + theMonth(month));	
	        return theMonth(month);
	}
	 
	/* public enum State  {
		
	 Alfa("Alfa"),
	 Eaglerise("Eaglerise"),
	 TBEA("TBEA");

	    private final String displayName;

	    State(String displayName) {
	        this.displayName = displayName;
	    }

	    public String getDisplayName() {
	        return displayName;
	    }
	 }*/
}
