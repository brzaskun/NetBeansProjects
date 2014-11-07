"use strict";
var TabKeyDown;

(function ($) {
    var focusable = ":input, a[href]";
 
    TabKeyDown = function(event) {
        if (!MYAPP.hasOwnProperty('liczydloWcisnietychEnter')) {
            MYAPP.liczydloWcisnietychEnter = 0;
        } else {
            if (MYAPP.liczydloWcisnietychEnter > 2) {
                MYAPP.liczydloWcisnietychEnter = 0;
            }
        }
        //Get the element that registered the event
        var $target = $(event.target);
        var taregetId = event.target.id;
        var czyZawieraWn = taregetId.indexOf("kontown");
        var czyZawieraMa = taregetId.indexOf("kontoma");
        var enterdefault = taregetId.indexOf("enterdefault");
        var typwiersza = $(document.getElementById("wpisywaniefooter:typwiersza")).val();
        var wnlubma = $(document.getElementById("wpisywaniefooter:wnlubma")).val();
        var wierszid = $(document.getElementById("wpisywaniefooter:wierszid")).val();
        var ominjednoklikniecie = false;
        var i = "formwpisdokument:dataList:"+wierszid+":opis";
        var i_obj = document.getElementById(i);
        if (i_obj) {
            ominjednoklikniecie = true;
        }
        var toJestPoleKonta = false;
        if (czyZawieraWn > 0 || czyZawieraMa > 0 || enterdefault > 0) {
            toJestPoleKonta = true;
        }
        if ($(event.target).is("button") === false) {
            if (isTabKey(event) && toJestPoleKonta === false) {
                var isTabSuccessful = tab(true, event.shiftKey, $target);
                if (isTabSuccessful) {
                    event.preventDefault();
                    event.stopPropagation();
                    event.stopImmediatePropagation();
                    return false;
                }
            } else if (isTabKey(event) && toJestPoleKonta === true && typwiersza === "0" && wnlubma === "Wn") {
                var isTabSuccessful = tab(true, event.shiftKey, $target);
                if (isTabSuccessful) {
                    event.preventDefault();
                    event.stopPropagation();
                    event.stopImmediatePropagation();
                    return false;
                }
            } else if (isTabKey(event) && ominjednoklikniecie) {
                    var isTabSuccessful = tab(true, event.shiftKey, $target);
                    console.log("omijam klikniecie");
                    MYAPP.liczydloWcisnietychEnter = 3;
                    event.preventDefault();
                    event.stopPropagation();
                    event.stopImmediatePropagation();
                    ominjednoklikniecie = false;
                    return false;
            } else if (isTabKey(event) && toJestPoleKonta === true && MYAPP.liczydloWcisnietychEnter === 0) {
                    MYAPP.liczydloWcisnietychEnter = 1;
                    event.preventDefault();
                    event.stopPropagation();
                    event.stopImmediatePropagation();
                    return false;
            } else if (isTabKey(event) && toJestPoleKonta === true && MYAPP.liczydloWcisnietychEnter === 1) {
                    console.log("wierszMa");
                    MYAPP.liczydloWcisnietychEnter = 2;
                    $(document.getElementById("wpisywaniefooter:dodajPustyWierszNaKoncu")).click();
                    event.preventDefault();
                    event.stopPropagation();
                    event.stopImmediatePropagation();
                    return false;
            } else if (isTabKey(event) && toJestPoleKonta === true && MYAPP.liczydloWcisnietychEnter === 2) {
                    var isTabSuccessful = tab(true, event.shiftKey, $target);
                    console.log("dodaje nowy wiersz");
                    MYAPP.liczydloWcisnietychEnter = 3;
                    event.preventDefault();
                    event.stopPropagation();
                    event.stopImmediatePropagation();
                    return false;
            }
        }
        };
 
    function LoadKeyDown() {
        //on adds a handler to the object.  In this case it is the document itself
        $(document).on("keydown", TabKeyDown);
    }
 
    function isTabKey(event) {
 
        if (!event.altKey && !event.ctrlKey && !event.metaKey && event.keyCode === 13) {
            return true;
        }
 
        return false;
    }
 
    function tab(isTab, isReverse, $target) {
        if (isReverse) {
            return performTab($target, -1);
        } else {
            return performTab($target, +1);
        }
    }
 
    function performTab($from, offset) {
        var $next = findNext($from,offset);
        $next.focus();
        $next.select();
        return true;
    }
 
    function findNext($from, offset) {
        var $focusable = $(focusable).not(":disabled").not(":hidden").not("a[href]:empty");
        var currentIndex = $focusable.index($from);
        var nextIndex = (currentIndex + offset) % $focusable.length;
        var $next = $focusable.eq(nextIndex);
        return $next;
    }
 
    $(LoadKeyDown);
})(jQuery);

