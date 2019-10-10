package com.ourTownBus.content.bus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.jdom2.Element;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ourTownBus.content.code.pubSetCode;
import com.ourTownBus.content.util.jsonWrite;
import com.ourTownBus.content.util.xmlRead;

@Controller
public class SvlBus {

	private pubSetCode pubsetCode;
	
	@Resource(name="xmlRead")
	private xmlRead xmlread;
	
	@Resource(name="jsonWrite")
	private jsonWrite jsonwrite;
	
	
	@RequestMapping(value="/locationBusStop")
	public String getMyLocationBubStop(@RequestParam HashMap<String, Object> data, Model model){
		String tmY = "";
		String tmX = "";
	
		try {
			tmY = data.get("tmy").toString();
			tmX = data.get("tmx").toString();
		} catch (Exception e) {}
		
		model.addAttribute("tmx", tmX);
		model.addAttribute("tmy", tmY);
		
		return "./page/bus/myLocationbusStop";
	}
	
	
	@RequestMapping(value="/busdetail")
	public String busDetail(@RequestParam HashMap<String, Object> data, Model model){
		List<?> busStopIdList = null;
		StringBuffer sb = null;
		String urlPath = "";
		String arsId = "";
		
		try {
			arsId   = pubsetCode.busInfoUrl[2][1] + data.get("arsId"); 
			urlPath = pubsetCode.busInfoUrl[2][0] + pubsetCode.serviceKey + arsId;
			busStopIdList = xmlread.getXmlRootList(urlPath, "getStationByUid");
			sb = mkView(busStopIdList);
		} catch (Exception e) {}
		
		
		model.addAttribute("tmx", data.get("tmx"));
		model.addAttribute("tmy", data.get("tmy"));
		model.addAttribute("bus_info", sb);
		return "./page/bus/busDetail";
	}
	
	
	/**
	 * 300M 이내의  내 주변 정류소를 찾는다 
	 * @param data
	 * @return
	 */
	@RequestMapping(value="/locationBusStopAajx")
	public @ResponseBody ArrayList<HashMap<String, Object>> locationBusStopAajx(@RequestParam HashMap<String, Object> data){
		String urlPath = "";
		urlPath = pubsetCode.busInfoUrl[1][0] + pubsetCode.serviceKey + pubsetCode.busInfoUrl[1][1] + data.get("tmx") + pubsetCode.busInfoUrl[1][2] + data.get("tmy") + pubsetCode.busInfoUrl[1][3];
		
		List<?> locationBusStopList = null;
		HashMap<String, Object> hash = new HashMap<String, Object>();
		ArrayList<HashMap<String, Object>> m = new ArrayList<HashMap<String,Object>>();
		try {
			locationBusStopList = xmlread.getXmlRootList(urlPath, "getStationByUid");
			
			for (int i = 0; i < locationBusStopList.size(); i++) {
				Element item = (Element) locationBusStopList.get(i);
				hash.put("arsId", item.getChildText("arsId"));
				hash.put("gpsX", item.getChildText("gpsX"));
				hash.put("gpsY", item.getChildText("gpsY"));
				hash.put("stationNm", item.getChildText("stationNm"));

				m.add(jsonwrite.mapConvertJsonMap(hash));
			}
		} catch (Exception e) {}
		
		System.out.println("주변 정류소 데이터 : "+m);
		return m;
	}
	
	
	
	/**
	 * 버스 대기 시간 HTML 생성
	 * @param bus_arriveList
	 * @return
	 */
	public StringBuffer mkView(List<?> bus_arriveList){
		List<?> arriveList = null;
		Date date = new Date();
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sDate=  new SimpleDateFormat("YYYY-MM-dd HH시 mm분 ss초 ");
		
		int iArriveList = 0;
		String realDate = sDate.format(date);
		
		arriveList = bus_arriveList;
		iArriveList = arriveList.size();
		
		if(iArriveList > 0){
			sb.append("<div class='row'>");
			sb.append("<div class='col-md-12 Box'>");
			sb.append(	"<table class='text-left'>");
			for(int i =0; i < 1; i++){
				Element item = (Element) arriveList.get(i);
				String nextNm ="";
				try {
					if(item.getChildText("nxtStn").equals(" ") || item.getChildText("nxtStn")==null){ nextNm = item.getChildText("adirection");}
					else{ nextNm = item.getChildText("nxtStn"); }
				} catch (Exception e) {}
				
				sb.append(		"<tr class='bus_Info_font_godic'>");
				sb.append(			"<td>"+item.getChildText("stNm")+"</td>");
				sb.append(			"<td>(&nbsp;"+item.getChildText("arsId")+"&nbsp;)</td>");
				sb.append(		"</tr>");
				sb.append(		"<tr class='bus_Info_font_godic'>");
				sb.append(			"<td>"+nextNm+" 방면</td>");
				sb.append(			"<td><img src='./resources/img/bus/right.png' style='height: 15px;'/></td>");
				sb.append(		"</tr>");
				sb.append(		"<tr class='bus_Info_font_godic'>");
				sb.append(			"<td><img onclick=location.reload(true); src='./resources/img/bus/newWebPage.png' style='cursor:pointer'/>&nbsp;&nbsp;"+realDate+"</td>");
				sb.append(		"</tr>");
			}
			sb.append(	"</table>");
			sb.append("</div>");
			
			for(int i =0; i < iArriveList; i++){
				Element item = (Element) arriveList.get(i);
				sb.append("<div class='col-md-12 Box' style='margin-top: 4px; min-height: 55px;'>");
				sb.append(	"<table>");
				sb.append(		"<tr class='busData_font_godic' style='font-size: 14pt;'>");
				sb.append(			"<td><strong>"+item.getChildText("rtNm")+"</strong></td>");
				sb.append(			"<td style=' right: 0; position: absolute;'>"+item.getChildText("arrmsg1")+"</td>");
				sb.append(		"</tr>");
				sb.append(		"<tr class='busData_font_godic'>");
				sb.append(			"<td style='left : 75%;top: 30px;position: absolute;'>"+item.getChildText("arrmsg2")+"</td>");
				sb.append(		"</tr>");
				sb.append(	"</table>");
				sb.append("</div>");
			}
				sb.append("</div>");
		}else{
			sb.append("<div class='row'>");
			sb.append("<h3 class='bus_Info_font_godic'>시스템 관련 오류 ( 전산실에 문의해주세요 )</h3>");
			sb.append("</div>");
		}
		  
		return sb;
	}
	
	
	
}
