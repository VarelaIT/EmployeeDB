
function setTheme(theme = false){
    if(theme){
        document.querySelector("html").setAttribute("data-theme", "dark");
        localStorage.setItem('theme', 'dark');
    }else{
        document.querySelector("html")!.setAttribute("data-theme", "light");
    }
}

window.onload = ()=>{
   if(localStorage.getItem('theme'))
        setTheme('dark');
   else
        setTheme();
}
