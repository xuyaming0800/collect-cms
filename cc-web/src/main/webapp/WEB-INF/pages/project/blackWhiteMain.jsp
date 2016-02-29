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
</style>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/asset/lib/bootstrap/css/bootstrap.min.css"> 
<link rel="stylesheet" href="<%=request.getContextPath()%>/js/asset/lib/jquery/jquery-ui.css">
<script src="<%=request.getContextPath()%>/js/asset/lib/jquery/jquery-1.11.3.js"></script>
<script src="<%=request.getContextPath()%>/js/asset/lib/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-datetimepicker.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="<%=request.getContextPath()%>/js/asset/lib/jquery/jquery-ui.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap-paginator.min.js"></script>

<script type="text/javascript">
var contextPath = "<%=request.getContextPath()%>";
</script>
</head>
<body>
	<div id="containerId" class="container-fluid">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">黑白名单</h3>
			</div>
			<div class="panel-body">
				<div class="panel panel-primary" style="margin-bottom:5px;">
					<div class="panel-body">
						<form class="form-horizontal" >
							<div class="form-group">
						      	<label class="col-sm-2 control-label">项目名称</label>
								<div class="col-sm-3">
						         	<input type="text" class="form-control" placeholder="项目名称" id="qProjectName" />
						      	</div>
						      	<label class="col-sm-2 control-label">类型</label>
								<div class="col-sm-3">
										<select class="form-control" id="qBlackWhiteListType">
								      	 <option value="">全部</option>
								         <option value="0">黑名单</option>
								         <option value="1">白名单</option>
								       </select>
						      	</div>
						      	<div class="col-sm-2">
						      	 	<button type="button" class="btn btn-primary"  id="queryBlackWhiteListBtn" >查询</button>
						      	</div>
							</div>
						</form>
					</div>
				</div>
				<div class="btn-group" role="group" aria-label="button" style="margin-bottom:5px;">
			      	 	<button type="button" class="btn btn-primary"  id="addBlackWhiteListBtn" >新建</button>
				</div>
				<div class="table-responsive">
					<table class="table table-striped table-hover"  style="margin-bottom:0px;">
						<thead>
								<tr>
								</tr>
								<tr>
								 <th>序号</th>
						          <th>项目名称</th>
						          <th>类型</th>
						          <th>操作</th>
								</tr>
						</thead>
						<tbody id="blackWhiteList">
				    	</tbody>
						<tfoot>
							<tr>
								<td colspan="4" style="text-align: right">
									<div>
										<ul id="blackWhiteListPage" class="pagination pagination-lg">
										</ul>
									</div> 
								</td>
							</tr>
						</tfoot>
				 </table>
				</div>
			</div>
		</div>
	</div>		
	<!-- 新建黑白名单模态框（Modal） -->
	<div class="modal fade" id="addBlackWhithListModal" tabindex="-1" role="dialog" 
	   aria-labelledby="myModalLabel" aria-hidden="true">
	   <div class="modal-dialog" style="width:40%;">
	      <div class="modal-content">
	         <div class="modal-header">
	            <button type="button" class="close" 
	               data-dismiss="modal" aria-hidden="true">
	                  &times;
	            </button>
	            <h4 class="modal-title" id="addBlackWhithListLabel">
	               黑白名单名单信息
	            </h4>
	         </div>
	         <div class="modal-body">
	         	<form class="form-horizontal" >
	         			<input type="hidden" id="blackWhiteListId">
						<div class="form-group">
							<label class="col-sm-3 control-label">项目名称</label>
							<div class="col-sm-9">
								<input type="hidden" id="ownerId">
					         	<input type="text" class="form-control" placeholder="项目名称" id="projectName"  />
					      	</div>
						</div>
						<div class="form-group">
					      <label for="name" class="col-sm-3 control-label">类型</label>
					      <div class="col-sm-9">
						      <select class="form-control" id="blackWhiteListType">
						      	 <option value="">请选择项目类型</option>
						         <option value="0">黑名单</option>
						         <option value="1">白名单</option>
						      </select>
						 </div>
					  </div>
					 <div class="form-group">
						<label class="col-sm-3 control-label">描述</label>	
						<div class="col-sm-9">	
				         	<textarea class="form-control" rows="3"  id="blackWhiteListDesc"></textarea>
				      	</div>
					</div> 
	         	</form>
	         </div>
	         <div class="modal-footer"> 
	         	 <button type="button" class="btn btn-success"  id="saveBlackWhiteListBtn">
	         	 	保存
	             </button>
	             <button type="button" class="btn btn-default"  data-dismiss="modal">
	            	关闭
	             </button>	
	          </div> 
	      </div><!-- /.modal-content -->
		</div><!-- /.modal --> 
	</div>
