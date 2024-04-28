package Logic;

import Persistence.TableSchemas;

public class Setup implements IStorageSetup {

    private String test = null;

    public Setup(){}
    public Setup(String test){
        this.test = test;
    }

    public boolean formatStorage(){
        TableSchemas.dropUploadsTable(test);
        TableSchemas.dropFailedLinesTable(test);
        TableSchemas.dropDepartmentsTable(test);
        TableSchemas.dropEmployeesTable(test);
        TableSchemas.createDepartmentsTable(test);
        TableSchemas.createEmployeesTable(test);
        TableSchemas.createEmployeesView(test);
        TableSchemas.createUploadsTable(test);
        TableSchemas.createFailedLinesTable(test);
        return true;
    }

    public boolean defaultDepartments(){
        TableSchemas.insertDefaultDepartments(test);
        return true;
    }
}
