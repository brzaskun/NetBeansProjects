
////function al(){
//    alert("document.getElementById(dodWiad:rodzajTrans).focus();");
//}

//
//function orientujsie(){
//    document.getElementById("dodWiad:rodzajTrans").focus();
//}

//function openwindow(){
//    alert("test");
//     my_window = window.open("", "mywindow1", "status=1,width=350,height=150");
//    my_window.document.write('<h1>Popup Test!</h1>');
//}

//function stopRKey(evt) { 
//  var evt = (evt) ? evt : ((event) ? event : null); 
//  var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null); 
//  if ((evt.keyCode == 13) && (node.type=="text"))  {return false;} 
//} 
//document.onkeypress = stopRKey; 

//nadaje odpowiednie kolory podświetlanym polom formularza
var kolorujpola = function() {
    $(':input').focus(
    function(){
        $(this).css({'background-color' : '#CEE4F0'});
    });

    $(':input').blur(
    function(){
        $(this).css({'background-color' : '#DFD8D1'});
    });
}

$(document).ready(kolorujpola());




 var wyloguj = function(){
        document.getElementById("templateform:wyloguj").click();
        if (document.getElementById("form:westIndex:panelwyboru")){
            window.location.href = "login.xhtml";
        } else {
            window.location.href = "../login.xhtml";
        }
    };

var aktywujsrodek = function(){
        document.getElementById("dodWiad:form:acForce1").focus();
};

var aktywujnetto = function(){
    dolaczwyliczenie();
    document.getElementById("dodWiad:opis").focus();
};

var zrobF = function (kwota) {
        var strX = kwota.replace(",", ".");
        strX = strX.replace(/\s/g, "");
        return parseFloat(strX);
 }
 var dolaczwyliczenie = function () {
     try {
        $(document.getElementById("dodWiad:tabelapkpir:1:kwotaPkpir_input")).on('keyup', function(event) {
            var roznica;
            if (event.which == 107) {
                var odjema = zrobF($(this).val());
                var odjemna = zrobF($(document.getElementById("dodWiad:tabelapkpir:0:kwotaPkpir_input")).val());
                roznica = odjemna - odjema;
                $(this).val(Math.abs(roznica));
                $(document.getElementById("dodWiad:tabelapkpir:1:kwotaPkpir_hinput")).val(Math.abs(roznica));
                event.preventDefault();
            }
         });
     } catch (ec) {
         alert("bladzik");
     }
     };


var aktywujopis = function (){
    var dokument = $('#dodWiad\\:rodzajTrans').val();
    if(dokument==='IN'){
        $('#dodWiad\\:tabelapkpir2\\:0\\:inwestycja').show();
//        $("#dodWiad\\:tabelapkpir2\\:0\\:inwestycja").bind('mouseover', function() {
//        alert($('#dodWiad\\:tabelapkpir2\\:0\\:inwestycja').val());
//            });
    $("#dodWiad\\:tabelapkpir2\\:0\\:inwestycja").bind('blur', function() {

        if( $('#dodWiad\\:tabelapkpir2\\:0\\:inwestycja').val()==="wybierz"){
            $('#dodWiad\\:tabelapkpir2\\:0\\:inwestycja').focus();
        }
        });
    } else {
        $('#dodWiad\\:tabelapkpir2\\:0\\:inwestycja').hide();
    }
    if(dokument==='LP'){
        $('#dodWiad\\:tabelapkpir2\\:0\\:dokumentprosty').attr('checked', true);
    }
    if(dokument==='PK'){
        $('#dodWiad\\:tabelapkpir2\\:0\\:dokumentprosty').attr('checked', true);
    }
    if(dokument==='ZUS'){
        $('#dodWiad\\:tabelapkpir2\\:0\\:dokumentprosty').attr('checked', true);
    }
    $('#dodWiad\\:opis').on('keydown',function(e){
        if(e.which=='120'){
            $('#dodWiad\\:dodajopis').click();
            $('#dodWiad\\:opis').focus();
        }
    }); 
   
    
    //dodaje nowa kolumne podczas wpisywania faktury. robi to po stwierdzeniu wcisniecia klawisza +. usuwa tez symbol + z ciagu opisu
    $('#dodWiad\\:opis_input').on('keyup',function(e){
        var kodklawisza = e.which;
        if(kodklawisza===107){
            $('#dodWiad\\:dodkol').click();
            var wartoscpola = $('#dodWiad\\:opis_input').val();
            $('#dodWiad\\:opis_input').val(wartoscpola.slice(0,-1));
            $(':kwotaPkpir_hinput').last().focus();
        }
         if(kodklawisza===109){
            $('#dodWiad\\:usunkol').click();
            var wartoscpola = $('#dodWiad\\:opis_input').val();
            $('#dodWiad\\:opis_input').val(wartoscpola.slice(0,-1));
            $(':kwotaPkpir_hinput').last().focus();
        }
    });
    $('#dodWiad\\:numerwlasny').focus();
};