<script type="text/javascript">
$(function () {
	//条件查询
	$("#queryBlackWhiteListBtn").click(function() {
		queryBlackWhiteList();
	}); 
	//新增
	$("#addBlackWhiteListBtn").click(function() {
		$("#blackWhiteListId").val("");
		$("#ownerId").val("");
		$("#projectName").val("");
		$("#projectName").attr("disabled",false);
		$("#blackWhiteListType").val("");
		$("#blackWhiteListDesc").val("");
		$("#addBlackWhithListModal").modal("show");
	}); 
	//自动完成
	bindAutocomplete("ownerId", "projectName", "/project/queryProjectInfos.html", "id", "projectName");
	
	$("#projectName").blur(function() {
		//验证黑白名单 项目是否重复
		checkUniqueProject();
	});
	//保存
	$("#saveBlackWhiteListBtn").click(function() {
		saveBlackWhiteList();
	});
	
	queryBlackWhiteList();
});
//验证项目唯一
function checkUniqueProject() {
	var ownerId = $("#ownerId").val();
	var projectName = $("#projectName").val();
	if(blackWhiteListId != "" && projectName != "") {
		jsonData = {"ownerId":ownerId};
		$.ajax({ 
	        type:"POST",
	        url:contextPath+"/project/checkUniqueProject.html",
	        dataType:"json",
	        data:JSON.stringify(jsonData),
	        contentType : "application/json;charset=utf-8",
	        error:function(data) {
	        	alert("系统异常："+data.status);
	        },
	        success:function(data){ 
	        	if(data.success == true) {
	        		if(data.code == "800002") {//此项目已存在
	        			alert(data.desc);
	        			$("#ownerId").val("");
	        			$("#projectName").val("");
	        		}else if(data.code == "800003") {//此项目不存在可新增
	        		}
	        	}else  {
	        		alert(data.desc);
	        	}
	        }
		});
	}
}

