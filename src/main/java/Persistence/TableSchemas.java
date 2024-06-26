package Persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Statement;

public class TableSchemas {
    private static final Logger logger = LogManager.getLogger("regular");

    public static boolean dropTemporaryLinesTable(String test, String tableName){
        String createTableQuery = "DROP TABLE " + tableName;

        try (Connection conn = PersistenceConnectivity.get(test)){
            Statement st = conn.createStatement();
            st.executeUpdate(createTableQuery);
            st.close();
        } catch (Exception e){
            logger.error("While dropping temporary lines table.\n\t" + e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean createTemporaryLinesTable(String test, String tableName){
        String createTableQuery = "CREATE TABLE " + tableName + " (line INT PRIMARY KEY)";

        try (Connection conn = PersistenceConnectivity.get(test)){
            Statement st = conn.createStatement();
            st.executeUpdate(createTableQuery);
            st.close();
        } catch (Exception e){
            logger.error("While creating temporary lines table.\n\t" + e.getMessage());
            return  false;
        }
        return  true;
    }

    public static void insertDefaultDepartments(String test) {
        String insertionQuery ="""
            INSERT INTO departments (name, description) VALUES
            ('HHRR', 'Human resources'),
            ('Janitor', 'Janitors department'),
            ('Reception', 'Reception department'),
            ('Security', 'Security department'),
            ('R&D', 'Research and development'),
            ('Management', 'Management development'),
            ('PR', 'Public relations')
        """;

        try (Connection conn = PersistenceConnectivity.get(test)){
            Statement st = conn.createStatement();
            st.executeUpdate(insertionQuery);
            st.close();
        } catch (Exception e){
            logger.error("While inserting default department.\n\t" + e.getMessage());
        }
    }

    public static void dropFailedLinesTable(String test) {
        String creationQuery =
                "DROP TABLE failed_lines CASCADE";

        try (Connection conn = PersistenceConnectivity.get(test)){
            Statement st = conn.createStatement();
            st.executeUpdate(creationQuery);
            st.close();
        } catch (Exception e){
            logger.error("While deleting the uploads schema.\n\t" + e.getMessage());
        }
    }

    public static void createFailedLinesTable(String test) {
        String creationQuery = """
            CREATE TABLE IF NOT EXISTS
            failed_lines (
                process_id INT NOT NULL,
                line INT NOT NULL,
                time TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP
            )
        """;

        try (Connection conn = PersistenceConnectivity.get(test)){
            Statement st = conn.createStatement();
            st.executeUpdate(creationQuery);
            st.close();
        } catch (Exception e){
            logger.error("While creating failed lines schema.\n\t" + e.getMessage());
        }
    }

    public static void dropUploadsTable(String test) {
        String creationQuery =
                "DROP TABLE uploads CASCADE";

        try (Connection conn = PersistenceConnectivity.get(test)){
            Statement st = conn.createStatement();
            st.executeUpdate(creationQuery);
            st.close();
        } catch (Exception e){
            logger.error("While deleting the uploads schema.\n\t" + e.getMessage());
        }
    }

    public static void createUploadsTable(String test) {
        String creationQuery = """
            CREATE TABLE IF NOT EXISTS
            uploads (
                id SERIAL PRIMARY KEY,
                file VARCHAR(64) NOT NULL,
                total INT DEFAULT 0,
                modified TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP
            )
        """;

        try (Connection conn = PersistenceConnectivity.get(test)){
            Statement st = conn.createStatement();
            st.executeUpdate(creationQuery);
            st.close();
        } catch (Exception e){
            logger.error("While creating uploads schema.\n\t" + e.getMessage());
        }
    }

    public static void createEmployeesView(String test) {
        String creationQuery = """
            CREATE VIEW employee_full_name AS
            SELECT CONCAT (e.name, ' ', last_name) AS full_name, e.id, e.name, e.last_name, e.birth_date AS bd,
            d.id AS dep_id, d.name AS department
            FROM employees e LEFT JOIN departments d ON department_id = d.id
        """;

        try (Connection conn = PersistenceConnectivity.get(test)){
            Statement st = conn.createStatement();
            st.executeUpdate(creationQuery);
            st.close();
        } catch (Exception e){
            logger.error("While creating the department schema.\n\t" + e.getMessage());
        }
    }

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

    // " department_id smallint references departments (id)"
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
                + " department_id smallint"
                + ")";

        try (Connection conn = PersistenceConnectivity.get(test)){
            Statement st = conn.createStatement();
            st.executeUpdate(creationQuery);
            st.close();
        } catch (Exception e){
            logger.error("While creating the employee schema.\n\t" + e.getMessage());
        }
    }

    public static void dropEmployeesView(String test) {
        String creationQuery =
                "DROP VIEW employees_full_name";
        try (Connection conn = PersistenceConnectivity.get(test)){
            Statement st = conn.createStatement();
            st.executeUpdate(creationQuery);
            st.close();
        } catch (Exception e){
            logger.error("While deleting the employee schema.\n\t" + e.getMessage());
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
