/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var pokazewidencjevatRK = function() {
    var wiersz = document.activeElement.id;
    var numerwiersza = wiersz.split(":")[2];
    var rodzajwiersza = "formwpisdokument:dataList:"+numerwiersza+":idwiersza";
    var typwiersza = rj(rodzajwiersza).innerHTML;
    if (typwiersza === "0") {
        PF('dialogewidencjavatRK').show();
    }
};


