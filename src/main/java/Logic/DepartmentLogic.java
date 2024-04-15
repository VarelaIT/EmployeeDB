package Logic;

import Entities.IPersistedDepartment;
import Persistence.DepartmentRepository;
import Persistence.IDepartmentRepository;

import java.util.ArrayList;
import java.util.List;

public class DepartmentLogic implements IDepartmentLogic{

    protected IDepartmentRepository departmentRepository;
    public DepartmentLogic(){
        this.departmentRepository = new DepartmentRepository();
    }

    public DepartmentLogic(String test){
        this.departmentRepository = new DepartmentRepository(test);
    }

    public List<IDepartmentResponse> get(){
        List<IDepartmentResponse> inStorageDepartments = new ArrayList<IDepartmentResponse>();
        departmentRepository.getAll().forEach(department -> inStorageDepartments.add(new DepartmentResponse(department)));

        return inStorageDepartments;
    }


    public IDepartmentResponse get(int id){
        IPersistedDepartment response = departmentRepository.get(id);

        if(response != null)
            return new DepartmentResponse(response);

        return null;
    }

    public IDepartmentResponse save(IDepartmentRequest departmentRequest) {
        IPersistedDepartment response = departmentRepository.save(departmentRequest);

        if (response != null)
            return new DepartmentResponse(response);

        return null;
    }

    public IDepartmentResponse update(int id, IDepartmentRequest departmentRequest) {
        IPersistedDepartment response = departmentRepository.update(id, departmentRequest);

        if (response != null)
            return new DepartmentResponse(response);

        return null;
    }

    public IDepartmentResponse delete(int id){
        IPersistedDepartment response = departmentRepository.delete(id);

        if(response != null)
            return new DepartmentResponse(response);

        return null;
    }
}
