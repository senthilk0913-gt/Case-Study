package casestudy.json.converter.util;

import java.io.File;
import java.nio.file.Paths;
import java.util.Map;

public class XMLJSONConverterUtil {

	public static boolean isFileExists(String filePath) {

		File file = new File(filePath);

		return file.exists();
	}

	public static boolean isValidFilePath(String filePath) {
		try {
			Paths.get(filePath);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public static boolean isNull(Object obj) {
		
		if (null == obj) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isMapNullOrEmpty(Map map) {
		
		if (null == map || map.isEmpty()) {
			return true;
		}
		
		return false;
		
	}

}
