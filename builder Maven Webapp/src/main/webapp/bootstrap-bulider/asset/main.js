require.config({
    //By default load any module IDs from js/lib
    baseUrl: 'asset/lib',
    //except, if the module ID starts with "app",
    //load it from the js/app directory. paths
    //config is relative to the baseUrl, and
    //never includes a ".js" extension since
    //the paths config could be for a directory.
    paths: {
        text: 'require/text',
        jquery: 'jquery/jquery-1.11.3',
        jqueryui: 'jquery/jquery-ui.min',
        jqueryvalue:'jquery/jquery-values',
		bootstrap: 'bootstrap/js/bootstrap.min',
		ejs: 'ejs/ejs',
		root: '..',
		home: '../..'
    },
    shim: {
    	bootstrap: {
		  deps: ['jquery'],
		  exports: 'bootstrap'
		},
		jqueryui: {
		  deps: ['jquery'],
		  exports: '$.fn.draggable,$.fn.droppable,$.fn.sortable'
		},
		jqueryvalue: {
			  deps: ['jquery'],
			  exports: '$.fn.values'
		}
	},
});
require(['home/user'], function (user){
	 user.init();
});
