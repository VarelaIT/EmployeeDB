package Logic;

import Persistence.DepartmentRepository;
import Persistence.IDepartmentRepository;

import java.util.ArrayList;
import java.util.List;

public class DepartmentLogic implements IDepartmentLogic{

    protected IDepartmentRepository departmentRepository;
    public DepartmentLogic(){
        this.departmentRepository = new DepartmentRepository();
    }

    public DepartmentLogic(IDepartmentRepository departmentRepository){
        this.departmentRepository = departmentRepository;
    }

    public List<IDepartmentResponse> get(){
        List<IDepartmentResponse> inStorageDepartments = new ArrayList<IDepartmentResponse>();
        departmentRepository.getAll().forEach(department -> inStorageDepartments.add(new DepartmentResponse(department)));

        return inStorageDepartments;
    }


    public IDepartmentResponse get(int id){
        IDepartmentResponse response = new DepartmentResponse(departmentRepository.get(id));
        return response;
    }

    public IDepartmentResponse save(IDepartmentRequest departmentRequest) {
        IDepartmentResponse response = new DepartmentResponse(departmentRepository.save(departmentRequest));

        return response;
    }
}
