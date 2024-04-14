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

    EmployeeLogic(IEmployeeRepository employeeRepository){
       this.employeeRepository = employeeRepository;
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

        IEmployeeResponse affectedEmployee = (IEmployeeResponse) employeeRepository.get(id);

        if (affectedEmployee == null)
            throw new RuntimeException("The targeted employee was updated, but could not be indexed.");

        return affectedEmployee;
    }

    @Override
    public List<IEmployeeResponse> get() {
        List<IEmployeeResponse> affectedEmployees = new ArrayList<IEmployeeResponse>();
        employeeRepository.get().forEach(employee ->
                affectedEmployees.add((IEmployeeResponse) employee)
        );

        return affectedEmployees;
    }

    @Override
    public IEmployeeResponse get(int id) {
        IEmployeeResponse affectedEmployee = (IEmployeeResponse) employeeRepository.get(id);

        if (affectedEmployee == null)
            throw new RuntimeException("The targeted employee could not be found.");

        return affectedEmployee;
    }

    @Override
    public IEmployeeResponse delete(int id) {
        return null;
    }
}
