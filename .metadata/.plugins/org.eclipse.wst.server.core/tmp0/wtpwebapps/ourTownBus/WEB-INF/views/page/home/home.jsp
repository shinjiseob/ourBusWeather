<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>우리 동네 버스</title>
</head>
<body>

<!-- header -->
<jsp:include page="../include/iHead.jsp"/>
<!-- header -->

<script type="text/javascript">
$(document).ready(function(){
	$("#bus_input").focus();
	util.getNowTime();
	// 내 주변정류소 찾기
	$("#locationBubStop").on("click",function(){
		var  tmY="";
		var  tmX="";
		var result = confirm('주변 정류소를 찾으시겠습니까?( 300M기준 )');
		if(result) {
			tmX = $("#tmx").val();
			tmY = $("#tmy").val();
			location.href="./locationBusStop?tmx="+tmX+"&tmy="+tmY;
		}
		else{}
	});
	
	$("#btn-weather").on("click",function(){ weather.getWeatherDataSeaven();});
	$("#bus_input").on("keyup",function(){
		var busInput = $(this); var bus_val = busInput.val();
		if(bus_val == "" || bus_val == null){ return false; }
		
		$.ajax({
			url:"./relationKeyword",
			data:{ keyword : bus_val },
			success : function(data){ $("#relationKeyword").html(data); },
			error : function(a, b, c){}
		});
	});
	
});


</script>

<!-- 
<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-12">
		<div id="myCarousel" class="col-xs-12 col-sm-12 col-md-12 carousel slide" data-ride="carousel">
		  <ol class="carousel-indicators">
		    <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
		    <li data-target="#myCarousel" data-slide-to="1"></li>
		    <li data-target="#myCarousel" data-slide-to="2"></li>
		  </ol>
		  보여지는 사진목록
		  <div class="carousel-inner">
		    <div class="item active"><img class="center-block" src="./resources/img/bus/images.jpg"></div>
		    <div class="item"><img class="center-block" src="./resources/img/bus/images (1).jpg"></div>
		    <div class="item"><img class="center-block" src="./resources/img/bus/images (2).jpg"></div>
		  </div>
		  왼쪽 오른쪽 controls
		  <a class="left carousel-control" href="#myCarousel" data-slide="prev">
		    <span class="glyphicon glyphicon-chevron-left"></span>
		    <span class="sr-only">Previous</span>
		  </a>
		  <a class="right carousel-control" href="#myCarousel" data-slide="next">
		    <span class="glyphicon glyphicon-chevron-right"></span>
		    <span class="sr-only">Next</span>
		  </a>
		</div>
	</div>
</div>
캐러셀 잠시보류
 -->

<div id="content">
	<div class="container">
	<div class="row">
	<input type="hidden" id="tmy"/>
	<input type="hidden" id="tmx"/>
	
	<!-- 날씨영역 -->
	<div class="col-xs-12 col-sm-12 col-md-offset-1 col-md-10 col-md-offset-1 weather_line Box">
		<div class="col-md-3 col-md-offset-2">
			<span id="btn-weather" class="font_godic" style="font-size: 14pt; cursor:pointer;"><img style="width: 90px;" src="./resources/img/weather/Weather.png"/>날씨정보</span>
		</div>
		<div class="col-xs-12 col-sm-12 col-md-12">
		<div class="col-md-4"></div>
		<div class="col-md-4 weather_Box">
			<div class="col-md-12" id="weatherImg" style="min-width: 250px; min-height: 50px;"></div>
			<div class="row">
			<div class="col-md-12" style="min-width: 250px; color:#58ACFA; min-height: 50px; border-top: 1px solid white;">
				<ul class="list-inline font_godic text-center" style="color: white; width: 100%; margin-top: 3px;">
					<li style="width: 30%"><img src="./resources/img/weather/icon/vec.png"/></li>
					<li style="width: 30%"><img src="./resources/img/weather/icon/pop.png"/></li>
					<li style="width: 30%"><img src="./resources/img/weather/icon/cloud.png"/></li>
				</ul>
				<ul class="list-inline font_godic text-center" style="color: white;">
					<li style="width: 30%" id="vec"></li>
					<li style="width: 30%" id="pop"></li>
					<li style="width: 30%" id="reh"><img src="./resources/img/weather/icon/vec.png"/></li>
				</ul>						
			</div>
			</div>
		</div>
		<div class="col-md-4"></div>
		</div>
	</div>
	<!-- 날씨영역 -->
	
	<!-- 검색영역 -->
	<div class="col-xs-12 col-sm-12 col-md-offset-1 col-md-10 col-md-offset-1 bus_line Box">
		<div class="col-md-3 col-md-offset-2">
			<span class="font_godic" style="font-size: 14pt;"><img style="width: 90px" src="./resources/img/bus/Bus-logo.svg.png"/>버스정보</span>
		</div>
		<div class="col-md-offset-2 col-md-8 col-md-offset-2">
            <div class="input-group stylish-input-group bus_searchLine">
                <input type="text" class="form-control bus_searchLine" id="bus_input" placeholder="정류소 명, 정류소ID..." >
                <span class="input-group-addon">
                    <button type="submit">
                        <span class="glyphicon glyphicon-search"></span>
                    </button>  
                </span>
                <span class="input-group-addon"><button class="glyphicon glyphicon-map-marker" id="locationBubStop">주변정류소</button></span>
            </div>
            <div class="row" id="relationKeyword"></div>
		</div>
	</div>
	<!-- 검색영역 -->
	
	</div>
	</div>
	<!-- floor -->
	<jsp:include page="../include/iFloor.jsp"/>
	<!-- floor -->
</div>

<!-- 내주변 정류소 위치 찾기 -->
<jsp:include page="../modal/modal_myLocationBusStop.jsp"/>
</body>
</html>