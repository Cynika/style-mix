if (sessionStorage.getItem("userName") == null) {
    modal('请<a href="editor.html">登陆</a>后享受本站功能！', 'loading.gif', 0);
    setTimeout("location.href='login.html';", 2000);
} else {
    if(sessionStorage.getItem("userName")!="admin"){
        $(".admin").addClass("hidden");
    }
    queryallPhoto();
    $(".photo").attr('src', getPath() + "/aligned_images/none.jpg"); //显示默认图片
}


function queryallPhoto() {
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
    var htmltext = $("#photo").render(photolist);
    $("#wall").html(htmltext);
}

function backimg(id) {
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
    $("#photo" + id).attr('src', getPath() + '/' + img + "?t=" + Math.random());
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