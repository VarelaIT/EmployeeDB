package MockPersistence;

import Entities.IEmployee;
import Entities.IPersistedEmployee;
import Entities.PersistedEmployee;
import Persistence.IEmployeeRepository;
import Persistence.PersistenceConnectivity;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;

public class MockEmployeeRepository extends PersistenceConnectivity implements IEmployeeRepository{

    private final String insertionQuery = "INSERT INTO test_employees (name, last_name, birth_date, department_id) VALUES (?, ?, ?, ?)"
            + " RETURNING id, name, last_name, birth_date, department_id";
    @Override
    public IPersistedEmployee save(IEmployee employee) {
        try {
            PreparedStatement st = conn.prepareStatement(insertionQuery);
            st.setString(1, employee.getName());
            st.setString(2, employee.getLastName());
            st.setDate(3, employee.getBirthDate());
            if(employee.getDepartmentId() == null)
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
        return null;
    }

    @Override
    public List<IPersistedEmployee> get() {
        return null;
    }
}
