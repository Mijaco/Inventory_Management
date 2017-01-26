package com.ibcs.desco.util;

/**
 * Created with IntelliJ IDEA.
 * User: hossaindoula
 * For feature
 * Date: 7/11/12
 * Time: 4:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeatureCollection {
    public static final String MODULE_NAME="BioMetricAttendance";
    public static final String ADMINISTRATIVE_OPERATION="Admin";
    public static final String ATTENDANCE_LIST_VIEWER="ViewAttendance";
    public static final String ATTENDANCE_ENTRY="AttendanceEntry";
    public static final String ATTENDANCE_EXIT="AttendanceExit";
    public static final String ATTENDANCE_LIST_MODIFIER="ModifyAttendance";
    public static final String ATTENDANCE_ADMIN="AdministerAttendance";// create,modify and delete attendance and make admin entry
    public static final String EMPLOYEE_REGISTRAR="RegisterEmployee";// registering new employee, view attendance but can not make admin entry
    public static final String EMPLOYEE_REGISTRAR_EDIT="EditEmployee";// registering new employee, view attendance but can not make admin entry

    public final static String []administrativeOperation={"Admin"};
    public final static String [] attendanceEntryPermissions = new String[]{FeatureCollection.ATTENDANCE_ENTRY,
            FeatureCollection.ADMINISTRATIVE_OPERATION,
            FeatureCollection.ATTENDANCE_ADMIN,
            FeatureCollection.ATTENDANCE_LIST_MODIFIER,
            FeatureCollection.EMPLOYEE_REGISTRAR,
            FeatureCollection.EMPLOYEE_REGISTRAR_EDIT
    };

    public final static String [] fingerprintRegistrationMenuPermissions = new String[]{
            FeatureCollection.EMPLOYEE_REGISTRAR,
            FeatureCollection.EMPLOYEE_REGISTRAR_EDIT,
            FeatureCollection.ADMINISTRATIVE_OPERATION,
            FeatureCollection.ATTENDANCE_ADMIN,
            FeatureCollection.ATTENDANCE_LIST_MODIFIER,
            FeatureCollection.EMPLOYEE_REGISTRAR,
            FeatureCollection.EMPLOYEE_REGISTRAR_EDIT
    };

    public final static String [] fingerprintRegistrationPermissions = new String[]{
            FeatureCollection.EMPLOYEE_REGISTRAR,
            FeatureCollection.ADMINISTRATIVE_OPERATION,
            FeatureCollection.ATTENDANCE_ADMIN,
            FeatureCollection.ATTENDANCE_LIST_MODIFIER,
            FeatureCollection.EMPLOYEE_REGISTRAR,
            FeatureCollection.EMPLOYEE_REGISTRAR_EDIT
    };

    public final static String [] fingerprintRegistrationEditPermissions = new String[]{
            FeatureCollection.EMPLOYEE_REGISTRAR_EDIT,
            FeatureCollection.ADMINISTRATIVE_OPERATION,
            FeatureCollection.ATTENDANCE_ADMIN,
            FeatureCollection.ATTENDANCE_LIST_MODIFIER,
            FeatureCollection.EMPLOYEE_REGISTRAR,
            FeatureCollection.EMPLOYEE_REGISTRAR_EDIT
    };

    public final static String [] attendanceExitPermissions = new String[]{FeatureCollection.ATTENDANCE_EXIT,
            FeatureCollection.ADMINISTRATIVE_OPERATION,
            FeatureCollection.ATTENDANCE_ADMIN,
            FeatureCollection.ATTENDANCE_LIST_MODIFIER,
            FeatureCollection.EMPLOYEE_REGISTRAR,
            FeatureCollection.EMPLOYEE_REGISTRAR_EDIT
    };

    public final static String [] attendanceListPermissions = new String[]{FeatureCollection.ATTENDANCE_LIST_VIEWER,
            FeatureCollection.ADMINISTRATIVE_OPERATION,
            FeatureCollection.ATTENDANCE_ADMIN,
            FeatureCollection.ATTENDANCE_LIST_MODIFIER,
            FeatureCollection.EMPLOYEE_REGISTRAR,
            FeatureCollection.EMPLOYEE_REGISTRAR_EDIT
    };

    public final static String [] attendanceListModifierPermissions = new String[]{
            FeatureCollection.ADMINISTRATIVE_OPERATION,
            FeatureCollection.ATTENDANCE_ADMIN,
            FeatureCollection.ATTENDANCE_LIST_MODIFIER,
            FeatureCollection.EMPLOYEE_REGISTRAR,
            FeatureCollection.EMPLOYEE_REGISTRAR_EDIT
    };

    public final static String [] attendanceMenuPermissions = new String[]{
            FeatureCollection.ATTENDANCE_LIST_VIEWER,
            FeatureCollection.ADMINISTRATIVE_OPERATION,
            FeatureCollection.ATTENDANCE_ADMIN,
            FeatureCollection.ATTENDANCE_LIST_MODIFIER,
    };

    public final static String [] attendanceAdminPermissions = new String[]{
            FeatureCollection.ADMINISTRATIVE_OPERATION,
            FeatureCollection.ATTENDANCE_ADMIN
    };

}
