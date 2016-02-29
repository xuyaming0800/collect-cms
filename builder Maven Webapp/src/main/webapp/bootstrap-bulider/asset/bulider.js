define(['jquery','text!root/module/config.json'],function ($,conf) {
	//定义初始化参数
	var option={
	   initJson:[],
	   confJson:$.parseJSON(conf),
	   saveMethod:null,
	   submitMethod:null
	};
	return {
		init: function(indexOption){
			require(['root/module/form'], function (form){
				//清理元素
				$("#bulidfrom").empty();
				if(indexOption.initJson){
					option.initJson=indexOption.initJson;
				}
				if(indexOption.saveMethod){
					option.saveMethod=indexOption.saveMethod;
				}
				if(indexOption.submitMethod){
					option.submitMethod=indexOption.submitMethod;
				}
				form.init($("#bulidfrom"),option);
			});
		}
	}
});