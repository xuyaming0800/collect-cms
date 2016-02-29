define(['jquery','bootstrap','jqueryui','bootstrappaginator'],function ($,bts,jqueryui,btsp) {
	var userApi;
	var project= {
	    queryHtmlList:function(){
	    	queryHtmlList();
	    },
		init: function(option,user){
			userApi=user;
			//客户名称绑定自动完成
			bindCustomAutocomplete("customId","customName","id","name");
			//项目负责人绑定自动完成
			bindUserAutocomplete("projectLeaderId","projectLeaderName","id","name");
			
			//打开新建项目模态窗口
			$("#addProjectBtn").click(function() {
				$("#projectId").val("");
		  		$("#projectName").val("");
		  		$("#appName").val("");
		  		$("#customId").val("");
		  		$("#customName").val("");
		  		$("#projectLeaderId").val("");
		  		$("#projectLeaderName").val("");
		  		//$("#projectType").val("");
		  		$("#projectDesc").val("");
		  		if($("#customName").prop("readonly") == true) {
		  			$("#customName").removeAttr("readOnly");
		  		}
		  		
				$("#addProjectModal").modal("show");
			});
			//保存新建项目信息
			$("#saveProjectBtn").click(function() {
				saveProject();
			});
			//查询项目
			$("#queryProjectList").click(function() {
				queryProjectList();
			});
			queryProjectList();
			//添加品类
			$("#addCollectClassBtn").click(function() {
				$("#addCollectClassDModal").modal("show");
				$("#collectClassId").val("");
				$("#collectClassName").val("");
				$("#collectClassParentId").val("");
//				$("#collectClassPay").val("0");
				$("#collectClassType").val(0);
				$("#collectCount").val("");
				$("#collectClassTaskType").val("0");
		  		$("#isRequired").val("");
				$("#showOrHide").hide();
				$("#showOrHideTaskType").show();
			});
			
			$("#saveClassBtn").click(function() {
				saveClass();
			});
			
			$("#addCollectClassChildBtn").click(function() {
				var collectClassParentId = $("#collectClassParentId").val();
				if(collectClassParentId != undefined && collectClassParentId != "") {
					$("#addCollectClassDModal").modal("show");
					$("#collectClassId").val("");
					$("#collectClassName").val("");
//					$("#collectClassPay").val("");
					$("#collectClassType").val(1);
					$("#collectCount").val("");
			  		$("#isRequired").val("");
			  		$("#showOrHideTaskType").hide();
					$("#showOrHide").show();				
				}else {
					alert("请先选择采集品类父类！");
				}
			});
			
			$("#collectClassModal").on("hide.bs.modal", function () {
				 $("#ownerId").val("");
				 $("#projectName").val("");
				 $("#customId").val("");
				 $("#collectClassParentId").val("");
				 $("#collectClassParentName").val("");
			})
		}
	};
	//绑定自动完成查询
	function bindUserAutocomplete(id,name,jsonId,jsonName) {
		$("#"+name+"").autocomplete({
		    source: function( request, response ) {
		    	$("#"+id+"").val("");
		        $.ajax({
		          type:"POST",
		          url:contextPath+"/common/getProjectLeaders.html",
		          dataType: "json",
		/*          url: getProjectLeaderUrl,
		          dataType: "jsonp",
		          jsonp: "jsoncallback",  */  
		          delay:500,
		          data: {
		            userName: request.term
		          },
		          success: function( data ) {
		        	  if(data!=null && data!=""){
							if(data.success){
								  response( $.map( data.info, function( item ) {
						              return {
						                label: item[jsonName] ,
						                value: item[jsonName] ,
						                hiddenId:item[jsonId]
						              }
						            }));
							}
		        	  }
		          }
		        });
		      },
		      minLength: 1,
		      select: function( event, ui ) {
		    	 $("#"+id+"").val(ui.item.hiddenId);
		      }
		      /**
		      open: function() {
		        $( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
		      },
		      close: function() {
		        $( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
		      } */
		});
	}
	
	function bindCustomAutocomplete(id,name,jsonId,jsonName) {
		$("#"+name+"").autocomplete({
		    source: function( request, response ) {
		    	$("#"+id+"").val("");
		        $.ajax({
		        type:"POST",
		          url:contextPath+"/common/getCustoms.html",
		          dataType: "json",
//		          url: getCustomUrl,
//		          dataType: "jsonp",
//		          jsonp: "jsoncallback",    
		          delay:500,
		          data: {
		        	  userName: request.term
		          },
		          success: function( data ) {
		        	  if(data!=null && data!=""){
							if(data.success){
								  response( $.map( data.info, function( item ) {
						              return {
						                label: item[jsonName] ,
						                value: item[jsonName] ,
						                hiddenId:item[jsonId]
						              }
						            }));
							}
		        	  }
		          }
		        });
		      },
		      minLength: 1,
		      select: function( event, ui ) {
		    	 $("#"+id+"").val(ui.item.hiddenId);
		      }
		      /**
		      open: function() {
		        $( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
		      },
		      close: function() {
		        $( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
		      } */
		});
	}
	
	//保存项目
	function saveProject() {
		var projectId = $("#projectId").val();
		var projectName = $("#projectName").val();
		var appName = $("#appName").val();
		var customId = $("#customId").val();
		var customName = $("#customName").val();
		var projectLeaderId = $("#projectLeaderId").val();
		var projectLeaderName = $("#projectLeaderName").val();
		//var projectType = $("#projectType").val();
		var projectDesc = $("#projectDesc").val();
		if(projectName == ""){
			alert("请输入项目名称！");
			return;
		}else if(appName == ""){
			alert("请输入应用名称！");
			return;
		}else if(customId == ""  || customName == "") {
			alert("请输入正确的客户名称！");
			return;
		}else if(projectLeaderId == ""   || projectLeaderName == "") {
			alert("请选择项目负责人！");
			return;
		}
		/* else if(projectType == "") {
			alert("请选择项目类型！");
			return;
		} 
		"projectType":projectType,
		*/
		var jsonData = {"id":projectId,"projectName":projectName,"appName":appName,"customId":customId,"projectLeaderId":projectLeaderId,"projectDesc":projectDesc}
		if(projectId != "") {
				$.ajax({ 
			        type:"POST",
			        url:contextPath+"/project/updateProjectInfo.html",
			        dataType:"json",
			        data:JSON.stringify(jsonData),
			        contentType : "application/json;charset=utf-8",
			        error:function(data) {
			        	alert("系统异常："+data.status);
			        },
			        success:function(data){ 
			        	if(data.success == true) {
			        		alert(data.desc);
			        		$("#addProjectModal").modal("hide");
			        		queryProjectList();
			        	}else  {
			        		alert(data.desc);
			        	}
			        }
				});
		}else {
			$.ajax({ 
		        type:"POST",
		        url:contextPath+"/project/saveProjectInfo.html",
		        dataType:"json",
		        data:JSON.stringify(jsonData),
		        contentType : "application/json;charset=utf-8",
		        error:function(data) {
		        	alert("系统异常："+data.status);
		        },
		        success:function(data){ 
		        	if(data.success == true) {
		        		alert(data.desc);
		        		$("#addProjectModal").modal("hide");
		        		queryProjectList();
		        	}else  {
		        		alert(data.desc);
		        	}
		        }
			});
		}
	}
		

	/**
	 * 查询
	 */
	function queryProjectList(page) {
		var flag = false;//是否初始化分页组件的标识 true为需要初始化
		if(page == undefined) {
			page = 1;
			flag = true;
		}
		var customName = $("#qCustomName").val();
	
		var projectName = $("#qProjectName").val();
		var jsonData = {"customName":customName,"projectName":projectName,"pageNo":page,"limit":10}
		$.ajax({ 
	        type:"POST",
	        url:contextPath+"/project/queryProjectInfos.html",
	        dataType:"json",
	        data:JSON.stringify(jsonData),
	        contentType : "application/json;charset=utf-8",
	        error:function(data) {
	        	alert("系统异常："+data.status);
	        },
	        success:function(data){ 
	        	
	        	if(data.success == true) {
	        		var htmlTable = $("#projectList").empty();
	        		var info = data.info.objectList;
					if(info.length >0 ) {
						$.each(info,function(i,n){
							var row = $("<tr ownerId="+n.id+" customId="+n.customId+" projectName="+n.projectName+"></tr>");
							//绑定双击事件
							bindDblclick(row,"projectDblclick");
														
							var td_1 = $("<td></td>");
							var td_2 = $("<td></td>");
							var td_3 = $("<td></td>");
							var td_4 = $("<td></td>");
							var td_5 = $("<td></td>");
							var td_6 = $("<td></td>");
							var td_7 = $("<td></td>");
							var td_8 = $("<td></td>");
							var td_9 = $("<td></td>");
							var viewHref = $("<a href='#'  projectDesc='"+n.projectDesc+"'>查看</a>");
							bindClick(viewHref,"viewDesc");
							var div= $("<div class='btn-group' role='group'></div>");
							var updateBtn = $("<button type='button'  projectId='"+n.id+"' projectName='"+n.projectName+"' appName='"+n.appName+"' customId='"+n.customId+"' customName='"+n.customName+"' projectLeaderId='"+n.projectLeaderId+"' projectLeaderName='"+n.projectLeaderName+"' projectType='"+n.projectType+"' projectDesc='"+n.projectDesc+"' class='btn btn-primary'>修改</button>");
							var activeBtn = $("<button type='button'  projectId='"+n.id+"' customId='"+n.customId+"'   class='btn btn-success'>启用</button>");
							var scrapBtn = $("<button type='button'  projectId='"+n.id+"' customId='"+n.customId+"'   class='btn btn-danger'>暂停</button>");
							var publishBtn = $("<button type='button'  projectId='"+n.id+"' customId='"+n.customId+"'   class='btn btn-success'>发布</button>");
							//绑定事件-start
							bindClick(updateBtn,"update");
							  //绑定事件-end
							div.append(updateBtn);
							div.append(activeBtn);
							div.append(scrapBtn);
							div.append(publishBtn);
						    td_1.append(i+1);
						    td_2.append(n.projectName);
						    if(n.projectType == 0) {
						    	 td_3.append("简单项目");
						    }else if(n.projectType == 1){
						    	 td_3.append("特殊项目");
						    }
						   
						    td_4.append(n.projectLeaderName);
						    td_5.append(n.customName);
						    var href = $("<a href='#' ></a>");
						    if(n.status == 0) {
						    	 td_6.append("暂停");
						    	 activeBtn.removeClass("disabled");
						    	 scrapBtn.addClass("disabled");
						    	 publishBtn.addClass("disabled");
						    	 bindClick(activeBtn,"active");
						    }else if(n.status == 1){
						    	 td_6.append("正常");
						    	 activeBtn.addClass("disabled");
						    	 scrapBtn.removeClass("disabled");
						    	 publishBtn.removeClass("disabled");
						    	 bindClick(scrapBtn,"scrap");
						    	 bindClick(publishBtn,"publish");
						    }
						    td_7.append(new Date(n.createTime).Format("yyyy-MM-dd hh:mm:ss"));
						    td_8.append(viewHref);
						    td_9.append(div);
						    row.append(td_1);
						    row.append(td_2);
						    row.append(td_3);
						    row.append(td_4);
						    row.append(td_5);
						    row.append(td_6);
						    row.append(td_7);
						    row.append(td_8);
						    row.append(td_9);
						    htmlTable.append(row);
						});
					}else {
						var row = $("<tr></tr>");
						var td = $("<td colspan='9'>未查询到数据</td>");
						row.append(td)
						htmlTable.append(row);
					}
					//当page为undefined 为第一次条件查询 初始化分页组件
					if(flag) {
						options.onPageClicked=function(event, originalEvent, type, page){
							queryProjectList(page);
				        }
						initPage(options,"projectPage",data.info.totalCount,data.info.limit);
					}
	        	}else  {
	        		alert(data.desc);
	        	}
	        }
		});
	}
	
	//更新项目
  	function updateProjectInfo(obj) {
  		$("#addProjectModal").modal("show");
  		var projectId = obj.attr("projectId");
  		var projectName = obj.attr("projectName");
  		var appName = obj.attr("appName");
  		var customId = obj.attr("customId");
  		var customName = obj.attr("customName");
  		var projectLeaderId = obj.attr("projectLeaderId");
  		var projectLeaderName = obj.attr("projectLeaderName");
  		//var projectType = obj.attr("projectType");
  		var projectDesc = obj.attr("projectDesc");
  		if(projectDesc == "null"){
  			projectDesc = "";
  		}
  		$("#projectId").val(projectId);
  		$("#projectName").val(projectName);
  		$("#appName").val(appName);
  		$("#customId").val(customId);
  		$("#customName").val(customName);
  		$("#projectLeaderId").val(projectLeaderId);
  		$("#projectLeaderName").val(projectLeaderName);
  		//$("#projectType").val(projectType);
  		if($("#customName").prop("readonly") == false || $("#customName").prop("readonly") == undefined) {
  			$("#customName").attr("readonly",true);
  		}
  		$("#projectDesc").val(projectDesc);
  	}
	//更新项目状态
	function updateProjectInfoStatus(obj,status) {
		var projectId = obj.attr("projectId");
		var customId = obj.attr("customId");
		var jsonData = {"id":projectId,"customId":customId,"status":status}
		$.ajax({ 
	        type:"POST",
	        url:contextPath+"/project/updateProjectInfoStatus.html",
	        dataType:"json",
	        data:JSON.stringify(jsonData),
	        contentType : "application/json;charset=utf-8",
	        error:function(data) {
	        	alert("系统异常："+data.status);
	        },
	        success:function(data){ 
	        	if(data.success == true) {
	        		alert(data.desc);
	        		queryProjectList();
	        	}else  {
	        		alert(data.desc);
	        	}
	        }
		});
	}
	
	//发布项目的H5页面
	function publishProject(obj) {
		var projectId = obj.attr("projectId");
		var customId = obj.attr("customId");
		var jsonData = {"ownerId":projectId,"customId":customId}
		$.ajax({ 
	        type:"POST",
	        url:contextPath+"/project/publishProject.html",
	        dataType:"json",
	        data:jsonData,
	        error:function(data) {
	        	alert("系统异常："+data.status);
	        },
	        success:function(data){ 
	        	if(data.success == true) {
	        		alert(data.desc);
	        	}else  {
	        		alert(data.desc);
	        	}
	        }
		});
	}
	
	//启用项目
	function activeProjectInfo(obj){
		if(confirm("确认启用该项目？")) {
			updateProjectInfoStatus(obj,"1");
		}
		
	}
	
	//发布项目
	function publishProjectInfo(obj){
		if(confirm("确认发布该项目？")) {
			publishProject(obj);
		}
		
	}
 	 //废弃项目
	function scrapProjectInfo(obj) {
		if(confirm("确认暂停该项目？")) {
			updateProjectInfoStatus(obj,"0");
		}
		
 	 }
	//绑定双击事件
	function bindDblclick(obj,type) {
		obj.dblclick(function(){
			if(type == "projectDblclick") {
				showCollectClassList(obj);
			}else if(type == "classDblclick") {
				showHtmlList(obj);
			}
		});
	}
	
	function showCollectClassList(obj) {
		$("#collectClassModal").modal("show");
		$("#ownerId").val(obj.attr("ownerId"));
		$("#projectName").val(obj.attr("projectName"));
		$("#customId").val(obj.attr("customId"));
		$("#collectClassChildList").empty();
		unSelectColor("projectList");
		obj.addClass("success")
		queryCollectClass();
	}
	
	function showHtmlList(obj) {
		$("#collectClassId").val(obj.attr("collectClassId"));
		//$("#collectClassPay").val(obj.attr("collectClassPay"));
		$("#collectClassName").val(obj.attr("collectClassName"));
		unSelectColor("collectClassChildList");
		obj.addClass("success")
		$("#htmlListModal").modal("show");
		queryHtmlList();
	}
	//绑定点击事件
	function bindClick(obj,type) {
		obj.click(function(){
			if(type == "update") {
				//modify
				updateProjectInfo(obj);
			}else if(type == "active") {
				//modify
				activeProjectInfo(obj);
			}else if(type == "publish") {
				//modify
				publishProjectInfo(obj);
			}else if(type == "scrap") {
				//modify
				scrapProjectInfo(obj);
			}else if(type == "updateClass") {
				//modify
				updateClass(obj);
			}else if(type == "deleteClass") {
				//modify
				deleteClass(obj);
			}else if(type == "entranceClass") {
				//modify
				updateClassEntranceStatus(obj);
			}else if(type == "rowClick") {
				//modify
				$("#collectClassParentId").val(obj.attr("collectClassParentId"));
				$("#collectClassParentName").val(obj.attr("collectClassParentName"));
				unSelectColor("collectClassList");
				obj.addClass("success")
				//查询子类品类
				queryCollectClassChild();
			}else if(type == "viewDesc") {
				viewProjectDesc(obj);
			}else if(type == "htmlActive") {
				//modify
				activeHtml(obj);
			}else if(type == "htmlScrap") {
				//modify
				scrapHtml(obj);
			}
		});
	}	
	
	function viewProjectDesc(obj) {
		$("#projectDescModal").modal("show");
		$("#vProjectDesc").text(obj.attr("projectDesc"));
	}
	
	function queryCollectClass(page) {
		var flag = false;//是否初始化分页组件的标识 true为需要初始化
		if(page == undefined) {
			page = 1;
			flag = true;
		}
		var ownerId = $("#ownerId").val();
		var customId = $("#customId").val();
		var jsonData = {"customId":customId,"collectClass":{"ownerId":ownerId},"pageNo":page,"limit":10};
		$.ajax({ 
	        type:"POST",
	        url:contextPath+"/project/queryCollectClass.html",
	        dataType:"json",
	        data:JSON.stringify(jsonData),
	        contentType : "application/json;charset=utf-8",
	        error:function(data) {
	        	alert("系统异常："+data.status);
	        },
	        success:function(data){ 
	        	
	        	if(data.success == true) {
	        		var htmlTable = $("#collectClassList").empty();
	        		var info = data.info.objectList;
					if(info.length >0 ) {
						$.each(info,function(i,n){
							var row = $("<tr collectClassParentId='"+n.id+"' collectClassParentName='"+n.collectClassName+"'></tr>");
							//绑定单击事件
							bindClick(row,"rowClick");
							var td_1 = $("<td></td>");
							var td_2 = $("<td></td>");
							var td_2_1 = $("<td></td>");
							var td_3 = $("<td></td>");
							var td_4 = $("<td></td>");
							var div= $("<div class='btn-group' role='group'></div>");
							var updateBtn = $("<button type='button'  collectClassId='"+n.id+"' collectClassName='"+n.collectClassName+"' collectClassType='"+n.collectClassType+"' class='btn btn-primary'>修改</button>");
							var deleteBtn = $("<button type='button'  collectClassId='"+n.id+"' collectClassType='"+n.collectClassType+"' class='btn btn-danger'>删除</button>");
							//绑定事件-start
							bindClick(updateBtn,"updateClass");
							bindClick(deleteBtn,"deleteClass");
							 //绑定事件-end
							div.append(updateBtn);
							div.append(deleteBtn);
						    td_1.append(i+1);
						    td_2.append(n.collectClassName);
						    if(n.taskType=="0"){
						    	td_2_1.append("被动");
						    }else{
						    	td_2_1.append("主动");
						    }
						    
						    if(n.status == 0) {
						    	td_3.append("废弃");
						    }else if(n.status == 1){
						    	td_3.append("正常");
						    }
						    td_4.append(div);
						    row.append(td_1);
						    row.append(td_2);
						    row.append(td_2_1);
						    row.append(td_3);
						    row.append(td_4);
						    htmlTable.append(row);
						});
					}else {
						var row = $("<tr></tr>");
						var td = $("<td colspan='4'>未查询到数据</td>");
						row.append(td)
						htmlTable.append(row);
					}
					//当page为undefined 为第一次条件查询 初始化分页组件
					if(flag) {
						options.onPageClicked=function(event, originalEvent, type, page){
							queryCollectClass(page);
				        }
						initPage(options,"collectClassPage",data.info.totalCount,data.info.limit);
					}
	        	}else  {
	        		alert(data.desc);
	        	}
	        }
		});
	}
	
	function saveClass() {
		var collectClassId = $("#collectClassId").val();
		var collectClassName = $("#collectClassName").val();
		if(collectClassName == ""){
			alert("请输入品类名称！");
			return;
		}
		var updateFlag=false;
		if(collectClassId != undefined && collectClassId != "" ){
			updateFlag=true;
		}
		var collectClassType = $("#collectClassType").val();
		var ownerId = $("#ownerId").val();
		var customId = $("#customId").val();
		var collectClassParentId = $("#collectClassParentId").val();
		var jsonData = {"customId":customId,"collectClass":{"id":collectClassId,"collectClassParentId":collectClassParentId,"collectClassName":collectClassName,"collectClassType":collectClassType,"ownerId":ownerId}};
		if(collectClassType == 1) {
			var collectClassPay = $("#collectClassPay").val();
			var reg = /^\d+(\.\d{1})?$/;     
	        var r = collectClassPay.match(reg);
	        if(r == null) {
	        	 $("#collectClassPay").val("");
	        	 alert("请输入正确的价格！");
	        	 return;
	        }
	        var collectCount = $("#collectCount").val();
	        reg = /^[1-9]\d*$/;     
	        r = collectCount.match(reg);
	        if(r == null) {
	        	 $("#collectCount").val("");
	        	 alert("请输入正确的采集次数！");
	        	 return;
	        }
	        var collectClassDistance = $("#collectClassDistance").val();
	/*        r = collectClassDistance.match(reg);
	        if(r == null) {
	        	 $("#collectClassDistance").val("");
	        	 alert("请输入正确的拍摄距离！");
	        	 return;
	        }*/
	  		var isRequired = $("#isRequired").val();
	  		if(isRequired == "") {
	  			 alert("请选择是否必须！");
	        	 return;
	  		}
			jsonData["collectClass"].collectClassPay = collectClassPay;
			jsonData["collectClass"].collectCount = collectCount;
			jsonData["collectClass"].collectClassDistance = collectClassDistance;
			jsonData["collectClass"].isRequired = isRequired;
		}else{
			if(!updateFlag){//新建
				var taskType=$("#collectClassTaskType").val();
				jsonData["collectClass"].taskType = taskType;
				
			}
		}
		if(updateFlag ) {//更新
			$.ajax({ 
		        type:"POST",
		        url:contextPath+"/project/updateCollectClass.html",
		        dataType:"json",
		        data:JSON.stringify(jsonData),
		        contentType : "application/json;charset=utf-8",
		        error:function(data) {
		        		alert("系统异常："+data.status);
		        },
		        success:function(data){ 
		        	if(data.success == true) {
		        		alert(data.desc);
		        		$("#addCollectClassDModal").modal("hide");
		        		if(collectClassType == 0) {
		        			queryCollectClass();
		        		}else if(collectClassType == 1) {
		        			queryCollectClassChild();
		        		}
		        	}else  {
		        		alert(data.desc);
		        	}
		        }
			});
		}else {//新增
			$.ajax({ 
		        type:"POST",
		        url:contextPath+"/project/saveCollectClass.html",
		        dataType:"json",
		        data:JSON.stringify(jsonData),
		        contentType : "application/json;charset=utf-8",
		        error:function(data) {
		        	alert("系统异常："+data.status);
		        },
		        success:function(data){ 
		        	if(data.success == true) {
		        		alert(data.desc);
		        		$("#addCollectClassDModal").modal("hide");
		        		if(collectClassType == 0) {
		        			queryCollectClass();
		        		}else if(collectClassType == 1) {
		        			queryCollectClassChild();
		        		}
		        	}else  {
		        		alert(data.desc);
		        	}
		        }
			});
		}
		
		
		
	}
	//更新品类
	function updateClass(obj){
		$("#addCollectClassDModal").modal("show");
		$("#showOrHide").hide();
		$("#showOrHideTaskType").hide();
		$("#collectClassId").val(obj.attr("collectClassId"));
		$("#collectClassName").val(obj.attr("collectClassName"));
		var collectClassType = obj.attr("collectClassType");
		$("#collectClassType").val(collectClassType);
		if(collectClassType == "1") {
			$("#showOrHide").show();
//			var collectClassPay = obj.attr("collectClassPay");
//			if(collectClassPay == "null") {
//				collectClassPay = "";
//			}
//			$("#collectClassPay").val(collectClassPay);
			$("#collectCount").val(obj.attr("collectCount"));
	  		$("#isRequired").val(obj.attr("isRequired"));
	  		
		}
		
		
	}
	//删除品类
	function deleteClass(obj) {
		if(confirm("确认删除该品类？")) {
			var collectClassId = obj.attr("collectClassId");
			var collectClassType = obj.attr("collectClassType");
			var customId = $("#customId").val();
			var jsonData = {"customId":customId,"collectClass":{"id":collectClassId,"status":"0"}};
			$.ajax({ 
		        type:"POST",
		        url:contextPath+"/project/updateCollectClassStatus.html",
		        dataType:"json",
		        data:JSON.stringify(jsonData),
		        contentType : "application/json;charset=utf-8",
		        error:function(data) {
		        	alert("系统异常："+data.status);
		        },
		        success:function(data){ 
		        	if(data.success == true) {
		        		alert(data.desc);
		        		if(collectClassType == 0) {
		        			queryCollectClass();
		        		}else if(collectClassType == 1) {
		        			queryCollectClassChild();
		        		}
		        	}else  {
		        		alert(data.desc);
		        	}
		        }
			});
		}
	}
	
	//更新品类入口状态
	function updateClassEntranceStatus(obj) {
		var collectClassId = obj.attr("collectClassId");
		var collectClassParentId = obj.attr("collectClassParentId");
		var collectClassType = obj.attr("collectClassType");
		var customId = $("#customId").val();
		var jsonData = {"customId":customId,"collectClass":{"id":collectClassId,"entranceStatus":"1","collectClassParentId":collectClassParentId}};
		$.ajax({ 
	        type:"POST",
	        url:contextPath+"/project/updateCollectClassEntranceStatus.html",
	        dataType:"json",
	        data:JSON.stringify(jsonData),
	        contentType : "application/json;charset=utf-8",
	        error:function(data) {
	        	alert("系统异常："+data.status);
	        },
	        success:function(data){ 
	        	if(data.success == true) {
	        		alert(data.desc);
	        		if(collectClassType == 0) {
	        			queryCollectClass();
	        		}else if(collectClassType == 1) {
	        			queryCollectClassChild();
	        		}
	        	}else  {
	        		alert(data.desc);
	        	}
	        }
		});
	}
	
	//查询子品类
	function queryCollectClassChild(page) {
		var flag = false;//是否初始化分页组件的标识 true为需要初始化
		if(page == undefined) {
			page = 1;
			flag = true;
		}
		var ownerId = $("#ownerId").val();
		var customId = $("#customId").val();
		var collectClassParentId = $("#collectClassParentId").val();
		var jsonData = {"customId":customId,"collectClass":{"ownerId":ownerId,"collectClassParentId":collectClassParentId},"pageNo":page,"limit":10};
		$.ajax({ 
	        type:"POST",
	        url:contextPath+"/project/queryCollectClass.html",
	        dataType:"json",
	        data:JSON.stringify(jsonData),
	        contentType : "application/json;charset=utf-8",
	        error:function(data) {
	        	alert("系统异常："+data.status);
	        },
	        success:function(data){ 
	        	
	        	if(data.success == true) {
	        		var htmlTable = $("#collectClassChildList").empty();
	        		var info = data.info.objectList;
					if(info.length >0 ) {
						$.each(info,function(i,n){
							var row = $("<tr collectClassId='"+n.id+"' collectClassPay='"+n.collectClassPay+"' collectClassName='"+n.collectClassName+"'></tr>");
							//绑定双击事件
							bindDblclick(row,"classDblclick");
							var td_1 = $("<td></td>");
							var td_2 = $("<td></td>");
							var td_3 = $("<td></td>");
							var td_4 = $("<td></td>");
							var td_5 = $("<td></td>");
							var td_6 = $("<td></td>");
							var td_7 = $("<td></td>");
							var div= $("<div class='btn-group' role='group'></div>");
							var updateBtn = $("<button type='button'  collectClassId='"+n.id+"' collectClassName='"+n.collectClassName+"' collectClassType='"+n.collectClassType+"' collectClassPay='"+n.collectClassPay+"' collectCount='"+n.collectCount+"' isRequired='"+n.isRequired+"'  class='btn btn-primary'>修改</button>");
							var deleteBtn = $("<button type='button'  collectClassId='"+n.id+"' collectClassType='"+n.collectClassType+"'   class='btn btn-danger'>删除</button>");
							var entranceBtn = $("<button type='button'  collectClassId='"+n.id+"' collectClassParentId='"+n.collectClassParentId+"' collectClassType='"+n.collectClassType+"'   class='btn btn-success'>设置为入口</button>");
							//绑定事件-start
							bindClick(updateBtn,"updateClass");
							bindClick(deleteBtn,"deleteClass");
							bindClick(entranceBtn,"entranceClass");
							 //绑定事件-end
							div.append(updateBtn);
							div.append(deleteBtn);
							div.append(entranceBtn);
						    td_1.append(i+1);
						    td_2.append(n.collectClassName);
						   /*  if(n.status == 0) {
						    	td_3.append("废弃");
						    }else if(n.status == 1){
						    	td_3.append("正常");
						    } */
						    if(n.taskType=="0"){
						    	td_3.append("被动");
						    }else{
						    	td_3.append("主动");
						    }
//						    td_3.append(n.collectClassPay);
						    td_4.append(n.collectCount);
						    if(n.isRequired == "0") {
						    	td_5.append("否");
						    }else if(n.isRequired == "1"){
						    	td_5.append("是");
						    }else {
						    	td_5.append("");
						    }
						    
						    if(n.entranceStatus == 0) {
						    	td_6.append("非入口");
						    }else if(n.entranceStatus == 1) {
						    	td_6.append("入口");
						    }
						    td_7.append(div);
						    row.append(td_1);
						    row.append(td_2);
						    row.append(td_3);
						    row.append(td_4);
						    row.append(td_5);
						    row.append(td_6);
						    row.append(td_7);
						    htmlTable.append(row);
						});
					}else {
						var row = $("<tr></tr>");
						var td = $("<td colspan='7'>未查询到数据</td>");
						row.append(td)
						htmlTable.append(row);
					}
					//当page为undefined 为第一次条件查询 初始化分页组件
					if(flag) {
						options.onPageClicked=function(event, originalEvent, type, page){
							queryCollectClassChild(page);
				        }
						initPage(options,"collectClassChildPage",data.info.totalCount,data.info.limit);
					}
	        	}else  {
	        		alert(data.desc);
	        	}
	        }
		});
	}

function unSelectColor(id) {
	$("#"+id+" >tr").each(function(i,n){
		$(this).removeClass("success")
	});
}


/**
 * 查询
 */
function queryHtmlList(page) {
	var flag = false;//是否初始化分页组件的标识 true为需要初始化
	if(page == undefined) {
		page = 1;
		flag = true;
	}
	var ownerId = $("#ownerId").val();
	var collectClassParentId = $("#collectClassParentId").val();
	var collectClassId = $("#collectClassId").val();
	var customId = $("#customId").val();
	if(ownerId == "" || collectClassParentId == "" || collectClassId == ""||customId=="") {
		alert("参数有误！");
		return;
	}
	var jsonData = {"ownerId":ownerId,"customId":customId,"collectClassParentId":collectClassParentId,"collectClassId":collectClassId,"pageNo":page,"limit":10}
	$.ajax({ 
        type:"POST",
        url:contextPath+"/htmlPage/queryHistoryHtmls.html",
        dataType:"json",
        data:JSON.stringify(jsonData),
        contentType : "application/json;charset=utf-8",
        error:function(data) {
        	alert("系统异常："+data.status);
        },
        success:function(data){ 
        	
        	if(data.success == true) {
        		var htmlTable = $("#htmlListId").empty();
        		var info = data.info.objectList;
				if(info.length >0 ) {
					$.each(info,function(i,n){
						//var row = $("<tr></tr>");
						var row = $("<tr class="+(n.status == 0 ? "danger" : "success")+"></tr>");
						var td_1 = $("<td></td>");
						var td_2 = $("<td></td>");
						var td_3 = $("<td></td>");
						var td_4 = $("<td></td>");
						var td_5 = $("<td></td>");
						var td_6 = $("<td></td>");
						var td_7 = $("<td></td>");
						var div= $("<div class='btn-group' role='group'></div>");
						var updateBtn = $("<button type='button'  ownerId="+n.ownerId+"  collectClassParentId="+n.collectClassParentId+"  collectClassId="+n.collectClassId+"  collectClassPay="+n.collectClassPay+"  collectClassName="+n.collectClassName+"  versionNo="+n.versionNo+" class='btn btn-primary'>修改</button>");
						
						div.append(updateBtn);
						var showInfo = "";
					    if(n.status == 0 ) {
					    	updateBtn.addClass("disabled");
					    	showInfo = "废弃";
					    	var activeBtn = $("<button type='button'  htmlId="+n.id+" ownerId="+n.ownerId+"  collectClassParentId="+n.collectClassParentId+"  collectClassId="+n.collectClassId+"   class='btn btn-success'>激活</button>");
					    	div.append(activeBtn);
					    	bindClick(activeBtn,"htmlActive");
						   
					    }else if(n.status == 1||n.status == 2 ){
					    	//绑定更新事件-start
//							require(['home/user'], function (user){
//						   	 user.bindUpdate(updateBtn);
//						    });
					    	userApi.bindUpdate(updateBtn);
							  //绑定事件-end
					    	showInfo = "正常";
					    	var scrapBtn = $("<button type='button'  htmlId="+n.id+" ownerId="+n.ownerId+"  collectClassParentId="+n.collectClassParentId+"  collectClassId="+n.collectClassId+"  class='btn btn-danger'>废弃</button>");
							//div.append(scrapBtn);
							 //bindClick(scrapBtn,"htmlScrap");
					    }
					    td_1.append(i+1);
					    td_2.append(n.ownerName);
					    td_3.append(n.collectClassParentName);
					    td_4.append(n.collectClassName);
					    td_5.append(n.versionNo);
					    td_6.append(n.createTimeFormat);
					    td_7.append(div);
					    row.append(td_1);
					    row.append(td_2);
					    row.append(td_3);
					    row.append(td_4);
					    row.append(td_5);
					    row.append(td_6);
					    row.append(td_7);
					    htmlTable.append(row);
					});
				}else {
					var row = $("<tr></tr>");
					var td = $("<td colspan='7'>未查询到数据</td>");
					row.append(td)
					htmlTable.append(row);
				}
				//当page为undefined 为第一次条件查询 初始化分页组件
				if(flag) {
					options.onPageClicked=function(event, originalEvent, type, page){
						queryHtmlList(page);
			        }
					initPage(options,"htmlListPage",data.info.totalCount,data.info.limit);
				}
        	}else  {
        		alert(data.desc);
        	}
        }
	});
}
//激活
function activeHtml(obj) {
	updateHtmlStatus(obj,1);
}
//废弃
function scrapHtml(obj) {
	updateHtmlStatus(obj,0);
}
//更新html页面状态
function updateHtmlStatus(obj,status) {
	var msg = "";
	if(status == 0) {
		msg ="确认是否废弃此版本？";
	}else if(status == 1) {
		msg ="确认是否激活此版本？";
	}
	if(confirm(msg)) {
		var ownerId = obj.attr("ownerId");
		var htmlId = obj.attr("htmlId");
		var customId = obj.attr("customId");
		var jsonData = {"ownerId":ownerId,"id":htmlId,"status":status,"customId":customId}
		$.ajax({ 
	        type:"POST",
	        url:contextPath+"/htmlPage/updateHtmlPageInfoStatus.html",
	        dataType:"json",
	        data:JSON.stringify(jsonData),
	        contentType : "application/json;charset=utf-8",
	        error:function(data) {
	        	alert("系统异常："+data.status);
	        },
	        success:function(data){ 
	        	if(data.success == true) {
	        		alert(data.desc);
	        		//刷新外层列表
	        		queryHtmlList();
	        		if($("#historyListModal").is(':visible')){
	        			//刷新历史版本列表
	        			viewHistoryHtmlList(obj);
	        		}
	        	}else  {
	        		alert(data.desc);
	        	}
	        }
		});
	}
}

//分页参数
var options = {
    	size:"large",
        bootstrapMajorVersion:3,
        currentPage:1,
        numberOfPages:5,
        onPageClicked:function(event, originalEvent, type, page){
        }
  }
function initPage(options,id,totalCount,limit) {
	//分页显示
	var pageElement = $("#"+id+"");
     options.totalPages=Math.floor(totalCount%limit==0?(totalCount/limit):(totalCount/limit+1));
    pageElement.bootstrapPaginator(options);
}	
return project;
});

//对Date的扩展，将 Date 转化为指定格式的String   
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，   
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
//例子：   
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18   
Date.prototype.Format = function(fmt) { // author: meizz
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};
