<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic:400,700,800&amp;subset=korean" rel="stylesheet">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="./resources/css/home.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=c380781ea07201145702d2a06217fea8"></script>
<script src="./resources/js/home.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	util.getLocation();
});
</script>

<div class="row">
	<div class="visible-sm visible-md visible-lg col-xs-12 col-sm-12 col-md-12 mainHeader">
		<div class="col-xs-1 col-md-offset-1 col-md-1">
			<ul class="list-inline font_godic"><li id="section1_logo"><a href="/"><img src="./resources/img/logo/Logo.png"/></a></li></ul>
		</div>
		<div class="col-xs-offset-1 col-xs-10 col-md-offset-3 col-md-7">
			<ul class="list-inline font_godic" id="section2_topBar">
				<li>자세한 날씨정보</li>
				<li>|</li>
				<li>자세한 버스정보</li>
			</ul>			
		</div>
	</div>
</div>