package SimpleIR;

import org.w3c.dom.Document;
import SimpleIR.makeCollection;
import SimpleIR.makeKeyword;

public class kuir {
	public static void main(String[] args) {
		
		makeCollection mc = new makeCollection();
		makeKeyword mk = new makeKeyword();
		//String PATH = "./html";
		
		String COMP = args[0]; // -c -k -i
		String PATH = args[1]; // 폴더경로나 xml파일
		
		//System.out.println(COMP);
		//System.out.println(PATH);
		
		if(COMP.equals("-c")) { // 0번 인자값이 -c
			Document doc = mc.setXML(); // doc 초기파일 작성
			doc = mc.makeBody(doc, PATH); // body에 내용 채우기
			mc.makeXML("collection", doc); // XML파일 생성 (만들파일이름, doc)
			System.out.println("collection.xml 생성 완료");
		}
		
		else if(COMP.equals("-k")) { // 0번 인자값이 -k
			Document doc = mk.getDoc(PATH);
			doc = mk.kkma(doc); // 형태소 작업
			mc.makeXML("index",doc); // XML파일 생성 (형태소 작업 한거)
			System.out.println("index.xml 생성 완료");
		}
		
		//-i 추가 해야함 2021.03.25 실습날
		
		else { // 0번 인자값이 엉뚱한값
			System.out.println("args[0]에 올바른 인자값을 넣어주세요.");
			System.out.println("ex) -c , -k, -i");
		}
		
	}
}