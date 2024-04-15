<!DOCTYPE html>
<html lang="en" data-theme="light">
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
  <link rel="icon" type="image/png" href="media/">
  <link
    rel="stylesheet"
    href="https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.min.css"
  />
  <link rel='stylesheet' type='text/css' href='./styles/global.css'/>
  <script src="https://unpkg.com/htmx.org@1.9.11" integrity="sha384-0gxUXCCR8yv9FM2b+U3FDbsKthCI66oH5IA9fHppQq9DDMHuMauqq1ZHBpJxQ0J0" crossorigin="anonymous"></script>
  <script type="module" src="./scripts/header.js" defer></script>
  <script type="module" src="./scripts/form.js" defer></script>
  <title>EmployeeDB - Departments</title>
</head>
<body>

    <main class="container">

        <jsp:include page="./WEB-INF/comp/header.jsp" />

        <section>
            <header>
                <h1>Departments</h1>
            </header>

            <article id="deparments-table">

                <div class="overflow-auto">
                    <table>
                        <thead>
                            <tr>
                                <th colspan="2">Department</th>
                                <th colspan="3">Description</th>
                                <th>Id</th>
                                <th colspan="2">Options</th>
                            </tr>
                        </thead>

                        <tbody
                            id="departmentTableBody"
                            hx-get="./api/department"
                            hx-trigger="load, newDepartment from:body"
                            hx-swap="innerHTML"
                        >
                            <tr>
                                <td>HHRR</td>
                                <td>Human Resources, department example.</td>
                                <td>1</td>
                                <td>Options go here!</td>
                            </tr>
                        </tbody>

                        <tbody id='table-form-container'>
                            <tr>
                                <form
                                    hx-post="./api/department"
                                    hx-trigger="submit"
                                    hx-target="#table-form-container"
                                >
                                <td colspan='2'>
                                    <input name="department" placeholder="Department name" required/>
                                </td>
                                <td colspan='3'>
                                    <input name="description" placeholder="Brief department description" required/>
                                </td>
                                <td>
                                </td>
                                <td colspan='2'>
                                    <input type="submit" value="Add"/>
                                </td>
                                <form>
                            </tr>
                        </tbody>
                    </table>
                </div>

            </article>
        </section>

        </section>

        <jsp:include page="./WEB-INF/comp/footer.jsp" />

    </main>

</body>
</html>
