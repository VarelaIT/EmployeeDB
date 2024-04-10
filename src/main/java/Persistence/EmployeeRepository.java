package Persistence;

import Entities.IEmployee;
import Entities.IPersistedEmployee;
import Entities.PersistedEmployee;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class EmployeeRepository  extends PersistenceConnectivity implements IEmployeeRepository{
    private String insertionQuery = "";
    @Override
    public IPersistedEmployee save(IEmployee employee) {
        try {
            PreparedStatement st = conn.prepareStatement(insertionQuery);
            st.setString(1, employee.getName());
            st.setString(2, employee.getLastName());
            st.setDate(2, employee.getBirthDate());
            st.setInt(2, employee.getDepartmentId());

            ResultSet result = st.executeQuery();

            result.next();
            int id = result.getInt("id");
            String name = result.getString("name");
            String lastName = result.getString("lastname");
            Date birthDate = result.getDate("bith_date");
            Integer departmentId = result.getInt("department_id");

            result.close();
            st.close();

            return new PersistedEmployee(id, name, lastName, birthDate, departmentId);
        } catch (Exception e){
            System.out.println("The Department Persistence log.\n\t" + e.getMessage());
        }

        return null;

    }

    @Override
    public IPersistedEmployee get(int id) {
        return null;
    }

    @Override
    public List<IPersistedEmployee> get() {
        return null;
    }
}
