package SimpleIR;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class makeCollection {
	public Document setXML() {
		try {
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
			Document doc = docBuilder.newDocument();
			
			Element docs = doc.createElement("docs");
			doc.appendChild(docs);
			
			return doc;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	public Document appendXML(Document doc, int num, String name, String txt) {
		NodeList n1 = doc.getElementsByTagName("docs");
		Node temp = n1.item(0);
		Element docs = (Element)temp;
		
		Element docId = doc.createElement("doc");
		docs.appendChild(docId);
		
		docId.setAttribute("id", Integer.toString(num));
		
		Element title = doc.createElement("title");
		title.appendChild(doc.createTextNode(name)); // 여기에 html 파일이름
		docId.appendChild(title);
		
		Element body = doc.createElement("body");
		body.appendChild(doc.createTextNode(txt)); // 여기에 내용
		docId.appendChild(body);
		
		return doc;
	}
	public Document makeBody(Document doc, String PATH) {
		File folder = new File(PATH); // 폴더 불러오기
		File[] list = folder.listFiles(); // 파일들 배열에 저장
 
		for(int i=0;i<list.length;i++) { // 파일 갯수만큼 반복해서 Append
			File file = list[i];
			String tempName = file.getName(); // 확장자 포함 파일명 저장
			
			int Idx = tempName.lastIndexOf("."); // 확장자 제거
			String name = tempName.substring(0,Idx); // name에 파일명저장
			
			try {
				FileReader fr = new FileReader(file);
				BufferedReader bufReader = new BufferedReader(fr);
	            String line = "";
	            String body = "";
	            while((line = bufReader.readLine()) != null){
	                body+=line;
	            }
	            
	            int setIdx = 0;
	            setIdx = 16 + name.length();
	            body = tagFilter(body);
	            body = body.substring(23+setIdx, body.length());
				
				doc = appendXML(doc, i, name, body); // doc에 body 내용 추가
	            bufReader.close();
	            
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return doc;
	}
	public void makeXML(String fileName,Document doc) {
		
		try {		
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new FileOutputStream(new File("./"+fileName+".xml")));
			
			transformer.transform(source, result);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public String tagFilter(String str){
	    if(str != null && !str.equals("")){
	        str = str.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
	    }else{
	        str = "";
	    }
	    return str;
	}
}
