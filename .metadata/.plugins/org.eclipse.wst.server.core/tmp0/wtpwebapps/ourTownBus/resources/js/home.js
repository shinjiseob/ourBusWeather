var weatherKey = "5b67f7f204119ad75b13090b17671e3e";
var weatherAddr = ["http://api.openweathermap.org/data/2.5/weather?", "http://api.openweathermap.org/data/2.5/forecast/daily?"]; //q={city name},{country code}&cnt={cnt}



/*
 * 액션처리 관련 JS
 */
var action = {
		
		/**
		 * table tr Row를 누르면 해당 정류소 상세정보 화면으로 이동 
		 */
		move_GetTrans : function(arsId, tmX, tmY){
			location.href="./busdetail?arsId="+arsId+"&tmx="+tmX+"&tmy="+tmY;
		}
};


/*
 * 공통 JS
 */
var util = {
		
		getNowTime : function(){
			// 년, 월, 일, 시간
			var month, day = "";
			var date = new Date();
			
			( (date.getMonth() + 1) < 10)? month = "0"+(date.getMonth() + 1) : month = (date.getMonth() + 1); // 월
			(date.getDate() < 10)? day = "0"+date.getDate() : day = date.getDate(); // 일
			
			var arrDate = new Array( date.getFullYear(), month, day, date.getHours() );
			return arrDate;
		},
		
		
		getLocation : function(){
		    if(navigator.geolocation){
		        navigator.geolocation.getCurrentPosition(util.locationSuccess, util.locationError);
		    }else{
		        console.log("지오 로케이션 없음");
		    }
		},
		
		locationSuccess : function(p){
		        // 위도/경도 
		        var tude = [p.coords.longitude,  p.coords.latitude];
		        // 기상청조회시 필요한 x, y값
		        var gisangcheong = util.dfs_xy_conv("toXY", tude[1], tude[0]);
		        console.log(util.dfs_xy_conv("toXY", tude[1], tude[0]));
		        $("#tmx").val(tude[0]);
		        $("#tmy").val(tude[1]);
		        
		        weather.getWeatherData2(gisangcheong.nx, gisangcheong.ny);
		        //이상한 버그인지는 몰라도 값이 api로 보내기전에 값이안들어가진다 그래서 좌표구하고 바로실행되는건 여기다 정의한다
		        //weather.getWeatherData();
	    },
		 
	    locationError : function(error){
		        var errorTypes = {
		            0 : "에러",
		            1 : "[위치기반 서비스 사용불가]\n허용을 눌러주세요 혹은 크롬, 사파리의 경우 \n보안상 위치정보수집 서비스가 불가능합니다 \n익스플로러로 접속하여 주십시오",
		            2 : "위치가 잡히지 않습니다",
		            3 : "응답시간 지났습니다"
		        };
		        var errorMsg = errorTypes[error.code];
		        alert(errorMsg);$(".weather_line").html("<h2 class='text-center'>익스플로러로 접속하여 주십시오</h2><img class='text-center' style='width:100%; height: 100%;' src='./resources/img/error/error.png'/>");
		        setInterval(function(){util.getLocation();},1000000);
	    },
	    
	    dfs_xy_conv : function(code, v1, v2) {
	    	var RE = 6371.00877; // 지구 반경(km)
			var GRID = 5.0; // 격자 간격(km)
			var SLAT1 = 30.0; // 투영 위도1(degree)
			var SLAT2 = 60.0; // 투영 위도2(degree)
			var OLON = 126.0; // 기준점 경도(degree)
			var OLAT = 38.0; // 기준점 위도(degree)
			var XO = 43; // 기준점 X좌표(GRID)
			var YO = 136; // 기1준점 Y좌표(GRID)
	    	
	    	
	        var DEGRAD = Math.PI / 180.0;
	        var RADDEG = 180.0 / Math.PI;

	        var re = RE / GRID;
	        var slat1 = SLAT1 * DEGRAD;
	        var slat2 = SLAT2 * DEGRAD;
	        var olon = OLON * DEGRAD;
	        var olat = OLAT * DEGRAD;

	        var sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
	        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
	        var sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
	        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
	        var ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
	        ro = re * sf / Math.pow(ro, sn);
	        var rs = {};
	        if (code == "toXY") {

	            rs['lat'] = v1;
	            rs['lng'] = v2;
	            var ra = Math.tan(Math.PI * 0.25 + (v1) * DEGRAD * 0.5);
	            ra = re * sf / Math.pow(ra, sn);
	            var theta = v2 * DEGRAD - olon;
	            if (theta > Math.PI) theta -= 2.0 * Math.PI;
	            if (theta < -Math.PI) theta += 2.0 * Math.PI;
	            theta *= sn;
	            rs['nx'] = Math.floor(ra * Math.sin(theta) + XO + 0.5);
	            rs['ny'] = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
	        }
	        else {
	            rs['nx'] = v1;
	            rs['ny'] = v2;
	            var xn = v1 - XO;
	            var yn = ro - v2 + YO;
	            ra = Math.sqrt(xn * xn + yn * yn);
	            if (sn < 0.0) - ra;
	            var alat = Math.pow((re * sf / ra), (1.0 / sn));
	            alat = 2.0 * Math.atan(alat) - Math.PI * 0.5;

	            if (Math.abs(xn) <= 0.0) {
	                theta = 0.0;
	            }
	            else {
	                if (Math.abs(yn) <= 0.0) {
	                    theta = Math.PI * 0.5;
	                    if (xn < 0.0) - theta;
	                }
	                else theta = Math.atan2(xn, yn);
	            }
	            var alon = theta / sn + olon;
	            rs['lat'] = alat * RADDEG;
	            rs['lng'] = alon * RADDEG;
	        }
	        return rs;
	    }
		
};





