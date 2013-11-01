var pilnujwprowadzanychrozrachunkow = function() {
    var limit = zrobFloat($(document.getElementById('rozrachunki:formE:dorozliczenia')).text());
    MYAPP.limit = limit;
    doklejsumowaniewprowadzonych();
};

var drugionShow = function() {
    ustawdialog('dialogdrugi', 'menudokumenty');
    pilnujwprowadzanychrozrachunkow();
    $(document.getElementById("wpisywaniefooter:wnlubma")).val(null);
    $(document.getElementById("wpisywaniefooter:wierszid")).val(null);
};

//wykonuje czynnosci podczas zamykania dialogu z rozrachunkami
var rozrachunkionHide = function() {
    resetujdialog('dialogdrugi');
    try {
    var powrot =  $(MYAPP.zaznaczonepole).attr('id');
    $(document.getElementById(powrot)).focus();
    $(document.getElementById(powrot)).select();
    } catch (e) { }
    $(document.getElementById("wpisywaniefooter:wnlubma")).val("");
    $(document.getElementById("wpisywaniefooter:wierszid")).val("");
    załadujmodelzachowywaniawybranegopola();
};
//sluzy do zaznaczania pol nierozrachunowych
var powrotdopola = function () {
    var powrot =  $(MYAPP.zaznaczonepole).attr('id');
    $(document.getElementById(powrot)).focus();
    $(document.getElementById(powrot)).select();
};
//sluszy do sumowania wprowadzonych kwot czy nie przekraczaja limitu i czy indywidualnie nie przekraczaja limitu w wierszu
var doklejsumowaniewprowadzonych = function() {
    $("#rozrachunki\\:dataList :input").keyup(function() {
        $(this).css("color", "black");
        $(this).css("font-weight", "normal");
        var numerwiersza = ($(this).attr('id').split(":"))[2];
        var wszystkiewiersze = $("#rozrachunki\\:dataList").find(":input");
        var iloscpozycji = wszystkiewiersze.length;
        var wprowadzonowpole = $(this).val();
        var wiersz = "rozrachunki:dataList:"+numerwiersza+":pozostalo";
        var wartoscpoprawej = zrobFloat($(document.getElementById(wiersz)).text());
        $(document.getElementById(wiersz)).css("font-weight", "normal");
        $(document.getElementById(wiersz)).css("color", "black");
        var wierszaktualny = "rozrachunki:formE:dorozliczenia";
        $(document.getElementById(wierszaktualny)).css("font-weight", "normal");
        $(document.getElementById(wierszaktualny)).css("color", "black");
        var wartoscwprowadzona = zrobFloat(wprowadzonowpole);
        if (wartoscwprowadzona > wartoscpoprawej) {
            if (wartoscpoprawej===0) {
                $(document.getElementById(wiersz)).css("font-weight", "600");
                $(document.getElementById(wiersz)).css("color", "green");
            } else {
                $(document.getElementById(wiersz)).css("font-weight", "900");
                $(document.getElementById(wiersz)).css("color", "red");
            }
        }
        if (wprowadzonowpole === " zł") {
            if (wartoscpoprawej >= MYAPP.limit) {
                $(this).val(MYAPP.limit);
            } else {
                $(this).val(wartoscpoprawej);
            }
        }
        var wprowadzono = 0;
        var j = 0;
        for (var i = 0; i < iloscpozycji; i = i + 2) {
            var wiersz = "rozrachunki:dataList:"+j+":pozostalo";
                wprowadzono += zrobFloat($(wszystkiewiersze[i]).val());
            if (wprowadzono > MYAPP.limit) {
                $(wszystkiewiersze[i]).css("font-weight", "900");
                $(wszystkiewiersze[i]).css("color", "red");
                $(document.getElementById(wierszaktualny)).css("font-weight", "900");
                $(document.getElementById(wierszaktualny)).css("color", "red");
            }
            j++;
        }
    });
};
//chodzenie po wierszach tabeli przy uzyciu klawiszy strzalek z przewijaniem
var przejdzwiersz = function () {
  var lolo = $("#zestawieniedokumentow\\:dataList_data").children("tr");
   if(!MYAPP.hasOwnProperty('nrbiezacegowiersza')){
        MYAPP.nrbiezacegowiersza = 0;
    } else {
        MYAPP.nrbiezacegowiersza += 1;
        if (MYAPP.nrbiezacegowiersza > lolo.length) {
            MYAPP.nrbiezacegowiersza = lolo.length;
        }
    }
  var komorki = $(lolo[MYAPP.nrbiezacegowiersza]).children("td");
  var czynaekranie = isScrolledIntoView(komorki[1]);
   if (!czynaekranie) {
    var wysokosc = 70;
    var elem = document.getElementById('zestawieniedokumentow:dataList');
    elem.scrollTop = elem.scrollTop + wysokosc;
  }
  $(komorki[1]).click();
};

var wrocwiersz = function () {
  var lolo = $("#zestawieniedokumentow\\:dataList_data").children("tr");
   if(!MYAPP.hasOwnProperty('nrbiezacegowiersza')){
        MYAPP.nrbiezacegowiersza = 0;
    } else {
        MYAPP.nrbiezacegowiersza -= 1;
        if (MYAPP.nrbiezacegowiersza < 0) {
            MYAPP.nrbiezacegowiersza = 0;
        }
    }
  var komorki = $(lolo[MYAPP.nrbiezacegowiersza]).children("td");
  var czynaekranie = isScrolledIntoView(komorki[1]);
   if (!czynaekranie) {
    var wysokosc = 70;
    var elem = document.getElementById('zestawieniedokumentow:dataList');
    elem.scrollTop = elem.scrollTop - wysokosc;
  }
  $(komorki[1]).click();
};

function isScrolledIntoView(elem)
{
    var docViewTop = $(window).scrollTop()+150;
    var docViewBottom = docViewTop + $(window).height()-300;

    var elemTop = $(elem).offset().top;
    var elemBottom = elemTop + $(elem).height();

    return ((elemBottom >= docViewTop) && (elemTop <= docViewBottom)
      && (elemBottom <= docViewBottom) &&  (elemTop >= docViewTop) );
}


