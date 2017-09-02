package Sterling.SterlingEnvValidation;

import org.testng.annotations.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.describedAs;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
public class SterlingXMLValidation {
	
	Node nNode;
	Element eElement;
	Map<String, String> hmap = new HashMap<>();
	String attribute_02 = "Name";
	String attribute_04 = "Status";
	String actualName;
	String expected;
	String actual;
	String actualStatus;
	String expectedStatus;
	String Name;
	String Status;
	String csvFile = "./resources/ServicesList.csv";
	
	@BeforeTest
	public void parseXML() throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
		File fXmlFile = new File("./resources/SterlingServices.xml");
		if (fXmlFile.exists()) {
			Document doc = dbBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("Server");

			String attribute_02 = "Name";
			String attribute_04 = "Status";
			
			

			if (nList != null && nList.getLength() > 0)
				for (int i = 0; i < nList.getLength(); i++) {
					nNode = nList.item(i);
					eElement = (Element) nNode;
					
					Name = eElement.getAttribute(attribute_02);
					Status = eElement.getAttribute(attribute_04);
					
					hmap.put(Name, Status);
					
					int count = hmap.size();
					System.out.println(count);

				}

		}
	}

	
	@Test(dataProvider = "dp")
	public void validateXML(String ServiceName, String ServiceStatusExpect) {

		String ServiseStatusActual = hmap.get(ServiceName);
		final String addInfo = "Interval: every 5 min. Desc.: Sterling Services Status.";

		assertThat(ServiseStatusActual,
				describedAs(addInfo + " Expected 'Active' status for ServiceStatusExpected [" + ServiceName + "],",
						allOf(notNullValue(), is ("Active"))));
		
		
			}
	
	
	@DataProvider(name = "dp")

	public Iterator<String[]> american() throws InterruptedException, IOException {

		String csvLine = "";

		String[] a = null;

		ArrayList<String[]> al = new ArrayList<>();

		BufferedReader br = new BufferedReader(new FileReader(csvFile));

		while ((csvLine = br.readLine()) != null) {

			a = csvLine.split(",");

			al.add(a);

		}
		
		br.close();
		return al.iterator();
	}

}



