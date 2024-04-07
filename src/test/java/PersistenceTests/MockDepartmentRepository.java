package PersistenceTests;

import Entities.IDepartment;
import Entities.IPersistedDepartment;
import Entities.PersistedDepartment;
import Persistence.JDBC.DBConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MockDepartmentRepository implements ITestDepartmentRepository {


    public Connection conn = new DBConn().getConn();
    public String seletAllQuery = "SELECT id, name, description FROM test_departments";
    public String seletOneQuery = "SELECT id, name, description FROM test_departments WHERE id = ?";
    public String insertionQuery =
            "INSERT INTO test_departments ( name, description) VALUES ( ?, ?) RETURNING id, name, description";

    @Override
    public void dropTable() {
        String statement = "DROP TABLE test_departments";
        try{
            Statement query = conn.createStatement();
            query.executeQuery(statement);
            query.close();
        }catch (Exception e){
            System.out.println("Storage Error:\n\t" + e.getMessage());
            closeConn();
        }
    }

    @Override
    public IPersistedDepartment save(IDepartment newDepartment) {
        try {
            PreparedStatement st = conn.prepareStatement(insertionQuery);
            st.setString(1, newDepartment.getName());
            st.setString(2, newDepartment.getDescription());

            ResultSet result = st.executeQuery();

            result.next();
            int id = result.getInt("id");
            String name = result.getString("name");
            String description = result.getString("description");

            result.close();
            st.close();

            return new PersistedDepartment(id, name, description);
        } catch (Exception e){
            System.out.println("The Department Persistence log.\n\t" + e.getMessage());
            closeConn();
        }
        return null;
    }

    @Override
    public IPersistedDepartment get(int id) {
        try {
            PreparedStatement stm = conn.prepareStatement(seletOneQuery);
            stm.setInt(1, id);
            ResultSet result = stm.executeQuery();
            result.next();
            return new PersistedDepartment(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getString("description")
            );
        } catch (Exception e) {
            System.out.println("Error while withdrawing the department:\n\t" + e.getMessage());
            closeConn();
        }
        return null;
    }
    @Override
    public List<IPersistedDepartment> getAll() {
        List<IPersistedDepartment> response = new ArrayList<IPersistedDepartment>();
        try {
            Statement stm = conn.createStatement();
            ResultSet result = stm.executeQuery(seletAllQuery);
            while(result.next()) {
                response.add(new PersistedDepartment(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("description")
                ));
            }

            return response;
        } catch (Exception e) {
            System.out.println("Error while withdrawing list of departments:\n\t" + e.getMessage());
            closeConn();
        }
        return null;
    }

    public void closeConn() {
        try {
            conn.close();
        } catch (Exception e){
            System.out.println("Error while closing the persistence:\n\t" + e.getMessage());
        }
    }

}
