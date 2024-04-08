package Persistence;

import Persistence.JDBC.DBConn;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public abstract class PersistenceConnectivity {
    public Connection conn;
    PersistenceConnectivity(){
        try{
            InitialContext cxt = new InitialContext();
            DataSource ds = (DataSource) cxt.lookup( "java:/comp/env/jdbc/postgres" );

            if ( ds == null )
                throw new Exception("Data source not found!");

            conn = ds.getConnection();
        } catch (Exception e) {
            //System.out.println("Connection pool error:\n\t" + e.getMessage());
            this.nonWebEnvironment();
        }
    }

    protected void nonWebEnvironment(){
         conn = new DBConn().getConn();
    }
}
