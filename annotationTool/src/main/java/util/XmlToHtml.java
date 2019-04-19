package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XmlToHtml {

	

	public static void main(String[] args) throws IOException{
		String test4 = "<?xml version=\"1.0\" ?><radiology_reports>  <report>    <O>Mammografie t,o,v , SVOB - opname 11/12/2015 ,</O>    <breast_composition>Mamma compositiebeeld C tot se ,</breast_composition>    <positive_finding>      <calcification>        <location>Beiderzijds</location>        een enkele        <morphology>afgeronde</morphology>        verkalking ,      </calcification>    </positive_finding>    <breast_composition>Redelijk goed beoordeelbaar mammogram ,</breast_composition>    <negative_finding>      <mass>        Geen        <margin>stellate</margin>        laesies ,      </mass>      <architectural_distortion>Geen circumscripte distorsies ,</architectural_distortion>      <mass>Geen evidente massa's ,</mass>      <calcification>        Geen        <distribution>clusters</distribution>        kalk ,      </calcification>    </negative_finding>    <O>geen maligniteitskenmerken</O>  </report></radiology_reports>";

		String structure = "[{\"text\":\"BIRADS\",\"icon\":\"images/network.png\",\"state\":{\"loaded\":true,\"opened\":true,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#ffffff\"},\"children\":[{\"id\":\"j2_2\",\"text\":\"positive_finding\",\"icon\":\"images/d6dbe5.png\",\"state\":{\"loaded\":true,\"opened\":true,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#d6dbe580\"},\"children\":[{\"id\":\"j2_5\",\"text\":\"mass\",\"icon\":\"images/99a582.png\",\"state\":{\"loaded\":true,\"opened\":true,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#99a58280\"},\"children\":[{\"id\":\"j2_7\",\"text\":\"location\",\"icon\":\"images/29639e.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#29639e80\"},\"children\":[],\"type\":\"default\"},{\"id\":\"j2_8\",\"text\":\"size\",\"icon\":\"images/46152c.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#46152c80\"},\"children\":[],\"type\":\"default\"},{\"id\":\"j2_9\",\"text\":\"margin\",\"icon\":\"images/18a0d0.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#18a0d080\"},\"children\":[],\"type\":\"default\"},{\"id\":\"j2_10\",\"text\":\"density\",\"icon\":\"images/b543a1.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#b543a180\"},\"children\":[],\"type\":\"default\"},{\"id\":\"j2_11\",\"text\":\"associated_features\",\"icon\":\"images/785110.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#78511080\"},\"children\":[],\"type\":\"default\"},{\"id\":\"j2_12\",\"text\":\"shape\",\"icon\":\"images/edccf1.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#edccf180\"},\"children\":[],\"type\":\"default\"}],\"type\":\"default\"},{\"id\":\"j2_6\",\"text\":\"calcification\",\"icon\":\"images/0b05e7.png\",\"state\":{\"loaded\":true,\"opened\":true,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#0b05e780\"},\"children\":[{\"id\":\"j2_13\",\"text\":\"location\",\"icon\":\"images/163786.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#16378680\"},\"children\":[],\"type\":\"default\"},{\"id\":\"j2_14\",\"text\":\"size\",\"icon\":\"images/fe02eb.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#fe02eb80\"},\"children\":[],\"type\":\"default\"},{\"id\":\"j2_15\",\"text\":\"morphology\",\"icon\":\"images/594c23.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#594c2380\"},\"children\":[],\"type\":\"default\"},{\"id\":\"j2_16\",\"text\":\"distribution\",\"icon\":\"images/19278e.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#19278e80\"},\"children\":[],\"type\":\"default\"},{\"id\":\"j2_17\",\"text\":\"associated_features\",\"icon\":\"images/70da27.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#70da2780\"},\"children\":[],\"type\":\"default\"}],\"type\":\"default\"},{\"id\":\"j2_18\",\"text\":\"architectural_distortion\",\"icon\":\"images/3d3a78.png\",\"state\":{\"loaded\":true,\"opened\":true,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#3d3a7880\"},\"children\":[{\"id\":\"j2_19\",\"text\":\"location\",\"icon\":\"images/31c9db.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#31c9db80\"},\"children\":[],\"type\":\"default\"},{\"id\":\"j2_20\",\"text\":\"associated_features\",\"icon\":\"images/288a55.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#288a5580\"},\"children\":[],\"type\":\"default\"}],\"type\":\"default\"},{\"id\":\"j2_21\",\"text\":\"associated_features\",\"icon\":\"images/546cad.png\",\"state\":{\"loaded\":true,\"opened\":true,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#546cad80\"},\"children\":[{\"id\":\"j2_22\",\"text\":\"location\",\"icon\":\"images/b69770.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#b6977080\"},\"children\":[],\"type\":\"default\"}],\"type\":\"default\"},{\"id\":\"j2_23\",\"text\":\"asymmetry\",\"icon\":\"images/8b3985.png\",\"state\":{\"loaded\":true,\"opened\":true,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#8b398580\"},\"children\":[{\"id\":\"j2_24\",\"text\":\"location\",\"icon\":\"images/203ea2.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#203ea280\"},\"children\":[],\"type\":\"default\"},{\"id\":\"j2_25\",\"text\":\"size\",\"icon\":\"images/75193a.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#75193a80\"},\"children\":[],\"type\":\"default\"},{\"id\":\"j2_26\",\"text\":\"associated_features\",\"icon\":\"images/8030d5.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#8030d580\"},\"children\":[],\"type\":\"default\"}],\"type\":\"default\"}],\"type\":\"default\"},{\"id\":\"j2_3\",\"text\":\"breast_composition\",\"icon\":\"images/9a4987.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#9a498780\"},\"children\":[],\"type\":\"default\"},{\"id\":\"j2_4\",\"text\":\"negative_finding\",\"icon\":\"images/19b4d9.png\",\"state\":{\"loaded\":true,\"opened\":true,\"selected\":true,\"disabled\":false},\"data\":{\"color\":\"#19b4d980\"},\"children\":[{\"id\":\"j2_27\",\"text\":\"mass\",\"icon\":\"images/19d67c.png\",\"state\":{\"loaded\":true,\"opened\":true,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#19d67c80\"},\"children\":[{\"id\":\"j2_28\",\"text\":\"location\",\"icon\":\"images/ac11f7.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#ac11f780\"},\"children\":[],\"type\":\"default\"},{\"id\":\"j2_29\",\"text\":\"margin\",\"icon\":\"images/f78807.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#f7880780\"},\"children\":[],\"type\":\"default\"}],\"type\":\"default\"},{\"id\":\"j2_30\",\"text\":\"calcification\",\"icon\":\"images/8697b8.png\",\"state\":{\"loaded\":true,\"opened\":true,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#8697b880\"},\"children\":[{\"id\":\"j2_31\",\"text\":\"location\",\"icon\":\"images/bd7a1d.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#bd7a1d80\"},\"children\":[],\"type\":\"default\"},{\"id\":\"j2_32\",\"text\":\"morphology\",\"icon\":\"images/4a0137.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#4a013780\"},\"children\":[],\"type\":\"default\"},{\"id\":\"j2_33\",\"text\":\"distribution\",\"icon\":\"images/6a6725.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#6a672580\"},\"children\":[],\"type\":\"default\"}],\"type\":\"default\"},{\"id\":\"j2_34\",\"text\":\"architectural_distortion\",\"icon\":\"images/922319.png\",\"state\":{\"loaded\":true,\"opened\":true,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#92231980\"},\"children\":[{\"id\":\"j2_35\",\"text\":\"location\",\"icon\":\"images/6b60d2.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#6b60d280\"},\"children\":[],\"type\":\"default\"}],\"type\":\"default\"},{\"id\":\"j2_36\",\"text\":\"associated_features\",\"icon\":\"images/4f1bfa.png\",\"state\":{\"loaded\":true,\"opened\":true,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#4f1bfa80\"},\"children\":[{\"id\":\"j2_37\",\"text\":\"location\",\"icon\":\"images/02bc6c.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#02bc6c80\"},\"children\":[],\"type\":\"default\"}],\"type\":\"default\"},{\"id\":\"j2_38\",\"text\":\"asymmetry\",\"icon\":\"images/399c79.png\",\"state\":{\"loaded\":true,\"opened\":true,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#399c7980\"},\"children\":[{\"id\":\"j2_39\",\"text\":\"location\",\"icon\":\"images/7bf0ad.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#7bf0ad80\"},\"children\":[],\"type\":\"default\"}],\"type\":\"default\"},{\"id\":\"j2_40\",\"text\":\"location\",\"icon\":\"images/09c27b.png\",\"state\":{\"loaded\":true,\"opened\":false,\"selected\":false,\"disabled\":false},\"data\":{\"color\":\"#09c27b80\"},\"children\":[],\"type\":\"default\"}],\"type\":\"default\"}],\"type\":\"default\"}]";		

		String finalres = convert(test4, structure);
		System.out.println(finalres);
	}
	

	public static String convert(String xml, String standard) {
		
		xml = xml.substring(42, xml.length() - 20);
		
		Document doc = convertStringToXMLDocument( xml );
		int level = 0;
		levels = new ArrayList<String>();
		json = standard;
		NodeList nodeList = doc.getDocumentElement().getChildNodes();
		printNode(nodeList, level, "");

		//inside report tags
		xml = xml.replace("\r", "");
		xml = xml.replace("\n", "");
		xml = xml.replace("  ", "");

		String pattern = "(<report>.*</report>)";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(xml);
		String a = "";
		while(m.find()){
			a = m.group(0);
		}

		a = a.replace("<report>","");
		a = a.replace("</report>","");
		
		String sent = "";
		pattern = "(<.*?>.*?)";
		r = Pattern.compile(pattern);
		m = r.matcher(a);
		ArrayList<String> kk = new ArrayList<>();
		String xmls = a;
		while(m.find()){
			kk.add(m.group(0));
		}

		int id = 0;
		int item = 0;
		
		for (int i = 0; i < kk.size(); i++) {
			String tag = kk.get(i);
			xmls = xmls.replaceFirst(tag, "");

			tag = tag.replace("<", "");
			tag = tag.replace(">", "");

			if(!tag.equals("O") && !tag.equals("/O")){
				if(!tag.contains("/")){
					String data = levels.get(item);
					item++;
					
					String[] datasplit = data.split(" ");
					String colourhex = datasplit[2];
					String treeid = datasplit[4];
					String name = datasplit[0];

					int red = Integer.valueOf(colourhex.substring( 1, 3 ), 16 );
					int blue = Integer.valueOf(colourhex.substring( 3, 5 ), 16 );
					int green = Integer.valueOf(colourhex.substring( 5, 7 ), 16 );
					
					sent = sent + "<span class=\""+ treeid + "\"" + " title=\"" + name  +"\" style=\"background:rgba(" + red + "," + green + "," + blue + ",0.5) none repeat scroll 0% 0%; border: medium none;\" id=\"" + id + "\">";
					id++;
				}else{
					sent = sent + "</span>";
				}
			} else if(tag.equals("O")) {
				item++;
			}

			String[] word = xmls.split("<");
			if(!word[0].isEmpty()){
				xmls = xmls.replaceFirst(word[0], "");
				sent = sent + word[0];
			}

		}

		return sent;
	}

	private static Document convertStringToXMLDocument(String xmlString)
	{
		//Parser that produces DOM object trees from XML content
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		//API to obtain DOM Document instance
		DocumentBuilder builder = null;
		try
		{
			//Create DocumentBuilder with default configuration
			builder = factory.newDocumentBuilder();

			//Parse the content to Document object
			Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
			return doc;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private static String json;
	private static List<String> levels;
	private static void printNode(NodeList nodeList, int level, String parentid) {
		level++;
		if (nodeList != null && nodeList.getLength() > 0) {
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					String substring = json;
					if(level != 1) {
						substring = StringUtils.substringAfter(substring, parentid);
					}
					
					String substringId = StringUtils.substringBefore(substring, node.getNodeName());
					substringId = StringUtils.substringAfterLast(substringId, "\"children\"");
					String id = StringUtils.substringBetween(substringId, "\"id\":\"", "\"");
					
					String element = StringUtils.substringAfter(json, "\"id\":\"" + id + "\"");
					String color = StringUtils.substringBetween(element, "\"data\":{\"color\":\"", "\"");

					levels.add(node.getNodeName() + " colour: " + color + " id: " + id );
					printNode(node.getChildNodes(), level, id);
				}

			}

		}

	}
}
