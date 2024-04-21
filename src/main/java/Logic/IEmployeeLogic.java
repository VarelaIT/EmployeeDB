package Logic;

import java.util.List;

public interface IEmployeeLogic {
    IEmployeeResponse save(IEmployeeRequest employee);
    IEmployeeResponse update(Integer id, IEmployeeRequest employee);
    List<IEmployeeResponse> get();
    List<IEmployeeResponse> get(Integer size, Integer page);
    List<IEmployeeResponse> find(String pattern);
    IEmployeeResponse get(int id);
    IEmployeeResponse delete(int id);
    int countEmployees();

}
