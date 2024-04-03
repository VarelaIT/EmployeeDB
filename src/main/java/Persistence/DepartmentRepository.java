package Persistence;

import Entities.IDepartment;
import Entities.IPersistedDepartment;
import Entities.PersistedDepartment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DepartmentRepository  implements IDepartmentRepository{
    public String insertionQuery =
            "INSERT INTO departments ( name, description)"
                    + " VALUES"
                    + " ( ?, ?)"
                    + " RETURNING id, name, description";

    public  IPersistedDepartment save(Connection conn, IDepartment newDepartment) {
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

    public IDepartment get(Connection conn, int id) {
        System.out.println("Not implemented by the developer.");
        return null;
    }
}
