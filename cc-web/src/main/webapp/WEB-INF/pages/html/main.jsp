<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<!DOCTYPE html>
<html>
<head>
 <meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="description" content="">
<meta name="author" content="">

<title>html5列表</title>
<style type="text/css">
	.starter-template {
		  padding: 40px 15px;
		}
		#build {
			border:#DDD 1px solid;
			-moz-border-radius: 4px;      /* Gecko browsers */
			-webkit-border-radius: 4px;   /* Webkit browsers */
			border-radius:4px;            /* W3C syntax */
			padding:10px;
		}
		#dropzone{
		    min-height:300px;
			height:auto;
			padding:20px;
		}
		.hover {
			background:#EEE;
		}
		.drop-item{
			z-index:9999;
		}
		#htmlJsContent ,#htmlCssContent {
			width:100%;
			border:#DDD 1px solid;
			min-height:300px;
		}

</style>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/asset/lib/bootstrap/css/bootstrap.min.css">
<!-- 新 awesome 核心 CSS 文件 -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/asset/lib/awesome/css/font-awesome.min.css">
<script type="text/javascript">
var contextPath = "<%=request.getContextPath()%>";
</script>
<script data-main="<%=request.getContextPath()%>/js/asset/main.js" src="<%=request.getContextPath()%>/js/asset/lib/require/require.js"></script>
<script src="<%=request.getContextPath()%>/js/asset/lib/jquery/jquery-1.11.3.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-datetimepicker.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-paginator.min.js"></script>
</head>
<body>
	<div id="containerId" class="container-fluid">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">HTML列表</h3>
			</div>
			<div class="panel-body">
					<div class="panel panel-primary" style="margin-bottom:5px;">
						<div class="panel-body">
							<form class="form-horizontal" >
								   <div class="form-group">
									      <label class="col-sm-1 control-label">客户名称</label>
									      <div class="col-sm-2">
									         <div class="btn-group" >
									         	<button type="button" class="btn btn-default dropdown-toggle "  
													data-toggle="dropdown" aria-expanded="false">
													<span id="qUserName" >请选择客户名称</span><input id="qUserId"
														type="hidden" /><span class="caret"></span>
												</button>
												<ul id="getQUserIds" class="dropdown-menu" role="menu">
													<li><a href="#">请选择客户名称</a></li>
													<li class="divider"></li>
												</ul>
											</div>
									      </div>
										  <label class="col-sm-1 control-label">项目名称</label>
									      <div class="col-sm-2">
									         <div class="btn-group" >
									         	<button type="button" class="btn btn-default dropdown-toggle "  
													data-toggle="dropdown" aria-expanded="false">
													<span id="qOwnerName" >请选择项目名称</span><input id="qOwnerId"
														type="hidden" /><span class="caret"></span>
												</button>
												<ul id="getQOwnerIds" class="dropdown-menu" role="menu">
													<li><a href="#">请选择项目名称</a></li>
													<li class="divider"></li>
												</ul>
											</div>
									      </div>
									      <label class="col-sm-1 control-label">采集品类大类</label>
									      <div class="col-sm-2">
									         <div class="btn-group" >
									         	<button type="button" class="btn btn-default dropdown-toggle" 
													data-toggle="dropdown" aria-expanded="false" >
													<span id="qCollectClassParentName" >请选择采集品类大类</span><input id="qCollectClassParentId"
														type="hidden" /><span class="caret"></span>
												</button>
												<ul id="getqCollectClassParentIds" class="dropdown-menu" role="menu">
													<li><a href="#">请选择采集品类大类</a></li>
													<li class="divider"></li>
												</ul>
											</div>
									      </div>
									       <label class="col-sm-1 control-label">采集品类小类</label>
									      <div class="col-sm-2">
									         <div class="btn-group" >
									         	<button type="button" class="btn btn-default dropdown-toggle" 
													data-toggle="dropdown" aria-expanded="false">
													<span id="qCollectClassName" >请选择采集品类小类</span><input id="qCollectClassId"
														type="hidden" /><span class="caret"></span>
												</button>
												<ul id="getQCollectClassIds" class="dropdown-menu" role="menu">
													<li><a href="#">请选择采集品类小类</a></li>
													<li class="divider"></li>
												</ul>
											</div>
									      </div>
									      <div class="col-sm-12" style="text-align: right;">
									      	 <button type="button" class="btn btn-primary"  id="queryHtmlList" >查询</button>
									      </div>
								   </div>
							</form>
						</div>
					</div>
					<div class="btn-group" role="group" aria-label="button" style="margin-bottom:5px;">
			      	 	<button type="button" class="btn btn-primary"  id="addHtmlButton" >新建</button>
					</div>
					<table class="table table-striped">
						<thead>
							<tr></tr>
							<tr>
								 <th>序号</th>
						          <th>项目名称</th>
						          <th>采集品类大类</th>
						          <th>采集品类小类</th>
						          <th>版本号</th>
						          <th>操作</th>
							</tr>
						</thead>
						<tbody id="htmlListId">
				    	</tbody>
						<tfoot>
							<tr>
								<td colspan="8" style="text-align: right">
									<div>
										<ul id="bp-3-element-lg" class="pagination pagination-lg">
										</ul>
									</div> 
								</td>
							</tr>
						</tfoot>
				</table>
			</div>
		</div>
	</div>
	<!-- 表单编辑模态框（Modal） -->
	<div class="modal fade" id="updateForm" tabindex="-1" role="dialog" 
	   aria-labelledby="myModalLabel" aria-hidden="true">
	   <div class="modal-dialog" style="width:90%;">
	      <div class="modal-content">
	         <div class="modal-header">
	            <button type="button" class="close" 
	               data-dismiss="modal" aria-hidden="true">
	                  &times;
	            </button>
	            <h4 class="modal-title" id="updateFormLabel">
	               表单编辑器
	            </h4>
	         </div>
	         <div class="modal-body">
	         	<input type="hidden"  id="versionNo">
				<ul id="htmlTab" class="nav nav-tabs">
				   <li class="active"> <a href="#htmlContent" data-toggle="tab">HTML</a></li>
				   <li><a href="#htmlJs" data-toggle="tab">JS</a></li>
				   <li><a href="#htmlCss" data-toggle="tab">CSS</a></li>
				</ul>
				<div id="htmlTabContent" class="tab-content">
				   <div class="tab-pane fade in active" id="htmlContent">
				       	<div id="bulidfrom">
						</div>
				   </div>
				   <div class="tab-pane fade" id="htmlJs">
				   		<div class="container-fluid">
      							<div class="col-xs-12" >
								     <!-- <div style="margin-top:5px;"><button type="button" class="btn btn-primary">提交</button></div>-->
								     <h2>JS内容</h2>
								     <hr> 
								     <div id="htmlJsContent" contentEditable="true" ></div>
								  </div>
						 </div>
				   </div>
				    <div class="tab-pane fade" id="htmlCss">
				    	<div class="container-fluid">
      							<div class="col-xs-12" >
								    	<!-- <div style="margin-top:5px;"><button type="button" class="btn btn-primary">提交</button></div>-->
								    	<h2>CSS内容</h2>
								     	<hr> 
							      		<div id="htmlCssContent" contentEditable="true"></div>
							       </div>
						 </div>
				   </div>
				</div>
	         </div>
	         <div class="modal-footer">
	         	<button type="button" id="submitJson" class="btn btn-success">提交</button>
	            <button type="button" class="btn btn-default" 
	               data-dismiss="modal">关闭
	            </button>
	         </div>
	      </div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	
	<!-- 历史版本列表模态框（Modal） -->
	<div class="modal fade" id="historyListModal" tabindex="-1" role="dialog" 
	   aria-labelledby="myModalLabel" aria-hidden="true">
	   <div class="modal-dialog" style="width:80%;">
	      <div class="modal-content">
	         <div class="modal-header">
	            <button type="button" class="close" 
	               data-dismiss="modal" aria-hidden="true">
	                  &times;
	            </button>
	            <h4 class="modal-title" id="historyListLabel">
	               历史版本
	            </h4>
	         </div>
	         <div class="modal-body">
	         	<table class="table">
					<thead>
						<tr>
						 	 <th>序号</th>
					          <th>项目名称</th>
					          <th>采集品类大类</th>
					          <th>采集品类小类</th>
					          <th>版本号</th>
					          <th>状态</th>
					          <th>操作</th>
						</tr>
					</thead>
					<tbody id="historyList">
			    	</tbody>
			    	<tfoot>
						<tr>
							<td colspan="8" style="text-align: right">
								<div>
									<ul id="html-history-page" class="pagination pagination-lg">
									</ul>
								</div> 
							</td>
						</tr>
					</tfoot>
			    </table>
	         </div>
	         <div class="modal-footer">
	            <button type="button" class="btn btn-default" 
	               data-dismiss="modal">关闭
	            </button>
	         </div>
	      </div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	
	<!-- 新建html  选择用户，采集类别 模态框（Modal） -->
	<div class="modal fade" id="selectModal" tabindex="-1" role="dialog" 
	   aria-labelledby="myModalLabel" aria-hidden="true">
	   <div class="modal-dialog" style="width:70%;">
	      <div class="modal-content">
	         <div class="modal-header">
	            <button type="button" class="close" 
	               data-dismiss="modal" aria-hidden="true">
	                  &times;
	            </button>
	            <h4 class="modal-title" id="selectModalLabel">
	               选择用户和采集品类
	            </h4>
	         </div>
	         <div class="modal-body">
		         <form class="form-horizontal" >
					<div class="form-group">
			         	 <label class="col-sm-1 control-label">客户名称</label>
				         <div class="btn-group col-sm-2" >
				         	<button type="button" class="btn btn-default dropdown-toggle "  
								data-toggle="dropdown" aria-expanded="false">
								<span id="userName" >请选择客户名称</span><input id="userId"
									type="hidden" /><span class="caret"></span>
							</button>
							<ul id="getUserIds" class="dropdown-menu" role="menu">
								<li><a href="#">请选择客户名称</a></li>
								<li class="divider"></li>
							</ul>
						</div>
						
						<label class="col-sm-1 control-label">项目名称</label>
				         <div class="btn-group col-sm-2" >
				         	<button type="button" class="btn btn-default dropdown-toggle "  
								data-toggle="dropdown" aria-expanded="false">
								<span id="ownerName" >请选择项目名称</span><input id="ownerId"
									type="hidden" /><span class="caret"></span>
							</button>
							<ul id="getOwnerIds" class="dropdown-menu" role="menu">
								<li><a href="#">请选择项目名称</a></li>
								<li class="divider"></li>
							</ul>
						</div>
						<label class="col-sm-1 control-label">采集品类大类</label>
						<div class="btn-group col-sm-2" >
				         	<button type="button" class="btn btn-default dropdown-toggle" 
								data-toggle="dropdown" aria-expanded="false" >
								<span id="collectClasspName" >请选择采集品类大类</span><input id="collectClassParentId"
									type="hidden" /><span class="caret"></span>
							</button>
							<ul id="getcollectClassParentIds" class="dropdown-menu" role="menu">
								<li><a href="#">请选择采集品类大类</a></li>
								<li class="divider"></li>
							</ul>
						</div>
						<label class="col-sm-1 control-label">采集品类小类</label>
						<div class="btn-group col-sm-2" >
				         	<button type="button" class="btn btn-default dropdown-toggle" 
								data-toggle="dropdown" aria-expanded="false">
								<span id="collectClassName" >请选择采集品类小类</span><input id="collectClassId"
									type="hidden" /><span class="caret"></span>
							</button>
							<ul id="getCollectClassIds" class="dropdown-menu" role="menu">
								<li><a href="#">请选择采集品类小类</a></li>
								<li class="divider"></li>
							</ul>
						</div>
					</div>
				</form>
	         </div>
	         <div class="modal-footer">
	         	<button type="button" id="addHtmlNextButton" class="btn btn-success">下一步</button>
	            <button type="button" class="btn btn-default" 
	               data-dismiss="modal">关闭
	            </button>
	         </div>
	      </div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
