package com.ibcs.desco.acl.model;

/**
 *
 * @author Ahasanul Ashid, IBCS
 * @author Abu Taleb, IBCS
 * 
 */
public class Menu {

    private int id;
    private String menu_name;
    private String class_reference;
    private String menu_link;
    private Object class_objects;
    private String parent_menu;
    private String root;
    private String is_admin;




    private Object parent_class_objects;


     public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public Object getParent_class_objects() {

    

        return parent_class_objects;
    }

    public String getParent_menu() {
        return parent_menu;
    }

    public void setParent_menu(String parent_menu) {
        this.parent_menu = parent_menu;
    }

    @SuppressWarnings("rawtypes")
	public Object getClass_objects() throws ClassNotFoundException, InstantiationException, IllegalAccessException {


		Class cls=Class.forName(class_reference);


        return cls.newInstance();
    }

    public String getClass_reference() {
        return class_reference;
    }

    public void setClass_reference(String class_reference) {
        this.class_reference = class_reference;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMenu_link() {
        return menu_link;
    }

    public void setMenu_link(String menu_link) {
        this.menu_link = menu_link;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public Object return_class() throws ClassNotFoundException, InstantiationException, IllegalAccessException {



        return class_objects;
    }


 public String getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(String is_admin) {
        this.is_admin = is_admin;
    }


}
