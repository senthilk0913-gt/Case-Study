Build Project
	1. Open Eclipse IDE
	2. Select File -> import -> option
	3. Select Maven Projects Option. Click on Next Button
	4. Select Project location of the project.
	5. Click Finish Button
	6. Now, We can verify the project in Eclipse.
	7. In the build path of the corresponding project, we can see the supporting libraries.
	8. Right Click on consumerBanking project to open context menu.
	9. Select Run as option and select Run Configurations.
	10. Enter "clean install" in the Goals section.
	11. Click Apply and run. It will start building the project.
	12. After successful completion of the build, we can see the generated Jar named "CustomJsonParser-1.0.jar" in the target folder.

Run the code:
	1. pom.xml has been configured to build a runnable jar.
	2. It will generate the jar with its dependencies in the folder target named "CustomJsonParser-1.0.jar" in the target folder.
	3. We can run the code using the below command in CLI
		java -jar CustomJsonParser-1.0.jar "<Json FilePath>" "<XML File Path>".
	4. Our code will convert the given valid json into the xml in the given location. 
	  
 External Libraries:
 
 	We have utilized the below external API's as dependencies to parse the JSON. You can validate it in pom.xml
 	
 		<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core -->
 		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-core</artifactId>
			<version>2.12.3</version>
		</dependency>
		
		<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-databind</artifactId>
			<version>2.12.3</version>
		</dependency>
		
Purpose:
	1. We have developed this project to convert any given json into an custom xml format.
	2. The converted XML format should contain the following the features.
		i. name of the xml tag for any json attribute should be the json attribute type.
			a. String data type to be coverted into <string>
			b. Integer, Float, Double, BigDecimal should be converted to <number>
			c. List should be converted into <array>
			d. Map should be converted into <object>
			c. Boolean should be converted into <boolean>
			d. Null value should be converted into <null> 
		ii. Json Attribute name should be converted into its corrsponding xml tags attribute name.
		iii.The root element should be Object.
		iv. Child xml of array tag should not have any attribute name.
		
Logic:
	There is no straight forward API available to fit into the above requirement.But there are some APIs available in the market to convert the json into map.
	Here we use Jackson Json parser API/ObjectMapper class to convert the json data into Map.
	
	1. System read the json input file using FileReader.
	2. Then json is converted into an object of LinkedHashMap (to maintain the json attribute order) using ObjectMapper.class of Jackson API.
				FileReader fileReader = new FileReader(filePath);

				ObjectMapper objectMapper = new ObjectMapper();

				LinkedHashMap jsonMap = objectMapper.readValue(fileReader, LinkedHashMap.class); 
	3. Json now converted into key, value pairs in the same order like json.
	4. Then jsonMap will be iterated using recursive method named toCustomXml() which takes input as json attribute value and json attribute name. It will return converted xml.
	   toCustomXML() method will perform the following operation.
	   
	   i. if the json attribute value is an instance of String, it will be converted into xml as below.
	   		a. if attribute name is null
	   			<string>objectValue</string>
	   		b. if attribute name is not null 
	   			<string name="attributeName">objectValue</string>
	   	ii. if the json attribute value is an instance of integer/float/BigDecimal/Double, it will be converted into xml as below.
	   		a. if attribute name is null
	   			<number>objectValue</number>
	   		b. if attribute name is not null 
	   			<number name="attributeName">objectValue</number>
	   	iii. if the json attribute value is an instance of Boolean, it will be converted into xml as below.
	   		a. if attribute name is null
	   			<boolean>objectValue</number>
	   		b. if attribute name is not null 
	   			<boolean name="attributeName">objectValue</boolean>
	   	iv. if the json attribute value is null, it will be converted into xml as below.
	   		a. if attribute name is null
	   			<null/>
	   		b. if attribute name is not null 
	   			<null name="attributeName"/>
	   	v. if the json attribute value is an instance of List, it will be converted into xml as below.
	   		a. if attribute name is null
	   			<array>
	   		b. if attribute name is not null 
	   			<array name="attributeName">
	   		c. Then system will iterate the List and call the toCustomXML() by passing object value and attribute name as null.
	   		d. After the completion of the iteration and end tag will be concatenated at the end </array>
	   	vi. if the json attribute value is an instance of Map, it will be converted into xml as below.
	   		a. if attribute name is null
	   			<array>
	   		b. if attribute name is not null 
	   			<object name="attributeName">
	   		c. Then system will iterate the map and call the toCustomXML() by passing object value (value) and attribute name (key).
	   		d. After the completion of the iteration and end tag will be concatenated at the end </object>
	   	
	   	5. After successful creation of the xml, system will write the converted xml into the path given in the input.
	   	
Test Input File:

	To test the project, we can utilize the sample jsons placed inside src/test/resources/ folder.
	   	
	   
	