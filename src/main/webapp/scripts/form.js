const forms = document.querySelectorAll("form");

forms.forEach(form => form.addEventListener("htmx:afterRequest", e => {
    if(e.detail.successful)
        e.detail.elt.reset();
}))

function isInvalid(node){
    if(node.validity.valueMissing){
        node.setCustomValidity('This field is required.');
        node.reportValidity();
        node.setCustomValidity('');
        return true;
    }else if(node.validity.patternMismatch){
        node.setCustomValidity('Unexpected pattern.');
        node.reportValidity();
        node.setCustomValidity('');
        return true;
    }else{
        return false;
    }
}

