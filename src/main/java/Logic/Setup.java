package Logic;

import Persistence.TableSchemas;

public class Setup implements IStorageSetup {

    public boolean formatStorage(){
        TableSchemas.dropDepartmentsTable(null);
        TableSchemas.createDepartmentsTable(null);
        TableSchemas.dropEmployeesTable(null);
        TableSchemas.createEmployeesTable(null);
        return true;
    }
}
