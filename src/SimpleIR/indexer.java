package SimpleIR;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class indexer {
	
	public List Filter(String tempDoc){ // index.xml 에서 형태소만 짤라서 리스트에 저장
		int Idx1, Idx2;
		String strText, numText;
		List array = new ArrayList();
		
		while(tempDoc.isEmpty()==false) { // 한글자 한숫자 씩 앞에서부터 자르는 방식
			Idx1 = tempDoc.indexOf(":");
			strText = tempDoc.substring(0, Idx1); // 한글만
			//System.out.println(strText);
			tempDoc = tempDoc.replaceFirst(strText, "");
			tempDoc = tempDoc.replaceFirst(":", "");
			//System.out.println(tempDoc);
			Idx2 = tempDoc.indexOf("#");
			numText = tempDoc.substring(0, Idx2); // 갯수만
			//System.out.println(numText);
			tempDoc = tempDoc.replaceFirst(numText, "");
			tempDoc = tempDoc.replaceFirst("#", "");
			//System.out.println(tempDoc);
			array.add(strText);
		}
	    
	    return array;
	}
	
	public List calculateWeight(List DocArr, String[][] BigArr) {
		double tf, df, N; // 문서에서의 단어 빈도수 , 몇개의 문서에서의 빈도수, 전체문서 수
		double w = 0; // weight 초기화
		N = DocArr.size(); // 리스트의 사이즈가 곧 전체 문서의 수
		tf = 0; // tf값 초기화
		df = 0; // df값 초기화
		int Idx1, Idx2; // String 작업을 위한 인덱스
		String strText, numText; // strText == 글자, numText == 나온빈도수
		
		List Wlist = new ArrayList();
		//List TextList = new ArrayList();
		
		String Sample = "";
		/*
		//틀 만들기
		for(int l=0;l<N;l++) {
			if(l==0) {
				Sample+=l+" "+"0.0";
			}
			else {
				Sample+=" "+l+" "+"0.0";
			}
		}
		//System.out.println(Sample); // doc 갯수만큼 0.0 으로 초기화
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<BigArr[i].length;j++) {
				String forBig = BigArr[i][j];
				if(Wlist.contains(forBig)) { // 이미있으면 초기화 안해도 됨
				}
				else {
					Wlist.add(forBig); // 한글 , sample 추가해서 틀 작성
					Wlist.add(Sample);
				}
			}
		}
		*/ // 0 안넣어도 되어서 필요 없어짐 ㅠ.ㅠ
		/*
		for(int i=0;i<Wlist.size()-5;i+=6) { //리스트에 잘 저장되어있는지 확인
			System.out.println(Wlist.get(i));
			System.out.print(Wlist.get(i+1).toString());
			System.out.println();
		}
		*/
		
		for(int i=0;i<N/*DocArr.size()*/;i++) {
			
			String tempDoc = DocArr.get(i).toString();
			//System.out.println(tempDoc);
			while(tempDoc.isEmpty()==false) {
				Idx1 = tempDoc.indexOf(":");
				strText = tempDoc.substring(0, Idx1); // 한글만
				//System.out.println(strText);
				tempDoc = tempDoc.replaceFirst(strText, "");
				tempDoc = tempDoc.replaceFirst(":", "");
				//System.out.println(tempDoc);
				Idx2 = tempDoc.indexOf("#");
				numText = tempDoc.substring(0, Idx2); // 빈도수만
				//System.out.println(numText);
				tempDoc = tempDoc.replaceFirst(numText, "");
				tempDoc = tempDoc.replaceFirst("#", "");
				//System.out.println(tempDoc);
				tf=Double.parseDouble(numText); // tf값 = 한 문서에서의 빈도수(numText)
				for(int j=0;j<N;j++) { // N개의 문서 전부 확인
					for(int k=0;k<BigArr[j].length;k++) { // 문서안의 모든 단어들로 접근(BigArr)
						if(BigArr[j][k].equals(strText)) { // 단어가 들어있는 문서가 있을때 마다 +1
							df++;
						}
					}
				}
				/*
				if(df==0) {
					System.out.println(strText); // 오류 확인용 if문
					System.out.println(df);
				}
				*/
				w = tf*Math.log(N/df); // w 값 계산
				//System.out.print(strText);
				//System.out.print(" N:"+N); // 값 확인용
				//System.out.print(" tf:"+tf);
				//System.out.print(" df:"+df);
				//System.out.println();
				//System.out.println(strText+" w:"+w);
				/*
				if(w==0) {
					System.out.println(strText); // 오류 확인용 if문
					System.out.println(tf);
					System.out.println(df);
				}
				*/
				df=0; // df값 초기화
				
				//여기서 w 해줘야함 (필요없어짐...) 밑에서 함
				/*
				int Wtemp = Wlist.indexOf(strText)+1; // 단어의 가중치모음 인덱스
				//System.out.println(Wtemp);
				String StrT1 = Wlist.get(Wtemp).toString(); //가중치 모음 가져오기
				//System.out.println(StrT1);
				String[] split = null;
				split = StrT1.split(" "); // 가중치 쪼개기 (docid / 가중치 / docid / 가중치 / ...)
				*/
				/*
				for(int o=0;o<split.length;o++) {
					System.out.println(split[o]);
				}
				*/
				//System.out.println(split.length);
				/*
				split[(i*2)+1] = Double.toString(w); // 계산된 적절한 위치에 가중치 덮어쓰기
				String StrT2="";
				for(int s=0;s<split.length;s++) { // 쪼갠거 다시 합하기
					if(s==0) {
						StrT2 += split[s];
					}
					else {
						StrT2 += " "+split[s];
					}
				}
				//System.out.println(StrT2);
				
				Wlist.set(Wtemp, StrT2); // 수정된 가중치 적용
				*/ // 여기도 필요없어짐 ㅠㅠ
				
				if(Wlist.contains(strText)) { // 문서의 단어가 리스트에 이미 존재하면 새로만들지 않고 기존에 추가
					int Wtemp = Wlist.indexOf(strText)+1; // 짝수=key 홀수=tf-idf 모음
					//System.out.println("홀수:"+Wtemp); // 이거 무조건 홀수여야함(test)
					Wlist.set(Wtemp, Wlist.get(Wtemp)+" "+i+" "+w);	//옆에 추가하기
				}
				else { // 문서의 단어가 처음등장 한거라면 리스트에 새로 추가
					Wlist.add(strText);
					Wlist.add(i+" "+w);
				}
				
			}
		}
		
		//System.out.println(Wlist);
		
		return Wlist;
	}
	
	public void makeHashmap(Document doc) throws IOException { // xml 파일 열어서 여기서 작업 
		FileOutputStream fileStream = new FileOutputStream("./index.post");
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);
		HashMap hm = new HashMap();
		
		NodeList n1 = doc.getElementsByTagName("body");
		
		List DocArr = new ArrayList();
		
		for(int i=0;i<n1.getLength();i++) { // 동적 배열에 한칸씩 Doc 느낌으로 저장
			Node temp = n1.item(i);
			DocArr.add(temp.getTextContent());
			
			//System.out.println(DocArr.get(i));
			//System.out.println(DocArr.get(i).getClass());
		}
		
		String[][] BigArr =  null;
		BigArr = new String[DocArr.size()][];
		
		for(int i=0;i<DocArr.size();i++) { // df계산을 위한 모든 형태소가 들어간 2차원 배열
			List array = Filter(DocArr.get(i).toString());
			BigArr[i] = new String[array.size()];
			for(int j=0;j<array.size();j++) {
				BigArr[i][j]=array.get(j).toString();
			}
		}
		/*
		for(int i=0;i<DocArr.size();i++) {
			for(int j=0;j<BigArr[i].length;j++) {
				System.out.println(BigArr[i][j]);
			}
		}
		*/
		List Wlist = calculateWeight(DocArr, BigArr); // tf-idf 계산 작업
		
		changeFloat(Wlist); // float로 바꿈
		
		//hm.put(word, weight);
		
		//여기서 반복문으로 한번에 만들어야함
		for(int i=0;i<Wlist.size()-1;i+=2) {
			hm.put(Wlist.get(i).toString(), Wlist.get(i+1).toString()); //리스트에 있는거 해쉬맵으로 저장
		}
		/*
		for(int i=0;i<Wlist.size()-1;i+=2) { //리스트에 잘 저장되어있는지 확인
			System.out.println(Wlist.get(i));
			System.out.println(Wlist.get(i+1));
			//System.out.println((i+1)+"/"+(Wlist.size()-1));
			System.out.println();
		}
		*/
		
		/*
		for(int i=0;i<Wlist.size()-1;i+=2) { //해쉬맵에 잘 저장되어있는지 확인
			System.out.print(Wlist.get(i)+" ");
			System.out.println(hm.get(Wlist.get(i)));
			System.out.println();
		}
		*/
		objectOutputStream.writeObject(hm);
		objectOutputStream.close();
	}
	
	public List changeFloat(List list) { // 리스트 받으면 가중치 떄서 float 리스트로 만들고 리턴
		 // 이걸 붙이기용 리스트로 쓰자
		List tempA = new ArrayList();
		
		for(int i=0;i<list.size()-1;i+=2) {
			String[] split = null;
			String temp ="";
			List array = new ArrayList();
			temp = list.get((i+1)).toString();
			split = temp.split(" ");
			for(int j=0;j<split.length;j++) {
				float f = Float.parseFloat(split[j]);
				//System.out.println(f);
				//f = (float) (Math.round(f*100)/100.0); // 셋째 자리에서 반올림
				//System.out.println(f);
				array.add(f);
				//System.out.println(array);
			}
			
			list.set(i+1, array);
			array = new ArrayList();
			//array.clear();
			/*
			for(int l=0;l<array.size();l++) { //리스트에 잘 저장되어있는지 확인
				System.out.println(array.get(l));
				//System.out.println(array.get(l+1));
				//System.out.println((i+1)+"/"+(Wlist.size()-1));
				System.out.println();
			}*/
		}
		/*
		for(int i=0;i<list.size()-1;i+=2) { //리스트에 잘 저장되어있는지 확인
			System.out.println(list.get(i));
			System.out.println(list.get(i+1));
			//System.out.println((i+1)+"/"+(Wlist.size()-1));
			System.out.println();
		}
		*/
		return list;
	}
}

	