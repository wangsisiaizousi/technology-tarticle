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
function getDataPustFile(){
	$.ajax({
		url: "/technology-tarticle/getDataPustFile", //json文件位置
		type: "GET", //请求方式为get
		async: false, //请求同步
		success: function(d) { //请求成功完成后要执行的方法 
		}
	})
}

self.setInterval(function() {
	getDataPustFile();
	location.reload();
}, 1000*60*30);
$(function() {
	var pos = 0;
	var LIST_ITEM_SIZE = 8;
	//滚动条距底部的距离
	var BOTTOM_OFFSET = 10;
	createListItems();
	$(document).ready(function() {
		$(window).scroll(function() {
			var $currentWindow = $(window);
			//当前窗口的高度
			var windowHeight = $currentWindow.height();
			console.log("current widow height is " + windowHeight);
			//当前滚动条从上往下滚动的距离
			var scrollTop = $currentWindow.scrollTop();
			console.log("current scrollOffset is " + scrollTop);
			//当前文档的高度
			var docHeight = $(document).height();
			console.log("current docHeight is " + docHeight);
			//当 滚动条距底部的距离 + 滚动条滚动的距离 >= 文档的高度 - 窗口的高度
			//换句话说：（滚动条滚动的距离 + 窗口的高度 = 文档的高度）  这个是基本的公式
			if((BOTTOM_OFFSET + scrollTop) >= docHeight - windowHeight) {
				createListItems();
			}
		});
	});

	function createListItems() {
		getData();
		var mydocument = document;
		var mylist = mydocument.getElementById("container");
		var docFragments = mydocument.createDocumentFragment();
		for(var i = pos; i < pos + LIST_ITEM_SIZE && i < data.length; ++i) {
			var div_element = document.createElement("div");
			div_element.className = "container";
			var h2_element = document.createElement("h2");
			h2_element.innerHTML = data[i].title;
			var div1_element = document.createElement("div");
			div1_element.className = "digest";
			var div2_element = document.createElement("div");
			div2_element.className = "text";
			var div3_element = document.createElement("div");
			div3_element.className = "content";
			div3_element.innerHTML = data[i].content.substring(0, 60) + "...";
			var a1_element = document.createElement("a");
			a1_element.href = "contentPage.html?num=" + i;
			a1_element.target = "_blank";
			a1_element.title = data[i].title;
			a1_element.innerHTML = "<h2>" + data[i].title + "</h2>";
			var a_detail = document.createElement("a");
			a_detail.href = "contentPage.html?num=" + i;
			a_detail.target = "_blank";
			a_detail.title = data[i].title;
			a_detail.innerHTML = "详细";
			div2_element.appendChild(a1_element);
			div3_element.appendChild(a_detail);
			div2_element.appendChild(div3_element)
			div1_element.appendChild(div2_element)
			div_element.appendChild(div1_element);
			document.body.appendChild(div_element);
		}
		pos += LIST_ITEM_SIZE;
	}
});