/**
 * Created by 2019/01/02
 */
function getShortUrl() {
    var orgUrl = $("#url").val();
    orgUrl = $.trim(orgUrl);
    console.log(orgUrl);

    //验证地址格式
    var reg=/^(http|ftp|https):\/\/([^/:]+)(:\d*)?(.*)$/i;
    if(!reg.test(orgUrl)){
        alert("这网址不是以http://、https://开头，或者不是网址！");
        return;
    }

    $.ajax({
        //cache : true,
        type : "POST",
        url : "shortUrl/genShortUrl",
        contentType: "application/json;charset=UTF-8",
        //dataType: "json",
        data : JSON.stringify({"url":orgUrl}),
        async : false,
        error : function() {
            alert("系统异常");
            $('#sUrlDiv').removeClass("hidden")
            $('#sUrl').html("<span style='color: red'>系统异常</span>");
        },
        success : function(data) {
            $('#sUrlDiv').removeClass("hidden")
            if (data.code == 1) {
                $('#sUrl').html(data.data.shortUrl);
            }else{
                alert(data.message);
                $('#sUrl').html("<span style='color: red'>"+data.message+"</span>");
            }
        }
    });

}

// function getHost() {
//     var protocol = window.location.protocol;
//     var host = window.location.host;
//     return protocol+'//'+host+'/';
// }