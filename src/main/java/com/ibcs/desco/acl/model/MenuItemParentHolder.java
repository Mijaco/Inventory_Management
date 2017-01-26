package com.ibcs.desco.acl.model;

/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 * 
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
*
* @author Ahasanul Ashid, IBCS
* @author Abu Taleb, IBCS
* 
*/
public class MenuItemParentHolder {

	private Map<String, List<Menu>> MenuMap;
	private HashMap<String, ClassUtility> lclass;
	private HashMap<String, String> rootMenus;

	public HashMap<String, String> getRootMenus() {
		return rootMenus;
	}

	public void setRootMenus(HashMap<String, String> rootMenus) {
		this.rootMenus = rootMenus;
	}

	public HashMap<String, ClassUtility> getLclass() {
		return lclass;
	}

	public void setLclass(HashMap<String, ClassUtility> lclass) {
		this.lclass = lclass;
	}

	public Map<String, List<Menu>> getMenuMap() {
		return MenuMap;
	}

	public void setMenuMap(Map<String, List<Menu>> MenuMap) {
		this.MenuMap = MenuMap;
	}

}
