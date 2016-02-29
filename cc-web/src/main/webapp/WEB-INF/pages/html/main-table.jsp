<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="description" content="">
<meta name="author" content="">

<title>html5列表</title>
<style type="text/css">

</style>
<script type="text/javascript">
var contextPath = "<%=request.getContextPath()%>";
$(function(){
	$("#query").click(function(){
		init();
	});
});

function init() {
	var ownerId = $("#ownerId").val();
	var collectClassIdp = $("#collectClassIdp").val();
	var collectClassId = $("#collectClassId").val();
	var jsonData = {"ownerId":ownerId,"collectClassIdp":collectClassIdp,"collectClassId":collectClassId}
	$.ajax({ 
        type:"POST",
        url:contextPath+"/htmlPage/queryHtmlPageInfos.html",
        dataType:"json",
        data:JSON.stringify(jsonData),
        contentType : "application/json;charset=utf-8",
        error:function(data) {
        	alert("查询异常，请联系管理员");
        },
        success:function(data){ 
        	if(data.success == true) {
        		//alert("success"+JSON.stringify(data.info));
        		var info = data.info;
        		var htmlList = $("#htmlListId").empty();
        		var htm = "";
        		
				if(info.length >0 ) {
					$.each(info,function(i,n){
	        			htm += "<tr>"
			    			+ "<td>" + (i+1) + "</td>"
			    			+ "<td>" + n.ownerId + "</td>"
			    			+ "<td>" + n.collectClassIdp + "</td>"
			    			+ "<td>" + n.collectClassId + "</td>"
			    			+ "<td>"
			    				 + '<div class="btn-group" role="group"><button type="button" class="btn btn-primary" onclick="javascript:updateHtml(' + n.id + ');" name="updateHtml">修改</button>'
			    				/*  + '<button type="button" class="btn btn-danger" onclick="javascript:delAuditSys(' + n.id + ');" name="">删除</button></div>'
			    				 + '<button type="button" class="btn btn-primary" onclick="javascript:delAuditSys(' + n.id + ');" name="addDss">激活</button></div>'
			    				 + '<button type="button" class="btn btn-danger" onclick="javascript:delAuditSys(' + n.id + ');" name="addDss">废弃</button></div>' */
			    			+ "</td>"
			    			+ "</tr>";
	        		});
				}else {
					var htm = "无数据";
				}
        		
        		htmlList.append(htm);
        	}else  {
        		alert(data.desc);
        	}
        }
	});
}

</script>
</head>
<body>
	<div id="containerId" class="container-fluid">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">HTML列表</h3>
			</div>
			<div class="panel-body">
				<table class="table table-striped">
					<thead>
						<tr>
							<td colspan="5">
								<div class="panel panel-primary">
									<div class="panel-body">
										<table class="table"  style="margin-bottom:0px;">
											<form>
												<tr>
													<td><input type="text" class="form-control" placeholder="客户ID" id="ownerId" ></td>
													<td><input type="text" class="form-control" placeholder="采集品类大类" id="collectClassIdp" ></td>
													<td><input type="text" class="form-control" placeholder="采集品类小类" id="collectClassId" ></td>
													<td><button type="button" class="btn btn-primary"  id="query">查询</button></td>
												</tr>
												<tr>
													<td><button type="button" class="btn btn-primary">新建</button></td>
													<td></td>
													<td></td>
													<td></td>
												</tr>
											</form>
										</table>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							 <th>序号</th>
					          <th>客户名称</th>
					          <th>采集品类大类</th>
					          <th>采集品类小类</th>
					          <th>操作</th>
						</tr>
					</thead>
					<tbody id="htmlListId">
			    	</tbody>
					<!-- <tfoot>
						<tr>
							<td colspan="8" style="text-align: right">
								<div>
									<ul id="bp-3-element-lg" class="pagination pagination-lg">
									</ul>
								</div> 
							</td>
						</tr>
					</tfoot> -->
				</table>
			</div>
		</div>
	</div>
</body>

</html>
