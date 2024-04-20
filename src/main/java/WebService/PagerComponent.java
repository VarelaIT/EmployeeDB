package WebService;

import Logic.DepartmentLogic;

public class PagerComponent {
    public static String department(Integer size, Integer page) {
        Integer rows = new DepartmentLogic().countDepartments();
        Integer count = 1;
        String previousButton = "";
        String nextButton = "";
        String counter = "1/1";

        if (size != null && page != null && rows != null) {
            if (page > size)
                previousButton = "<a class='button' hx-target='#departmentTableBody' hx-get='./api/department?size="
                        + size + "&page=" + (page - size) + "'>Previous</a>";

            if (page < rows)
                nextButton = "<a class='button' hx-target='#departmentTableBody' hx-get='./api/department?size="
                        + size + "&page=" + (size + page) + "'>next</a>";

            int totalPage = count / size;
            int currentPage = page / size;
            counter = currentPage + "/" + totalPage;
        }

        String payload = """
        <tr>
            <div class='pager-container'>
                $prevBtn
                <span>$count</span>
                $nextBtn
            </div>
        </tr>
        """;

        return payload.replace("$prevBtn", previousButton).replace("$count", counter).replace("$nextBtn", nextButton);
    }
}
