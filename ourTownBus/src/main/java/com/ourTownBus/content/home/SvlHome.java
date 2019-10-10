package com.ourTownBus.content.home;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.jdom2.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ourTownBus.content.code.pubSetCode;
import com.ourTownBus.content.util.xmlRead;

@Controller
public class SvlHome {

	private pubSetCode pubsetCode;
	
	@Resource(name="xmlRead")
	private xmlRead xmlread;
	
	
	
	
	
	@RequestMapping(value = "/")
	public String Svlhome(){
		
		return "./page/home/home";
	}
	
	@RequestMapping(value = "/ArriveStationInfo")
	public String SvlArriveStationInfo(){
		return "./page/home/home";
	}
	

	
	
	
	/**
	 * 정류소를  키워드를 받아 LIKE의 정류소명을 반환해준다
	 * @param data
	 * @return
	 */
	@RequestMapping(value="/relationKeyword")
	public @ResponseBody StringBuffer Keyword(@RequestParam HashMap<String, Object> data){
		StringBuffer sb = null;
		List<?> busNmList = null;
		List<?> busStopIdList = null;
		String [] aRet = {"", ""}; aRet[0] = "0";
		String keyword = "";
		String urlPath = "";

		try {
			keyword = pubsetCode.busInfoUrl[0][1]+data.get("keyword").toString();
			urlPath = pubsetCode.busInfoUrl[0][0] + pubsetCode.serviceKey + keyword;
			busNmList = xmlread.getXmlRootList(urlPath, "getStationByName");
			
			keyword = pubsetCode.busInfoUrl[2][1]+data.get("keyword").toString();
			urlPath = pubsetCode.busInfoUrl[2][0] + pubsetCode.serviceKey + keyword;
			busStopIdList = xmlread.getXmlRootList(urlPath, "getStationByUid");
		} catch (Exception e) {}
		
		sb = mkView(busNmList, busStopIdList);
		return sb;
	}
	
	
	/**
	 * 화면에 보여질 HTML을 만든다
	 * @param busNumList
	 * @param busStopIdList
	 * @return
	 */
	public StringBuffer mkView(List<?> busNumList, List<?> busStopIdList){
		StringBuffer sb = null;
		int iList = 0;
		int iList2 = 0;
		try { iList = busNumList.size(); } catch (Exception e) {}
		try { iList2 = busStopIdList.size(); } catch (Exception e) {}
		
			sb = new StringBuffer();
			
			sb.append("<div class='col-md-12'>");
			sb.append(	"<ul class='nav nav-tabs nav-justified'>");
			sb.append(		"<li class='active'><a class='busOrStop' href='#bus_stop' data-toggle='tab'>정류소 명</a></li>");
			sb.append(		"<li><a class='busOrStop' href='#bus' data-toggle='tab'>정류소 ID</a></li>");
			sb.append(	"</ul>");
			sb.append(	"<div class='tab-content'>");
			sb.append(		"<div class='tab-pane fase' id='bus'>");
			sb.append(			"<div class='col-md-12'>");
			sb.append(				"<table style='margin-top: 6px; margin-Bottom: 6px;'>");
							if( iList2 > 0 ){
								for (int i = 0; i < 1; i++) {
								Element item = (Element) busStopIdList.get(i);
			sb.append(					"<tr class='tableHover' style='cursor:pointer' onclick=action.move_GetTrans('").append(item.getChildText("arsId")+"'").append(",'").append(item.getChildText("gpsX")).append("'").append(",'").append(item.getChildText("gpsY")).append("');>");
			sb.append(					"<td class='busData_font_godic' style='color : #BDBDBD;'>").append(item.getChildText("arsId")).append("</td>");
			sb.append(					"<td>&nbsp|&nbsp</td>");
			sb.append(					"<td class='font_godic'>").append(item.getChildText("stNm")).append("</td>");;
			sb.append(					"</tr>");
								}
							}else{ sb.append("<tr><td class='font_godic'>데이터가 존재하지 않습니다.</td></tr>"); }
			sb.append(				"</table>");
			sb.append(			"</div>");
			sb.append(		"</div>");
			sb.append(		"<div class='tab-pane fase in active' id='bus_stop'>");
			sb.append(			"<div class='row col-md-12'>");
			sb.append(				"<table style='margin-top: 6px; margin-Bottom: 6px;'>");
								if( iList > 0 ){
									for (int i = 0; i < iList; i++) {
										Element item = (Element) busNumList.get(i);
			sb.append(					"<tr class='tableHover' style='cursor:pointer' onclick=action.move_GetTrans('").append(item.getChildText("arsId")+"'").append(",'").append(item.getChildText("tmX")).append("'").append(",'").append(item.getChildText("tmY")).append("');>");
			sb.append(					"<td class='busData_font_godic' style='color : #BDBDBD;'>").append(item.getChildText("arsId")).append("</td>");
			sb.append(					"<td>&nbsp|&nbsp</td>");
			sb.append(					"<td class='font_godic'>").append(item.getChildText("stNm")).append("</td>");;
			sb.append(					"</tr>");
									}
								}else{ sb.append("<tr><td class='font_godic'>데이터가 존재하지 않습니다.</td></tr>"); }
			sb.append(				"</table>");
			sb.append(			"</div>");
			
			sb.append(		"</div>");
			sb.append(	"</div>");
			sb.append("</div>");
			
		return sb;
	}
	
	
}