/*
 * 날씨 관련 JS
 */
var weather = {
		
		getWeatherData : function(tmx, tmy){
			var apiURI = weatherAddr[0]+"lat="+$("#tmy").val()+"&lon="+$("#tmx").val()+"&appid="+weatherKey;
	        $.ajax({
	            url: apiURI,
	            dataType: "json",
	            type: "GET",
	            cache : false,
	            success: function(resp) {
	            	var str = "";
	            	
	                console.log(resp);
	                console.log("현재온도 : "+ (resp.main.temp- 273.15) );
	                console.log("현재습도 : "+ resp.main.humidity);
	                console.log("날씨 : "+ resp.weather[0].main );
	                console.log("상세날씨설명 : "+ resp.weather[0].description );
	                console.log("날씨 이미지 : "+ resp.weather[0].icon );
	                console.log("바람   : "+ resp.wind.speed );
	                console.log("나라   : "+ resp.sys.country );
	                console.log("도시이름  : "+ resp.name );
	                console.log("구름  : "+ (resp.clouds.all) +"%" );
	                
	                str+="<table style='width: 100%;'>";
	                str+="<tr>";
	                str+="<td style='min-width: 33.3%;'>"+(resp.main.temp- 273.15)+"</td>";
	                str+="<td style='min-width: 33.3%;'>"+resp.main.humidity+"</td>";
	                str+="</tr>";
	                str+="</table>";
	                
	                var imgURL = "http://openweathermap.org/img/w/" + resp.weather[0].icon + ".png";
	                $("#weatherImg").attr("src", imgURL);

	            }
	        });
		},
		getWeatherData2 : function(tmx, tmy){
			var arrDate = new Array(util.getNowTime());
			var base_date = arrDate[0][0]+""+arrDate[0][1]+""+arrDate[0][2];
			var time = arrDate[0][3];
			
			// 새벽 02시 이후로 3시간마다 데이터 업데이트 하기때문에 그사이 시간으로 조회하면 오류
			if( time >= 02  && time < 05){ time = "0"+2; }
			else if( time >= 05  && time < 08){ time = "0"+5; }
			else if( time >= 08  && time < 11){ time = "0"+8; }
			else if( time >= 11  && time < 14){ time = 11; }
			else if( time >= 14  && time < 17){ time = 14; }
			else if( time >= 17  && time < 20){ time = 17; }
			else if( time >= 20  && time < 23){ time = 20; }
			else { time = 23; }
			
	        $.ajax({
	            url: "./getWeatherData",
	            data : {tmx : tmx, tmy : tmy, base_date : base_date, base_time : time+"00"},
	            dataType: "json",
	            type: "GET",
	            cache : false,
	            success: function(data) {
	            	console.log(data);
	            	var resultMsg = data.response.header.resultMsg;
	            	if(resultMsg == "OK"){console.log(data);
	            	
	            		var t3h = "";
	            		var vec = "";
	            		var pop = "";
		            	var str = "";
		            	var reh = "";
		            	var skyWords = "";
	            		var imgUrlSky = "./resources/img/weather/icon/";
	            		var imgDetailSky = "";
	            		var arrLen = data.response.body.items.item.length;
	            		
	            		// 필요한 데이터 추출(강수형태, 하늘상태)
	            		for(var i=0; i < arrLen; i++){
	            			var category = data.response.body.items.item[i].category;
	            			var fcstValue = data.response.body.items.item[i].fcstValue;
	            			
//	            			console.log("항목값 : "+category + "  val : "+fcstValue);
	            			if("PTY" == category){
	            				switch (fcstValue){
									case 0: console.log(category+" 없음"); break;
									case 1: console.log(category+" 비"); break;
									case 2: console.log(category+" 비/눈"); break;
									case 3: console.log(category+" 눈"); break;
								}
	            			}
	            			else if("SKY" == category){
	            				switch (fcstValue){
									case 1: imgDetailSky= "NB01.png"; skyWords ="맑음"; break;
									case 2: imgDetailSky= "NB02.png"; skyWords ="구름조금"; break;
									case 3: imgDetailSky= "NB03.png"; skyWords ="구름많음"; break;
									case 4: imgDetailSky= "NB04.png"; skyWords ="흐림"; break;
	            				}
	            			}
	            			else if("T3H" == category){ t3h = fcstValue; }
	            			else if("VEC" == category){ vec = Math.floor((( fcstValue + 22.5 * 0.5) / 22.5)); }
	            			else if("POP" == category){ pop = fcstValue; }
	            			else if("REH" == category){ reh = fcstValue; }
	            			
	            		}
	            		
	            		str +="<table class='bus_Info_font_godic' style='width:100%;'>";
	            		str +=	"<tr>";
        				str +=		"<td class='text-center'width='70%'><img style='width: 70px;'height='60px;' src='"+imgUrlSky+imgDetailSky+"'/></td>";
        				str +=		"<td width='4%'>"+t3h+"°C</b></td>";
        				str +=	"</tr>";
        				str +=	"<tr>";
        				str +=		"<td width='75%'></td>";
        				str +=		"<td width='25%'>"+skyWords+"</b></td>";
        				str +=	"</tr>";
        				str +="</table>";
        				$("#weatherImg").html(str);
	            		$("#vec").html(vec+"m/s");
	            		$("#pop").html(pop+"%");
	            		$("#reh").html(reh+"%");
	            		
        				console.log("==============================================");
	            		console.log("이미지경로 imgUrlSky : "+imgUrlSky);
	            		console.log("하늘상태 skyWords : "+skyWords);
	            		console.log("3시간 기온 T3H : "+t3h);
	            		console.log("풍속 VEC : "+vec);
	            	}else{ alert("데이터가 존재하지않습니다."); }
	            	
	            }
	        });
		}
		
		
};


