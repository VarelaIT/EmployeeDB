package Logic;

import Entities.Employee;
import Persistence.EmployeeRepository;
import Persistence.IEmployeeRepository;
import Persistence.IPersistedEmployee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class EmployeeLogic implements IEmployeeLogic {

    private static final Logger logger = LogManager.getLogger("regular");
    IEmployeeRepository employeeRepository;

    public EmployeeLogic(){
        this.employeeRepository = new EmployeeRepository();
    }

    public EmployeeLogic(String test){
       this.employeeRepository = new EmployeeRepository(test);
    }

    @Override
    public IEmployeeResponse save(IEmployeeRequest employeeRequest) {
        if (!employeeRequest.verifyInput()) {
            logger.warn(Employee.class + " invalid input while trying to store an employee.");
            return null;
        }

        IPersistedEmployee storedEmployee= employeeRepository.save(employeeRequest);

        if (storedEmployee != null)
            return new EmployeeResponse(storedEmployee);

        logger.trace(
            "The requested employee was not saved successfully.\n\tName: " + employeeRequest.getName()
            + ",\n\tDescription:" + employeeRequest.getLastName()
        );
        return null;
    }

    @Override
    public IEmployeeResponse update(Integer id, IEmployeeRequest employeeRequest) {
        if (id == null || employeeRequest == null)
            return null;

        if (!employeeRequest.verifyInput()) {
            logger.warn(Employee.class + " invalid input while trying to store an employee.");
            return null;
        }

        int rowsAffected = employeeRepository.update(id, employeeRequest);

        if (rowsAffected != 1)
            logger.trace("The targeted employee was not affected by the update execution.");

        IPersistedEmployee affectedEmployee = employeeRepository.get(id);

        if (affectedEmployee != null)
            return new EmployeeResponse(affectedEmployee);

        logger.trace(
            "The requested employee was not updated successfully.\n\tName: " + employeeRequest.getName()
            + ",\n\tDescription:" + employeeRequest.getLastName()
        );
        return null;
    }

    @Override
    public List<IEmployeeResponse> get() {
        List<IEmployeeResponse> affectedEmployees = new ArrayList<IEmployeeResponse>();
        var response = employeeRepository.get();

        if (response != null){
            response.forEach(employee ->
                    affectedEmployees.add(new EmployeeResponse(employee))
            );
            return affectedEmployees;
        }

        logger.trace("No employees to return.");
        return null;
    }

    @Override
    public IEmployeeResponse get(int id) {
        IPersistedEmployee affectedEmployee = employeeRepository.get(id);

        if (affectedEmployee != null)
            return new EmployeeResponse(affectedEmployee);

        logger.trace("Employee with id " + id + " was not found.");
        return null;
    }

    @Override
    public IEmployeeResponse delete(int id) {
        IPersistedEmployee response = employeeRepository.delete(id);

        if (response != null)
            return new EmployeeResponse(response);

        logger.trace("Employee with id " + id + " could not be deleted.");
        return null;
    }
}
