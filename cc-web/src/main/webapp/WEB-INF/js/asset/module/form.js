define([ 'jquery'], function($) {
	return {
		init : function(bulidfrom,option) {
			// 使用模板渲染页面
			require([ 'root/module/container' ], function(container) {
				container.initIndexContainer(bulidfrom,option);
			});
		}
		
	}
});