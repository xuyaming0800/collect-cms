define([ 'jquery', 'jqueryui' ], function($, jui) {
	var option={
			indexContainer:null,
			dragContainer:null,
			dropContainer:null,
			configJson:null
	};
	return {
		initIndexContainer :function(container,formOption){
			if(container.children().size()==0){
				//重新初始化
				option.indexContainer=null;
				option.dragContainer=null;
				option.dropContainer=null;
				option.configJson=null;
			}
			if (option.indexContainer == null){
				 option.indexContainer=container;
				 option.configJson=formOption.confJson;
				// 渲染
				require([ 'root/module/render' ], function(render) {
						render.renderIndexPage(formOption);
				});
			}
			
			
			
		},
		initDragContainer : function() {
			// 初始化空间容器
			if (option.dragContainer == null){
				option.dragContainer = option.indexContainer;
				$.each(option.configJson, function(i, n) {
					if(n.children){
						$.each(n.children, function(j, name) {
							require(['root/module/render'], function (render){
								render.renderItem(name,n.id);
							});
						});
					}
					
				});
			}
				
		},
		initDropContainer : function(container,formOption) {
			// 初始化空间容器
			if (option.dropContainer == null){
				option.dropContainer = container;
				$(".deletezone").droppable({
					tolerance:"touch",
					hoverClass : 'hover',
					drop : function(e, ui) {
						if( ui.draggable.hasClass("drag-item")){
							ui.draggable.popover("destroy");
							ui.draggable.remove();
						}
					}
				});
				// 初始化拖拽区域
				option.dropContainer.droppable({
					activeClass : 'active',
					hoverClass : 'hover',
					accept : ":not(.ui-sortable-helper)",
					greedy:"true",
					drop : function(e, ui) {
						// 弹出框
						if( ui.draggable.hasClass("drop-item")){
							var dropItem = ui.draggable.clone();
							dropItem.bind('change click dblclick focus keydown mousedown select',function(e){
								e.preventDefault();
							});
							var data_popover_name = ui.draggable.data("data-json-type");
							dropItem.removeClass("drop-item");
							dropItem.addClass("drag-item");
							//存储JSON对象 不从新渲染 直接拷贝指定
							dropItem.data("data-json",ui.draggable.data("data-json"));
							dropItem.data("data-json-type",data_popover_name);
							var dropObject = $(this);
							// 渲染
							require([ 'root/module/render' ], function(render) {
								render.renderItemPopover(data_popover_name, dropItem);
								//自行添加到左侧
								option.dropContainer.append(dropItem);
							});
						}
						
					}
				}).sortable({
					items : '.drag-item',
					cancel :false,
					sort : function() {
						$(this).removeClass("active");
					}
				});
				//如果有数据传入 那么初始化数据库中存在的数据
				if(formOption.initJson){
					var dropDataArray=new Array();
					$.each(formOption.initJson, function(i, n) {
						var dropData={
							renderName:n.type,
							renderJson:n.jsonText,
							render:null
						};
						dropDataArray.push(dropData);
					});
					// 渲染 渲染不走AMD方式
					require([ 'root/module/render' ], function(render) {
						var _obj=render.renderDropItems(dropDataArray);
						if(_obj){
							$.each(_obj, function(i, n) {
								if(n.render){
									//渲染POPOVER 走AMD
									require([ 'root/module/render' ], function(render) {
										render.renderItemPopover(n.renderName, n.render,n.renderJson);
									});
									option.dropContainer
									.append(n.render.addClass("drag-item"));
								}
							});
						}
						
						
					});
				}
				
			}
			
		},
		appnedDragItem : function(tabId, render) {// 将元素插入到页面
			// 拖拽
			option.dragContainer.find("div.tab-content > div#" + tabId)
			.append(render.draggable({
				appendTo : 'body',
				helper : 'clone',
				cancel :false
			}));
		},
		replaceDropItem : function(newRender,oldObject) {// 元素重构后加入到后面替换旧的元素
			//只在重构左侧元素的时候使用 如果右侧拖拽过来的 请自行append到左侧
			if(oldObject){
				oldObject.after(newRender);
				require([ 'root/module/render' ], function(render) {
					render.destoryItem(oldObject);
				});
			}
			//其他情况不支持append请自行append
		
		},
		appendReloadDropItem : function(render,oldObject) {// 元素拖拽到左面
			oldObject.after(render);
			require([ 'root/module/render' ], function(render) {
				render.destoryItem(oldObject);
			});
		},
		appendIndex : function(render) {// 
			option.indexContainer.html(render);
		}
	}
});