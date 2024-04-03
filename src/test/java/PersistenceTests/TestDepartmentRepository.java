package PersistenceTests;

import Entities.IDepartment;
import Entities.IPersistedDepartment;
import Entities.PersistedDepartment;
import Persistence.IDepartmentRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestDepartmentRepository implements ITestDepartmentRepository {


    public String insertionQuery =
            "INSERT INTO test_departments ( name, description) VALUES ( ?, ?) RETURNING id, name, description";

    @Override
    public void dropTable(Connection conn) {
        String statement = "DELETE FROM test_departments";
        try{
            Statement query = conn.createStatement();
            query.executeQuery(statement);
            query.close();
        }catch (Exception e){
            System.out.println("Storage Error:\n\t" + e.getMessage());
        }
    }

    @Override
    public IPersistedDepartment save(Connection conn, IDepartment newDepartment) {
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
        }
        return null;
    }

    @Override
    public IPersistedDepartment get(Connection conn, int id) {
        String query = "SELECT id, name, description FROM test_departments WHERE id = ?";
        try {
            PreparedStatement stm = conn.prepareStatement(query);
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
        }
        return null;
    }

}