//绑定自动完成查询
function bindAutocomplete(id,name,url,jsonId,jsonName) {
	$("#"+name+"").autocomplete({
	    source: function( request, response ) {
	    	$("#"+id+"").val("");
	        $.ajax({
	          url: contextPath+url,
	          type:"POST",
	          dataType: "json",
	          delay:500,
	          data: JSON.stringify({
		        	projectName: request.term,
		            pageNo:1,
		            limit:20
		          }),
	          contentType : "application/json;charset=utf-8",
	          success: function( data ) {
	            response( $.map( data.info.objectList, function( item ) {
	              return {
	                label: item[jsonName] ,
	                value: item[jsonName] ,
	                hiddenId:item[jsonId]
	              }
	            }));
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
/**
 * 查询
 */
function queryBlackWhiteList(page) {
	var flag = false;//是否初始化分页组件的标识 true为需要初始化
	if(page == undefined) {
		page = 1;
		flag = true;
	}
	var projectName = $("#qProjectName").val();

	var blackWhiteListType = $("#qBlackWhiteListType").val();
	var jsonData = {"projectName":projectName,"blackWhiteListType":blackWhiteListType,"pageNo":page,"limit":10}
	$.ajax({ 
        type:"POST",
        url:contextPath+"/project/queryBlackWhiteList.html",
        dataType:"json",
        data:JSON.stringify(jsonData),
        contentType : "application/json;charset=utf-8",
        error:function(data) {
        	alert("系统异常："+data.status);
        },
        success:function(data){ 
        	
        	if(data.success == true) {
        		var htmlTable = $("#blackWhiteList").empty();
        		var info = data.info.objectList;
				if(info.length >0 ) {
					$.each(info,function(i,n){
						var row = $("<tr></tr>");
						var td_1 = $("<td></td>");
						var td_2 = $("<td></td>");
						var td_3 = $("<td></td>");
						var td_4 = $("<td></td>");
						var div= $("<div class='btn-group' role='group'></div>");
						var updateBtn = $("<button type='button'  blackWhiteListId='"+n.id+"' projectName='"+n.projectName+"' ownerId='"+n.ownerId+"' blackWhiteListType='"+n.blackWhiteListType+"' blackWhiteListDesc='"+n.blackWhiteListDesc+"'  class='btn btn-primary'>修改</button>");
						var deleteBtn = $("<button type='button'  blackWhiteListId='"+n.id+"'   class='btn btn-danger'>删除</button>");
						//绑定事件-start
						bindClick(updateBtn,"update");
						bindClick(deleteBtn,"delete");
						  //绑定事件-end
						div.append(updateBtn);
						div.append(deleteBtn);
					    td_1.append(i+1);
					    td_2.append(n.projectName);
					    if(n.blackWhiteListType == 0) {
					    	 td_3.append("黑名单");
					    }else if(n.blackWhiteListType == 1){
					    	 td_3.append("白名单");
					    }
					    td_4.append(div);
					    row.append(td_1);
					    row.append(td_2);
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
						queryBlackWhiteList(page);
			        }
					initPage(options,"blackWhiteListPage",data.info.totalCount,data.info.limit);
				}
        	}else  {
        		alert(data.desc);
        	}
        }
	});
}

//保存和更新
function saveBlackWhiteList() {
	var blackWhiteListId = $("#blackWhiteListId").val();
	var ownerId = $("#ownerId").val();
	var projectName = $("#projectName").val();
	var blackWhiteListType = $("#blackWhiteListType").val();
	var blackWhiteListDesc = $("#blackWhiteListDesc").val();
	if(projectName == "" || ownerId == ""){
		alert("请输入正确的项目名称！");
		return;
	}else	if(blackWhiteListType == "") {
		alert("请选择类型！");
		return;
	}
	var jsonData = {"id":blackWhiteListId,"ownerId":ownerId,"blackWhiteListType":blackWhiteListType,"blackWhiteListDesc":blackWhiteListDesc}
	if(projectName != "" && blackWhiteListType != "" ) {
		if(blackWhiteListId != "") {
			$.ajax({ 
		        type:"POST",
		        url:contextPath+"/project/updateBlackWhiteList.html",
		        dataType:"json",
		        data:JSON.stringify(jsonData),
		        contentType : "application/json;charset=utf-8",
		        error:function(data) {
		        	alert("系统异常："+data.status);
		        },
		        success:function(data){ 
		        	if(data.success == true) {
		        		alert(data.desc);
		        		$("#addBlackWhithListModal").modal("hide");
		        		queryBlackWhiteList();
		        	}else  {
		        		alert(data.desc);
		        	}
		        }
			});
		}else {
			$.ajax({ 
		        type:"POST",
		        url:contextPath+"/project/saveBlackWhiteList.html",
		        dataType:"json",
		        data:JSON.stringify(jsonData),
		        contentType : "application/json;charset=utf-8",
		        error:function(data) {
		        	alert("系统异常："+data.status);
		        },
		        success:function(data){ 
		        	if(data.success == true) {
		        		alert(data.desc);
		        		$("#addBlackWhithListModal").modal("hide");
		        		queryBlackWhiteList();
		        	}else  {
		        		alert(data.desc);
		        	}
		        }
			});
		}
		
	}else {
		alert("请输入必要内容");
	}
}
//更新
function updateBlackWhiteList(obj) {
	var blackWhiteListId = obj.attr("blackWhiteListId");
	var ownerId = obj.attr("ownerId");
	var projectName = obj.attr("projectName");
	var blackWhiteListType = obj.attr("blackWhiteListType");
	var blackWhiteListDesc = obj.attr("blackWhiteListDesc");
	$("#blackWhiteListId").val(blackWhiteListId);
	$("#ownerId").val(ownerId);
	$("#projectName").val(projectName);
	$("#projectName").attr("disabled",true);
	$("#blackWhiteListType").val(blackWhiteListType);
	$("#blackWhiteListDesc").val(blackWhiteListDesc);
	$("#addBlackWhithListModal").modal("show");
}
//删除
function deleteBlackWhiteList(obj){
	var blackWhiteListId = obj.attr("blackWhiteListId");
	var jsonData = {"id":blackWhiteListId,"status":0};
	$.ajax({ 
        type:"POST",
        url:contextPath+"/project/updateBlackWhiteListStatus.html",
        dataType:"json",
        data:JSON.stringify(jsonData),
        contentType : "application/json;charset=utf-8",
        error:function(data) {
        	alert("系统异常："+data.status);
        },
        success:function(data){ 
        	if(data.success == true) {
        		alert(data.desc);
        		queryBlackWhiteList();
        	}else  {
        		alert(data.desc);
        	}
        }
	});
}
//绑定点击事件
function bindClick(obj,type) {
	obj.click(function(){
		if(type == "update") {
			updateBlackWhiteList(obj);
		}else if(type == "delete") {
			deleteBlackWhiteList(obj);
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
