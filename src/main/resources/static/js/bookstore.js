var TT = EGOU = {
	checkLogin : function(){
		var _token=getCookie("TT_TOKEN");
		console.log("bookstore.js->>"+_token);
		if(_token==''){
			console.log("bookstore.js->>用户还没登录或退出了");
			return ;
		}
		console.log("bookstore.js->>用户登录了");
		$.ajax({
			url : "/user/token.action/" + _token,
			type : "GET",
			success : function(data){
				if(data.status == 200){
					var uname = data.data.uname;
					var html = uname + "，欢迎来到学子书城！<a id='my_logout_id' href=\"/user/logout.action\" class=\"link-logout\">[退出]</a><b>|</b>";
					$("#loginbar").html(html);
				}
			}
		});
	}
}
$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
	$(document).on('click', '.link-logout', function() {
		var href=$(this).attr("href");
		$.ajax({
			url:href,
			type:'GET',
			success:function(data){
				if(data.status==200){
					$("#loginbar").html('您好！欢迎来到学子书城！<a href="/chooseRole.html">[登录]</a>&nbsp;<a href="/user/regist.html">[免费注册]</a><b>|</b>');
				}
			},
			error:function(jqXHR,textStatus){
				console.log(jqXHR.status);
				console.log(textStatus);
			}
		});
		return false;
	});
});