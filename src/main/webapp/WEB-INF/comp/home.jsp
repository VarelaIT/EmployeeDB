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
  <!--<script src="https://unpkg.com/htmx.org@1.9.11" integrity="sha384-0gxUXCCR8yv9FM2b+U3FDbsKthCI66oH5IA9fHppQq9DDMHuMauqq1ZHBpJxQ0J0" crossorigin="anonymous"></script>-->
  <script type="module" src="./scripts/header.js" defer></script>
  <title>EmployeeDB</title>
</head>
<body>

    <main class="container">

        <jsp:include page="./WEB-INF/comp/header.jsp" />

        <section  id="about">

            <div class="grid">
                <article>
                    <img src="https://freepngimg.com/thumb/technology/63583-visualization-data-illustration-png-image-high-quality.png" alt="Image not available.">
                </article>

                <article>
                    <h1>About this project</h1>

                    <p>
                        EmployeeDB is the first web application developed by Ismael Varela in Java,
                        the stakes are high... Hopping to catch the attention of the employers;
                        <b>let`s go for the million registers!</b>
                    </p>

                    <ul>
                        <li><a href="/EmployeeDB/employees">Employees</a></li>
                        <li><a href="/EmployeeDB/departments">Departments</a></li>
                        <li><a href="/EmployeeDB/employees/upload">Upload Employees</a></li>
                        <li><a href="/EmployeeDB/setup">Set Up</a></li>
                    </ul>
                </article>
            </div>

        </section>

        <jsp:include page="./WEB-INF/comp/footer.jsp" />

    </main>

</body>
</html>
