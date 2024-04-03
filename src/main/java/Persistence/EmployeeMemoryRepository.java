package Persistence;

import Entities.IEmployee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeMemoryRepository implements IEmployeeRepository{
    private List<IEmployee> storage = new ArrayList<IEmployee>();
    private int length = 0;
    private int index = 0;

    @Override
    public void save(IEmployee employee) {
        index++;
        employee.setId(index);
        storage.add(employee);
        length++;
    }

    @Override
    public IEmployee get(int id) {
        IEmployee current;
        for(int i = 0; i < storage.size(); i++){
            current = storage.get(i);
            if (current.getId() == id)
                return current;
        }
        return null;
    }
}
