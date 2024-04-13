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
  <title>EmployeeDB - Employees</title>
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
                                <th>Name</th>
                                <th>Last Name</th>
                                <th>Age</th>
                                <th>Department</th>
                                <th>Id</th>
                            </tr>
                        </thead>

                        <tbody
                            id="employeesTableBody"
                            hx-get="./api/employee"
                            hx-trigger="load"
                            hx-swap="innerHTML"
                        >
                            <tr>
                                <td>Ismael</td>
                                <td>Varela</td>
                                <td>38</td>
                                <td>R&D, department example.</td>
                                <td>1</td>
                            </tr>
                        </tbody>

                        <tr>
                            <form
                                hx-post="./api/department"
                                hx-trigger="submit"
                                hx-swap="afterend"
                                hx-target="#departmentTableBody"
                            >
                            <td>
                                <input name="department" placeholder="Department name" required/>
                            </td>
                            <td>
                                <input name="description" placeholder="Brief department description" required/>
                            </td>
                            <td>
                                <input type="submit" value="Add"/>
                            </td>
                            <form>
                        </tr>
                    </table>
                </div>

            </article>
        </section>

        </section>

        <jsp:include page="./WEB-INF/comp/footer.jsp" />

    </main>

</body>
</html>