//var pokazsrodki = function(){
//     if($('#dodWiad\\:rodzajTrans').val()==='OTS'){
//        $('#dodWiad\\:srodkiLista').show();
//    }
//}


//to jest konieczne do wyswietlania prawidlowych nazw w kalendarzu
 PrimeFaces.locales['pl'] = {
            closeText: 'Zamknij',
            prevText: 'Poprzedni',
            nextText: 'Nast\u0119pny',
            currentText: 'Bie\u017cący',
            monthNames: ['Stycze\u0144','Luty','Marzec','Kwiecie\u0144','Maj','Czerwiec','Lipiec','Sierpie\u0144','Wrzesie\u0144','Pa\u017adziernik','Listopad','Grudzie\u0144'],
            monthNamesShort: ['Sty','Lut','Mar','Kwi','Maj','Cze', 'Lip','Sie','Wrz','Pa\u017a','Lis','Gru'],
            dayNames: ['Niedziela','Poniedzia\u0142ek','Wtorek','\u015aroda','Czwartek','Pi\u0105tek','Sobota'],
            dayNamesShort: ['Nie','Pon','Wt','\u015ar','Czw','Pt','So'],
            dayNamesMin: ['N','P','W','\u015a','Cz','P','S'],
            weekHeader: 'Tydzie\u0144',
            firstDay: 1,
            isRTL: false,
            showMonthAfterYear: false,
            yearSuffix: 'r',
            timeOnlyTitle: 'Tylko czas',
            timeText: 'Czas',
            hourText: 'Godzina',
            minuteText: 'Minuta',
            secondText: 'Sekunda',
            currentText: 'Teraz',
            ampm: false,
            month: 'Miesi\u0105c',
            week: 'Tydzie\u0144',
            day: 'Dzie\u0144',
            allDayText: 'Ca\u0142y dzie\u0144'
 };
  
