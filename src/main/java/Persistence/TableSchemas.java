package Persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Statement;

public class TableSchemas {
    private static final Logger logger = LogManager.getLogger("regular");

    public static void createDepartmentsTable(String test) {
        String creationQuery =
                "CREATE TABLE"
                        + " IF NOT EXISTS"
                        + " departments"
                        + "("
                        + " id SERIAL PRIMARY KEY,"
                        + " name VARCHAR(64) UNIQUE NOT NULL,"
                        + " description VARCHAR(128)"
                        + ")";

        try (Connection conn = PersistenceConnectivity.get(test)){
            Statement st = conn.createStatement();
            st.executeUpdate(creationQuery);
            st.close();
        } catch (Exception e){
            logger.error("While creating the department schema.\n\t" + e.getMessage());
        }
    }

    public static void createEmployeesTable(String test) {
        String creationQuery =
                "CREATE TABLE"
                        + " IF NOT EXISTS"
                        + " employees"
                        + "("
                        + " id SERIAL PRIMARY KEY,"
                        + " name VARCHAR(64) NOT NULL,"
                        + " last_name VARCHAR(64) NOT NULL,"
                        + " birth_date date NOT NULL,"
                        + " department_id smallint references departments (id)"
                        + ")";

        try (Connection conn = PersistenceConnectivity.get(test)){
            Statement st = conn.createStatement();
            st.executeUpdate(creationQuery);
            st.close();
        } catch (Exception e){
            logger.error("While creating the employee schema.\n\t" + e.getMessage());
        }
    }


    public static void dropEmployeesTable(String test) {
        String creationQuery =
                "DROP TABLE employees CASCADE";

        try (Connection conn = PersistenceConnectivity.get(test)){
            Statement st = conn.createStatement();
            st.executeUpdate(creationQuery);
            st.close();
        } catch (Exception e){
            logger.error("While deleting the employee schema.\n\t" + e.getMessage());
        }
    }

    public static void dropDepartmentsTable(String test) {
        String creationQuery =
            "DROP TABLE departments CASCADE";

        try (Connection conn = PersistenceConnectivity.get(test)){
            Statement st = conn.createStatement();
            st.executeUpdate(creationQuery);
            st.close();
        } catch (Exception e){
            logger.error("While deleting the department schema.\n\t" + e.getMessage());
        }
    }
}
