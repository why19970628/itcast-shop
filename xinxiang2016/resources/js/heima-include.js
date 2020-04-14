/*包含页面*/
$(function(){
    $.get("http://www.xinxiang2016.com:8848/xinxiang2016/header.html",function (data) {
        $("#header").html(data);
    });
    $.get("http://www.xinxiang2016.com:8848/xinxiang2016/foot.html",function (data) {
        $("#footer").html(data);
    });
});