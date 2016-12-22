/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function logout() {
    deleteCookie("session");
    location.reload();
}

function saveDoc() {
    var docData = $("#DocData").val();
    //post("operations.jsp", {session:getCookie("session"), type:"save", data:docData});
    $.post("operations.jsp", {session:getCookie("session"), type:"save", data:docData}, 
    function(data){
       $( "#result" ).empty().append(data);
       $('#result').hide().fadeIn('fast').delay(2000).fadeOut('fast');
    });
}

function lockDoc() {
    //post("operations.jsp", {session:getCookie("session"), type:"lock"});
    $.post("operations.jsp", {session:getCookie("session"), type:"lock"}, 
    function(data){
       $( "#result" ).empty().append(data);
       $('#result').hide().fadeIn('fast').delay(2000).fadeOut('fast');
       $.post("operations.jsp", {session:getCookie("session"), type:"docowner"},
       function(data2) {
           $("#docOwner").text("Lock owner: " + data2.trim());
       });
    });
}

function unlockDoc() {
    //post("operations.jsp", {session:getCookie("session"), type:"unlock"});
    $.post("operations.jsp", {session:getCookie("session"), type:"unlock"}, 
    function(data){
       $("#result").empty().append(data);
       $("#result").hide().fadeIn('fast').delay(2000).fadeOut('fast');
       $.post("operations.jsp", {session:getCookie("session"), type:"docowner"},
       function(data2) {
           $("#docOwner").text("Lock owner: " + data2.trim());
       });
    });
}

function reloadDoc() {
    location.reload(true);
}

function deleteCookie(name) {
    document.cookie = name + '=;expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) === ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) === 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}
function post(path, params, method) {
    method = method || "post"; // Set method to post by default if not specified.

    // The rest of this code assumes you are not using a library.
    // It can be made less wordy if you use one.
    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);

    for(var key in params) {
        if(params.hasOwnProperty(key)) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", params[key]);

            form.appendChild(hiddenField);
         }
    }

    document.body.appendChild(form);
    form.submit();
}

