package Logic;

import Entities.Employee;
import Persistence.EmployeeRepository;
import Persistence.IEmployeeRepository;
import Persistence.IPersistedEmployee;
import Persistence.IReportEmployeesPerDepartment;
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
    public List<IReportEmployeesPerDepartmentResponse> reportEmployeesPerDepartment(){
        List<IReportEmployeesPerDepartment> answer = employeeRepository.perDepartment();
        List<IReportEmployeesPerDepartmentResponse> response = new ArrayList<>();

        if (answer == null)
            return  null;

        for (IReportEmployeesPerDepartment element: answer){
            response.add(new ReportEmployeesPerDepartmentResponse(element));
        }

        return response;
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
            logger.error(Employee.class + " invalid input while trying to store an employee.");
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
        return get(25, 1);
    }

    @Override
    public List<IEmployeeResponse> get(Integer size, Integer page) {
        List<IEmployeeResponse> affectedEmployees = new ArrayList<IEmployeeResponse>();
        List<IPersistedEmployee> response;

        if (size != null && size < 101 && page !=null) {
            int row = (page - 1) * size;
            response = employeeRepository.get(size, row);
        }else
            response = employeeRepository.get(25, 1);

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
    public List<IEmployeeResponse> find(String pattern) {
        List<IEmployeeResponse> affectedEmployees = new ArrayList<IEmployeeResponse>();
        List<IPersistedEmployee> response = null;

        if (pattern != null)
            response = employeeRepository.find(pattern);

        if (response != null){
            response.forEach(employee ->
                    affectedEmployees.add(new EmployeeResponse(employee))
            );
            return affectedEmployees;
        }

        logger.trace(EmployeeLogic.class + " No employees were found.");
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

    public int countEmployees(){
        return employeeRepository.countRegisters();
    }
}
