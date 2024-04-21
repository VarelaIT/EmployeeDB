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
  <link rel='stylesheet' type='text/css' href='/EmployeeDB/styles/global.css'/>
  <!--<script src="https://unpkg.com/htmx.org@1.9.11" integrity="sha384-0gxUXCCR8yv9FM2b+U3FDbsKthCI66oH5IA9fHppQq9DDMHuMauqq1ZHBpJxQ0J0" crossorigin="anonymous"></script>-->
  <script type="module" src="/EmployeeDB/scripts/header.js" defer></script>
  <script type="module" src="/EmployeeDB/scripts/form.js" defer></script>
  <title>EmployeeDB - Employees</title>
</head>
<body>

    <main class="container">

        <jsp:include page="/WEB-INF/comp/header.jsp" />

        <section>
            <header>
                <h1>Employees</h1>
            </header>

            <article id="employee-table">

                <header>
                    <form
                        hx-get="/EmployeeDB/api/employee/find"
                        hx-trigger="submit"
                        hx-target="#employeeTableBody"
                        hx-swap="innerHTML"
                    >
                        <input name="pattern" type="search" pattern=".{2,64}" required placeholder="Name of the employee"/>
                        <input type="submit" value="Search"/>
                    </form>
                </header>
                <div class="overflow-auto">
                    <table>
                        <thead>
                            <tr>
                                <th colspan='2'>Name</th>
                                <th colspan='2'>Last Name</th>
                                <th>Birth</th>
                                <th colspan='2'>Department</th>
                                <th>Id</th>
                                <th colspan='3' style="min-width: 300px;">Options</th>
                            </tr>
                        </thead>

                        <tbody
                            id="employeeTableBody"
                            hx-get="/EmployeeDB/api/employee"
                            hx-trigger="load, newEmployee from:body"
                            hx-swap="innerHTML"
                        >
                            <tr>
                                <td colspan='2'>Ismael</td>
                                <td colspan='2'>Varela</td>
                                <td>38</td>
                                <td colspan='2'>R&D, department example.</td>
                                <td>1</td>
                                <td colspan='3'><button>Edit</button><button>Delete</button></td>
                            </tr>
                        </tbody>

                        <tbody id='table-form-container'>
                            <tr>
                                <form
                                    hx-post="/EmployeeDB/api/employee"
                                    hx-trigger="submit"
                                    hx-target="#table-form-container"
                                >
                                <td colspan='2'>
                                    <input name="name" placeholder="Employee name" required pattern=".{2,32}"/>
                                </td>
                                <td colspan='2'>
                                    <input name="lastName" placeholder="Employee last name" required pattern=".{2,64}"/>
                                </td>
                                <td>
                                    <input name="birthDate" type="date" required/>
                                </td>
                                <td colspan='2'>
                                    <select name='departmentId' required
                                       hx-get='/EmployeeDB/api/department?mode=options'
                                       hx-trigger='load'
                                       hx-swap='innerHTML'
                                    >
                                        <option value='1'>R&D</option>
                                    </select>
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

        <jsp:include page="/WEB-INF/comp/footer.jsp" />

    </main>

</body>
</html>
