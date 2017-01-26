How to Run this Project:
1. Set database username and password in database.properties.

2. Go to database.properties and replace the word 'update' to 'create' only for first RUN. 
   hibernate.hbm2ddl.auto=create
   
5. now run the project from server.
   
6. After first run you must Go to database.properties and replace the word previous line to from 'create' to 'update'
   hibernate.hbm2ddl.auto=update

7. now re-run the project from server.

8. Execute the startup.sql file to create a user named admin/admin for login which will allow the permission of Employee object.

9. after launch application you can login using admin/admin 

[N.B: if you create new database with username:'desco' and password : 'desco', you need not to follow steps 1 and 2 ]

......Thank you.....