<script type="text/javascript">
	var userJson = [{"username":"张三","userId":"zhangsan"},{"username":"李四","userId":"lisi"},{"username":"王五","userId":"wangwu"},{"username":"赵六","userId":"zhaoliu"},{"username":"黄七","userId":"huangqi"}];
	var ccIdpJson = [{"name":"小区","id":"xiaoqu"},{"name":"广告","id":"guanggao"}];
	var ccIdJson = {
						"xiaoqu":[{"name":"入口","id":"rukou"},{"name":"楼栋","id":"loudong"},{"name":"停车场","id":"tingchechang"}]
						,"guanggao":[{"name":"公交站亭","id":"gongjiaozhanting"},{"name":"墙体","id":"qiangti"},{"name":"LED广告","id":"LED"}]
						};
	
	var appJson = {
							"zhangsan":[{"ownerName":"项目一","ownerId":"1"},{"ownerName":"项目二","ownerId":"2"},{"ownerName":"项目三","ownerId":"3"}]
							,"lisi":[{"ownerName":"项目t一","ownerId":"t1"},{"ownerName":"项目t二","ownerId":"t2"},{"ownerName":"项目t三","ownerId":"t3"}]
							,"wangwu":[{"ownerName":"项目f一","ownerId":"f1"},{"ownerName":"项目f二","ownerId":"f2"},{"ownerName":"项目f三","ownerId":"f3"}]
							,"zhaoliu":[{"ownerName":"项目k一","ownerId":"k1"},{"ownerName":"项目k二","ownerId":"k2"},{"ownerName":"项目k三","ownerId":"k3"}]
							,"huangqi":[{"ownerName":"项目j一","ownerId":"j1"},{"ownerName":"项目jt二","ownerId":"j2"},{"ownerName":"项目j三","ownerId":"j3"}]
						};
	
	$(function (){
		//查询HTML
		$("#queryHtmlList").click(function() {
			queryHtmlList();
		});
		//新建HTML
		$("#addHtmlButton").click(function() {
			$("#selectModal").modal("show");
			//重置
			$("#ownerName").text("请选择客户名称");
			$("#ownerId").val("");//客户ID
			
			$("#collectClasspName").text("请选择采集品类大类");
			$("#collectClassParentId").val("");//客户ID
			
			$("#collectClassName").text("请选择采集品类小类");
			$("#collectClassId").val("");//客户ID
		});
		//初始化查询下拉菜单
		$.each(userJson,function(i,n){
			$('<li><a href="#" objectId="'+n.userId+'">'+n.username+'</a></li>').appendTo("#getQUserIds");
		});
		//绑定事件
		$("#getQUserIds li > a").click(function(){
			var self = $(this);
			var qUserId = self.attr("objectId");
			$("#qUserName").text(self.text());
			$("#qUserId").val(qUserId);//客户ID
			//ulId,showName,jsonName,jsonId,spanId,inputId,relateId
			initSelectMenu("getQOwnerIds","请选择项目名称","ownerName","ownerId","qOwnerName","qOwnerId",qUserId,true);
		});
		
		$.each(ccIdpJson,function(i,n){
			$('<li><a href="#" objectId="'+n.id+'">'+n.name+'</a></li>').appendTo("#getqCollectClassParentIds");
		});
		//绑定事件
		$("#getqCollectClassParentIds li > a").click(function(){
			var self = $(this);
			var qCollectClassParentId = self.attr("objectId");
			$("#qCollectClassParentName").text(self.text());
			$("#qCollectClassParentId").val(qCollectClassParentId);//采集大类ID
			var ccIds = ccIdJson[qCollectClassParentId]; 
			$("#getQCollectClassIds").empty();
			$('<li><a href="#">请选择采集品类小类</a></li>').appendTo("#getQCollectClassIds");
			$('<li class="divider"></li>').appendTo("#getQCollectClassIds");
			$.each(ccIds,function(i,n){
				$('<li><a href="#" objectId="'+n.id+'">'+n.name+'</a></li>').appendTo("#getQCollectClassIds");
			});
			//绑定事件
			$("#getQCollectClassIds li > a").click(function(){
				var self = $(this);
				$("#qCollectClassName").text(self.text());
				$("#qCollectClassId").val(self.attr("objectId"));//采集小类ID
			});
		});
		
		
		
		//初始化选择下拉菜单
		$.each(userJson,function(i,n){
			$('<li><a href="#" objectId="'+n.userId+'">'+n.username+'</a></li>').appendTo("#getUserIds");
		});
		//绑定事件
		$("#getUserIds li > a").click(function(){
			var self = $(this);
			var qUserId = self.attr("objectId");
			$("#userName").text(self.text());
			$("#userId").val(qUserId);//客户ID
			initSelectMenu("getOwnerIds","请选择项目名称","ownerName","ownerId","ownerName","ownerId",qUserId,true);
		});
		
		$.each(ccIdpJson,function(i,n){
			$('<li><a href="#" objectId="'+n.id+'">'+n.name+'</a></li>').appendTo("#getcollectClassParentIds");
		});
		//绑定事件
		$("#getcollectClassParentIds li > a").click(function(){
			var self = $(this);
			var scollectClassParentId = self.attr("objectId");
			$("#collectClasspName").text(self.text());
			$("#collectClassParentId").val(scollectClassParentId);//采集大类ID
			var ccIds = ccIdJson[scollectClassParentId];
			$("#getCollectClassIds").empty();
			$('<li><a href="#">请选择采集品类小类</a></li>').appendTo("#getCollectClassIds");
			$('<li class="divider"></li>').appendTo("#getCollectClassIds");
			$.each(ccIds,function(i,n){
				$('<li><a href="#" objectId="'+n.id+'">'+n.name+'</a></li>').appendTo("#getCollectClassIds");
			});
			//绑定事件
			$("#getCollectClassIds li > a").click(function(){
				var self = $(this);
				$("#collectClassName").text(self.text());
				$("#collectClassId").val(self.attr("objectId"));//采集小类ID
			});
		});
		
		//初始化应用名称
		/**
		*ulId  <ul>的ID
		* showName   选择提示信息
		* showName   选择提示信息
		* jsonName   json数据显示名称
		* jsonId   json数据Id
		* spanId   span的Id
		* inputId   隐藏输入域的ID
		* relateId 父类ID  即查询下级数据的父ID
		*flag 标识是否还有下级关联
		**/
		function initSelectMenu(ulId,showName,jsonName,jsonId,spanId,inputId,relateId,flag) {
			//初始化应用项目名称
			$("#"+ulId).empty();
			$("#"+spanId).text(showName);
			$('<li><a href="#">'+showName+'</a></li>').appendTo("#"+ulId);
			$('<li class="divider"></li>').appendTo("#"+ulId);
			var uAppJson = appJson[relateId];
			$.each(uAppJson,function(i,n){
				$('<li><a href="#" objectId="'+n[jsonId]+'">'+n[jsonName]+'</a></li>').appendTo("#"+ulId);
			});
			//绑定事件
			$("#"+ulId+" li > a").click(function(){
				var self = $(this);
				$("#"+spanId).text(self.text());
				$("#"+inputId).val(self.attr("objectId"));//采集小类ID
			});
		}
		
		//初始化客户下拉框
		/* $.ajax({ 
	        type:"POST",
	        url:contextPath+"/htmlPage/queryHtmlPageInfos.html",
	        dataType:"json",
	        data:JSON.stringify(jsonData),
	        contentType : "application/json;charset=utf-8",
	        error:function(data) {
	        	alert(data.desc);
	        },
	        success:function(data){ 
	        	data = $.parseJSON(data);
				$.each(data,function(i,n){
					if(n.id==3||n.id==4)
					$('<li><a href="#" objectId="'+n.id+'">'+n.system_name+'</a></li>').appendTo("#getOwnerIds");
				});
				//绑定事件
				$("#getOwnerIds li > a").click(function(){
					var self = $(this);
					$("#ownerName").text(self.text());
					$("#scollectClassParentId").val(self.attr("objectId"));//客户ID
				})
	        }
		}); */
	});

	/**
	 * 查询
	 */
	function queryHtmlList(page) {
		var flag = false;//是否初始化分页组件的标识 true为需要初始化
		if(page == undefined) {
			page = 1;
			flag = true;
		}
		var ownerId = $("#qOwnerId").val();
		var collectClassParentId = $("#qCollectClassParentId").val();
		var collectClassId = $("#qCollectClassId").val();
		var jsonData = {"ownerId":ownerId,"collectClassParentId":collectClassParentId,"collectClassId":collectClassId,"pageNo":page,"limit":10}
		$.ajax({ 
	        type:"POST",
	        url:contextPath+"/htmlPage/queryHtmlPageInfos.html",
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
							var row = $("<tr></tr>");
							var td_1 = $("<td></td>");
							var td_2 = $("<td></td>");
							var td_3 = $("<td></td>");
							var td_4 = $("<td></td>");
							var td_5 = $("<td></td>");
							var td_6 = $("<td></td>");
							var div= $("<div class='btn-group' role='group'></div>");
							var updateBtn = $("<button type='button'  ownerId="+n.ownerId+"  collectClassParentId="+n.collectClassParentId+"  collectClassId="+n.collectClassId+" class='btn btn-primary'>修改</button>");
							var scrapBtn = $("<button type='button'  htmlId="+n.id+" ownerId="+n.ownerId+"   class='btn btn-danger'>废弃</button>");
							var historyBtn = $("<button type='button'  htmlId="+n.id+" ownerId="+n.ownerId+"  collectClassParentId="+n.collectClassParentId+"  collectClassId="+n.collectClassId+" class='btn btn-info'>查看历史</button>");
							//绑定事件-start
							require(['home/user'], function (user){
						   	 user.bindUpdate(updateBtn);
						    });
						    bindClick(scrapBtn,"scrap");
						    bindClick(historyBtn,"history");
							  //绑定事件-end
							div.append(updateBtn);
							div.append(scrapBtn);
							div.append(historyBtn);
						    td_1.append(i+1);
						    td_2.append(n.ownerName);
						    td_3.append(n.collectClassParentName);
						    td_4.append(n.collectClassName);
						    td_5.append(n.versionNo);
						    td_6.append(div);
						    row.append(td_1);
						    row.append(td_2);
						    row.append(td_3);
						    row.append(td_4);
						    row.append(td_5);
						    row.append(td_6);
						    htmlTable.append(row);
						});
					}else {
						var row = $("<tr></tr>");
						var td = $("<td colspan='6'>未查询到数据</td>");
						row.append(td)
						htmlTable.append(row);
					}
					//当page为undefined 为第一次条件查询 初始化分页组件
					if(flag) {
						options.onPageClicked=function(event, originalEvent, type, page){
							queryHtmlList(page);
				        }
						initPage(options,"bp-3-element-lg",data.info.totalCount,data.info.limit);
					}
	        	}else  {
	        		alert(data.desc);
	        	}
	        }
		});
	}
