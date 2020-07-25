//	判断是否登录 转到编辑页
if (sessionStorage.getItem("userName") != null) {
    $('#loginResult').addClass('alert-success');
    $('#userName').attr("disabled", true);
    $('#userPassword').attr("disabled", true);
    $('#loginResult').html(sessionStorage.getItem("userName") + '，您已登录！');
    modal('您已登录，正在为你跳转至<a href="editor.html">编辑</a>页面！', 'loading.gif', 0);
    setTimeout("location.href='editor.html';", 2000);
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

//检查名字是否已占用
var nameCheck = false;
$('#userName').blur(function(){
    var userName = $('#userName').val();
    if( userName!= ''){
        $.ajax({
            type: "post",
            url: getPath() + "/isRegistered",
            async: false,
            dataType:'json',
            data: {"name":userName},
            success: function (data) {
                if (data.flag) {
                    $('#userName').addClass('is-valid');
                    nameCheck = true;
                } else {
                    $('#userName').addClass('is-invalid');
                    $('#userNameFeedback').addClass('invalid-feedback');
                    $('#userNameFeedback').html('抱歉，该用户名已被注册！');
                }
            },
            error: function () {
                modal('网络错误！', 'warning.png', 2);
            }
        })
    }

});

$('#userPasswordCheck').focus(function () {
    $('#userPasswordCheck').removeClass('is-invalid');
    $('#passwordCheckFeedback').html('');
    $('#passwordCheckFeedback').removeClass('invalid-feedback');
});

$('#userName').focus(function () {
    $('#userName').removeClass('is-invalid');
    $('#userNameFeedback').html('');
    $('#userNameFeedback').removeClass('invalid-feedback');
});

$('#userPassword').focus(function () {
    $('#userPassword').removeClass('is-invalid');
});

$('#userEmail').focus(function () {
    $('#userEmail').removeClass('is-invalid');
});


function getPath() {
    var curPath = window.document.location.href;
    var pathName = window.document.location.pathname;
    var pos = curPath.indexOf(pathName);
    var localhostPath = curPath.substring(0, pos);
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return localhostPath + projectName;
}

//注册验证内容
$('#registerBtn').click(function () {
    var flag = true;
    var flag2 = true;
    var userName = $('#userName').val();
    var userPassword = $('#userPassword').val();
    var userPasswordCheck = $('#userPasswordCheck').val();
    var userEmail = $('#userEmail').val();
    if (userName == '') {
        $('#userName').addClass('is-invalid');
        flag = false;

    }

    if(userPassword == ''){
        $('#userPassword').addClass('is-invalid');
        flag = false;
    }

    if(userEmail == ''){
        $('#userEmail').addClass('is-invalid');
        flag = false;
    }

    if(userPasswordCheck==''){
        $('#userPasswordCheck').addClass('is-invalid');
        flag = false;
    }

    if(!flag){
        $('#registerFeedback').addClass('invalid-feedback');
        $('#registerFeedback').html('密码不能为空！');
    }

    if(userPassword != userPasswordCheck){
        console.log(userPassword)
        console.log(userPasswordCheck)
        $('#userPasswordCheck').addClass('is-invalid');
        $('#passwordCheckFeedback').html('两次输入不一致，请检查密码！');
        $('#passwordCheckFeedback').addClass('invalid-feedback');
        flag2 = false;
    }

    if (flag && nameCheck && flag2) {
        // console.log($('form').serialize());
        modal('注册中，请稍后', 'loading.png', 0);
        $.ajax({
            type: "post",
            url: getPath() + "/registerUser",
            async: false,
            dataType:'json',
            contentType: 'application/json',
            data:JSON.stringify({"id":0,"name":userName,"password":userPassword,"email":userEmail,"role":false}),
            success: function (data) {
                if (data.flag) {
                    $('#userName').addClass('is-valid');
                    $('#userPassword').addClass('is-valid');
                    $('#userEmail').addClass('is-valid');
                    $('#userPasswordCheck').addClass('is-valid');
                    $('#registerResult').addClass('alert alert-success');
                    $('#registerResult').html('注册成功！跳转至<a href="login.html">登录</a>界面');
                    modal('注册成功！跳转至<a href="login.html">登录</a>界面', 'ok.png', 2);
                    setTimeout("location.href='login.html';", 3000);

                } else {
                    modal('注册失败！，请检查填写内容', 'warning.png', 2);

                }
            },
            error: function () {
                modal('网络错误', 'warning.png', 2);
            }
        })
    }


});

