package SimpleIR;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Document;

public class genSnippet {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//라면 밀가루 달걀 밥 생선 라면 물 소금 반죽 첨부 봉지면 인기 초밥 라면 밥물 채소 소금 초밥 종류 활어
		//-f input.txt -q "키워드열"
		//스트링 쪼개서 리스트에 넣고 리스트 인덱스로 불러와서 비교한뒤 카운트 해서 가장 높은 카운트의 인덱스 출력
		
		
		
		//구현목록입니다.
		//인풋으로 받은 스트링을 공백단위로 구분해서 리스트로 저장하는것 구현
		//텍스트파일 불러와서 라인별로 리스트에 저장하는것 구현
		//라인별로 저장된 텍스트를 공백단위로 구분해서 비교할때 사용할수있도록함
		// 가장 유사도 높은 라인 출력
		
		String COMP = args[0];
		String PATH = args[1];
		String TEXT = args[3]; 
		
		if(COMP.equals("-f")) { // 0번 인자값이 -c 2주차
			try {
			File input = new File(PATH);
			FileReader fr = new FileReader(input);
			BufferedReader bufReader = new BufferedReader(fr);
			
			List textlist = new ArrayList();
			String line = "";
            while((line = bufReader.readLine()) != null){ // 각 줄마다 리스트에 저장
                textlist.add(line);
            }
            //System.out.println(textlist);
            
            int best = 0; //여기에 제일 유사도 높은라인
            int index = 0;
            //List inputlist = new ArrayList();
            List<String> inputlist = new ArrayList<>(Arrays.asList(TEXT.split(" ")));
            //System.out.println(inputlist);
            for(int i=0;i<inputlist.size();i++) {
            	for(int j=0;j<textlist.size();j++) {
            		int count = 0;
            		String temp = textlist.get(j).toString();
            		List<String> templist = new ArrayList<>(Arrays.asList(TEXT.split(" ")));
            		for(int k=0;k<templist.size();k++) {
            			if(templist.get(k).equals(textlist.get(j))) {
                			count++;
                		}
            		}
            		if(best<count) {
            			best = j; //가장 유사도 높은라인
            		}
            		else if(best == count) {
            			
            		}
            		
            	}
            }
			
            System.out.println(textlist.get(best)); // 가장 유사도 높은 라인 출력
            
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
