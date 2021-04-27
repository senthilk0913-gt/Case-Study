package casestudy.json.converter.def.impl;

import static casestudy.json.converter.helper.XMLJSONConverterHelper.toOrderedMap;
import static casestudy.json.converter.helper.XMLJSONConverterHelper.writeXmlIntoFile;
import static casestudy.json.converter.helper.XMLJSONConverterHelper.prepareXML;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import casestudy.json.converter.constants.XMLJSONConverterConstants;
import casestudy.json.converter.def.XMLJSONConverterI;
import casestudy.json.converter.util.XMLJSONConverterUtil;


public class XMLJSONConverterImpl implements XMLJSONConverterI, XMLJSONConverterConstants {

	public void convertJSONtoXML(String JsonFilePath, String xmlFilePath) throws Exception {
		
		LinkedHashMap<Object, Object> jsonMap = null;
		String convertedXML = XMLJSONConverterConstants.EMPTY;
		
		jsonMap = toOrderedMap(JsonFilePath);
		
		if (XMLJSONConverterUtil.isMapNullOrEmpty(jsonMap))
			throw new Exception("Unable to Parse JSON");
		
		convertedXML = toCustomXml(jsonMap, null).toString();
		
		writeXmlIntoFile(xmlFilePath, convertedXML);
		
	}
	
	
	private StringBuilder toCustomXml(Object jsonObject, Object jsonAttributeName) {

		StringBuilder xml = new StringBuilder(EMPTY);

		if (null == jsonObject) {

			xml.append(prepareXML(NULL, null, jsonAttributeName, true));

		} else if (jsonObject instanceof String) {

			xml.append(prepareXML(STRING, jsonObject, jsonAttributeName, true));

		} else if (jsonObject instanceof Integer 
				|| jsonObject instanceof BigInteger 
				|| jsonObject instanceof BigDecimal
				|| jsonObject instanceof Float 
				|| jsonObject instanceof Long
				|| jsonObject instanceof Double) {

			xml.append(prepareXML(NUMBER, jsonObject, jsonAttributeName, true));

		} else if (jsonObject instanceof Boolean) {

			xml.append(prepareXML(BOOLEAN, jsonObject, jsonAttributeName, true));

		}  else if (jsonObject instanceof Map)  {

			xml.append(prepareXML(OBJECT, jsonObject, jsonAttributeName, false));

			for (Object key : ((Map) jsonObject).keySet()) {

				xml.append(toCustomXml(((Map) jsonObject).get(key), key));

			}
			xml.append(END_TAG.replace(TAG, OBJECT));

		} else if (jsonObject instanceof List) {


			xml.append(prepareXML(ARRAY, jsonObject, jsonAttributeName, false));

			for (Object object : (List)jsonObject) {

				xml.append(toCustomXml(object, null));
			}

			xml.append(END_TAG.replace(TAG, ARRAY));

		}

		return xml;

	}

}
