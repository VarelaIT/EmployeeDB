package Persistence;

import Entities.IEmployee;
import Entities.PersistedEmployee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository extends PersistenceConnectivity implements IEmployeeRepository{

    protected String insertionQuery = """
        INSERT INTO employees (name, last_name, birth_date, department_id) VALUES (?, ?, ?, ?)
        RETURNING id, name, last_name, birth_date, department_id
    """;
    protected String selectAllQuery = """
        SELECT e.id as id, e.name as name, e.last_name as last_name, e.birth_date as bd, d.id as dep_id, d.name as department
        FROM employees e
        LEFT JOIN departments d ON e.department_id = d.id
    """;
    protected String selectOneQuery = """
        SELECT e.id as id, e.name as name, e.last_name as last_name, e.birth_date as bd, d.id as dep_id, d.name as department
        FROM employees e
        LEFT JOIN departments d ON e.department_id = d.id
        WHERE e.id = ?
    """;

    EmployeeRepository(){
        super();
    }

    public EmployeeRepository(String test){
        super(test);
    }

    @Override
    public IPersistedEmployee save(IEmployee employee) {
        try {
            PreparedStatement st = conn.prepareStatement(insertionQuery);
            st.setString(1, employee.getName());
            st.setString(2, employee.getLastName());
            st.setDate(3, employee.getBirthDate());
            if (employee.getDepartmentId() == null)
                st.setNull(4, Types.INTEGER);
            else
                st.setInt(4, employee.getDepartmentId());

            ResultSet result = st.executeQuery();

            result.next();
            int id = result.getInt("id");
            String name = result.getString("name");
            String lastName = result.getString("last_name");
            Date birthDate = result.getDate("birth_date");
            int evaluate = result.getInt("department_id");
            Integer departmentId = evaluate == 0 ? null : evaluate;

            result.close();
            st.close();

            return new PersistedEmployee(id, name, lastName, birthDate, departmentId, null);
        } catch (Exception e){
            System.out.println("Create, Employee Persistence log.\n\t" + e.getMessage());
        }

        return null;

    }

    @Override
    public IPersistedEmployee get(int id) {
        IPersistedEmployee persistedEmployee = null;
        try {
            PreparedStatement stm = conn.prepareStatement(selectOneQuery);
            stm.setInt(1, id);
            ResultSet result = stm.executeQuery();

            if (result.next()) {
                persistedEmployee = new PersistedEmployee(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("last_name"),
                        result.getDate("bd"),
                        result.getInt("dep_id"),
                        result.getString("department")
                );
            }

            result.close();
            stm.close();
            return persistedEmployee;
        } catch (Exception e) {
            System.out.println("Error while withdrawing the employee:\n\t" + e.getMessage());
        }


        return null;
    }

    @Override
    public List<IPersistedEmployee> get() {
        List<IPersistedEmployee> response = new ArrayList<IPersistedEmployee>();

        try {
            Statement stm = conn.createStatement();
            ResultSet result = stm.executeQuery(selectAllQuery);
            while(result.next()) {
                response.add(new PersistedEmployee(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("last_name"),
                        result.getDate("bd"),
                        result.getInt("dep_id"),
                        result.getString("department")
                ));
            }

            result.close();
            stm.close();
            return response;
        } catch (Exception e) {
            System.out.println("Error while withdrawing list of employees:\n\t" + e.getMessage());
        }
        return null;
    }

    private String updateQuery = """
        UPDATE employees
        SET name = ?, last_name = ?, birth_date = ?, department_id = ?
        WHERE id = ?
    """;

    public int update(int id, IEmployee employee){
        int affectedRows = 0;

        try {
            PreparedStatement st = conn.prepareStatement(updateQuery);
            st.setString(1, employee.getName());
            st.setString(2, employee.getLastName());
            st.setDate(3, employee.getBirthDate());
            if (employee.getDepartmentId() == null)
                st.setNull(4, Types.INTEGER);
            else
                st.setInt(4, employee.getDepartmentId());
            st.setInt(5, id);

            affectedRows = st.executeUpdate();

            st.close();
            return affectedRows;
        } catch (Exception e){
            System.out.println("Update, Employee Persistence log.\n\t" + e.getMessage());
        }

        return affectedRows;
    }

    private String deleteOneQuery = """
        DELETE FROM employees WHERE id = ?
    """;
    @Override
    public IPersistedEmployee delete(int id) {
        IPersistedEmployee targetEmployee = get(id);

        try {
            PreparedStatement st = conn.prepareStatement(deleteOneQuery);
            st.setInt(1, targetEmployee.getId());
            int affectedRows = st.executeUpdate();

            if (affectedRows != 1)
                return null;

            st.close();
        } catch (Exception e) {
            System.out.println("Delete, Employee Persistence log.\n\t" + e.getMessage());
        }

        return targetEmployee;
    }
}
