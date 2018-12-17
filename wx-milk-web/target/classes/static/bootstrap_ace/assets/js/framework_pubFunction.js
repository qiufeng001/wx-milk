	var hashH = "";
	var bodyHeight = "";
	var showHeight = "";
	var showModalId = "";
	
	function setHash(contextPath) {
		bodyHeight = $("#iframe_body").height();
		showHeight = bodyHeight;
		//alert("bodyHeight:     "+bodyHeight);
		//alert(showModalId);
		if(showModalId != "") {
			//alert("showModalId    	"+$(showModalId).height());
			if($(showModalId).height() > bodyHeight) {
				showHeight = $(showModalId).height()+5;
			}
		}
		
		if(showHeight == hashH) {
			return;
		}
		
		hashH = showHeight;
		var url = contextPath+"/mvc/handleIfame.do#"+hashH;
		//alert(url);
		$("#iframeDiv").html('<iframe id="hiddenIframe" name="hiddenIframe" src="'+url+'" width="0" height="0" style="display:none;"></iframe>');
	}

function showModal(obj){
	var id = obj.attr("id");
	//alert(obj.prop("outerHTML"));
	//将html代码加载至iframe外的modal_area区域
	$(window.parent.document).find("#modal_area").append(obj.prop("outerHTML"));
	//赋值
	/*alert(option[i]);
	for(var i=0;i<option.length;i++){
		alert(option[i]);
	}*/
	//显示模态块
	$(window.parent.document).find("#"+id).modal({
		keyboard:false,
		backdrop:false,
		show:true
	});
	//背景至灰
	$(window.parent.document).find("body").addClass("modal-open").append("<div class=\"modal-backdrop fade in\"></div>");
};
function hideModal(){
	/*var id = $(obj).closest(".modal").attr("id");
	alert($("#"+id).prop("outerHTML"));
	$("#"+id).modal("hide");*/
	$("#modal_area").html("");
	$(document.body).removeClass("modal-open");
	$(document.body).find("div").remove(".modal-backdrop");
};

//异步加载页面
function ajaxLoadPage(areaId,url,param){
	$.post(url, param, function(result) {
		$(areaId).html(result).hide().fadeIn('fast');
	});
}
//异步加载页面
function ajaxMenuLoadPage(areaId,url){
	/*$.post(url, function(result) {
		$(areaId).html(result).hide().fadeIn('fast');
	});*/
	$("#loadMainContent").attr("src", url);
}
//自定义confirm控件
function kj_confirm(message){
	$( "#dialog-confirm p" ).text(message);
	$( "#dialog-confirm" ).removeClass('hide').dialog({
		resizable: false,
		modal: true,
		title: "提示",
		title_html: true,
		buttons: [
			{
				html: "确定",
				"class" : "btn btn-xs",
				click: function() {
					$( this ).dialog( "close" );
					//alert("点击确定");
					return "true";
				}
			}
			,
			{
				html: "取消",
				"class" : "btn btn-xs",
				click: function() {
					$( this ).dialog( "close" );
					//alert("点击取消");
					return "false";
				}
			}
		]
	});
}

//自定义alert控件
function kj_alert(message){
	//e.preventDefault();
	$( "#dialog-alert p" ).text(message);
	$( "#dialog-alert" ).removeClass('hide').dialog({
		resizable: false,
		modal: true,
		title: "提示",
		title_html: true,
		buttons: [
			{
				html: "确定",
				"class" : "btn btn-xs",
				click: function() {
					$( this ).dialog( "close" );
				}
			}
		]
	});
}





























