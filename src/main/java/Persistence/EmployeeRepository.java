package Persistence;

import Entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository  extends PersistenceConnectivity implements IEmployeeRepository{

    protected String insertionQuery = "INSERT INTO employees (name, last_name, birth_date, department_id) VALUES (?, ?, ?, ?)"
            + " RETURNING id, name, last_name, birth_date, department_id";
    protected String selectAllQuery = "SELECT id, name, last_name, birth_date, departments.name as department FROM employees"
            + " LEFT JOIN departments ON employees.id = departments.id";
    protected String selectOneQuery = "SELECT id, name, last_name, birth_date, departments.name as department FROM employees"
            + " LEFT JOIN departments ON employees.id = departments.id WHERE employees.id = ?";

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

            return new PersistedEmployee(id, name, lastName, birthDate, departmentId);
        } catch (Exception e){
            System.out.println("The Employee Persistence log.\n\t" + e.getMessage());
        }

        return null;

    }

    @Override
    public IPersistedEmployee get(int id) {
        try {
            PreparedStatement stm = conn.prepareStatement(selectOneQuery);
            stm.setInt(1, id);
            ResultSet result = stm.executeQuery();

            result.next();
            IPersistedEmployee persistedEmployee = new PersistedEmployee(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getString("last_name"),
                    result.getDate("birth_date"),
                    result.getInt("department_id")
            );

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
                        result.getDate("birth_date"),
                        result.getInt("department_id")
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
}
