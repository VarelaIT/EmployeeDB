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