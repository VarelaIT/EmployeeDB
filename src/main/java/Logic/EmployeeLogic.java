package Logic;

import Persistence.EmployeeRepository;
import Persistence.IEmployeeRepository;
import Persistence.IPersistedEmployee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeLogic implements IEmployeeLogic {

    IEmployeeRepository employeeRepository;

    public EmployeeLogic(){
        this.employeeRepository = new EmployeeRepository();
    }

    public EmployeeLogic(String test){
       this.employeeRepository = new EmployeeRepository(test);
    }

    @Override
    public IEmployeeResponse save(IEmployeeRequest employee) {
        IEmployeeResponse storedEmployee = new EmployeeResponse(employeeRepository.save(employee));

        if(storedEmployee == null)
            throw new RuntimeException("The new employee could not be stored in this service. Please try again latter.");

        return storedEmployee;
    }

    @Override
    public IEmployeeResponse update(int id, IEmployeeRequest employee) {
        int rowsAffected = employeeRepository.update(id, employee);

        if (rowsAffected != 1)
            throw new RuntimeException("The targeted employee was not affected by the change.");

        IPersistedEmployee affectedEmployee = employeeRepository.get(id);

        if (affectedEmployee == null)
            throw new RuntimeException("The targeted employee was updated, but could not be indexed.");

        return new EmployeeResponse(affectedEmployee);
    }

    @Override
    public List<IEmployeeResponse> get() {
        List<IEmployeeResponse> affectedEmployees = new ArrayList<IEmployeeResponse>();
        var response = employeeRepository.get();

        if (response != null){
            response.forEach(employee ->
                    affectedEmployees.add(new EmployeeResponse(employee))
            );
        }

        return affectedEmployees;
    }

    @Override
    public IEmployeeResponse get(int id) {
        IPersistedEmployee affectedEmployee = employeeRepository.get(id);

        if (affectedEmployee == null)
            return null;

        return new EmployeeResponse(affectedEmployee);
    }

    @Override
    public IEmployeeResponse delete(int id) {
        IEmployeeResponse deleted = new EmployeeResponse(employeeRepository.delete(id));
        return deleted;
    }
}
