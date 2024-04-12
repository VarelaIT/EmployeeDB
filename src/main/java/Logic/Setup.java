package Logic;

import Persistence.PersistenceConnectivity;
import Persistence.TableSchemas;

public class Setup extends PersistenceConnectivity implements IStorageSetup {

    public boolean formatStorage(){
        TableSchemas.dropDepartmentsTable(conn);
        TableSchemas.createDepartmentsTable(conn);
        TableSchemas.dropEmployeesTable(conn);
        TableSchemas.createEmployeesTable(conn);
        return true;
    }
}
