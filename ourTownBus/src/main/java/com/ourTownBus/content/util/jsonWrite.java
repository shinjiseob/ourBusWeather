package com.ourTownBus.content.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class jsonWrite {

	private List<HashMap<String, Object>> m = null;
	private HashMap< String, List<HashMap<String, Object>> > m2 = null;
	private HashMap<String, Object> hashMap = null;
	private ObjectMapper mapper = null;
	
	/**
	 * JSON을 Map으로 변환시켜 Map 타입으로 반환시킨다.
	 * @param (String)
	 * @return
	 */
	public HashMap<String, Object> jsonConvertMap(String json){
		mapper = new ObjectMapper();
		try{ hashMap = mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {}); }catch(Exception e){}
		
		return hashMap ;
	}
	
	/**
	 * JSON을 Map으로 변환시켜 List<HashMap<String, Object>> 타입으로 반환시킨다.
	 * @param jsonStr
	 * @return
	 */
	public List<HashMap<String, Object>> jsonConvertListMap(String json){
		mapper = new ObjectMapper();
		try{ m = mapper.readValue(json, new TypeReference<List<HashMap<String, Object>>>() {}); }catch(Exception e){}
		return m;
	}
	
	
	/**
	 * JSON을 Map으로 변환시켜 다시 List<hashMap<String,Object>> 타입으로 반환시킨다.
	 * @param jsonStr
	 * @return
	 */
	public List<HashMap<String, Object>> jsonConvertMapConvertListMap(String json){
		HashMap<String, Object> map2 = null;
		List<HashMap<String, Object>> m3 =null;
		mapper = new ObjectMapper();
		map2 = this.jsonConvertMap(json);
		
		m3  =(List<HashMap<String, Object>>) map2.get("matches");
		return m3;
	}
	
	
	/**
	 * 매개변수 Map으로 받아서 변환시켜  jsonMap 타입으로 반환시킨다.
	 * @param (String)
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public HashMap<String, Object> mapConvertJsonMap(HashMap<String, Object> map) throws JsonGenerationException, JsonMappingException, IOException{
		String json = "";
		mapper = new ObjectMapper();
		json = mapper.writeValueAsString(map);
		try{ hashMap = mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {}); }catch(Exception e){}
		return hashMap ;
	}
	
	
	/**
	 * Map을 JSON으로 변환시켜 String 타입으로 반환시킨다.
	 * @param map
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public String mapConvertJsonStr(HashMap<String, Object> map) throws JsonGenerationException, JsonMappingException, IOException{
		String json = "";
		mapper = new ObjectMapper();
		json = mapper.writeValueAsString(map);
		return json;
	}

}