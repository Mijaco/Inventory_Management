package com.ibcs.desco.acl.model;

/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 */
import java.util.ArrayList;
import java.util.List;
/**
*
* @author Ahasanul Ashid, IBCS
* @author Abu Taleb, IBCS
* 
*/
public class ACL {

	public List<String> returnMenu() {

		List<String> na = new ArrayList<String>();
		na.add(0, "CDR MANAGEMENT");
		na.add(1, "Process");
		na.add(2, "Rating Configuration");
		na.add(3, "Month End Process");
		na.add(4, "Suspense Manager");
		na.add(5, "Reports");
		na.add(6, "ADMINISTRATION");
		na.add(7, "Re-Rating");

		return na;

	}

}
