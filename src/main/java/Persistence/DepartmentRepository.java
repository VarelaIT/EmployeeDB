package Persistence;

import Entities.IDepartment;
import Entities.IPersistedDepartment;
import Entities.PersistedDepartment;
import Persistence.JDBC.DBConn;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepository extends PersistenceConnectivity  implements IDepartmentRepository{
    public String seletAllQuery = "SELECT id, name, description FROM departments";
    public String seletOneQuery = "SELECT id, name, description FROM departments WHERE id = ?";
    public String deleteOneQuery = "DELETE FROM departments WHERE id = ?";
    public String insertionQuery = """
       INSERT INTO departments (name, description)
       VALUES ( ?, ?)
       RETURNING id, name, description
    """;
    public String updateQuery = """
       UPDATE departments SET name = ?, description = ?
       Where id = ?
       RETURNING id, name, description
    """;

    public DepartmentRepository(){
        super();
    }

    public DepartmentRepository(String test){
        super(test);
    }

    public  IPersistedDepartment save(IDepartment newDepartment) {
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

    public  IPersistedDepartment update(int id, IDepartment department) {
        try {
            PreparedStatement st = conn.prepareStatement(updateQuery);
            st.setString(1, department.getName());
            st.setString(2, department.getDescription());
            st.setInt(3, id);

            ResultSet result = st.executeQuery();
            IPersistedDepartment persistedDepartment;

            result.next();
            persistedDepartment = new PersistedDepartment(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getString("description")
            );

            result.close();
            st.close();

            return persistedDepartment;
        } catch (Exception e){
            System.out.println("Persistence log error while updating the department.\n\t" + e.getMessage());
        }

        return null;
    }

    public IPersistedDepartment get(int id) {
        IPersistedDepartment persistedDepartment = null;
        try {
            PreparedStatement stm = conn.prepareStatement(seletOneQuery);
            stm.setInt(1, id);
            ResultSet result = stm.executeQuery();

            if (result.next()) {
                persistedDepartment = new PersistedDepartment(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("description")
                );
            }

            result.close();
            stm.close();
            return persistedDepartment;
        } catch (Exception e) {
            System.out.println("Error while withdrawing the department:\n\t" + e.getMessage());
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

            result.close();
            stm.close();
            return response;
        } catch (Exception e) {
            System.out.println("Error while withdrawing list of departments:\n\t" + e.getMessage());
        }

        return null;
    }

    @Override
    public IPersistedDepartment delete(int id){
        IPersistedDepartment department = get(id);

        try {
            PreparedStatement st = conn.prepareStatement(deleteOneQuery);
            st.setInt(1, department.getId());
            int affectedRows = st.executeUpdate();
            st.close();

            if (affectedRows != 1)
                return null;
        } catch (Exception e) {
            System.out.println("Persistence logs error while deleting the department.\n\t" + e.getMessage());
        }

        return department;
    }

}
