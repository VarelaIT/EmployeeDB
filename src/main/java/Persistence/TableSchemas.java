package Persistence;

import java.sql.Connection;
import java.sql.Statement;

public class TableSchemas {
    public static void createDepartmentTable(Connection conn) {
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

    public static void createEmployeeTable(Connection conn) {
        String creationQuery =
                "CREATE TABLE"
                        + " IF NOT EXISTS"
                        + " employees"
                        + "("
                        + " id SERIAL PRIMARY KEY,"
                        + " name VARCHAR(64) NOT NULL,"
                        + " last.name VARCHAR(64) NOT NULL,"
                        + " birth.date date NOT NULL,"
                        + " department.id smallint references departments (id)"
                        + ")";

        try {
            Statement st = conn.createStatement();
            st.executeUpdate(creationQuery);
            st.close();
        } catch (Exception e){
            System.out.println("The Department Persistence log.\n\t" + e.getMessage());
        }
    }

    public static void createTestEmployeeTable(Connection conn) {
        String creationQuery =
                "CREATE TABLE"
                        + " IF NOT EXISTS"
                        + " test_employees"
                        + "("
                        + " id SERIAL PRIMARY KEY,"
                        + " name VARCHAR(64) NOT NULL,"
                        + " last.name VARCHAR(64) NOT NULL,"
                        + " birth.date date NOT NULL,"
                        + " department.id smallint references test_departments (id)"
                        + ")";

        try {
            Statement st = conn.createStatement();
            st.executeUpdate(creationQuery);
            st.close();
        } catch (Exception e){
            System.out.println("The Department Persistence log.\n\t" + e.getMessage());
        }
    }

    public static void dropTestEmployeesTable(Connection conn) {
        String creationQuery =
                "DROP TABLE test_departments";

        try {
            Statement st = conn.createStatement();
            st.executeUpdate(creationQuery);
            st.close();
        } catch (Exception e){
            System.out.println("The Department Persistence log.\n\t" + e.getMessage());
        }
    }

    public static void createTestDepartmentsTable(Connection conn) {
        String creationQuery =
                "CREATE TABLE"
                        + " IF NOT EXISTS"
                        + " test_departments"
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

    public static void dropTestDepartmentsTable(Connection conn) {
        String creationQuery =
            "DROP TABLE test_departments";

        try {
            Statement st = conn.createStatement();
            st.executeUpdate(creationQuery);
            st.close();
        } catch (Exception e){
            System.out.println("The Department Persistence log.\n\t" + e.getMessage());
        }
    }
}