//绑定事件
function bindClick(obj,type) {
	obj.click(function(){
		if(type == "active") {
			activeHtml(obj);
		}else if(type == "scrap") {
			scrapHtml(obj);
		}else if(type == "history") {
			viewHistoryHtmlList(obj);
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
		var jsonData = {"ownerId":ownerId,"id":htmlId,"status":status}
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

//查看历史
function viewHistoryHtmlList(obj,page) {
	var flag = false;//是否初始化分页组件的标识 true为需要初始化
	if(!$("#historyListModal").is(':visible')){
		$("#historyListModal").modal("show");
		flag = true;
	}
	if(page == undefined) {
		page = 1;
	}
	//var htmlId = obj.attr("htmlId");
	var ownerId = obj.attr("ownerId");
	var collectClassParentId = obj.attr("collectClassParentId");
	var collectClassId = obj.attr("collectClassId");
	var jsonData = {"ownerId":ownerId,"collectClassParentId":collectClassParentId,"collectClassId":collectClassId,"pageNo":page,"limit":10}
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
        		//alert("success"+JSON.stringify(data.info));
        		var htmlTable = $("#historyList").empty();
        		var info = data.info.objectList;
				if(info.length >0 ) {
					$.each(info,function(i,n){
						var row = $("<tr class="+(n.status == 0 ? "danger" : "success")+"></tr>");
						var td_1 = $("<td></td>");
						var td_2 = $("<td></td>");
						var td_3 = $("<td></td>");
						var td_4 = $("<td></td>");
						var td_5 = $("<td></td>");
						var td_6 = $("<td></td>");
						var td_7 = $("<td></td>");
						var div= $("<div class='btn-group' role='group'></div>");
					    var showInfo = "";
					    if(n.status == 0 ) {
					    	showInfo = "废弃";
					    	var activeBtn = $("<button type='button'  htmlId="+n.id+" ownerId="+n.ownerId+"  collectClassParentId="+n.collectClassParentId+"  collectClassId="+n.collectClassId+"   class='btn btn-success'>激活</button>");
					    	div.append(activeBtn);
					    	bindClick(activeBtn,"active");
						   
					    }else if(n.status == 1 ){
					    	showInfo = "正常";
					    	var scrapBtn = $("<button type='button'  htmlId="+n.id+" ownerId="+n.ownerId+"  collectClassParentId="+n.collectClassParentId+"  collectClassId="+n.collectClassId+"  class='btn btn-danger'>废弃</button>");
							div.append(scrapBtn);
							 bindClick(scrapBtn,"scrap");
					    }
					    td_1.append(i+1);
					    td_2.append(n.ownerName);
					    td_3.append(n.collectClassParentName);
					    td_4.append(n.collectClassName);
					    td_5.append(n.versionNo);
					    td_6.append(showInfo);
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
					var td = $("<td colspan='6'>未查询到数据</td>");
					row.append(td)
					htmlTable.append(row);
				}
        		
				//当page为undefined 为第一次条件查询 初始化分页组件
				if(flag) {
					options.onPageClicked=function(event, originalEvent, type, page){
			        	viewHistoryHtmlList(obj,page);
			        }
					initPage(options,"html-history-page",data.info.totalCount,data.info.limit);
				}
        	}else  {
        		alert(data.desc);
        	}
        }
	});
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

</script>

</body>

</html>
