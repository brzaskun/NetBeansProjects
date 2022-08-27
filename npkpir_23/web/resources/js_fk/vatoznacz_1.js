"use strict";
var TabKeyVat1;

(function ($) {
    var focusable = ":input, a[href]";

    TabKeyVat1 = function (event) {
        if(r("dialogewidencjevatdok").is(":visible")){
            //Get the element that registered the event
            var $target = $(event.target);
            var taregetId = event.target.id;
            if (taregetId === "") {
                taregetId = event.target.name;
            }
            var zawartoscpola = r(taregetId).val();
            try {
                if (isSpaceKey(event)) {
                    //PF('grmes').renderMessage({summary:'Oznaczono wiersz', detail: '', severity: 'info'})
                    oznaczfakturespacja();
                    event.preventDefault();
                    event.stopPropagation();
                    event.stopImmediatePropagation();
                }
            } catch (e){}
        }
    }
    
    

    function isSpaceKey(event) {
        if (!event.altKey && !event.ctrlKey && !event.metaKey && event.keyCode === 32) {
            return true;
        }
        return false;
    }
    


})(jQuery);
