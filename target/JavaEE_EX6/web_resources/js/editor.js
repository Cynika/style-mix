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

//校准脸部
function aligned(num) {
    modal('正在校准人脸...<br>请勿关闭网页', 'loading.gif', 0);
    var s;
    $.ajax({
        type: 'POST',   //请求方式
        url: getPath() + '/aligned',
        //请求的url地址
        dataType: 'json',   //返回格式为json
        async: true,
        data: {'who': num},    //参数值
        success: function (data) {
            //请求成功时处理
            if (data.back == 1) {
                modal('校准人脸完成<br>请查看预览图', 'ok.png', 0, true);
                var img = aimg(num);
                $("#img" + num).attr('src', getPath() + '/' + img + "?t=" + Math.random());
            }
            if (data.back == 0)
                modal('校准错误，请检查图片', 'warning.png', 0, true);
            if (data.back == -1)
                modal('校准错误，请检查参数', 'warning.png', 0, true);
            if (data.back == -2)
                modal('校准错误，图片上传没有完成', 'warning.png', 0, true);
        },
        error: function (data) {
            modal('网络错误', 'warning.png', 2);
        }
    });


}

//获得校准的图像
function aimg(num) {
    var img;
    $.ajax({
        type: 'POST',   //请求方式
        url: getPath() + '/aimg',
        //请求的url地址
        dataType: 'json',   //返回格式为json
        async: false,
        data: {'who': num},    //参数值
        success: function (data) {
            //请求成功时处理
            img = data.back;
        },
        error: function (data) {
            modal('网络错误', 'warning.png', 2);
        }
    });
    return img;
}

//获得生成的图像
function mimg(num) {
    var img;
    $.ajax({
        type: 'POST',   //请求方式
        url: getPath() + '/mimg',
        //请求的url地址
        dataType: 'json',   //返回格式为json
        async: false,
        success: function (data) {
            //请求成功时处理
            img = data.back;
        },
        error: function (data) {
            modal('网络错误', 'warning.png', 2);
        }
    });
    return img;
}

//特征码化脸部
function latent(num) {
    modal('正在特征化人脸...<br>预计十分钟完成，您可以暂时离开网页', 'loading.gif', 0);
    $.ajax({
        type: 'POST',   //请求方式
        url: getPath() + '/latent',
        //请求的url地址
        dataType: 'json',   //返回格式为json
        async: true,
        data: {'who': num},    //参数值
        success: function (data) {
            //请求成功时处理
            if (data.back == 1) {
                modal('抱歉，久等了<br>特征化人脸完成并存档', 'ok.png', 0, true);
                getstates();
            }
            if (data.back == 0)
                modal('特征化错误，服务器可能堵塞', 'warning.png', 0, true);
            if (data.back == -1)
                modal('特征化错误，请检查参数', 'warning.png', 0, true);
            if (data.back == -2)
                modal('特征化错误，人脸校准没有完成', 'warning.png', 0, true);
        },
        error: function (data) {
            //请求出错处理
            modal('网络错误', 'warning.png', 2);
        }
    });
}

function mix(mode) {
    modal('正在生成人脸...<br>请勿关闭网页', 'loading.gif', 0);
    $.ajax({
        type: 'POST',   //请求方式
        url: getPath() + '/mix',
        //请求的url地址
        dataType: 'json',   //返回格式为json
        async: true,
        data: {
            'mode': mode,
            'mixs': [$("#face").slider('getValue'),
                $("#age").slider('getValue'),
                $("#gender").slider('getValue'),
                $("#beauty").slider('getValue'),
                $("#smile").slider('getValue'),
                $("#angle").slider('getValue')]
        },    //参数值
        success: function (data) {
            //请求成功时处理
            if (data.back == 1) {

                modal('新的人脸已生成<br>请查看生成图', 'ok.png', 0, true);
                var img = mimg();
                $("#img3").attr('src', getPath() + '/' + img + "?t=" + Math.random());
                sharemodal();
            }
            if (data.back == 0)
                modal('生成错误，服务器可能堵塞', 'warning.png', 0, true);
            if (data.back == -1)
                modal('生成错误，请检查参数', 'warning.png', 0, true);
            if (data.back == -2)
                modal('生成错误，没有完成特征化所需人脸', 'warning.png', 0, true);
        },
        error: function (data) {
            //请求出错处理
            modal('网络错误', 'warning.png', 2);
        }
    });

}

//网页检查特征码化任务状态
function getstates() {
    sta1 = getstate(1);
    sta2 = getstate(2);
    if (sta1 == 2 || sta1 == 2) {
        modal('您有特征化委托还没完成，请耐心等待', 'loading.gif', 0);
        setTimeout(function () {
            location.reload();
        }, 10000);

    } else if (sta1 == 3) {
        modal('检测到您有相片①特征化存档<br>可以直接生成', 'ok.png', 2, true);
        $("#latent1").addClass("btn-secondary");
        $("#latent1").html("重新特征化");
        if (sta2 == 3 && sta1 != 3) {
            modal('检测到您有相片②特征化存档<br>可以直接混合生成', 'ok.png', 2, true);
            $("#latent2").addClass("btn-secondary");
            $("#latent2").html("重新特征化");
        }
        if (sta2 == 3 && sta1 == 3) {
            modal('检测到您有相片①②特征化存档<br>可以直接混合生成', 'ok.png', 2, true);
            $("#latent2").addClass("btn-secondary");
            $("#latent2").html("重新特征化");
        }
    }
};

