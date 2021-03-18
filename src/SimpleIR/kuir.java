package SimpleIR;

import org.w3c.dom.Document;
import SimpleIR.makeCollection;
import SimpleIR.makeKeyword;

public class kuir {
	public static void main(String[] args) {
		
		makeCollection mc = new makeCollection();
		makeKeyword mk = new makeKeyword();
		//String PATH = "./html";
		String PATH = args[0];
		
		if(PATH.contains(".xml")) { // xml 파일로 인자값 줄시
			Document doc = mk.getDoc(PATH);
			doc = mk.kkma(doc); // 형태소 작업
			mc.makeXML("index",doc); // XML파일 생성 (형태소 작업 한거)
			System.out.println("index.xml 생성 완료");
		}
		
		else { // html 디렉토리 // html 디렉토리로 인자값 줄시
			Document doc = mc.setXML(); // doc 초기파일 작성
			doc = mc.makeBody(doc, PATH); // body에 내용 채우기
			mc.makeXML("collection", doc); // XML파일 생성 (만들파일이름, doc)
			System.out.println("collection.xml 생성 완료");
		}
		
	}
}