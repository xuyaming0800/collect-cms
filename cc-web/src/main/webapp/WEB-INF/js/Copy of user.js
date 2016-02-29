define(['jquery','bootstrap'],function ($,bts) {
	var option={
			ownerId:null,
			collectClassParentId:null,
			collectClassId:null,
			operateType:null
	};
	/**
	 * 新增HTML
	 */
	function addHtml() {
		option.ownerId=$("#ownerId").val();
    	option.collectClassParentId=$("#collectClassParentId").val();
    	option.collectClassId=$("#collectClassId").val()
    	option.operateType="addHtml"
		showModal(option);
    	$("#selectModal").modal("hide");
	}
	/**
	 * 更新HTML
	 */
	function updateHtml(object) {
		//object.
		option.ownerId= object.attr("ownerId");
    	option.collectClassParentId=object.attr("collectClassParentId");
    	option.collectClassId=object.attr("collectClassId");
    	option.operateType="updateHtml"
		showModal(option);
	}
	 //回调  
	  function save(saveJson){
	    //调用后台方法
	    var jsonData = {"ownerId":option.ownerId,"collectClassParentId":option.collectClassParentId,"collectClassId":option.collectClassId,"versionNo":"","datas":saveJson}
		$.ajax({ 
	        type:"POST",
	        url:contextPath+"/htmlPage/saveDetailConfigs.html",
	        dataType:"json",
	        data:JSON.stringify(jsonData),
	        contentType : "application/json;charset=utf-8",
	        error:function(data) {
	        	alert(data.desc);
	        },
	        success:function(data){ 
	        	if(data.success == true) {
	        		alert(data.desc);
//	        		$("#versionNo").val(data.info.versionNo);
	        	}else  {
	        		alert(data.desc);
	        	}
	        }
		});
	  }
	  //回调提交
	  function submit(saveJson,saveHtml){
		 var formObj =$(saveHtml.get(0));
		 var ownerId = $("<input type='hidden' id='ownerId' name='ownerId'  value="+option.ownerId+" />");
		 var collectClassParentId = $("<input type='hidden' id='collectClassParentId' name='collectClassParentId'  value="+option.collectClassParentId+" />");
		 var collectClassId = $("<input type='hidden' id='collectClassId' name='collectClassId'  value="+option.collectClassId+" />");
		 formObj.append(ownerId);
		 formObj.append(collectClassParentId);
		 formObj.append(collectClassId);
	    var jsText = $("#htmlJsContent").text();
	    var cssText = $("#htmlCssContent").text();
	    var jsonData = {"ownerId":option.ownerId,"collectClassParentId":option.collectClassParentId,"collectClassId":option.collectClassId,"versionNo":"","htmlText":saveHtml.get(0).outerHTML,"cssText":cssText,"jsText":jsText,"datas":saveJson}
		$.ajax({ 
	        type:"POST",
	        url:contextPath+"/htmlPage/saveHtmlPageInfo.html",
	        dataType:"json",
	        data:JSON.stringify(jsonData),
	        contentType : "application/json;charset=utf-8",
	        error:function(data) {
	        	alert(data.desc);
	        },
	        success:function(data){ 
	        	if(data.success == true) {
	        		alert(data.desc);
	        		$("#updateForm").modal("hide");
	        		//$("#queryHtmlList").click();
	        		queryHtmlList();
	        	}else  {
	        		alert(data.desc);
	        	}
	        }
		});
	  } 
	  /**
		 * 打开模态 表单编辑窗口
		 */
		function showModal (param) {
			if(param.ownerId != "" && param.ownerId != null && param.collectClassParentId != "" && param.collectClassParentId != null  && param.collectClassId != "" && param.collectClassId != null) {
				$("#bulidfrom").empty();
				$("#htmlJsContent").empty();
				$("#htmlCssContent").empty();
				$("#updateForm").modal("show");
				 var _option={
	      				    initJson:[],
	      				    saveMethod:save,
	      				    submitMethod:submit
		      	 };
				 if(param.operateType == "addHtml"){
					 require(['root/bulider'], function (bulider){
						 bulider.init(_option);
					});  
				 }
				 else if(param.operateType == "updateHtml") {
					  var jsonData = {"ownerId":param.ownerId,"collectClassParentId":param.collectClassParentId,"collectClassId":param.collectClassId};
					 //此处JSON自行获取
					  $.ajax({
						    type:"POST",
					        url:contextPath+"/htmlPage/queryHtmlPageInfo.html",
					        dataType:"json",
					        data:JSON.stringify(jsonData),
					        contentType : "application/json;charset=utf-8",
					        error:function(data) {
					        	alert(data.desc);
					        },
						   success: function(data){
							   if(data.success == true) {
								    var initData = data.info.datas;
					        		_option.initJson=initData,
					        		 require(['root/bulider'], function (bulider){
					        			 bulider.init(_option);
									}); 
					        		$("#htmlJsContent").text(data.info.jsText);
									$("#htmlCssContent").text(data.info.cssText);
					        	}else  {
					        		alert(data.desc);
					        		$("#updateForm").modal("hide");
					        	}
							 
						   }
					 });
				  }
			}else {
				alert("请先输入必要属性参数");
			}
			
		}
	return {
		init: function(option){
			require(['root/bulider'], function (bulider){
				/**bind按钮
				 * addHtmlNextButton
				 */
//				$("button#addHtmlButton").click(function(){
//					addHtml();
//				});
				$("button#addHtmlNextButton").click(function(){
					addHtml();
				});
			});
		},
		bindUpdate:function(object){
			object.click(function(){
				updateHtml(object);
			})
		}
	}
});
