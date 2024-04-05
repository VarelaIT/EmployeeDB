const themeSwitcher = document.querySelector("#themeSwitch");

function setTheme(theme = false){
    if(theme){
        document.querySelector("html").setAttribute("data-theme", "dark");
        localStorage.setItem('theme', 'dark');
    }else{
        document.querySelector("html").setAttribute("data-theme", "light");
    }
}

themeSwitch.addEventListener('change', (event) => {
    let value = false;
    localStorage.removeItem('theme');

    if(themeSwitcher.checked){
        value = 'dark';
        localStorage.setItem('theme', value);
    }

    setTheme(value);
});

window.onload = ()=>{
   if(localStorage.getItem('theme')){
        setTheme('dark');
        themeSwitcher.checked = true;
   }else
        setTheme();
}
