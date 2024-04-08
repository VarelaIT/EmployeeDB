package Logic;

import Entities.IPersistedDepartment;
import Persistence.DepartmentRepository;
import Persistence.IDepartmentRepository;
import Persistence.ITestDepartmentRepository;

import java.util.List;

public class DepartmentLogic implements IDepartmentLogic{

    protected IDepartmentRepository departmentRepository;
    public DepartmentLogic(){
        this.departmentRepository = new DepartmentRepository();
    }

    public DepartmentLogic(ITestDepartmentRepository departmentRepository){
        this.departmentRepository = departmentRepository;
    }

    public String get(){
        List<IPersistedDepartment> inStorageDepartments = departmentRepository.getAll();

        String rawPayload = "";

        for (IPersistedDepartment department : inStorageDepartments) {
            String tableRow = new Object2TextParser().departmentTableRow(department);

            rawPayload = rawPayload.concat(tableRow);
        }

        return rawPayload;
    }


    public String get(int id){
        return new Object2TextParser().departmentTableRow(departmentRepository.get(id));
    }

    public String save(IDepartmentRequest departmentRequest) {
        String response = "The request input is invalid.";

        if(departmentRequest.verifyInput()) {
            IPersistedDepartment department = departmentRepository.save(departmentRequest);
            response = new Object2TextParser().departmentTableRow(department);
        }

        return response;
    }
}
