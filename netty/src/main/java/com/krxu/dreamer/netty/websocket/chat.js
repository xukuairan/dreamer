var socket;
if (!window.WebSocket) {
    window.WebSocket = window.MozWebSocket;
}
if (window.WebSocket) {
    socket = new WebSocket("ws://127.0.0.1:7070/crt/10000/" + rndNum(5));
    socket.onmessage = function (event) {
        //解析字符串to json对象
        var obj = eval("(" + event.data + ")");
        var textContent = obj.content;
        $(".chatBox-content-demo").append("<div class=\"clearfloat\">" +
            "<div class=\"author-name\"><small class=\"chat-date\">2017-12-02 14:26:58</small> </div> " +
            "<div class=\"right\"> <div class=\"chat-message\"> " + textContent + " </div> " +
            "<div class=\"chat-avatars\"><img src=\"img/icon01.png\" alt=\"头像\" /></div> </div> </div>");
        //发送后清空输入框
        $(".div-textarea").html("");
        //聊天框默认最底部
        $(document).ready(function () {
            $("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
        });

    };
    socket.onopen = function (event) {
        var ta = document.getElementById('responseText').innerText;
        ta.value = "打开WebSoket 服务正常，浏览器支持WebSoket!" + "\r\n";

    };
    socket.onclose = function (event) {
        var ta = document.getElementById('responseText').innerText;
        ta.value = "";
        ta.value = "WebSocket 关闭" + "\r\n";
    };

} else {
    alert("您的浏览器不支持WebSocket协议！");
}

function send(message) {
    if (message == null || message == '') {
        alert("消息为空");
        return;
    }
    if (!window.WebSocket) {
        return;
    }
    if (socket.readyState == WebSocket.OPEN) {
        socket.send(message);
    } else {
        alert("WebSocket 连接没有建立成功！");
    }

}


screenFuc();

function screenFuc() {
    var topHeight = $(".chatBox-head").innerHeight();//聊天头部高度
    //屏幕小于768px时候,布局change
    var winWidth = $(window).innerWidth();
    if (winWidth <= 768) {
        var totalHeight = $(window).height(); //页面整体高度
        $(".chatBox-info").css("height", totalHeight - topHeight);
        var infoHeight = $(".chatBox-info").innerHeight();//聊天头部以下高度
        //中间内容高度
        $(".chatBox-content").css("height", infoHeight - 46);
        $(".chatBox-content-demo").css("height", infoHeight - 46);

        $(".chatBox-kuang").css("height", totalHeight - topHeight);
        $(".div-textarea").css("width", winWidth - 106);
    } else {
        $(".chatBox-info").css("height", 495);
        $(".chatBox-content").css("height", 448);
        $(".chatBox-content-demo").css("height", 448);

        $(".chatBox-kuang").css("height", 495);
        $(".div-textarea").css("width", 260);
    }
}

(window.onresize = function () {
    screenFuc();
})();


$(".chatBox-head-one").toggle();
$(".chatBox-head-two").toggle();
$(".chatBox-list").fadeToggle();
$(".chatBox-kuang").fadeToggle();

//传名字
$(".ChatInfoName").text("nameeeee");

//传头像
$(".ChatInfoHead>img").attr("src", "img/icon01.png");

//聊天框默认最底部
$(document).ready(function () {
    $("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
});


//发送信息
$("#chat-fasong").click(function () {
    var textContent = $(".div-textarea").html().replace(/[\n\r]/g, '<br>')

    if (textContent != "") {
        $(".chatBox-content-demo").append("<div class=\"clearfloat\">" +
            "<div class=\"author-name\"><small class=\"chat-date\">2017-12-02 14:26:58</small> </div> " +
            "<div class=\"right\"> <div class=\"chat-message\"> " + textContent + " </div> " +
            "<div class=\"chat-avatars\"><img src=\"img/icon01.png\" alt=\"头像\" /></div> </div> </div>");
        //发送后清空输入框
        $(".div-textarea").html("");
        //聊天框默认最底部
        $(document).ready(function () {
            $("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
        });
        send(textContent);
    }
});

//      发送表情
$("#chat-biaoqing").click(function () {
    $(".biaoqing-photo").toggle();
});
$(document).click(function () {
    $(".biaoqing-photo").css("display", "none");
});
$("#chat-biaoqing").click(function (event) {
    event.stopPropagation();//阻止事件
});

$(".emoji-picker-image").each(function () {
    $(this).click(function () {
        var bq = $(this).parent().html();
        console.log(bq)
        $(".chatBox-content-demo").append("<div class=\"clearfloat\">" +
            "<div class=\"author-name\"><small class=\"chat-date\">2017-12-02 14:26:58</small> </div> " +
            "<div class=\"right\"> <div class=\"chat-message\"> " + bq + " </div> " +
            "<div class=\"chat-avatars\"><img src=\"img/icon01.png\" alt=\"头像\" /></div> </div> </div>");
        //发送后关闭表情框
        $(".biaoqing-photo").toggle();
        //聊天框默认最底部
        $(document).ready(function () {
            $("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
        });
    })
});

//发送图片
function selectImg(pic) {
    if (!pic.files || !pic.files[0]) {
        return;
    }
    var reader = new FileReader();
    reader.onload = function (evt) {
        var images = evt.target.result;
        $(".chatBox-content-demo").append("<div class=\"clearfloat\">" +
            "<div class=\"author-name\"><small class=\"chat-date\">2017-12-02 14:26:58</small> </div> " +
            "<div class=\"right\"> <div class=\"chat-message\"><img src=" + images + "></div> " +
            "<div class=\"chat-avatars\"><img src=\"img/icon01.png\" alt=\"头像\" /></div> </div> </div>");
        //聊天框默认最底部
        $(document).ready(function () {
            $("#chatBox-content-demo").scrollTop($("#chatBox-content-demo")[0].scrollHeight);
        });
    };
    reader.readAsDataURL(pic.files[0]);
}

