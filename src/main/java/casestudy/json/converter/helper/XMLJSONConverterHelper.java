package casestudy.json.converter.helper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import casestudy.json.converter.constants.XMLJSONConverterConstants;
import casestudy.json.converter.util.XMLJSONConverterUtil;

public class XMLJSONConverterHelper implements XMLJSONConverterConstants {

	public static LinkedHashMap<Object, Object> toOrderedMap(String filePath) {
		LinkedHashMap<Object, Object> jsonMap = null;
		FileReader fileReader = null;

		try {

			if (XMLJSONConverterUtil.isFileExists(filePath)) {

				fileReader = new FileReader(filePath);

				ObjectMapper objectMapper = new ObjectMapper();

				jsonMap = objectMapper.readValue(fileReader, LinkedHashMap.class);

				fileReader.close();

			} else {
				throw new FileNotFoundException("File is not availabele : file Name : " + filePath);
			}

		} catch (FileNotFoundException fiNotExcep) {
			fiNotExcep.printStackTrace();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonMap;
	}



	private static StringBuilder convertJsonIntoXML(Object jsonObject, Object jsonAttributeName) {

		StringBuilder xml = new StringBuilder(EMPTY);

		if (null == jsonObject) {

			xml = xml.append(prepareXML(NULL, null, jsonAttributeName, true));

		} else if (jsonObject instanceof String) {

			xml = xml.append(prepareXML(STRING, jsonObject, jsonAttributeName, true));

		} else if (jsonObject instanceof Integer || jsonObject instanceof BigDecimal
				|| jsonObject instanceof Float || jsonObject instanceof Double) {

			xml = xml.append(prepareXML(NUMBER, jsonObject, jsonAttributeName, true));

		} else if (jsonObject instanceof Boolean) {

			xml = xml.append(prepareXML(BOOLEAN, jsonObject, jsonAttributeName, true));

		}  else if (jsonObject instanceof Map)  {

			xml = xml.append(prepareXML(OBJECT, jsonObject, jsonAttributeName, false));

			for (Object hashMapKey : ((Map) jsonObject).keySet()) {

				xml = xml.append(convertJsonIntoXML(((Map) jsonObject).get(hashMapKey), hashMapKey));

			}
			xml = xml.append(END_TAG.replace(TAG, OBJECT));

		} else if (jsonObject instanceof List) {


			xml = xml.append(prepareXML(ARRAY, jsonObject, jsonAttributeName, false));

			for (Object object : (List)jsonObject) {

				xml  = xml.append(convertJsonIntoXML(object, null));
			}

			xml = xml.append(END_TAG.replace(TAG, ARRAY));

		}

		return xml;

	}

	
	public static void writeXmlIntoFile(String filePath, String data) throws IOException {
		
		FileWriter fileWriter = null;
		
		if (XMLJSONConverterUtil.isValidFilePath(filePath)) {
			
			fileWriter = new FileWriter(filePath);
			fileWriter.write(data);
			fileWriter.flush();
			fileWriter.close();
			
		} else {
			throw new IOException("Invalid File Path : " + filePath);
		}
	}

	public static String prepareXML(String tagName, Object value, Object attributeName, boolean isEndTagRequired) {

		String xml = EMPTY;

		if (null == value) {

			if  (null == attributeName) {
				xml =  NULL_VALUE_WITHOUT_ATTR;
			} else {
				xml = NULL_VALUE_WITH_ATTR;
				xml = xml.replace(ATTR, attributeName.toString());
			}
			xml = xml.replace(TAG, tagName);

			return xml;
		}


		if (null == attributeName) {
			xml = "<" + tagName + ">";

		} else {
			xml = "<" + tagName + " name=\"" + attributeName + "\">";
		}

		if (isEndTagRequired) {
			xml = xml + value +"</" + tagName + ">";
		}


		return xml;
	}

}