/*
 * 버스 관련 JS
 */
var bus = {
		
		
		/**
		 * Div id를 받아 맵 api를 적용시킨다
		 * @param mapId
		 */
		setMapTmXY : function(mapId){
			var tmx = $("#map_tmx").val();
			var tmy = $("#map_tmy").val();
			if(tmx == "" || tmy == ""){ alert("위치정보가 없습니다."); }
			var container = document.getElementById(mapId);
			var options = {
				center: new daum.maps.LatLng(tmy, tmx),
				level: 3
			};
	
			var map = new daum.maps.Map(container, options);
			bus.getmapOpction(1, 1, map, tmx, tmy);
		},

		
		/**
		 * 다음지도의 옵션적용 함수 
		 * 0 : 사용안함, 1 : 사용함
		 * @param makerUse
		 * @param trafficUse
		 */
		getmapOpction : function(makerUse, trafficUse, map, tmx, tmy){
			if(trafficUse == 1 ){
				// 지도에 교통정보를 표시하도록 지도타입을 추가합니다
				map.addOverlayMapTypeId(daum.maps.MapTypeId.TRAFFIC);
			}if(makerUse == 1 ){
				
				var markerPosition = new daum.maps.LatLng(tmy, tmx);
				// 마커생성
				var marker = new daum.maps.Marker({
				    position: markerPosition
				});
				// 마커 적용
				marker.setMap(map);
			}
		},
		
		
		/**
		 * 내 주변 정류소 정보들을 받아 Daum map API 적용
		 * @param pointList
		 * @param tmx
		 * @param tmy
		 * @param mapId
		 */
		 myLocationBusStop : function(pointList, tmx, tmy, mapId){
			var points = [];
			var busStopNm = [];
			var busarsId = [];

			// 지도 표시할 div와 지도의 중심좌표 설정
			var mapContainer = document.getElementById(mapId),
		    mapOption = { 
		        center: new daum.maps.LatLng(tmy, tmx),
		        level: 3 // 지도의 확대 레벨
		    };

		    // 지도 생성
		    var map = new daum.maps.Map(mapContainer, mapOption);
		    // 교통정보 표시
		    map.addOverlayMapTypeId(daum.maps.MapTypeId.TRAFFIC);

		    // 주변 정류소 좌표
			for(var i =0 ; i<pointList.length; i++){
			    points.push(new daum.maps.LatLng(pointList[i].gpsY, pointList[i].gpsX));
			    busStopNm.push(pointList[i].stationNm);
			    busarsId.push(pointList[i].arsId);
			}console.log(points);console.log(busStopNm);


			// 지도를 재설정할 범위정보를 가지고 있을 LatLngBounds 객체를 생성합니다
			var bounds = new daum.maps.LatLngBounds();    
			
			var i, marker;
			for (i = 0; i < points.length; i++) {
			    // 배열의 좌표들이 잘 보이게 마커를 지도에 추가합니다
			    marker = new daum.maps.Marker({ position : points[i] });
			    marker.setMap(map);
			    
			    var iwContent = '<div style="padding:5px;"><p>'+busarsId[i]+'</p><b>'+busStopNm[i]+'</b></div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
			    iwPosition = new daum.maps.LatLng(33.450701, 126.570667); //인포윈도우 표시 위치입니다
			    
			    // 인포윈도우를 생성합니다
			    var infowindow = new daum.maps.InfoWindow({
			    	position : iwPosition, 
			    	content : iwContent 
			    });
			    
			    // 마커 위에 인포윈도우를 표시합니다. 두번째 파라미터인 marker를 넣어주지 않으면 지도 위에 표시됩니다
			    infowindow.open(map, marker);
			    
			    // LatLngBounds 객체에 좌표를 추가합니다
			    bounds.extend(points[i]);
			}
			
			

			function setBounds() {
			    // LatLngBounds 객체에 추가된 좌표들을 기준으로 지도의 범위를 재설정합니다
			    // 이때 지도의 중심좌표와 레벨이 변경될 수 있습니다
			    map.setBounds(bounds);
			}
		}
		
};