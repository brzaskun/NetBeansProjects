var ustawdialogWindow = function(nazwa, szerokosc, wysokosc) {
    $(document.getElementById(nazwa)).width(szerokosc).height(wysokosc);
    try {
        $(document.getElementById(nazwa)).position({
        my: "center center",
        at: "center center",
        of: window,
        collision: "none none"
        });
        $(document.getElementById(nazwa)).css()
    } catch (Exception) {
       //alert ("blad w fukncji ustawdialog w pliku dialog.js wiersz 16 "+Exception);
    }
};
    
var pokazmes = function() {
  PF('grmes').renderMessage({summary:'Wybrano wiersz', detail: 'można edytować', severity: 'info'});  
};


var ustawdialogwymiary = function(nazwa,rodzic) {
    $(document.getElementById(nazwa)).width(1250).height(700);
    try {
        $(document.getElementById(nazwa)).position({
        my: "left top",
        at: "left+40px top",
        of: $(document.getElementById(rodzic)),
        collision: "none none"
        });
    } catch (Exception) {
        alert ("blad w fukncji ustawdialog w pliku dialog.js wiersz 1 "+Exception);
    }

};

var ustawdialogframe = function(rodzic) {
    let nazwa = $($($.find('iframe')[0]).parent()[0]).parent()[0];
    $($(nazwa)[0]).on('show', function() {
      alert('afterShow');
    })
    try {
        setTimeout(ustaw2(nazwa,rodzic),3000);
    } catch (Exception) {
        alert ("blad w fukncji ustawdialog w pliku dialog.js wiersz 1 "+Exception);
    }

};

var ustaw2 = function(nazwa,rodzic) {
    $(document.getElementById($(nazwa)[0].id)).position({
        my: "left top",
        at: "left-200px top",
        of: $(document.getElementById(rodzic)),
        collision: "none none"
        });
};

var ustawdialog = function(nazwa,rodzic, szerokosc, wysokosc) {
    $(document.getElementById(nazwa)).width(szerokosc).height(wysokosc);
    try {
        $(document.getElementById(nazwa)).position({
        my: "left top",
        at: "left+70px top+55px",
        of: $(document.getElementById(rodzic)),
        collision: "none none"
        });
    } catch (Exception) {
       //alert ("blad w fukncji ustawdialog w pliku dialog.js wiersz 16 "+Exception);
    }

};

var ustawdialogAuto = function(nazwa, szerokosc, wysokosc) {
    $(document.getElementById(nazwa)).width(szerokosc).height(wysokosc);
    try {
        $(document.getElementById(nazwa)).position({
        my: "left top",
        at: "left+60px top+30px",
        of: $(document.getElementById(znajdzmenu(nazwa))),
        collision: "none none"
        });
    } catch (Exception) {
       //alert ("blad w fukncji ustawdialog w pliku dialog.js wiersz 16 "+Exception);
    }

};

var ustawdialogCenter = function(nazwa,rodzic, szerokosc, wysokosc) {
    $(document.getElementById(nazwa)).width(szerokosc).height(wysokosc);
    try {
        $(document.getElementById(nazwa)).position({
        my: "center center",
        at: "center center",
        of: $(document.getElementById(rodzic)),
        collision: "none none"
        });
    } catch (Exception) {
       //alert ("blad w fukncji ustawdialog w pliku dialog.js wiersz 16 "+Exception);
    }

};

var ustawdialogrk = function(nazwa,rodzic, szerokosc, wysokosc) {
    $(document.getElementById(nazwa)).width(szerokosc).height(wysokosc);
    try {
        $(document.getElementById(nazwa)).position({
        my: "left center",
        at: "left+150px center",
        of: $(document.getElementById(rodzic)),
        collision: "none none"
        });
    } catch (Exception) {
       //alert ("blad w fukncji ustawdialog w pliku dialog.js wiersz 16 "+Exception);
    }

};



var resetujdialog = function(nazwa) {
    $(document.getElementById(nazwa)).removeAttr('style');
};

var znajdzmenu = function(nazwa) {
    return $(document.getElementById(nazwa)).parent().find(".menutabView")[0].id;
};
