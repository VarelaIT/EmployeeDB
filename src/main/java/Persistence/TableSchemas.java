package Persistence;

import java.sql.Connection;
import java.sql.Statement;

public class TableSchemas {
    public static void createDepartmentsTable(Connection conn) {
        String creationQuery =
                "CREATE TABLE"
                        + " IF NOT EXISTS"
                        + " departments"
                        + "("
                        + " id SERIAL PRIMARY KEY,"
                        + " name VARCHAR(64) UNIQUE NOT NULL,"
                        + " description VARCHAR(128)"
                        + ")";

        try {
            Statement st = conn.createStatement();
            st.executeUpdate(creationQuery);
            st.close();
        } catch (Exception e){
            System.out.println("The Department Persistence log.\n\t" + e.getMessage());
        }
    }

    public static void createEmployeesTable(Connection conn) {
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

        try {
            Statement st = conn.createStatement();
            st.executeUpdate(creationQuery);
            st.close();
        } catch (Exception e){
            System.out.println("The Employee Persistence log.\n\t" + e.getMessage());
        }
    }


    public static void dropEmployeesTable(Connection conn) {
        String creationQuery =
                "DROP TABLE employees CASCADE";

        try {
            Statement st = conn.createStatement();
            st.executeUpdate(creationQuery);
            st.close();
        } catch (Exception e){
            System.out.println("The Employee Persistence log.\n\t" + e.getMessage());
        }
    }

    public static void dropDepartmentsTable(Connection conn) {
        String creationQuery =
            "DROP TABLE departments CASCADE";

        try {
            Statement st = conn.createStatement();
            st.executeUpdate(creationQuery);
            st.close();
        } catch (Exception e){
            System.out.println("The Department Persistence log.\n\t" + e.getMessage());
        }
    }
}
