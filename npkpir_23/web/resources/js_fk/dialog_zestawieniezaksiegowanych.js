"use strict";

var pokazwierszedok = function() {
        PF('wiersze').show();
};

var ustawdialogwiersze = function(nazwa,menu) {
    $(document.getElementById(nazwa)).width(1000).height(400);
    try {
        $(document.getElementById(nazwa)).position({
        my: "center top",
        at: "center center",
        of: $(document.getElementById(menu)),
        collision: "none none"
    });
    } catch (Exception) {
        alert ("blad w fukncji ustaw w pliku dialog.js wiersz 1 "+Exception);
    }

};

var schowajeditbutton = function(rzad) {
    MYAPP.schowajeditbuttonnr = rzad;
    var wiersz = 'zestawieniedokumentow:dataList:'+rzad+':edytujbutton';
    r(wiersz).hide();
};

var pokazeditbutton = function() {
    try {
        rzad = MYAPP.schowajeditbuttonnr;
        var wiersz = 'zestawieniedokumentow:dataList:'+rzad+':edytujbutton';
        r(wiersz).show();
    } catch (e) {
        
    }
};

var zapisywierszywybordok = function() {
    PF('wpisywanie').show();
    var lp = document.getElementById('zestawieniezapisownakontachpola:wierszDoPodswietlenia').value;
    var nazwa = 'formwpisdokument:dataList:'+lp+':opisdokwpis';
    $(document.getElementById(nazwa)).select();
};

var pokazwierszoznaczony = function() {
    try {
        if (document.getElementById('formwpisdokument:numerwlasny').value !== "") {
//            r("formwpisdokument:data2DialogWpisywanie").focus();
//            r("formwpisdokument:data2DialogWpisywanie").select();
//        } else {
            var lp = document.getElementById('zestawieniezapisownakontachpola1:wierszDoPodswietlenia').value;
            //var jest = rj("formwpiskonta:wyborkonta_input").value;
            if (lp !== "-1") {
                var nazwa = "formwpisdokument:dataList:"+lp+":opisdokwpis";
                var bliskietd = r(nazwa).closest("td")[0];
                var tablicaid = $(bliskietd).closest(".walkingtable2")[0].id;
                obliczwysokosc(tablicaid);
                var przesun = isScrolledIntoView(bliskietd);
                document.getElementById(tablicaid).scrollTop = document.getElementById(tablicaid).scrollTop + przesun;
                $($(bliskietd).closest("tr")[0]).css("background-color", "blue");
                $($(bliskietd).closest("tr")[0]).css("color", "blue");
                r(nazwa).focus();
                r(nazwa).select();
                document.getElementById('zestawieniezapisownakontachpola1:wierszDoPodswietlenia').value = "-1";
            }
        }
    } catch (e){
        //alert('blad '+e);
    }
};

