<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@ page language="java"  import="java.util.Date" %>
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

<link rel="stylesheet" href="<%=request.getContextPath()%>/js/asset/lib/jquery/jquery-ui.css">
<script type="text/javascript">
var contextPath = "<%=request.getContextPath()%>";
</script>
</head>
<body>
    <input type="hidden" id="customId"><!-- 客户ID -->
    <input type="hidden" id="ownerId"><!-- 项目ID -->
    <input type="hidden" id="collectClassType"><!-- 品类类型 -->
    <input type="hidden" id="collectClassParentId"><!-- 父类ID -->
    <input type="hidden" id="collectClassParentName"><!-- 父类名称 -->
    <input type="hidden" id="collectClassId"><!-- 类ID -->
    <input type="hidden"  id="versionNo"><!-- 版本号 -->
    <input type="hidden"  id="collectClassDistance" value="0"><!-- 拍摄距离 -->
    
	<div id="containerId" class="container-fluid">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">项目列表（双击列表管理采集品类）</h3>
			</div>
			<div class="panel-body">
				<div class="panel panel-primary" style="margin-bottom:5px;">
					<div class="panel-body">
						<form class="form-horizontal" >
							<div class="form-group">
								<label class="col-sm-2 control-label">客户名称</label>
								<div class="col-sm-3">
									<input type="hidden" id="qCustomId">
						         	<input type="text" class="form-control" placeholder="客户名称" id="qCustomName"  />
						      	</div>
						      	<label class="col-sm-2 control-label">项目名称</label>
								<div class="col-sm-3">
						         	<input type="text" class="form-control" placeholder="项目名称" id="qProjectName" />
						      	</div>
						      	<div class="col-sm-2">
						      	 	<button type="button" class="btn btn-primary"  id="queryProjectList" >查询</button>
						      	</div>
							</div>
						</form>
					</div>
				</div>
				<div class="btn-group" role="group" aria-label="button" style="margin-bottom:5px;">
			      	 	<button type="button" class="btn btn-primary"  id="addProjectBtn" >新建</button>
				</div>
				<div class="table-responsive">
					<table class="table table-striped table-hover"  style="margin-bottom:0px;">
						<thead>
								<tr>
								</tr>
								<tr>
								 <th>序号</th>
						          <th>项目名称</th>
						          <th>项目类别</th>
						          <th>项目负责人</th>
						          <th>客户名称</th>
						          <th>项目状态</th>
						          <th>创建时间</th>
						          <th>项目描述</th>
						          <th>操作</th>
								</tr>
						</thead>
						<tbody id="projectList">
				    	</tbody>
						<tfoot>
							<tr>
								<td colspan="9" style="text-align: right">
									<div>
										<ul id="projectPage" class="pagination pagination-lg">
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
	<!-- 新建项目模态框（Modal） -->
	<div class="modal fade" id="addProjectModal" tabindex="-1" role="dialog" 
	   aria-labelledby="myModalLabel" aria-hidden="true">
	   <div class="modal-dialog" style="width:40%;">
	      <div class="modal-content">
	         <div class="modal-header">
	            <button type="button" class="close" 
	               data-dismiss="modal" aria-hidden="true">
	                  &times;
	            </button>
	            <h4 class="modal-title" id="addProjectLabel">
	               项目信息
	            </h4>
	         </div>
	         <div class="modal-body">
	         	<form class="form-horizontal" >
	         			
						<div class="form-group">
							<label class="col-sm-3 control-label">项目名称</label>
							<div class="col-sm-9">
								<input type="hidden" id="projectId">
					         	<input type="text" class="form-control" placeholder="项目名称" id="projectName"  />
					      	</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">应用名称</label>
							<div class="col-sm-9">
					         	<input type="text" class="form-control" placeholder="项目名称" id="appName"  />
					      	</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">客户名称</label>
							<div class="col-sm-9">
						         <input type="text" class="form-control" placeholder="客户名称" id="customName"  />
					      	</div>
						</div>
						<!-- <div class="form-group">
					      <label for="name" class="col-sm-3 control-label">项目类型</label>
					      <div class="col-sm-9">
						      <select class="form-control" id="projectType">
						      	 <option value="">请选择项目类型</option>
						         <option value="0">简单项目</option>
						         <option value="1">特殊项目</option>
						      </select>
						 </div>
					  </div> -->
					  <div class="form-group">
							<label class="col-sm-3 control-label">项目负责人</label>
							<div class="col-sm-9">
						      <input type="hidden" id="projectLeaderId">
						       <input type="text" class="form-control" placeholder="项目负责人" id="projectLeaderName"  />
					      	</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">项目描述</label>
							<div class="col-sm-9">
					         	<textarea class="form-control" rows="3"  id="projectDesc"></textarea>
					      	</div>
						</div>
	         	</form>
	         </div>
	         <div class="modal-footer">
	         	 <button type="button" class="btn btn-success"  id="saveProjectBtn">
	         	 	保存
	            </button>
	            <button type="button" class="btn btn-default"  data-dismiss="modal">
	            	关闭
	            </button>
	         </div>
	      </div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	
	<!-- 项目描述模态框（Modal） -->
	<div class="modal fade" id="projectDescModal" tabindex="-1" role="dialog" 
	   aria-labelledby="myModalLabel" aria-hidden="true">
	   <div class="modal-dialog" style="width:40%;">
	      <div class="modal-content">
	         <div class="modal-header">
	            <button type="button" class="close" 
	               data-dismiss="modal" aria-hidden="true">
	                  &times;
	            </button>
	            <h4 class="modal-title" id="projectDescModalLabel">
	               项目描述
	            </h4>
	         </div>
	         <div class="modal-body">
	         	<form class="form-horizontal" >
						<div class="form-group">
							<div class="col-sm-12">
								<div id="vProjectDesc" style="height:100px;overflow:auto;border: #DDD 1px solid"></div>
					        </div>
						</div>
	         	</form>
	         </div>
	         <div class="modal-footer">
	            <button type="button" class="btn btn-default"  data-dismiss="modal">
	            	关闭
	            </button>
	         </div>
	      </div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	
	<!--添加采集品类模态框（Modal） -->
	<div class="modal fade" id="collectClassModal" tabindex="-1" role="dialog" 
	   aria-labelledby="myModalLabel" aria-hidden="true">
	   <div class="modal-dialog" style="width:80%;height:80%;">
	      <div class="modal-content">
	         <div class="modal-header">
	            <button type="button" class="close" 
	               data-dismiss="modal" aria-hidden="true">
	                  &times;
	            </button>
	            <h4 class="modal-title" id="collectClassModalLabel">
	              采集品类信息
	            </h4>
	         </div>
	         <div class="modal-body">
	         	<div class="panel panel-danger" style="margin-bottom:0px;">
					<div class="panel-heading">
						<h3 class="panel-title">采集品类(单击编辑子品类信息)</h3>
					</div>
					<div class="panel-body"  style="height:300px;overflow: auto;">
							<div class="btn-group" role="group" aria-label="button" style="margin-bottom:5px;">
						      	 	<button type="button" class="btn btn-primary"  id="addCollectClassBtn" >新建</button>
							</div>
							<div class="table-responsive">
								<table class="table table-striped table-hover"  style="margin-bottom:0px;">
									<thead>
											<tr>
											</tr>
											<tr>
											 <th>序号</th>
									          <th>品类名称</th>
									          <th>任务类型</th>
									          <th>状态</th>
									          <th>操作</th>
											</tr>
									</thead>
									<tbody id="collectClassList">
							    	</tbody>
									<tfoot>
										<tr>
											<td colspan="5" style="text-align: right">
												<div>
													<ul id="collectClassPage" class="pagination pagination-lg">
													</ul>
												</div> 
											</td>
										</tr>
									</tfoot>
							 </table>
						</div>
					</div>
				</div>
				<div class="panel panel-primary" >
					<div class="panel-heading">
						<h3 class="panel-title">采集品类子类(双击编辑品类HTML信息)</h3>
					</div>
					<div class="panel-body" style="height:280px;overflow: auto;">
						<div class="btn-group" role="group" aria-label="button" style="margin-bottom:5px;">
						      	 	<button type="button" class="btn btn-primary"  id="addCollectClassChildBtn" >新建</button>
							</div>
							<div class="table-responsive">
								<table class="table table-striped table-hover"  style="margin-bottom:0px;">
									<thead>
											<tr>
											</tr>
											<tr>
											 <th>序号</th>
									          <th>品类名称</th>
									          <th>任务类型</th>
									          <th>采集次数</th>
									          <!-- <th>拍摄距离</th> -->
									          <th>是否必须</th>
									          <th>是否入口</th>
									          <th>操作</th>
											</tr>
									</thead>
									<tbody id="collectClassChildList">
							    	</tbody>
									<tfoot>
										<tr>
											<td colspan="7" style="text-align: right">
												<div>
													<ul id="collectClassChildPage" class="pagination pagination-lg">
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
	         <div class="modal-footer">
	            <button type="button" class="btn btn-default"  data-dismiss="modal">
	            	关闭
	            </button>
	         </div>
	      </div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	<!-- 新建大品类模态框（Modal） -->
	<div class="modal fade" id="addCollectClassDModal" tabindex="-1" role="dialog" 
	   aria-labelledby="myModalLabel" aria-hidden="true">
	   <div class="modal-dialog" style="width:40%;">
	      <div class="modal-content">
	         <div class="modal-header">
	            <button type="button" class="close" 
	               data-dismiss="modal" aria-hidden="true">
	                  &times; 
	            </button>
	            <h4 class="modal-title" id="addCollectClassDModalLabel">
	               品类信息
	            </h4>
	         </div> 
	         <div class="modal-body"> 
	         	<form class="form-horizontal" > 
						<div class="form-group"> 
							<label class="col-sm-3 control-label">品类名称</label>
							<div class="col-sm-9">
					         	<input type="text" class="form-control" placeholder="品类名称" id="collectClassName"  />
					      	</div>
						</div>
						<div class="form-group" id="showOrHideTaskType"> 
							<label class="col-sm-3 control-label">品类任务类型</label>
							<div class="col-sm-9">
						         	<select id="collectClassTaskType" class="form-control">
						         		<option value="0" selected = "selected">被动任务</option>
						         		<option value="1">主动任务</option>
