package casestudy.json.converter.factory;

import casestudy.json.converter.def.XMLJSONConverterI;
import casestudy.json.converter.def.impl.XMLJSONConverterImpl;

public class ConverterFactory {
	
	
	public static XMLJSONConverterI getInstance() {
		
		return new XMLJSONConverterImpl();
		
	}

}
