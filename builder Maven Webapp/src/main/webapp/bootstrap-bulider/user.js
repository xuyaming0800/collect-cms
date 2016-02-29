define(['jquery'],function ($) {
	return {
		init: function(){
			require(['root/bulider'], function (bulider){
				 //回调  
				  function save(saveJson){
				    //调用后台方法
				    alert(JSON.stringify(saveJson))
				  }
				  //回调
				  function submit(saveJson,saveHtml){
				    //调用后台方法
				    alert(saveHtml.get(0).outerHTML);
				  } 
				 //此处JSON自行获取
				  $.ajax({
					   type: "POST",
					   url:"test.json" ,
					   async:false,
					   dataType:"json",
					   contentType:"application/json",
					   success: function(msg){
						   var option={
								    initJson:msg,
								    saveMethod:save,
								    submitMethod:submit
								 };
						   bulider.init(option);
					   }
				 });
				 
				 
			});
		}
	}
});