 package com.ourTownBus.content.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class xmlRead {
	
	private URL url          = null;
	private List<?> list     = null;
	private Element rootNode = null;
	
	
	/**
	 * XML Root msgBody List객체를 반환한다
	 * @param urlPath
	 * @param gbn
	 */
	public List<?> getXmlRootList(String urlPath, String gbn){
		
		try {
			url = new URL (urlPath);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("accept-language", "ko");
			
			SAXBuilder saxBuilder = new SAXBuilder();
			Document doc = saxBuilder.build(conn.getInputStream());
			
			rootNode = doc.getRootElement().getChild("msgBody");
			list = rootNode.getChildren();
			
		} catch (IOException e){ System.out.println(e.getMessage());}
		  catch (JDOMException jome){ System.out.println(jome.getMessage()); }
		
		return list;
	}
	
	
}