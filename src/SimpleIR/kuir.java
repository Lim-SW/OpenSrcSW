package SimpleIR;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;

public class kuir {
	public static void main(String[] args) throws IOException {
		
		makeCollection mc = new makeCollection();
		makeKeyword mk = new makeKeyword();
		indexer ix = new indexer();
		searcher sc = new searcher();
		//String PATH = "./html";
		
		String COMP = args[0]; // -c -k -i
		String PATH = args[1]; // 폴더경로나 xml파일
		
		//System.out.println(COMP);
		//System.out.println(PATH);
		
		if(COMP.equals("-c")) { // 0번 인자값이 -c 2주차
			Document doc = mc.setXML(); // doc 초기파일 작성
			doc = mc.makeBody(doc, PATH); // body에 내용 채우기
			mc.makeXML("collection", doc); // XML파일 생성 (만들파일이름, doc)
			System.out.println("collection.xml 생성 완료");
		}
		
		else if(COMP.equals("-k")) { // 0번 인자값이 -k 3주차
			Document doc = mk.getDoc(PATH);
			doc = mk.kkma(doc); // 형태소 작업
			mc.makeXML("index",doc); // XML파일 생성 (형태소 작업 한거)
			System.out.println("index.xml 생성 완료");
		}
		
		else if(COMP.equals("-i")) { // 0번 인자값이 -i 4주차
			Document doc = mk.getDoc(PATH);
			ix.makeHashmap(doc); //index.xml
			System.out.println("index.post 생성 완료");
		}
		
		else if(COMP.equals("-s")) { // 0번 인자값이 -s 5주차
			String query = args[3]; // 질의어
			String PATH2 = PATH.replace("index.post", "collection.xml"); //같은경로에 collection.xml 있어야함
			Document doc = mk.getDoc(PATH2); //collection.xml 가져오기
			
			List<String> list = new ArrayList<>();
			list = sc.CalcSim(PATH, query, doc);
			//System.out.println(list);
			sc.scSort(list, doc);
			
		}
		
		else { // 0번 인자값이 엉뚱한값
			System.out.println("args[0]에 올바른 인자값을 넣어주세요.");
			System.out.println("ex) -c , -k, -i");
		}
		
	}
}