var oknoklientanowego = function(){
    window.open("kliencipopup.xhtml?redirect=true","",'status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var wydrukpkpir = function(kto){
    window.open('../wydruki/pkpir'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
    document.getElementById("form:pkpirwysylka").style.display='inline';
};

var wydrukinwestycja = function(kto){
    window.open('../wydruki/inwestycja'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
    //document.getElementById("form:pkpirwysylka").style.display='inline';
};

var wydrukzbiorcze = function(kto){
    window.open('../wydruki/pkpir'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
    document.getElementById("akordeon:form:pkpirwysylka").style.display='inline';
};

var wydrukstr = function(kto){
    window.open('../wydruki/srodki'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
    document.getElementById("formSTR:ewwysylka").style.display='inline';
    
};
// progress on transfers from the server to the client (downloads)
//function updateProgress (oEvent) {
//  if (oEvent.lengthComputable) {
//    var percentComplete = oEvent.loaded / oEvent.total;
//    // ...
//  } else {
//    // Unable to compute progress information since the total size is unknown
//  }
//}
 
//function transferComplete(evt) {
//  alert("The transfer is complete.");
//}
// 
//function transferFailed(evt) {
//  alert("An error occurred while transferring the file.");
//}
// 
//function transferCanceled(evt) {
//  alert("The transfer has been canceled by the user.");
//}

var wydrukvat7 = function(kto, index){
    window.open('../wydruki/VAT7Comb'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
    document.getElementById("formX:akordeon:dataList:"+index+":mailbutton").style.display='inline';
};

var wydrukvat7wysylka = function(kto){
    window.open('../wydruki/VAT7Comb'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
    document.getElementById("formX:dokumentyLista:0:mailbutton").style.display='inline';
};

var wydrukvatue = function(kto){
    window.open('../wydruki/VATUE'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

 var schowajmailbutton = function (index) {
     $(document.getElementById("formX:akordeon:dataList:"+index+":mailbutton")).attr('display','none');
 };

var wydrukobroty = function(kto){
    window.open('../wydruki/obroty'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
    document.getElementById("formX:obrotywysylka").style.display='inline';
};

var wydruksumavat = function(kto){
    window.open('../wydruki/vatsuma'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var wydrukpk = function(kto){
    window.open('../wydruki/pk'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var wydrukfaktura = function(kto){
    window.open('../wydruki/faktura'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};
var wydrukpit5 = function(kto){
    window.open('../wydruki/pit5'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
};

var wydrukewidencje = function(kto,nazwa){
    if(!nazwa.indexOf("sprzedaż", 0)){
    var nazwanowa = nazwa.substr(0, nazwa.length-1);
    } else {
        nazwanowa = nazwa;
    }
    window.open('../wydruki/vat-'+nazwanowa+'-'+kto+'.pdf','','status=no,toolbar=no,location=no,menubar=no,resizable,width=1008,height=690,scrollbars,left=100,top=50');
}

var focusdatavalidate = function(){
        document.getElementById("dodWiad:dataPole").focus();
        };

var number_format = function(number) {
// Formats a number with grouped thousands
//
// version: 906.1806
// discuss at: http://phpjs.org/functions/number_format
// +   original by: Jonas Raoni Soares Silva (http://www.jsfromhell.com)
// +   improved by: Kevin van Zonneveld (http://kevin.vanzonneveld.net)
// +     bugfix by: Michael White (http://getsprink.com)
// +     bugfix by: Benjamin Lupton
// +     bugfix by: Allan Jensen (http://www.winternet.no)
// +    revised by: Jonas Raoni Soares Silva (http://www.jsfromhell.com)
// +     bugfix by: Howard Yeend
// +    revised by: Luke Smith (http://lucassmith.name)
// +     bugfix by: Diogo Resende
// +     bugfix by: Rival
// +     input by: Kheang Hok Chin (http://www.distantia.ca/)
// +     improved by: davook
// +     improved by: Brett Zamir (http://brett-zamir.me)
// +     input by: Jay Klehr
// +     improved by: Brett Zamir (http://brett-zamir.me)
// +     input by: Amir Habibi (http://www.residence-mixte.com/)
// +     bugfix by: Brett Zamir (http://brett-zamir.me)
// *     example 1: number_format(1234.56);
// *     returns 1: '1,235'
// *     example 2: number_format(1234.56, 2, ',', ' ');
// *     returns 2: '1 234,56'
// *     example 3: number_format(1234.5678, 2, '.', '');
// *     returns 3: '1234.57'
// *     example 4: number_format(67, 2, ',', '.');
// *     returns 4: '67,00'
// *     example 5: number_format(1000);
// *     returns 5: '1,000'
// *     example 6: number_format(67.311, 2);
// *     returns 6: '67.31'
// *     example 7: number_format(1000.55, 1);
// *     returns 7: '1,000.6'
// *     example 8: number_format(67000, 5, ',', '.');
// *     returns 8: '67.000,00000'
// *     example 9: number_format(0.9, 0);
// *     returns 9: '1'
// *     example 10: number_format('1.20', 2);
// *     returns 10: '1.20'
// *     example 11: number_format('1.20', 4);
// *     returns 11: '1.2000'
// *     example 12: number_format('1.2000', 3);
// *     returns 12: '1.200'
var n = number, prec = 2, thousands_sep = " ", dec_point = ".";

var toFixedFix = function (n,prec) {
    var k = Math.pow(10,prec);
    return (Math.round(n*k)/k).toString();
};

n = !isFinite(+n) ? 0 : +n;
prec = !isFinite(+prec) ? 0 : Math.abs(prec);
var sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep;
var dec = (typeof dec_point === 'undefined') ? '.' : dec_point;

var s = (prec > 0) ? toFixedFix(n, prec) : toFixedFix(Math.round(n), prec); //fix for IE parseFloat(0.55).toFixed(0) = 0;

var abs = toFixedFix(Math.abs(n), prec);
var _, i;

if (abs >= 1000) {
    _ = abs.split(/\D/);
    i = _[0].length % 3 || 3;

    _[0] = s.slice(0,i + (n < 0)) +
          _[0].slice(i).replace(/(\d{3})/g, sep+'$1');
    s = _.join(dec);
} else {
    s = s.replace('.', dec);
}

var decPos = s.indexOf(dec);
if (prec >= 1 && decPos !== -1 && (s.length-decPos-1) < prec) {
    s += new Array(prec-(s.length-decPos-1)).join(0)+'0';
}
else if (prec >= 1 && decPos === -1) {
    s += dec+new Array(prec).join(0)+'0';
}
return s+" zł"; };


 function przekazdate(){
     document.getElementById("dodWiad:dataTPole").value = document.getElementById("dodWiad:dataPole").value;
 };
 
 function dodajPkpirX(){
     document.getElementById("dodWiad:netto1").value = number_format((document.getElementById("dodWiad:kwotaPkpir_hinput").value -0)+(document.getElementById("dodWiad:kwotaPkpirX_hinput").value -0));
      document.getElementById("dodWiad:sumbrutto").value = number_format(
            (document.getElementById("dodWiad:netto1_hinput").value -0)
            +(document.getElementById("dodWiad:vat1_hinput").value -0));
     document.getElementById("dodWiad:brutto1").value = number_format((document.getElementById("dodWiad:netto1_hinput").value -0)+(document.getElementById("dodWiad:vat1_hinput").value -0));
 };
 
 function dataprzyjecia(){
     if(document.getElementById("dodWiad:acForce1_hinput").value===null){
     } else {
         document.getElementById("dodWiad:tabelasrodkitrwaleOT:0:dataprz").value = document.getElementById("dodWiad:dataPole").value;
         document.getElementById("dodWiad:tabelasrodkitrwaleOT:0:nazwasrodka").focus();
     }
 };

     
     
     function ustawDateSrodekTrw(){
    var dataWyst = document.getElementById("dodWiad:tabelasrodkitrwaleOT:0:dataprz");
     var re = /^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$/;
     var testw = dataWyst.value;
     if (!testw.match(re)){
         dataWyst.value = "b\u0142ędna data";
     }
};


        
 

function generujoknowyboru(){
    $('#form\\:confirmDialog').bind('mouseover',function(){$('body').fadeIn(20);
});

}


//rodzial dotyczacy umiejscawiania pozycji z  faktury
function petlawywolujaca(lw,gr,co){
        //alert('petlawywolujaca');
        var dlugosc = lw.length-1;
        for(var j = 0; j < dlugosc; j++){
            //alert(lw[j]+","+gr[j]+","+co[j]);
            var lewy = parseInt(lw[j]);
            var cos = co[j];
            var gora = parseInt(gr[j]);
            $(document.getElementById(cos)).css({ position: "absolute",
            marginLeft: 0, marginTop: 0,
            top: gora, left: lewy });

  }};


var sprawdzczykopiowacklienta = function () {
    var zawartosc = $('#dodWiad\\:acForce_input').val();
    if(zawartosc==="+"){
        var text = $('#zobWiad\\:nazwa').html();
        $('#dodWiad\\:acForce_input').val($('#zobWiad\\:nazwa').html());
        $('#dodWiad\\:acForce_hinput').val($('#zobWiad\\:nazwa').html());
        $('#dodWiad\\:acForce_input').focus();
        $('#dodWiad\\:acForce_input').select();
        dialogklient.search(text);
        event.cancelBubble = true;
        event.stopPropagation();
        event.stopImmediatePropagation();
    }
};

var skopiujdanenowegoklienta = function () {
    dlg123.hide();
    $('#dodWiad\\:acForce_input').focus();
    var szukana = document.getElementById('formX:nazwaPole').value;
    dialogklient.search(szukana);

};


var przeniesKwotaDoNetto = function () {
        var i = 0;
        var kwotanetto = "start";
        var suma = 0.0;
        var vat = 0.0;
        var rodzajtransakcji = $('#dodWiad\\:rodzajTrans').val();
        while(kwotanetto){
            kwotanetto = $('#dodWiad\\:tabelapkpir\\:' + i + '\\:kwotaPkpir_hinput').val() - 0;
            if(!isNaN(kwotanetto)){
                suma += kwotanetto;
            }
            i++;
        }
        console.log(kwotanetto);
        $('#dodWiad\\:netto1_input').val(number_format(suma));
        $('#dodWiad\\:netto1_hinput').val(suma);
        if(rodzajtransakcji === "WDT" || rodzajtransakcji === "UPTK" || rodzajtransakcji === "EXP" ){
            vat = 0.0;
        } else {
            vat = suma * 0.23;
        }
        $('#dodWiad\\:vat1').val(number_format(vat));
        $('#dodWiad\\:brutto1').val(number_format(suma + vat));
        $('#dodWiad\\:sumbrutto').val(number_format(suma + vat));
    };


var selcolor = function () {
    
};

var varzmienkolorpola47deklvat = function () {
    $("#form\\:pole47").css({
            backgroundColor: '#ADD8E6'
    });
    $("#form\\:pole47").focus();
    $("#form\\:pole47").select();
};


 