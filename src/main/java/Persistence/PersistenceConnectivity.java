package Persistence;

import Persistence.JDBC.DBConn;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public class PersistenceConnectivity {

    public static Connection get(String test){
        if (test != null)
            return new DBConn().getConn();

        try{
            InitialContext cxt = new InitialContext();
            DataSource ds = (DataSource) cxt.lookup( "java:/comp/env/jdbc/postgres" );

            if ( ds == null )
                throw new Exception("Data source not found!");

            return ds.getConnection();
        } catch (Exception e) {
            System.out.println("Connection pool error:\n\t" + e.getMessage());
        }
        return null;
    }

}
