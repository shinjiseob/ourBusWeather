<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-xs-12 col-sm-12 col-md-12 mainfloor">
	<div class="col-md-offset-4 col-md-4 col-md-offset-4 text-center">
		<ul class="list-inline font_godic" id="section2_topBar">
			<li onclick="getLi_view('service');">서비스</li>
			<li>|</li>
			<li onclick="getLi_view('make');">만든이</li>
			<li>|</li>
			<li onclick="getLi_view('use');">이용방법</li>
		</ul>			
	</div>
	<div class="col-md-offset-2 col-md-8 col-md-offset-2" id="li_view"></div>

</div>
