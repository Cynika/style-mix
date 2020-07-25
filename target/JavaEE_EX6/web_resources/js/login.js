//	判断是否登录 转到编辑页
if (sessionStorage.getItem("userName") != null) {
    $('#loginResult').addClass('alert-success');
    $('#userName').attr("disabled", true);
    $('#userPassword').attr("disabled", true);
    $('#loginResult').html(sessionStorage.getItem("userName") + '，您已登录！');
    modal('您已登录，正在为你跳转至<a href="editor.html">编辑</a>页面！', 'loading.gif', 0);
    setTimeout("location.href='editor.html';", 2000);
}


$(function () {
    var userName = $.cookie('userName');
    var userPassword = $.cookie('userPassword');
    console.log(userName)

    $('#userName').val(userName);
    $('#userPassword').val(userPassword);
    if (userName != null && userName != '' && userPassword != null && userPassword != '') {//选中保存秘密的复选框
        $("#rememberMe").attr('checked', true);
    }
});


$('#userName').focus(function () {
    $('#userPassword').removeClass('is-invalid');
    $('#tip').html('');
})

$('#userPassword').focus(function () {
    $('#userPassword').removeClass('is-invalid');
    $('#tip').html('');
});

$('#loginBtn').click(function () {
        var flag = true;
        var userName = $('#userName').val();
        var userPassword = $('#userPassword').val();
        if (userName == '' || userPassword == '') {
            $('#tip').css({'display': 'inline', 'margin-left': '40%'});
            $('#tip').html('账户或者密码不得为空！');
            flag = false;
        }

        if ($("#rememberMe").is(":checked")) {
            $.cookie("userName", userName, {expires: 7});
            $.cookie("userPassword", userPassword, {expires: 7});
        } else {
            $.cookie("userName", "", {expires: -1});
            $.cookie("userPassword", "", {expires: -1});
        }

        if (flag) {
            modal('登录中，请稍后', 'loading.png', 0);
            $.ajax({
                type: "post",
                url: getPath() + "/loginUser",
                async: false,
                dataType: "json",
                data: $('form').serialize(),
                success: function (data) {
                    if (data.flag) {
                        $('#userName').addClass('is-valid');
                        $('#userPassword').addClass('is-valid');
                        $('#loginResult').removeClass('alert-warning');
                        $('#loginResult').addClass('alert-success');
                        $('#loginResult').html('登录成功！');
                        modal('登录成功，正在为您跳转至<a href="editor.html">编辑</a>页面！', 'ok.png', 0);
                        setTimeout("location.href='editor.html';", 2000);

                        sessionStorage.setItem("userName", userName);

                    } else {
                        $('#userPassword').addClass('is-invalid');
                        $('#loginResult').addClass('alert-warning');
                        $('#loginResult').html('用户名或密码错误！');
                        modal('登录失败，用户名或密码错误！', 'warning.png', 2);

                    }
                },
                error: function () {
                    modal('网络错误！', 'warning.png', 2);

                }
            })
        }
    }
);

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

function modal(str, img, time) {
    $("#message_text").html(str);
    $("#message_img").attr('src', "web_resources/img/" + img + "?t=" + Math.random()); //显示图片
    $("#myModal").modal("show");
    if (time != 0) {
        time = time * 1000;
        setTimeout(" $(\"#myModal\").modal(\"hide\");", time);
    }
}

