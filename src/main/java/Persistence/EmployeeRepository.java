package Persistence;

import Entities.IEmployee;
import Entities.PersistedEmployee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class EmployeeRepository implements IEmployeeRepository{

    protected String findNameQuery = """
        SELECT * FROM employee_full_name WHERE full_name ILIKE ? LIMIT 100
    """;
    protected String insertionQuery = """
        INSERT INTO employees (name, last_name, birth_date, department_id) VALUES (?, ?, ?, ?)
        RETURNING id, name, last_name, birth_date, department_id
    """;
    protected String selectAllQuery = """
        SELECT e.id as id, e.name as name, e.last_name as last_name, e.birth_date as bd, d.id as dep_id, d.name as department
        FROM employees e
        LEFT JOIN departments d ON e.department_id = d.id
        ORDER BY id
        LIMIT ? OFFSET ?
    """;
    protected String selectOneQuery = """
        SELECT e.id as id, e.name as name, e.last_name as last_name, e.birth_date as bd, d.id as dep_id, d.name as department
        FROM employees e
        LEFT JOIN departments d ON e.department_id = d.id
        WHERE e.id = ?
    """;
    private String test = null;

    private static final Logger logger = LogManager.getLogger("regular");

    public EmployeeRepository(){
    }

    public EmployeeRepository(String test){
        this.test = test;
    }

    @Override
    public IPersistedEmployee save(IEmployee employee) {
        IPersistedEmployee response = null;

        try (Connection conn = PersistenceConnectivity.get(test)){
            PreparedStatement st = conn.prepareStatement(insertionQuery);
            st.setString(1, employee.getName());
            st.setString(2, employee.getLastName());
            st.setDate(3, employee.getBirthDate());
            if (employee.getDepartmentId() == null)
                st.setNull(4, Types.INTEGER);
            else
                st.setInt(4, employee.getDepartmentId());

            ResultSet result = st.executeQuery();

            if(result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String lastName = result.getString("last_name");
                Date birthDate = result.getDate("birth_date");
                int evaluate = result.getInt("department_id");
                Integer departmentId = evaluate == 0 ? null : evaluate;
                response = new PersistedEmployee(id, name, lastName, birthDate, departmentId, null);
            }

            result.close();
            st.close();

            return response;
        } catch (Exception e){
            logger.error("While persisting an employee.\n\t" + e.getMessage());
        }

        return null;

    }

    @Override
    public IPersistedEmployee get(int id) {
        IPersistedEmployee persistedEmployee = null;

        try (Connection conn = PersistenceConnectivity.get(test)){
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
            logger.error("While withdrawing an employee.\n\t" + e.getMessage());
        }


        return null;
    }

    @Override
    public List<IPersistedEmployee> get() {
        return get(25, 0);
    }

    @Override
    public List<IPersistedEmployee> get(int size, int page) {
        List<IPersistedEmployee> response = new ArrayList<IPersistedEmployee>();

        try (Connection conn = PersistenceConnectivity.get(test)){
            PreparedStatement stm = conn.prepareStatement(selectAllQuery);
            stm.setInt(1, size);
            stm.setInt(2, page);
            ResultSet result = stm.executeQuery();
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
            logger.error("While withdrawing employees.\n\t" + e.getMessage());
        }
        return null;
    }

    @Override
    public List<IPersistedEmployee> find(String pattern) {
        if (pattern == null)
            return null;

        List<IPersistedEmployee> response = new ArrayList<IPersistedEmployee>();
        pattern = "%" + pattern + "%";

        try (Connection conn = PersistenceConnectivity.get(test)){
            PreparedStatement stm = conn.prepareStatement(findNameQuery);
            stm.setString(1, pattern);
            ResultSet result = stm.executeQuery();
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
            logger.error("While withdrawing employees.\n\t" + e.getMessage());
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

        try (Connection conn = PersistenceConnectivity.get(test)){
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
            logger.error("While updating an employee.\n\t" + e.getMessage());
        }

        return affectedRows;
    }

    private String deleteOneQuery = "DELETE FROM employees WHERE id = ?";
    @Override
    public IPersistedEmployee delete(int id) {
        IPersistedEmployee targetEmployee = get(id);

        if (targetEmployee == null)
            return  null;

        try (Connection conn = PersistenceConnectivity.get(test)){
            PreparedStatement st = conn.prepareStatement(deleteOneQuery);
            st.setInt(1, targetEmployee.getId());
            int affectedRows = st.executeUpdate();
            st.close();

            if (affectedRows != 1)
                return null;
        } catch (Exception e) {
            logger.error("While deleting an employee.\n\t" + e.getMessage());
        }

        return targetEmployee;
    }

    @Override
    public int countRegisters(){
        int count = 0;

        try (Connection conn = PersistenceConnectivity.get(test)){
            Statement stm = conn.createStatement();
            ResultSet result = stm.executeQuery("SELECT COUNT(*) FROM employees");

            if (result.next())
                count = parseInt(String.valueOf(result.getInt(1)));

            result.close();
            stm.close();

            return count;
        } catch (Exception e) {
            logger.error("While counting departments:\n\t" + e.getMessage());
        }

        return 0;
    }

}
