package casestudy.json.converter.main;

import casestudy.json.converter.def.XMLJSONConverterI;
import casestudy.json.converter.factory.ConverterFactory;

/**
 * @author Senthilkumar
 *
 */
public class JsonToXMLConverterMain {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		String jsonFilePath = null;
		
		String xmlFilePath = null;
		
		XMLJSONConverterI xmljsonConverterI = null;
		
		try {
			
			if (null == args || args.length != 2) {
				
				throw new Exception("Insufficient input");
			}
			
			jsonFilePath = args[0];
			xmlFilePath = args[1];
			
			xmljsonConverterI = ConverterFactory.getInstance();
			
			xmljsonConverterI.convertJSONtoXML(jsonFilePath, xmlFilePath);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
