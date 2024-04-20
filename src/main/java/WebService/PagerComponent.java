package WebService;

import Logic.DepartmentLogic;
import Logic.EmployeeLogic;

public class PagerComponent {

    public static String build(String entity, Integer size, Integer page) {
        int rows = 0;
        if (entity.equals("employee"))
            rows = new EmployeeLogic().countEmployees();

        if (entity.equals("department"))
            rows = new DepartmentLogic().countDepartments();
        String previousButton = "";
        String nextButton = "";
        String counter = "1/1";

        if (size == null)
            size = 25;

        if (page == null)
            page = 1;

        int totalPage = (int) Math.ceil((double) rows / size);
        counter = page + "/" + totalPage;
        int nextPage = page + 1;

        if (page > 1)
            previousButton = "<button hx-trigger='click' hx-target='#"
                    + entity + "TableBody' hx-get='/EmployeeDB/api/"
                    + entity + "?size="
                    + size + "&page=" + (page - 1) + "'>Previous</button>";

        if (nextPage * size < rows + size)
            nextButton = "<button hx-trigger='click' hx-target='#"
                    + entity + "TableBody' hx-get='/EmployeeDB/api/"
                    + entity + "?size="
                    + size + "&page=" + nextPage + "'>next</button>";


        String payload = """
        <tr>
            <td colspan='6' class='pager-container'>
                $prevBtn
                <span>Page $count</span>
                $nextBtn
            </td>
        </tr>
        """;

        return payload.replace("$prevBtn", previousButton).replace("$count", counter).replace("$nextBtn", nextButton);
    }
}
