<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<script src="${resPrefixUrl }/js/app.v2.js"></script> <!-- Bootstrap --> <!-- App -->
<script src="${resPrefixUrl }/js/charts/flot/jquery.flot.min.js" cache="false"></script>
<script src="${resPrefixUrl }/js/bootbox.min.js"></script>
<script src="${resPrefixUrl }/js/charts/flot/demo.js" cache="false"></script>
<script src="${resPrefixUrl }/bower_components/bootstrapvalidator/dist/js/bootstrapValidator.min.js"></script>
<script src="${resPrefixUrl }/js/yunhuni.js" ></script>
<script>
	$(function(){
		var node = "";
		var pathSplit = location.pathname.split("/");
		if(pathSplit.length > 2){
			node = pathSplit[2]
		}
		if(node=='statistics'){
			node=pathSplit[2]+'/'+pathSplit[3];
		}
		if(node != null && node != ""){
			$('.nav-router').each(function(){
				var nav = $(this).attr('data-router');
				if(nav==node){
					$(this).addClass('active')
				}
			});
		}
	})
	function showtoast(tips,url) {
		$('.tips-toast').hide().html('');
		$('.tips-toast').css('display','block').html(tips);
		setInterval(function(){hidetoast(url);},3000)
	}
	function hidetoast(url){
		$('.tips-toast').hide();
		if(url!=undefined && url!='' && url!='undefined'){
			window.location.href=url;
		}
	}
	function getFormJson(form) {
		var o = {};
		var a = $(form).serializeArray();
		$.each(a, function () {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [o[this.name]];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	}
</script>
