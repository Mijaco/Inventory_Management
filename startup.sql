Insert into ROLES
   (ID, ROLE, ROLE_ID)
 Values
   (1, 'ROLE_ADMIN', 1);
COMMIT;

Insert into AUTH_USER
   (ID, EMAIL, LOCKED, NAME, PASSWORD, 
    ROLEID, USERID)
 Values
   (1, 'admin@gmail.com', 0, 'admin', 'admin', 
    1, 'admin');
COMMIT;

Insert into OBJECT_REFERENCE
   (ID, CLASS_NAME, CLASS_REFERENCE, CLASSIFIED, DISPLAY_NAME)
 Values
   (1, 'Employee', 'com.ibcs.acl.model.Employee', 0, 'Employee');
COMMIT;

Insert into PERMISSION_TABLE
   (P_ID, CLASSIFIED, OBJECT_ID, P_DELETE, P_EDIT, 
    P_READ, P_WRITE, ROLE_ID)
 Values
   (1, 0, 1, 1, 1, 
    1, 1, 1);
COMMIT;