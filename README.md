# TeamProject2017_05
The project uses a remote server. So simply:

Perform a git clone

Importing the project into Eclipse

File -> Import -> Maven -> Existing Maven Projects
Click Browse
Find the directory where the git clone occured the folder is probably called TeamProject2017_05
Click finish
1) <b>Go to the package called home.login > MainApplicationStart.java, to start the application.</b>
<h1>Login Details</h1>

<table border=1px>
  <tr>
    <th>Role</th>
    <th>Username</th>
    <th>Password</th>
  </tr>
  <tr>
    <td>Manager</td>
    <td>dmatthews</td>
    <td>asd123</td>
  </tr>
  <tr>
    <td>Kitchen Staff</td>
    <td>pChef</td>
    <td>asd123</td>
  </tr>
  <tr>
    <td>Waiter</td>
    <td>pWaiter</td>
    <td>asd123</td>
  </tr>
  <tr>
    <td>Customer</td>
    <td>pCustomer</td>
    <td>asd123</td>
  </tr>
</table>

2) To access the database make sure you have postgresql and postgresql-client installed in the terminal put 
```psql postgres://wrjdzkknwbwrgy:c088b08d8dd44a728068086ae26a1007a1c24c5db4fb6f583cd092aec7a8093c@ec2-54-75-235-2.eu-west-1.compute.amazonaws.com:5432/d5krt20tjkhn6b``` 
and then press enter.
--------------------------------------------------------------------------------------------------------------------------------

If there is issues with the remote server then you may need to perform do the following steps instead since this will setup the local server using tomcat.

Note: You may do this using Eclipse IDE for Java Developers - eventually this has always led to too many issues and errors and so its not recommended at all.

Download Java EE
- Download the Eclipse Installer
https://eclipse.org/downloads/
- Open up the Installer
- Click 'Eclipse IDE for Java EE Developers' and install
- Once ready Launch

Downloading TomCat
- Go to http://tomcat.apache.org/download-90.cgi
- In core under Binary Distributions select "64-bit Windows zip" (or 32) for Windows
- Extract this to a directory
- Download the postgres driver http://personal.rhul.ac.uk/zbvd/644/postgresql-jdbc.jar and place it in the lib/ directory which is inside the TomCat directory (which was just extracted).

Perform a git clone

Importing the project into Eclipse EE
- File -> Import -> Maven -> Existing Maven Projects
- Click Browse
- Find the directory where the git clone occured the folder is probably called TeamProject2017_05
- Click finish 

Setting up the server
- Right click on the whole project -> Run as  -> Run on server -> Apache -> Tomcat v9.0 Server and tick always use this server when running this project
- Click Browse to find the Tomcat directory (which was extracted and contains the postgres driver jar)
- Click next
- You should see on the right side the name of the project and click finish

Running the server
- Again, right click on the project -> Run as -> Run on server, this time the console should show some initialisation text showing that the server is starting up, once a webpage is shown then the server has been loaded.

Running the applications
- All use JavaFX so simple all other applications/java files should be run as usual by right clicking then selecting Run as  -> Java Application
