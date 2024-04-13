package Logic;

import java.util.List;

public interface IEmployeeLogic {
    IEmployeeResponse save(IEmployeeRequest employee);
    IEmployeeResponse update(int id, IEmployeeRequest employee);
    List<IEmployeeResponse> get();
    IEmployeeResponse get(int id);
    IEmployeeResponse delete(int id);

}
