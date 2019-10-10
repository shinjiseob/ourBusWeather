package com.ourTownBus.content.weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ourTownBus.content.code.pubSetCode;
import com.ourTownBus.content.util.jsonWrite;

@Controller
public class SvlWeather {

	@Resource(name="jsonWrite")
	private jsonWrite jsonwrite;
	private pubSetCode pubsetcode;
	
	
	@RequestMapping(value="/getWeatherData")
	public @ResponseBody HashMap<String, Object> getWeatherData(@RequestParam HashMap<String, Object> data){
		System.out.println(data);
		String line = "";
		String urlPath = "";
		String resultStr = "";
		
		try {
			  urlPath = pubsetcode.weatherInfoUrl[0][0] + pubsetcode.serviceKey
			  + pubsetcode.weatherInfoUrl[0][1]+data.get("base_date") + pubsetcode.weatherInfoUrl[0][2] + data.get("base_time")
			  + pubsetcode.weatherInfoUrl[0][3] + data.get("tmx") + pubsetcode.weatherInfoUrl[0][4]
			  + data.get("tmy") + pubsetcode.weatherInfoUrl[0][5];
		} catch (Exception e) {}
		
		System.out.println(urlPath);
		BufferedReader br = null;
		HashMap<String, Object> hash = new HashMap<String, Object>();
		
		try {
			URL cUrl = new URL(urlPath);
			HttpURLConnection conn = (HttpURLConnection) cUrl.openConnection();
			conn.setRequestMethod("GET");
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while ((line = br.readLine()) != null) { resultStr = resultStr + line +"\n"; }
			hash = jsonwrite.jsonConvertMap(resultStr);
			
		} catch (Exception e){  }

		return hash;
	}
}
