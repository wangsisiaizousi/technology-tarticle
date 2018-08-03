var data;

function getData() {
	$.ajax({
		url: "/technology-tarticle/getData", //json文件位置
		type: "GET", //请求方式为get
		dataType: "json", //返回数据格式为json
		async: false, //请求同步
		success: function(_data) { //请求成功完成后要执行的方法 
			data = _data;
		}
	})
}