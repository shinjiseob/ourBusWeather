<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>버스 정류소 상세보기</title>
</head>
<body>
<jsp:include page="../include/iHead.jsp"/>
<script type="text/javascript">
$(document).ready(function(){
	bus.setMapTmXY('map');
	var bus_info = "${bus_info}";
	if(bus_info != "" || bus_info != null){ $("#bus_arrive").html(bus_info); }
});
</script>

<div id="bus_Content">
	<input type="hidden" id="map_tmx" value="${tmx }">
	<input type="hidden" id="map_tmy" value="${tmy }">
	<div class="container" style="height: 100%;">
	<div class="row">
		<div class="col-xs-12 col-sm-12 col-md-12 bus_line">
			<div class="row">
				<div class="col-xs-12 col-sm-12 col-md-6" id="map" style="width: 50%; height: 600px; "></div>
				<div class="col-xs-12 col-sm-12 col-md-6" id="bus_arrive" style="width: 50%; height: auto;">
				
					<!-- <div class="row">
					<div class="col-md-12 Box">
						<table class="text-left">
							<tr class="bus_Info_font_godic">
								<td>신월동 우성상가</td>
								<td>(&nbsp;15-296&nbsp;)</td>
							</tr>
							<tr class="bus_Info_font_godic">
								<td>신월동 방면</td>
								<td><img src="./resources/img/bus/right.png" style="height: 15px;"/></td>
							</tr>
							<tr class="bus_Info_font_godic">
								<td>잠시후 도착</td>
							</tr>
						</table>
					</div>
					<div class="col-md-12 Box" style="margin-top: 4px; min-height: 55px;">
						<table>
							<tr class="busData_font_godic" style="font-size: 14pt;">
								<td><strong>651</strong></td>
								<td style=" right: 0; position: absolute;">곧도착</td>
							</tr>
							<tr class="busData_font_godic">
								<td style="left : 80%;top: 30px;position: absolute;">약 16분(10번째전)</td>
							</tr>
						</table>
					</div>
					</div> -->
					
				</div>
			</div>
		</div>
	</div>
	</div>
</div>
</body>
</html>