package Persistence;

import Persistence.JDBC.DBConn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public class PersistenceConnectivity {

    private static final Logger logger = LogManager.getLogger("regular");

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
            logger.error("Connection pool error:\n\t" + e.getMessage());
        }
        return null;
    }

}
