package com.ourTownBus.content.code;

import org.springframework.stereotype.Component;

@Component
public class pubSetCode {
	
	public static final String serviceKey = "serviceKey=%2FHzn6ZfPV3kzlqzDtRo7GdYwV2KDyyJEPXP%2FHXuUB8IV4uX56GPDYd4gNgRiovCO83UT33ZTh%2FnfXmmvcSJI3w%3D%3D";
	
	/**
	 * 0번째 정류소명 검색 / 1번째 정류소이름
	 * 1번째 0번 사용자 위치기반 정류소 검색/ 1번째 1번 경도/ 1번째 2번 위도/ 1번째 3번 검색반경 기본500
	 * 2번째 0번 입력받은 정류소Id 검색 
	 */
	public static String [][]busInfoUrl = {
		{"http://ws.bus.go.kr/api/rest/stationinfo/getStationByName?", "&stSrch="},
		{"http://ws.bus.go.kr/api/rest/stationinfo/getStationByPos?", "&tmX=", "&tmY=", "&radius=300"},
		{"http://ws.bus.go.kr/api/rest/stationinfo/getStationByUid?", "&arsId="}
	};

	

	/**
	 * 0번째 동네날씨정보
	 */
	public static String [][]weatherInfoUrl = {
		{"http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastSpaceData?", "&base_date=", "&base_time=", "&nx=", "&ny=", "&numOfRows=10&pageSize=10&pageNo=1&startPage=1&_type=json"}
	};
	
	

	/**
	 * 0번째 400
	 * 1번째 403
	 * 2번째 404
	 */
	public static String [][]errorCode = {
		{"ERROR 400 [ 잘못된 요청 ] 문제"},
		{"ERROR 401 [ key를 재대로 포함하지않음 ] 문제"},
		{"ERROR 403 [ ApiKey 만료 ] 문제"},
		{"ERROR 404 [ Not found summoner ] 문제"}
	};
	
	/**
	 * error 메시지를 받아 체크후 에러내용문구로 반환해준다.
	 * @param error
	 * @return
	 */
	public static String errorGbn(String error){
		String returnErrorStr = "";
		
		if(error.indexOf("400") > 0){ returnErrorStr = errorCode[0][0]; }
		else if(error.indexOf("401") > 0){ returnErrorStr = errorCode[1][0]; }
		else if(error.indexOf("403") > 0){ returnErrorStr = errorCode[2][0]; }
		else if(error.indexOf("404") > 0){ returnErrorStr = errorCode[3][0]; }
		
		return returnErrorStr;
	}
}
