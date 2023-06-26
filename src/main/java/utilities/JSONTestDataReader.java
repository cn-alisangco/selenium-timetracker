package utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;


public class JSONTestDataReader {

	private Map<String, Object> testDataMap = new HashMap<String, Object>();


	public JSONTestDataReader(String testDataLocation, String id)  {
		String loc = System.getProperty("user.dir") + testDataLocation;
		JSONObject testDataObject = null;
		try {
			String contents = new String(Files.readAllBytes(Paths.get(loc)));
			JSONObject object = new JSONObject(contents);
			testDataObject = (JSONObject) object.get(id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		testDataMap = testDataObject.toMap();
	}

	public Map<String, Object> getTestData() {
		return testDataMap;
	}



}
