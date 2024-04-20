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
        int currentPage = (int) Math.ceil((double) (page + 1) / size);
        counter = currentPage + "/" + totalPage;
        int nextPage = size + page;

        if (page >= size)
            previousButton = "<button hx-trigger='click' hx-target='#"
                    + entity + "TableBody' hx-get='/EmployeeDB/api/"
                    + entity + "?size="
                    + size + "&page=" + (page - size) + "'>Previous</button>";

        if (nextPage < rows)
            nextButton = "<button hx-trigger='click' hx-target='#"
                    + entity + "TableBody' hx-get='/EmployeeDB/api/"
                    + entity + "?size="
                    + size + "&page=" + nextPage + "'>next</button>";


        String payload = """
        <tr>
            <td class='pager-container'>
                $prevBtn
                <span>Page $count</span>
                $nextBtn
            </td>
        </tr>
        """;

        return payload.replace("$prevBtn", previousButton).replace("$count", counter).replace("$nextBtn", nextButton);
    }
}
