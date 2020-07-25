if (sessionStorage.getItem("userName") == null) {
    modal('请<a href="editor.html">登陆</a>后享受本站功能！', 'loading.gif', 0);
    setTimeout("location.href='login.html';", 2000);
} else {
    getphotolist();
}

function modal(str, img, time, set_x) {
    $("#message_text").html(str);
    $("#message_img").attr('src', "web_resources/img/" + img + "?t=" + Math.random()); //显示图片
    $("#myModal_foot").html("");
    if (set_x == true) {
        var x = '<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>'
        $("#myModal_foot").html(x);
    }
    $("#myModal").modal("show");
    if (time != 0) {
        time = time * 1000;
        setTimeout(" $(\"#myModal\").modal(\"hide\");", time);
    }
}


function getphotolist() {
    var photolist;
    $.ajax({
        type: 'POST',   //请求方式
        url: getPath() + '/queryAllPhoto',
        //请求的url地址
        dataType: 'json',   //返回格式为json
        async: false, //参数值
        success: function (data) {
            //请求成功时处理
            photolist = JSON.parse(data.back);
        },
        error: function (data) {
            //请求出错处理
            modal('网络错误', 'warning.png', 2);
        }
    });
    var htmltext = $("#tmp2").render(photolist);
    $("#table2").html(htmltext);
}

function deletePhoto(id) {
    $.ajax({
        type: 'POST',   //请求方式
        url: getPath() + '/deletePhoto',
        //请求的url地址
        dataType: 'json',   //返回格式为json
        async: false, //参数值
        data: {'id': id},
        success: function (data) {
            //请求成功时处理
            if (data.back == 1) {
                modal('操作成功', 'ok.png', 2);
            }
        },
        error: function (data) {
            //请求出错处理
            modal('网络错误', 'warning.png', 2);
        }
    });
    getphotolist();
}

function toimg(id) {
    var img;
    $.ajax({
        type: 'POST',   //请求方式
        url: getPath() + '/pimg',
        //请求的url地址
        dataType: 'json',   //返回格式为json
        async: false,
        data: {'photo': id},    //参数值
        success: function (data) {
            //请求成功时处理
            img = data.back;
        },
        error: function (data) {
            modal('网络错误', 'warning.png', 2);
        }
    });
    window.location.href = getPath() + '/' + img;
}

function getPath() {
    var curPath = window.document.location.href;
    var pathName = window.document.location.pathname;
    var pos = curPath.indexOf(pathName);
    var localhostPath = curPath.substring(0, pos);
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    // console.log(pathName)
    // console.log(pathName.substr(1))
    // console.log(projectName)
    return localhostPath + projectName;
}

