package WebService;

import Logic.DepartmentLogic;

public class PagerComponent {
    public static String department(Integer size, Integer page) {
        int rows = new DepartmentLogic().countDepartments();
        Integer count = 1;
        String previousButton = "";
        String nextButton = "";
        String counter = "1/1";

        if (size != null && page != null) {
            int totalPage = (int) Math.ceil((double) rows / size);
            int currentPage = (int) Math.ceil((double) (page + 1) / size);
            counter = currentPage + "/" + totalPage;
            int nextPage = size + page;

            if (page > size)
                previousButton = "<a class='button' hx-target='#departmentTableBody' hx-get='./api/department?size="
                        + size + "&page=" + (page - size) + "'>Previous</a>";

            if (nextPage < rows)
                nextButton = "<a class='button' hx-target='#departmentTableBody' hx-get='./api/department?size="
                        + size + "&page=" + nextPage + "'>next</a>";

        }

        String payload = """
        <tr>
            <td class='pager-container'>
                $prevBtn
                <span>$count</span>
                $nextBtn
            </td>
        </tr>
        """;

        return payload.replace("$prevBtn", previousButton).replace("$count", counter).replace("$nextBtn", nextButton);
    }
}
