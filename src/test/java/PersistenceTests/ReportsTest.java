package PersistenceTests;

import Entities.Department;
import Entities.IDepartment;
import Entities.IPersistedDepartment;
import Persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ReportsTest {
    private IDepartmentRepository departmentRepository;
    private IEmployeeRepository employeeRepository;
    private IUploadRepository uploadRepository;
    private final DateFormat bdObj= new SimpleDateFormat("dd-MM-yyyy");

    public String test = "test";

    @BeforeEach
    public void employeeStorageInitialization(){
        TableSchemas.dropDepartmentsTable(test);
        TableSchemas.createDepartmentsTable(test);
        TableSchemas.dropEmployeesTable(test);
        TableSchemas.createEmployeesTable(test);
        TableSchemas.createEmployeesView(test);
        employeeRepository = new EmployeeRepository(test);
        departmentRepository = new DepartmentRepository(test);
        uploadRepository = new UploadRepository(test);
    }

    @Test
    public void createEmployeePerDepartmentReport(){
        IDepartment departmentA = new Department("Data Analysis", "Analyzing Data and stuff");
        IDepartment departmentB = new Department("Design", "Graphical & visual design");
        IPersistedDepartment persistedA = departmentRepository.save(departmentA);
        IPersistedDepartment persistedB = departmentRepository.save(departmentB);
        String chunk = "('Michael', 'Johnson', '1978-11-02', " + persistedA.getId() +"),"
            + "('Emily', 'Williams', '1982-04-10', " + persistedB.getId() + "),"
            + "('Liam', 'Davis', '1994-03-15', " + persistedB.getId() + "),"
            + "('Noah', 'Miller', '1982-01-05', " + persistedB.getId() + ")";
        int afectedRows = uploadRepository.employeesChunk(chunk);

        List<IReportEmployeesPerDepartment> report = employeeRepository.perDepartment();
        Integer totalA = 0;
        Integer totalB = 0;
        for (IReportEmployeesPerDepartment element: report){
            if (element.getId() == persistedA.getId())
                totalA = element.getTotal();
            else if (element.getId() == persistedB.getId())
                totalB = element.getTotal();
        };

        assertEquals(1, totalA);
        assertEquals(3, totalB);
    }

}
