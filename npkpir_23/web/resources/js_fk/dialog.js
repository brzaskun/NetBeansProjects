var ustawdialog = function(nazwa,rodzic) {
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

var ustawdialog = function(nazwa,rodzic, szerokosc, wysokosc) {
    $(document.getElementById(nazwa)).width(szerokosc).height(wysokosc);
    try {
        $(document.getElementById(nazwa)).position({
        my: "left top",
        at: "left+40px top",
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


