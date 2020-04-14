/*包含页面*/
$(function(){
    $.get("http://localhost:8020/web/header.html",function (data) {
        $("#header").html(data);
    });
    $.get("http://localhost:8020/web/foot.html",function (data) {
        $("#footer").html(data);
    });
});