//获得特征码化任务状态
function getstate(num) {
    var result;
    $.ajax({
        type: 'POST',   //请求方式
        url: getPath() + '/state',
        //请求的url地址
        dataType: 'json',   //返回格式为json
        async: false,
        data: {'who': num},    //参数值
        success: function (data) {
            //请求成功时处理
            result = data.back;
        },
        error: function (data) {
            //请求出错处理
            modal('网络错误', 'warning.png', 2);
        }
    });
    return result;
}

//上传图片
function fileUpload(num) {
    var option = {
        type: 'POST',
        url: getPath() + '/upload',
        dataType: 'json',
        data: {
            fileName: 'file',
            who: num
        },
        success: function (data) {
            if (data.back == 1) {
                modal('照片上传成功', 'ok.png', 0, true)
            } else {
                modal('上传失败，请检查照片大小和格式', 'warning.png', 0, true)
            }

        },
        error: function () {
            modal('网络错误', 'warning.png', 2);
        }

    };
    $("#form" + num).ajaxSubmit(option);
}

//	判断是否登录 转到登陆页
if (sessionStorage.getItem("userName") == null) {

    modal('请<a href="editor.html">登陆</a>后享受本站功能！', 'loading.gif', 0);
    setTimeout("location.href='login.html';", 2000);
} else {
    if(sessionStorage.getItem("userName")!="admin"){
        $(".admin").addClass("hidden");
    }
    $("#img1").attr('src', getPath() + "/aligned_images/none.jpg"); //显示默认图片
    $("#img2").attr('src', getPath() + "/aligned_images/none.jpg"); //显示默认图片
    $("#img3").attr('src', getPath() + "/aligned_images/none.jpg"); //显示默认图片

    //预加载滑动条
    goslider();
    getstates();
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

function sharePhoto() {
    var desc = $("#desc").val();
    $.ajax({
        type: 'POST',   //请求方式
        url: getPath() + '/sharePhoto',
        //请求的url地址
        dataType: 'json',   //返回格式为json
        async: false,
        data: {'desc': desc},
        success: function (data) {
            //请求成功时处理
            if (data.back == 1) {
                modal('照片发布成功<br>可以在照片墙查看', 'ok.png', 0, true)

            } else {
                modal('发布失败<br>请检查账号是否封禁', 'warning.png', 0, true)
            }
        },
        error: function (data) {
            //请求出错处理
            modal('网络错误', 'warning.png', 2);
        }
    });
}

function sharemodal() {
    str = '填入描述可以分享到照片墙';
    var input = '<input class="form-control" id="desc" type="text">';
    $("#message_text").html(str + '<br>' + input);
    var img = mimg();
    $("#message_img").attr('src', getPath() + '/' + img + "?t=" + Math.random());
    var s = '<button type="button" class="btn btn-default" data-dismiss="modal" onclick="sharePhoto()">分享</button>';
    var x = '<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>';

    $("#myModal_foot").html(s + x);
    $("#myModal").modal("show");
}


function goslider() {
    $("#face").slider({
        ticks: [0, 100, 200, 300, 400, 500, 600],
        ticks_labels: ['左方', '0.6', '0.3', '各占一半', '0.3', '0.6', '右方'],
        ticks_snap_bounds: 20
    });
    $("#age").slider({
        ticks: [0, 100, 200, 300, 400, 500, 600],
        ticks_labels: ['+30岁', '+20岁', '+10岁', '不变', '-10岁', '-20岁', '-30岁'],
        ticks_snap_bounds: 20
    });
    $("#gender").slider({
        ticks: [0, 100, 200, 300, 400, 500, 600],
        ticks_labels: ['纯女性', '+2', '+1', '不变', '+1', '+2', '纯男性'],
        ticks_snap_bounds: 20
    });
    $("#beauty").slider({
        ticks: [0, 100, 200, 300, 400, 500, 600],
        ticks_labels: ['减..kg', '减20kg', '减10kg', '不变', '加10kg', '加20kg', '加..kg'],
        ticks_snap_bounds: 20
    });
    $("#smile").slider({
        ticks: [0, 100, 200, 300, 400, 500, 600],
        ticks_labels: ['大笑', '+2', '+1', '不变', '+1', '+2', '难过'],
        ticks_snap_bounds: 20
    });
    $("#angle").slider({
        ticks: [0, 100, 200, 300, 400, 500, 600],
        ticks_labels: ['向左', '+2', '+1', '不变', '+1', '+2', '向右'],
        ticks_snap_bounds: 20
    });
}









