package Logic;

import Persistence.TableSchemas;

public class Setup implements IStorageSetup {

    public boolean formatStorage(){
        TableSchemas.dropEmployeesView(null);
        TableSchemas.dropDepartmentsTable(null);
        TableSchemas.createDepartmentsTable(null);
        TableSchemas.dropEmployeesTable(null);
        TableSchemas.createEmployeesTable(null);
        TableSchemas.createEmployeesView(null);
        return true;
    }
}
