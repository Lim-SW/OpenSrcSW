package SimpleIR;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class makeKeyword {
	
	public Document kkma(Document doc) {
		NodeList n1 = doc.getElementsByTagName("body");
		//System.out.println(n1.getLength());
		
		for(int i=0;i<n1.getLength();i++) { // 형태소 작업
			Node temp = n1.item(i);
			String kkma = temp.getTextContent();
			KeywordExtractor ke = new KeywordExtractor();
			KeywordList kl = ke.extractKeyword(kkma, true);
			//System.out.println(kl);
			Keyword kwrd;
			kkma =""; // 빈 스트링문으로 초기화
			for(int j=0;j<kl.size();j++) { // 형태소 작업한 Body의 스트링문 합치기
				kwrd = kl.get(j);
				kkma += kwrd.getString()+":"+kwrd.getCnt()+"#";
			}
			temp.setTextContent(kkma); // 형태소 작업한 전체 body를 교체
		}
		
		return doc;
	}
	public Document getDoc(String PATH) {
		try {
			File xml = new File(PATH);
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(xml);
			doc.getDocumentElement().normalize();
			
			return doc;
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return null;
	}
}
