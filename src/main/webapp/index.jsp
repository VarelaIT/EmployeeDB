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
  <link rel='stylesheet' type='text/css' href='/css/global.css'/>
  <script src="https://unpkg.com/htmx.org@1.9.11" integrity="sha384-0gxUXCCR8yv9FM2b+U3FDbsKthCI66oH5IA9fHppQq9DDMHuMauqq1ZHBpJxQ0J0" crossorigin="anonymous"></script>
  <script type="module" src="./scripts/header.js" defer></script>
  <title>EmployeeDB</title>
</head>
<body>

    <main class="container">

        <header>
            <h1>EmployeeDB</h1>
            <input type="checkbox" role="switch" checked onchange="()=>toggleTheme(!theme)"/>
            <img src="https://frugalentrepreneur.com/wp-content/uploads/2019/10/big-data.jpg" alt="Image not available.">
        </header>

        <aside>
            <nav>
                <a href="/#about">About</a>
                <a href="/department">Departments</a>
                <a href="/employees">Employees</a>
            </nav>
        </aside>

        <section  id="about">

            <article>
                <p>
                    EmployeeDB is the first web application developed by Ismael Varela in Java,
                    the stakes are high... Hopping to catch the attention of the employers;
                    <b>let`s begin!</b>
                </p>
            </article>

        </section>

        <footer>
            <p>
                Develop by <a href="https://varelait.com">VarelaIT</a>
            </p>
        </footer>

    </main>

</body>
</html>
