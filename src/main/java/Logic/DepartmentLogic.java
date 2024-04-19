package Logic;

import Entities.IPersistedDepartment;
import Persistence.DepartmentRepository;
import Persistence.IDepartmentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class DepartmentLogic implements IDepartmentLogic{

    private static final Logger logger = LogManager.getLogger("regular");
    protected IDepartmentRepository departmentRepository;
    public DepartmentLogic(){
        this.departmentRepository = new DepartmentRepository();
    }

    public DepartmentLogic(String test){
        this.departmentRepository = new DepartmentRepository(test);
    }

    public IDepartmentResponse save(IDepartmentRequest departmentRequest) {
        if (!departmentRequest.verifyInput()) {
            logger.warn(DepartmentLogic.class + " Invalid input while trying to store a department.");
            return null;
        }

        IPersistedDepartment response = departmentRepository.save(departmentRequest);

        if (response != null)
            return new DepartmentResponse(response);

        logger.trace(
                "Department was not saved successfully.\n\tName: " + departmentRequest.getName()
                        + ",\n\tDescription:" + departmentRequest.getDescription()
        );
        return null;
    }

    public IDepartmentResponse update(int id, IDepartmentRequest departmentRequest) {
        if (!departmentRequest.verifyInput()) {
            logger.warn(DepartmentLogic.class + " Invalid input while trying to update a department.");
            return null;
        }

        IPersistedDepartment response = departmentRepository.update(id, departmentRequest);

        if (response != null)
            return new DepartmentResponse(response);

        logger.trace(
                "Department was not updated successfully.\n\tId: " + id
                        + "\n\tName: " + departmentRequest.getName()
                        + ",\n\tDescription:" + departmentRequest.getDescription()
        );
        return null;
    }

    public List<IDepartmentResponse> get(){
        List<IDepartmentResponse> inStorageDepartments = new ArrayList<IDepartmentResponse>();
        List<IPersistedDepartment> response;

        response = departmentRepository.getAll();
        if (response != null){
            response.forEach(department -> inStorageDepartments.add(new DepartmentResponse(department)));
            return inStorageDepartments;
        }


        logger.trace("Departments not retrieved while getting a department list.");
        return null;
    }


    public IDepartmentResponse get(int id){
        IPersistedDepartment response = departmentRepository.get(id);

        if(response != null)
            return new DepartmentResponse(response);

        logger.trace("Department not retrieved while getting by id.");
        return null;
    }

    public IDepartmentResponse delete(int id){
        IPersistedDepartment response = departmentRepository.delete(id);

        if(response != null)
            return new DepartmentResponse(response);

        return null;
    }
}