<!-- 						         		<option value="0" selected="selected">被动任务</option> -->
						         	</select>
						     </div>
						</div>
						<div  id="showOrHide">
<!-- 							<div class="form-group">  -->
<!-- 								<label class="col-sm-3 control-label">价格</label> -->
<!-- 								<div class="col-sm-9"> -->
<!-- 						         	<input type="text" class="form-control" placeholder="价格" id="collectClassPay"  /> -->
<!-- 						      	</div> -->
<!-- 							</div> -->
                            <input type="hidden" class="form-control"  id="collectClassPay" value="0"  />
							<div class="form-group"> 
								<label class="col-sm-3 control-label">是否必须</label>
								<div class="col-sm-9">
						         	<select id="isRequired" class="form-control">
						         		<option value="" selected = "selected">选择是否必须</option>
						         		<option value="1">是</option>
						         		<option value="0">否</option>
						         	</select>
						      	</div>
							</div>
							<div class="form-group"> 
								<label class="col-sm-3 control-label">采集次数</label>
								<div class="col-sm-9">
						         	<input type="text" class="form-control" placeholder="采集次数" id="collectCount"  />
						      	</div>
							</div>
						<!-- 	<div class="form-group"> 
								<label class="col-sm-3 control-label">拍摄距离</label>
								<div class="col-sm-9">
						         	<input type="text" class="form-control" placeholder="拍摄距离（整数）" id="collectClassDistance"  />
						      	</div>
							</div> -->
						</div>
						
	         	</form>
	         </div>
	         <div class="modal-footer">
	         	 <button type="button" class="btn btn-success"  id="saveClassBtn">
	         	 	保存
	            </button>
	            <button type="button" class="btn btn-default"  data-dismiss="modal">
	            	关闭
	            </button>
	         </div>
	      </div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	
	<!-- HTML5列表模态框（Modal） -->
	<div class="modal fade" id="htmlListModal" tabindex="-1" role="dialog" 
	   aria-labelledby="myModalLabel" aria-hidden="true">
	   <div class="modal-dialog" style="width:90%;">
	      <div class="modal-content">
	         <div class="modal-header">
	            <button type="button" class="close" 
	               data-dismiss="modal" aria-hidden="true">
	                  &times;
	            </button>
	            <h4 class="modal-title" id="htmlListModalLabel">
	             	HTML5列表
	            </h4>
	         </div>
	         <div class="modal-body">
	         	<div class="panel panel-primary" >
					<div class="panel-body" >
			         		<div class="btn-group" role="group" aria-label="button" style="margin-bottom:5px;">
					      	 	<button type="button" class="btn btn-primary"  id="addHtmlBtn" >新建</button>
							</div>
							<div class="table-responsive">
								<table class="table table-striped  table-hover">
									<thead>
										<tr></tr>
										<tr>
											 <th>序号</th>
									          <th>项目名称</th>
									          <th>采集品类大类</th>
									          <th>采集品类小类</th>
									          <th>版本号</th>
									          <th>创建时间</th>
									          <th>操作</th>
										</tr>
									</thead>
									<tbody id="htmlListId">
							    	</tbody>
									<tfoot>
										<tr>
											<td colspan="6" style="text-align: right">
												<div>
													<ul id="htmlListPage" class="pagination pagination-lg">
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
	         <div class="modal-footer">
	            <button type="button" class="btn btn-default"  data-dismiss="modal">
	            	关闭
	            </button>
	         </div>
	      </div><!-- /.modal-content -->
		</div><!-- /.modal -->
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

</body>
<!-- <script src="<%=request.getContextPath()%>/js/cc-web.js"></script> -->
<script data-main="<%=request.getContextPath()%>/js/asset/main.js?bust=<%=new Date().getTime() %>" src="<%=request.getContextPath()%>/js/asset/lib/require/require.js?bust=<%=new Date().getTime() %>"></script>
</html>
