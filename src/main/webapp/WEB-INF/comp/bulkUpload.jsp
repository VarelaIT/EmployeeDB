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
  <title>EmployeeDB - Departments</title>
</head>
<body>

    <main class="container">

        <jsp:include page="/WEB-INF/comp/header.jsp" />

        <section>

            <header>
                <h1>Employees File Upload</h1>
            </header>

            <article id="upload-info-container">
                <form
                    enctype="multipart/form-data"
                    hx-post="/EmployeeDB/api/employee/upload"
                    hx-trigger="submit"
                    hx-target="#upload-info-container"
                >
                    <input type="hidden" name="MAX_FILE_SIZE" value="200000" />

                    <label>
                        CSV file
                        <input
                            type="file"
                            name="file"
                            placeholder="Select a CSV file."
                            accept=".txt, .csv"
                            required
                        />
                    </label>

                    <input type='submit' value='Upload'/>
                </form>
            </article>
        </section>

        <jsp:include page="/WEB-INF/comp/footer.jsp" />

    </main>

</body>
</html>
