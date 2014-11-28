function ustawDate(rok,mc){
    var dataWyst = document.getElementById("dodWiad:dataPole");
    var wart = dataWyst.value;
    if(mc!==10&&mc!==11&&mc!==12){
        mc = "0"+mc;
    }
    var re1 = /[0-3][0-9]/;
    var re2 = /[0-1][0-9]\S[0-3][0-9]/;
    var re3 = /[2][0][0-9][0-9]\S[0-1][0-9]\S[0-3][0-9]/;
     if (wart.match(re3)) {
            dataWyst.value = wart ;
        } else if (wart.match(re2)){
            dataWyst.value = rok + "-"+wart;
        } else if (wart.match(re1)){
            dataWyst.value = rok + "-" + mc + "-" + wart ;
        }
     var re = /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/;
     var testw = dataWyst.value;
     if (!testw.match(re)){
         dataWyst.value = "b\u0142ędna data";
     } else {
         var dataplatnosc = document.getElementById("dodWiad:tabelapkpir2:0:dataTPole");
         var datasprzedazy = document.getElementById("dodWiad:dataSPole");
         var rozliczony = document.getElementById("dodWiad:tabelapkpir2:0:rozliczony");
         dataplatnosc.value = dataWyst.value;
         datasprzedazy.value = dataWyst.value;
         $(rozliczony).attr('checked', true);
     }
   };
   
function ustawDateSprzedazy(rok,mc){
    var dataWyst = document.getElementById("dodWiad:dataSPole");
    var wart = dataWyst.value;
    if(mc!==10&&mc!==11&&mc!==12){
        mc = "0"+mc;
    }
    var re1 = /[0-3][0-9]/;
    var re2 = /[0-1][0-9]\S[0-3][0-9]/;
    var re3 = /[2][0][0-9][0-9]\S[0-1][0-9]\S[0-3][0-9]/;
     if (wart.match(re3)) {
            dataWyst.value = wart ;
        } else if (wart.match(re2)){
            dataWyst.value = rok + "-"+wart;
        } else if (wart.match(re1)){
            dataWyst.value = rok + "-" + mc + "-" + wart ;
        }
     var re = /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/;
     var testw = dataWyst.value;
     if (!testw.match(re)){
         dataWyst.value = "b\u0142ędna data";
     }
   };

function ustawDateFK(rok,mc, koncowkaadresu){
    try {
        var adres = "formwpisdokument:"+koncowkaadresu;
        var dataWyst = document.getElementById(adres);
        var wart = dataWyst.value;
        if(mc!==10&&mc!==11&&mc!==12){
            mc = "0"+mc;
        }
        //dzieki temu akceptuje wpis 0214
        if (wart.length === 4) {
            var nowa = wart.substring(0, 2)+"-"+wart.substring(2, 4);
            wart = nowa;
        }
        if (wart.length === 8) {
            var nowa = wart.substring(0, 4)+"-"+wart.substring(4, 6)+"-"+wart.substring(6, 8);
            wart = nowa;
        }
        var re1 = /[0-3][0-9]/;
        var re2 = /[0-1][0-9]\S[0-3][0-9]/;
        var re3 = /[2][0][0-9][0-9]\S[0-1][0-9]\S[0-3][0-9]/;
         if (wart.match(re3)) {
                dataWyst.value = wart ;
            } else if (wart.match(re2)){
                dataWyst.value = rok + "-"+wart;
            } else if (wart.match(re1)){
                dataWyst.value = rok + "-" + mc + "-" + wart ;
            }
         var re = /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/;
         var testw = dataWyst.value;
         if (!testw.match(re)){
             dataWyst.value = "b\u0142ędna data";
         } else {
    //         var dataplatnosc = document.getElementById("formwpisdokument:dataTPole");
    //         var datasprzedazy = document.getElementById("formwpisdokument:dataSPole");
    //         var rozliczony = document.getElementById("formwpisdokument:rozliczony");
    //         dataplatnosc.value = dataWyst.value;
    //         datasprzedazy.value = dataWyst.value;
    //         $(rozliczony).attr('checked', true);
         }
         if (koncowkaadresu === "data1DialogWpisywanie") {
            var adres = "formwpisdokument:data2DialogWpisywanie";
            var dataWyst1 = document.getElementById(adres);
            dataWyst1.value = dataWyst.value;
            adres = "formwpisdokument:data3DialogWpisywanie";
            dataWyst1 = document.getElementById(adres);
            dataWyst1.value = dataWyst.value;
            adres = "formwpisdokument:data4DialogWpisywanie";
            dataWyst1 = document.getElementById(adres);
            dataWyst1.value = dataWyst.value;
         }
          if (koncowkaadresu === "data2DialogWpisywanie") {
            var adres = "formwpisdokument:data3DialogWpisywanie";
            var dataWyst1 = document.getElementById(adres);
            dataWyst1.value = dataWyst.value;
         }
     } catch (e) {
         alert("Blad ustawdate.js ustawDateFK(rok,mc) "+e.toString());
     }
   };
   
   var weryfikujdatekursreczny = function () {
       var dataWyst = document.getElementById("formkursrecznie:dataKursReczny:0:datakurs");
       var re = /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/;
       var testw = dataWyst.value;
         if (!testw.match(re)){
             dataWyst.value = "b\u0142ędna data";
         }
   };
   
   var weryfikujnumertabeli = function () {
       var nrTabeli = document.getElementById("formkursrecznie:dataKursReczny:0:numertabeli");
       var re = /\d{3}[\/]\w{1}[\/]\w{3}[\/]\d{4}/;
       var testw = nrTabeli.value;
         if (!testw.match(re)){
             nrTabeli.value = "b\u0142ędny numer tabeli";
         }
   };
   
   function ustawDateFKRK(rok,mc, koncowkaadresu){
    try {
        var adres = "ewidencjavatRK:"+koncowkaadresu;
        var dataWyst = document.getElementById(adres);
        var wart = dataWyst.value;
        if(mc!==10&&mc!==11&&mc!==12){
            mc = "0"+mc;
        }
        var re1 = /[0-3][0-9]/;
        var re2 = /[0-1][0-9]\S[0-3][0-9]/;
        var re3 = /[2][0][0-9][0-9]\S[0-1][0-9]\S[0-3][0-9]/;
         if (wart.match(re3)) {
                dataWyst.value = wart ;
            } else if (wart.match(re2)){
                dataWyst.value = rok + "-"+wart;
            } else if (wart.match(re1)){
                dataWyst.value = rok + "-" + mc + "-" + wart ;
            }
         var re = /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/;
         var testw = dataWyst.value;
         if (!testw.match(re)){
             dataWyst.value = "b\u0142ędna data";
         } else {
    //         var dataplatnosc = document.getElementById("formwpisdokument:dataTPole");
    //         var datasprzedazy = document.getElementById("formwpisdokument:dataSPole");
    //         var rozliczony = document.getElementById("formwpisdokument:rozliczony");
    //         dataplatnosc.value = dataWyst.value;
    //         datasprzedazy.value = dataWyst.value;
    //         $(rozliczony).attr('checked', true);
         }
         if (koncowkaadresu === "data1DialogVAT") {
            var adres = "ewidencjavatRK:data2DialogVAT";
            var dataWyst1 = document.getElementById(adres);
            dataWyst1.value = dataWyst.value;
         }
     } catch (e) {
         alert("Blad ustawdate.js ustawDateFKRK(rok,mc) "+e.toString());
     }
   };
