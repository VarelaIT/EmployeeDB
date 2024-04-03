package Persistence.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;


public class DBConn extends Credentials{
    private Properties props = new Properties();
    public Connection conn;

    public DBConn() {

        this.props.setProperty("user", this.user);
        this.props.setProperty("password", this.password);

        try {
            this.conn = DriverManager.getConnection(this.url, this.props);
        } catch (Exception exception){
            System.out.println("Database Connection Failed! " + exception.getMessage());
            System.exit(-1);
        }

    }

    public Connection getConn () {
        return  this.conn;
    }

}
