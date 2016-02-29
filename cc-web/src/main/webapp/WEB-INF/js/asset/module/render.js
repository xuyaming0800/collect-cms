define([ 'jquery', 'ejs','bootstrap','jqueryvalue' ], function($, ejs,bts,jv) {
	EJS.config( {cache: true, type: '<', ext: '' } );
	var templateUrl=contextPath+"/js/asset/template/";
	
	var render={
			renderIndexPage :function(formOption){//渲染主页面
				require(['text!root/template/form.html' ], function(page) {
					// 渲染
					var newHtml = $(new EJS({
						text : page
					}).render({
						supplies : formOption.confJson
					}));
					// 插入页面
					require([ 'root/module/container' ], function(container) {
						container.appendIndex(newHtml);
						container.initDragContainer();
						container.initDropContainer($("#dropzone"),formOption);
						bindButtons(formOption);
					});	
				});
			},
			renderItem : function(renderName, tabId, renderJson) {// 渲然拖拽元素
				require([ 'text!root/template/' + renderName + ".html",
						'text!root/template/' + renderName + "-default.json", ],
						function(template, data) {
							var _template =packageItemObject(
									renderItemHtml(template,data,renderJson),renderName);
							// 插入页面
							require([ 'root/module/container' ],
									function(container) {
										container.appnedDragItem(tabId, _template);
							});

						});
			},
			renderDropItems : function(dropDataArray) {// 初始化左侧区域 必须同步 因为顺序问题 不采用AMD模式
				for(var i in dropDataArray){
					var _template =packageItemObject(
							renderDropItemHtml(templateUrl + dropDataArray[i].renderName + ".html",templateUrl + dropDataArray[i].renderName + "-default.json",dropDataArray[i].renderJson),dropDataArray[i].renderName);
					_template.removeClass("drop-item");
					dropDataArray[i].render=_template;
				}
				return dropDataArray;
				
			},
			reloadItem : function(renderName,popoverHtml, renderJson,oldObject) {// 渲然拖拽元素
				require([ 'text!root/template/' + renderName + ".html",
						'text!root/template/' + renderName + "-default.json",
						'text!root/template/' + renderName + "-popover.html",
						'text!root/module/popover.html'],
						function(template, data,templatePopover,basicTemplate) {
					        var _template =packageItemObject(
							   renderItemHtml(template,data,renderJson),renderName,"drag-item");
					        packageItemPopOverObject(templatePopover,data,basicTemplate,popoverHtml,_template,renderName,renderJson);
					        // 插入页面
							require([ 'root/module/container' ], function(container) {
								container.appendReloadDropItem(_template,oldObject);
							});

						});
			},
			destoryItem:function(itemObject){
				  itemObject.popover("destroy");
				  itemObject.remove();
			},
			
			renderItemPopover : function(renderName, itemObject, renderJson,oldObject) {// 渲染元素的弹出框
				require([ 'text!root/template/' + renderName + "-popover.html",
						'text!root/template/' + renderName + "-default.json",
						'text!root/module/popover.html' ], function(template, data,
						basicTemplate) {
					packageItemPopOverObject(template,data,basicTemplate
							,renderItemPopOverHtml(template,data,basicTemplate,renderJson)
							,itemObject,renderName,renderJson);
					// 插入页面 //仅在oldProject替换时候生效
					require([ 'root/module/container' ], function(container) {
						container.replaceDropItem(itemObject,oldObject);
					});
				});

			}
		};
	
    function bindButtons(formOption){
    	$("#saveJson").unbind("click"); 
		$("#saveJson").click(function(){
			var saveObject=$("#dropzone div.drag-item");
			var saveArray=new Array();
			if(saveObject){
				$.each(saveObject,function(i,n){
				    var _obj=$(n).data("data-json");
				    var _type=$(n).data("data-json-type");
				    var _result={
				    		"type":$(n).data("data-json-type")+"",
				    		"jsonText":$(n).data("data-json")
				    }
				    saveArray.push(_result);
				});
			}
			formOption.saveMethod(saveArray);
		});
		$("#submitJson").unbind("click"); 
		$("#submitJson").click(function(){
			var submitHtml=$("#target").clone();
			submitHtml.find("div").removeClass("ui-droppable").
			removeClass("ui-sortable").
			removeClass("row").
			removeClass("ui-draggable").
			removeClass("ui-draggable-handle").
			removeClass("drag-item");
			var saveObject=$("#dropzone div.drag-item");
			var saveArray=new Array();
			if(saveObject){
				$.each(saveObject,function(i,n){
				    var _obj=$(n).data("data-json");
				    var _type=$(n).data("data-json-type");
				    var _result={
				    		"type":$(n).data("data-json-type")+"",
				    		"jsonText":$(n).data("data-json")
				    }
				    saveArray.push(_result);
				});
			}
			formOption.submitMethod(saveArray,submitHtml);
		});
	}
	
	//渲染页面控件元素
	function renderItemHtml(template,data,renderJson){
		var _renderJson = "";
		if (renderJson) {
			// 传入renderJson使用data渲染
			_renderJson = $.extend($.parseJSON(data),renderJson);
		} else {
			_renderJson = $.parseJSON(data);
		}
		var _template = new EJS({
			text : template
		}).render(_renderJson);
		var result={
				renderObject:_template,
				renderJson:_renderJson
		}
		return result;
	}
	function renderDropItemHtml(template,defaultJsonUrl,renderJson){
		$.ajaxSetup({
			  global: false,
			  async:false
		});
		$.getJSON(defaultJsonUrl+"?bust=" + (new Date()).getTime(),
				function(data){
			       renderJson=$.extend(data,renderJson);
			    }
		);
		$.ajaxSetup({
			  global: false,
			  async:true
		});
		var _template = new EJS({
			url : template
		}).render(renderJson);
		var result={
				renderObject:_template,
				renderJson:renderJson
		}
		return result;
	}
	//空间元素转JQ对象
	function packageItemObject(result,renderName,className){
		var name="drop-item row ";
		var template=result.renderObject;
		if(className){
			name=className;
		}
		var obj=$(template);
		//存储JSON对象
		obj.data("data-json",result.renderJson);
		obj.data("data-json-type",renderName);
		//阻止默认事件
		obj.bind('change click dblclick focus keydown mousedown select',function(e){
			e.preventDefault();
		})
		return obj.addClass(name);
	}
	
	//渲染元素的弹出窗口
	function renderItemPopOverHtml(template,data,basicTemplate,renderJson){
		var _renderJson = "";
		var save=false;
		if (renderJson) {
			_renderJson = $.extend($.parseJSON(data),renderJson);
		} else {
			_renderJson = $.parseJSON(data);
		}
		//无需封装为jquery对象
		var _template = new EJS({
			text : template
		}).render(_renderJson);
		var popoverHtml = new EJS({
			text : basicTemplate
		}).render({
			popoverBody : _template
		});
		return popoverHtml;
	}
	//封装
	function packageItemPopOverObject(template,data,basicTemplate,popoverHtml,itemObject,renderName,renderJson){
		popoverHtml=$(popoverHtml);
		var _renderJson=renderJson;
		popoverHtml.find("div.popover-operation  button.btn-success").click(function(){
			var obj=popoverHtml.values();
			var _orgin=$.parseJSON(data);
			for(var key in obj){
				//替换所有含有的元素
				_orgin[key]=obj[key];
			}
			obj=_orgin;
			_renderJson=obj;
			popoverHtml=$(renderItemPopOverHtml(template,data,basicTemplate,obj));
			render.reloadItem(renderName,popoverHtml,_renderJson,itemObject);
			
		});
		popoverHtml.find("div.popover-operation  button.btn-danger").click(function(){
			itemObject.popover("hide");
		});
		
		bindPopover(renderName,popoverHtml,_renderJson,itemObject);
		return popoverHtml
	}
	function bindPopover(renderName,popoverHtml,_renderJson,itemObject){
		itemObject.popover({
			html : true,
			title : '修改属性',
			template : '<div class="popover" style="max-width:900px" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>',
			placement:'auto',
			content : popoverHtml
		}).on("hidden.bs.popover",function(){
			itemObject=$(this).clone(false);
			itemObject.data("data-json",$(this).data("data-json"));
			itemObject.data("data-json-type",$(this).data("data-json-type"));
			itemObject.bind('change click dblclick focus keydown mousedown select',function(e){
				e.preventDefault();
			});
			var _obj=$(this);
			render.renderItemPopover(renderName, itemObject, _renderJson,_obj);
		}).on("show.bs.popover",function(){
			$(".popover:visible").popover("hide");
		});
		;
		return itemObject;
	}
	
	
	return render;
});