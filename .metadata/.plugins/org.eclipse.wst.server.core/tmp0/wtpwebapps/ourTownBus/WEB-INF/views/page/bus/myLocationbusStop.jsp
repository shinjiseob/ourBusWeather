<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>내 주변 정류소 보기 ( 300M )</title>
</head>
<body>
<jsp:include page="../include/iHead.jsp"/>
<script type="text/javascript">
$(document).ready(function(){
	$.ajax({
		url:"./locationBusStopAajx",
		data:{ tmx : $("#map_tmx").val(), tmy : $("#map_tmy").val() },
		success : function(data){ bus.myLocationBusStop(data, $("#map_tmx").val(), $("#map_tmy").val(), 'map'); },
		error : function(a, b, c){}
	});
});
</script>

<div id="bus_Content">
	<input type="hidden" id="map_tmx" value="${tmx }">
	<input type="hidden" id="map_tmy" value="${tmy }">
	<div class="container" style="height: 100%;">
	<div class="row">
		<div class="col-xs-12 col-sm-12 col-md-12 bus_line">
			<div class="row">
				<div class="col-xs-12 col-sm-12 col-md-6" id="map" style="width: 80%; height: 600px; "></div>
				<div class="col-xs-12 col-sm-12 col-md-6" id="bus_arrive" style="width: 50%; height: auto;">
				</div>
			</div>
		</div>
	</div>
	</div>
</div>
</body>
</html>