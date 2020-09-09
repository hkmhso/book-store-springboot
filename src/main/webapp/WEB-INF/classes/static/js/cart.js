$(function() {
	$('.imfor').each(function(){
		var price=parseFloat($(this).children('.pices').children('.pices_information').children('span').html());
		var amount=parseFloat($(this).children('.num').children('input').val());
		var amountPrice=price*amount;
		$(this).children('.totle').children('.totle_information').html(amountPrice.toFixed(2));
	});
	//全选
	$(".all").click(function() {
		amountadd();
		if($('.all>span').hasClass('normal')){
			$('.all>span').addClass('true').removeClass('normal');
			$('.all>span>img').attr('src','../img/cart/product_true.png');
			$(".Each>span").each(function() {
				$(this).addClass('true').removeClass('normal');
				$(this).children('img').attr('src','../img/cart/product_true.png');
			})
			totl();
			//添加下选中的图书，以便删除时后台需要
			$('.Each>span').children(":hidden").attr("name","book");
		}else{
			$('.all>span').addClass('normal').removeClass('true');
			$('.all>span>img').attr('src','../img/cart/product_normal.png');
			$('.Each>span').addClass('normal').removeClass('true');
			$('.Each>span>img').attr('src','../img/cart/product_normal.png');
			$(".susum").text(0.00);
			$(".susumOne").text(0.00);
			$('.total').text(0);
			$('.totalOne').text(0);
			//删除未选中的图书，不需要传给后台
			$('.Each>span').children(":hidden").attr("name","");
		}
	});
	//单选,复选框全部选上，则将全选复选框也选上，否则不选上
	$('.Each>span').click(function(){
		amountadd();
		if($(this).hasClass('normal')){
			$(this).addClass('true').removeClass('normal');
			$(this).children('img').attr('src','../img/cart/product_true.png');
			var amou=parseInt($('.total').text());
			amou++;
			$('.total').text(amou);
			$('.totalOne').text(amou);
			amountadd();
			//添加下选中的图书，以便删除时后台需要
			$(this).children(":hidden").attr("name","book");
		}else{
			$(this).addClass('normal').removeClass('true');
			$(this).children('img').attr('src','../img/cart/product_normal.png');
			var amou=parseInt($('.total').text());
			amou--;
			$('.total').text(amou);
			$('.totalOne').text(amou);
			var newamo=parseInt($('.susum').text())-parseInt($(this).parent().parent().siblings('.totle').children('.totle_information').text());
			$('.susum').text(newamo.toFixed(2));
			$('.susumOne').text(newamo.toFixed(2));
			//删除未选中的图书，不需要传给后台
			$(this).children(":hidden").attr("name","");
		}
		//定义一个标识符用来保存被选上的复选框的个数
		var checkedSize=0;
		//每点击一个复选框，就判断是否所有的复选框都被选上了
		$('.Each>span').each(function(){
			if($(this).hasClass('true')){
				checkedSize++;
			}
		})
		//如果所有的复选框全部被选上，将全选复选框勾选上
		if(checkedSize==$('.Each>span').length){
			$('.all>span').addClass('true').removeClass('normal');
			$('.all>span>img').attr('src','../img/cart/product_true.png');
		}
		else{
			$('.all>span').addClass('normal').removeClass('true');
			$('.all>span>img').attr('src','../img/cart/product_normal.png');
		}
	});
});
//合计
function totl() {
	var sum = 0.00;
	var amount=0;
	$(".totle_information").each(function() {
		sum += parseInt($(this).text());
		$(".susum").text(sum.toFixed(2));
		$(".susumOne").text(sum.toFixed(2));
		amount++;
		$('.total').text(amount);
		$('.totalOne').text(amount);
	})
};
// 单独
function amountadd(){
	var amo=0;
	$('.Each>span').each(function(){
		if($(this).hasClass('true')){
			amo+=parseInt($(this).parent().parent().siblings('.totle').children('.totle_information').text());
		}
	})
	$('.susum').text(amo.toFixed(2));
	$('.susumOne').text(amo.toFixed(2));
};
