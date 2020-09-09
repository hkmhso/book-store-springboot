<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>登录</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- page style -->
  <style>

  </style>
  <!-- Bootstrap 3.3.7 -->
  <link rel="stylesheet" href="../css/bootstrap/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="../css/font-awesome/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="../css/Ionicons/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="../css/AdminLTE/AdminLTE.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="../css/iCheck/square/blue.css">
  <!-- Google Font -->
  <!-- <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic"> -->
</head>
<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
    <a><b>学子书城管理系统</b></a>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg">管理员登录</p>

    <form action="" method="post" id="fm-login">
      <div class="form-group has-feedback">
        <input type="text" class="form-control" placeholder="Name" name="uname" id="uname">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" placeholder="Password" name="upwd" id="upwd">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="row">
        <!-- /.col -->
        <div class="col-xs-4">
          <button type="button" class="btn btn-primary btn-block btn-flat" id="bt-login">登录</button>
        </div>
        <!-- /.col -->
      </div>
    </form>

    <!-- /.social-auth-links -->
    <a href="#">忘记密码？</a><br>
  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- jQuery 3 -->
<script src="../js/jquery/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="../js/bootstrap/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="../js/iCheck/icheck.min.js"></script>
<!-- page script -->
<script>
  $(function () {
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
    });
    $("#uname").blur(function(){
        var data = $("#uname").val();
        if (data == null || data == "") {
            alert("用户名不能为空！");
            return false;
        }
        $.ajax({
            type:"POST",
            url:"/BookStore/user/page/unameCheck.action",
            data:"uname="+data,
            beforeSend:function(XMLHttpRequest)
            {
                $("#showResult").text("正在查询");
            },
            success:function(msg)
            {
                if(msg ==="yes"){
                    alert("用户名正确");
                }else if(msg === 'no'){
                    alert("用户名不存在");
                }
            },
            error:function()
            {
                //错误处理
                alert("系统异常");
            }
        });
    });
    $('#bt-login').click(function(){
    	login();
    });
    //用户按下Enter键的监听事件
     $(document).keydown(function(event){
	  	if(event.which==13){
	  		login();
	  	}
    })
    //登录功能
    function login(){
    	//window.location.href =  "index.html";
        //读取用户的输入——表单序列化
        var inputData = $('#fm-login').serialize();
        // alert(inputData);
        //异步提交请求，进行验证
        $.ajax({
        	async: true,
            type: 'POST',
            url: '/BookStore/user/page/login.action',
            data: inputData,
            success: function(txt, msg, xhr){
            	// alert("*"+txt+"*");
                if(txt=='admin'){  //登录成功
                    var loginName = $('[name="uname"]').val();
                    console.log(loginName);
                    window.location.href = "/BookStore/user/page/showallbook.action?role=1";
                }else{ //登录失败
                   // $('#showResult').html('登录失败！');
                   // $("#showResult").css("color","red");
                   if(txt=='user'){
                  	 alert("你不是管理员");
                   }else{
                   	 alert("登录失败");
                   }
                }
            }
        });
    }
  });
 
</script>
</body>
</html>
