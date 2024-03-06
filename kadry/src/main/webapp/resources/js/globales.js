/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var con = function () {
    $("body").css("cursor", "wait");
};

var coff = function () {
    $("body").css("cursor", "default");
};

var concoff = function(ile) {
    $("body").css("cursor", "wait");
    setTimeout(function(){ coff(); }, ile);
};


var pokazwydruk = function (ktoco) {
    setTimeout(window.open('resources/wydruki/' + ktoco, '', 'status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50'), 10000);
};

var wydrukXML = function (nazwa) {
    window.open('resources/xml/' + nazwa, '', 'status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var wydrukPDF = function (nazwa) {
    window.open('resources/pdf/' + nazwa, '', 'status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

function ustawDateSprzedazy(rok, mc) {
    try {
        var dataWyst = document.getElementById(this.event.target.name);
        var wart = dataWyst.value;
        if (mc !== 10 && mc !== 11 && mc !== 12) {
            mc = "0" + mc;
        }
        var re1 = /[0-3][0-9]/;
        var re2 = /[0-1][0-9]\S[0-3][0-9]/;
        var re3 = /[2][0][0-9][0-9]\S[0-1][0-9]\S[0-3][0-9]/;
        if (wart.match(re3)) {
            dataWyst.value = wart;
        } else if (wart.match(re2)) {
            dataWyst.value = rok + "-" + wart;
        } else if (wart.match(re1)) {
            dataWyst.value = rok + "-" + mc + "-" + wart;
        }
        var re = /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/;
        var testw = dataWyst.value;
        if (!testw.match(re)) {
            dataWyst.value = null;
        }
        if (isValidDate(wart)===false) {
            dataWyst.value = wart+" jest niepoprawną datą";
        }
    } catch (e){}
};
    //sprawdza lata przestępne
    function isValidDate(dateString) {
        const regex = /^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$/;
        if (!regex.test(dateString)) {
            return false;
        }

        const [year, month, day] = dateString.split('-').map(Number);
        const date = new Date(year, month - 1, day);
        if (date.getFullYear() === year && date.getMonth() === month - 1 && date.getDate() === day) {
            return true;
        }

        return false;
    }

$(document).on("ajaxStart pfAjaxSend", function () {
    $("html").addClass("progress");
}).on("ajaxStop pfAjaxComplete", function () {
    $("html").removeClass("progress");
});
