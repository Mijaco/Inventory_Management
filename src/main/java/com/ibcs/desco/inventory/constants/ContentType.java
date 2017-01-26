
package com.ibcs.desco.inventory.constants;

/**
 * @author nasrin.akter
 *
 */
public enum ContentType {
	
	
	CENTRAL_STORE("cs"),
	SUB_STORE("ss"),
	LOCAL_STORE("ls"),
	WORKSHOP("ws"),
	WS_CN_XFORMER_REPAIR_MATERIALS("wsm"),
	WS_CN_XFORMER("wsx"),
	WS_LS_PREVENTIVE_MAINTENANCE("ls"),
	PREVENTIVE_MAINTENANCE("PREVENTIVE MAINTENANCE"),
	CONTRACTOR("cn"),
	APPROVE("APPROVE"),
	VERIFY("VERIFY"),
	INITIATE("INITIATE"),
	LS_CONTRACTOR("c2ls"),
	CREATE("CREATE"),
	SINGLE_PHASE("1-PHASE"),
	THREE_PHASE("3-PHASE");
	

	private final String contentTypeName;
	

	private ContentType(String contentTypeName) {
		this.contentTypeName = contentTypeName;
	}


	@Override
	public String toString() {
		return contentTypeName;
	}


}
