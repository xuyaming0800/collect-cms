define(['jquery','bootstrap'],function ($,bts) {
	var option={
			ownerId:null,
			projectName:null,
			collectClassParentId:null,
			collectClassParentName:null,
			collectClassId:null,
			collectClassName:null,
			collectClassPay:null,
			 userMoney:null,
			customMoney:null,
			collectClassName:null,
			versionNo:null,
			operateType:null,
			customId:null
	};
	/**
	 * 新增HTML
	 */
	function addHtml() {
		option.ownerId=$("#ownerId").val();
		option.projectName=$("#projectName").val();
    	option.collectClassParentId=$("#collectClassParentId").val();
    	option.collectClassParentName=$("#collectClassParentName").val();
    	option.collectClassId=$("#collectClassId").val();
    	option.collectClassName=$("#collectClassPay").val();
    	option.collectClassName=$("#collectClassName").val();
    	option.customId=$("#customId").val();
    	option.operateType="addHtml";
    	initCollectClassPrice(option);
		//showModal(option);
    	//$("#selectModal").modal("hide");
	}
	/**
	 * 更新HTML
	 */
	function updateHtml(object) {
		//object.
		option.ownerId= object.attr("ownerId");
		option.projectName=$("#projectName").val();
    	option.collectClassParentId=object.attr("collectClassParentId");
    	option.collectClassParentName=$("#collectClassParentName").val();
    	option.collectClassId=object.attr("collectClassId");
    	option.collectClassPay=object.attr("collectClassPay");
    	option.collectClassName=object.attr("collectClassName");
    	option.versionNo=object.attr("versionNo");
    	option.customId=$("#customId").val();
    	option.operateType="updateHtml";
    	initCollectClassPrice(option);
		//showModal(option);
	}
	 //回调  
	  function save(saveJson){
	    //调用后台方法
	    var jsonData = {"customId":option.customId,"ownerId":option.ownerId,"collectClassParentId":option.collectClassParentId,"collectClassId":option.collectClassId,"datas":saveJson}
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
		 var projectName= $("<input type='hidden' id='projectName' name='projectName'  value="+option.projectName+" />");
		 var collectClassParentId = $("<input type='hidden' id='collectClassParentId' name='collectClassParentId'  value="+option.collectClassParentId+" />");
		 var collectClassParentName = $("<input type='hidden' id='collectClassParentName' name='collectClassParentName'  value="+option.collectClassParentName+" />");
		 var collectClassId = $("<input type='hidden' id='collectClassId' name='collectClassId'  value="+option.collectClassId+" />");
		 var collectClassName = $("<input type='hidden' id='collectClassName' name='collectClassName'  value="+option.collectClassName+" />");
		 var collectClassPay = $("<input type='hidden' id='money' name='money'  value="+option.collectClassPay+" />");
		 var userMoney = $("<input type='hidden' id='userMoney' name='userMoney'  value="+option.userMoney+" />");
		 var customMoney = $("<input type='hidden' id='customMoney' name='customMoney'  value="+option.customMoney+" />");
		 var level = $("<input type='hidden' id='level' name='level'  value=attrs."+option.collectClassName+" />");
		 var extraInfo="$extraInfo$";
		 formObj.append(ownerId);
		 formObj.append(projectName);
		 formObj.append(collectClassParentId);
		 formObj.append(collectClassParentName);
		 formObj.append(collectClassId);
		 formObj.append(collectClassName);
		 formObj.append(level);
		 formObj.append(extraInfo);
		 formObj.append(userMoney);
		 formObj.append(customMoney);
		 formObj.append(collectClassPay);
		 formObj.find(".hidden_title").remove();
		 var nav = formObj.find("#navbarFixedTop");
		 if(nav != undefined) {
			 nav.addClass("navbar-fixed-top");
		 }
	    var jsText = $("#htmlJsContent").text();
	    var cssText = $("#htmlCssContent").text();
	    var jsonData = {"customId":option.customId,"ownerId":option.ownerId,"collectClassParentId":option.collectClassParentId,"collectClassId":option.collectClassId,"versionNo":"","htmlText":saveHtml.get(0).outerHTML,"cssText":cssText,"jsText":jsText,"datas":saveJson}
		//alert(saveHtml.get(0).outerHTML+"--------------"+JSON.stringify(saveJson));
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
	        		projectApi.queryHtmlList();
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
				var modalOptions={
				  backdrop:'static'	
				};
				$("#updateForm").modal(modalOptions);
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
					  var jsonData = {"ownerId":param.ownerId,"collectClassParentId":param.collectClassParentId,"collectClassId":param.collectClassId,"versionNo":param.versionNo,"customId":param.customId};
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
		//初始化品类价格，如果价格未初始化则不能添加页面(跨域不支持 同步属性)
		function initCollectClassPrice(option) {
			$.ajax({ 
		        type:"POST",
		        url:contextPath+"/common/getCollectClassPrice.html",
		        dataType:"json",
//		        url:getCollectClassPriceUrl,
//		        dataType: "jsonp",
//		        jsonp: "jsoncallback",   
		        data:{ownerId:option.ownerId,collectClassParentId:option.collectClassParentId,collectClassId:option.collectClassId,customId:option.customId},
		        error:function(data) {
		        	//alert("系统异常："+data.status);
		        	option.userMoney=0;
	            	option.customMoney=0;
	        		showModal(option);
		        },
		        success:function(data){ 
		        	if(data.success == true) {
		        		var info = data.info;
		        		var userMoney = info.userMoneyMax;
		        		var customMoney = info.customMoneyMax;
		    
		        		//设置用户采集价格和客户扣款价格
		        		option.userMoney=userMoney;
		            	option.customMoney=customMoney;
		        		showModal(option);
		        	}else {
		        		 //alert(data.desc);
		        		alert("此采集品类价格不存在,请先初始化采集价格再创建页面！");
		        		option.userMoney=0;
		            	option.customMoney=0;
		        		showModal(option);
		        	}
		        }
			})
		}
	return {
		init: function(option){
			require(['root/bulider'], function (bulider){
				/**bind按钮
				 * addHtmlNextButton
				 */
				$("button#addHtmlBtn").click(function(){
					addHtml();
				});
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
