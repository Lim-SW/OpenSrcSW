package SimpleIR;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class searcher  {
//-s ./index.post -q "라면에는 면, 분말 스프가 있다."

	List<String> CalcSim2(String PATH, String query, Document Doc){
		HashMap hm = new HashMap();
		hm = getHashMap(PATH);
		
		List queryList = new ArrayList(); // 형태소 작업 시작
		KeywordExtractor ke = new KeywordExtractor(); 
		KeywordList kl = ke.extractKeyword(query, true);
		Keyword kwrd;
		for(int j=0;j<kl.size();j++) {
			kwrd = kl.get(j);
			queryList.add(kwrd.getString());
		}
		//System.out.println(queryList);  // 형태소 작업 끝
		
		List<String> calList = new ArrayList(); // 계산값 누적 여기에 저장
		List<String> ipList = new ArrayList(); // 이너프로덕트 계산값 저장
		List<String> cosineList = new ArrayList(); //최종 코사인값 저장
		float wqRoot = 0; // wq값은 1이므로 정해짐 여기에 저장
		NodeList n1 = Doc.getElementsByTagName("title"); // 반복문에 쓸 길이용
		wqRoot = (float) Math.sqrt(queryList.size());
		wqRoot = (float) (Math.round(wqRoot*100)/100.0); // 루트 wq 고정
		//System.out.println(wqRoot);
		
		for(int i=0;i<queryList.size();i++) {
			if(hm.containsKey(queryList.get(i))) { // 쿼리문의 단어가 해쉬맵에 있으면
				String temp = (String) hm.get(queryList.get(i));
				temp = temp.replace("[", "");
				temp = temp.replace("]", "");
				List<String> list = new ArrayList<>(Arrays.asList(temp.split(", ")));
				//System.out.println(list);
				
				float Wq = 1;
				float ten = 0;
				
				Node title = n1.item(i);
				
				int banbok=(n1.getLength()*2)-list.size();
				
				if(list.size()<10) {
					banbok = 10-list.size();
					for(int y=0;y<banbok;y++){
						list.add("0.0");//일단 가득 채워
					}
					
					for(int x=0;x<list.size()-1;x+=2) { // 없는거 doc번호랑 0으로 틀 맞추기
						if(!list.get(x).equals(Float.toString(ten))) {
							list.add(x, "0.0");
							list.add(x, Float.toString(ten));
							ten++;
						}
						
						else {
							ten++;
						}
						
						while(list.size()>10) { // index 초과하는거 삭제
							int size = list.size();
							list.remove(size-1);
						}
					}
				}
				//System.out.println(list);
				//Query*id 0 1 2 3 4
				if(calList.size()==0) { // 리턴용 리스트
					for(int n=0;n<n1.getLength();n++) {
						calList.add("0.0");
						ipList.add("0.0");
					}
				}
				
				//List<String> wdList = new ArrayList();
				//System.out.println(list);
				
				//이건 루트 안 제곱 계산
				for(int l=0;l<n1.getLength();l++) { // 값 계산한거 
					float calf1 = Float.parseFloat(calList.get(l));
					float calf2 = Float.parseFloat(list.get((l*2)+1)); //가중치값
					calf2 = (float) Math.pow(calf2, 2);
					float calf = calf1+calf2;
					//calf = (float) (Math.round(calf*100)/100.0); // 반올림
					calList.set(l, Float.toString(calf)); // 리스트에 저장
				}
				
				//이건 이너프로덕트
				for(int l=0;l<n1.getLength();l++) { // 값 계산한거 
					float calf1 = Float.parseFloat(ipList.get(l));
					float calf2 = Wq*Float.parseFloat(list.get((l*2)+1)); // 1(단어가중치)이랑 곱하기
					//System.out.println(calf2);
					float calf = calf1+calf2;
					//calf = (float) (Math.round(calf*100)/100.0); // 반올림
					ipList.set(l, Float.toString(calf)); // 리스트에 저장
				}
				
				//System.out.println(calList);
				//System.out.println(ipList);
				
			}		
		}
		List<String> finalList = new ArrayList();
		for (int l=0;l<n1.getLength();l++) {
			//System.out.println(Float.valueOf(calList.get(l)));
			//System.out.println(wqRoot);
			//System.out.println();
			float down = (float) (wqRoot * Math.sqrt(Float.valueOf(calList.get(l))));
			float up = Float.valueOf(ipList.get(l));
			//System.out.println(down);
			//System.out.println(up);
			if(down==0) {
				//System.out.println("0임");
				finalList.add("0.0");
			}
			else {
				//System.out.println("값나옴"+(up/down));
				finalList.add(Double.toString(Math.round((up/down)*100)/100.0));
			}
		}
		//System.out.println(finalList);
		return finalList;
	}

	
	HashMap getHashMap(String PATH) { // .post 파일 열어서 해쉬맵으로 오브젝트 인풋 스트림
		try {
			FileInputStream fileStream = new FileInputStream(PATH);
			ObjectInputStream objectIutputStream = new ObjectInputStream(fileStream);
			HashMap hm = new HashMap();
			hm = (HashMap) objectIutputStream.readObject();
			objectIutputStream.close();
			//System.out.println(hm);
			
			return hm;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return null;
	}
	
	void scSort(List<String> list, Document doc){
		List<String> namelist = new ArrayList<>();
		List<Float> floatlist = new ArrayList<>();
		List<String> templist = new ArrayList<>();
		HashMap<String,Float> hm = new HashMap<>();		
		NodeList n1 = doc.getElementsByTagName("title");
		Node title;
		
		for(int i=0;i<list.size();i++) { // 다큐먼트 이름 저장
			title = n1.item(i);
			namelist.add(title.getTextContent());
		}
		//System.out.println(namelist);
		
		for(int l=0;l<n1.getLength();l++) { // 값 계산한거 해쉬맵에 저장
			float fl = Float.parseFloat(list.get(l));
			floatlist.add(fl);
			hm.put(namelist.get(l), floatlist.get(l));
		}
		//System.out.println(floatlist);
		//System.out.println(hm);
		
		for(int j=0;j<n1.getLength();j++) { // 값이 0 인거 따로 보관
			if(floatlist.get(j)==0.0) {
				templist.add(namelist.get(j));
				hm.remove(namelist.get(j));
			}
		}
		
		List<Entry<String, Float>> list_entries = new ArrayList<Entry<String, Float>>(hm.entrySet());

		Collections.sort(list_entries, new Comparator<Entry<String, Float>>() { // 비교함수 Comparator를 사용하여 내림 차순으로 정렬
			public int compare(Entry<String, Float> obj1, Entry<String, Float> obj2) // compare로 값을 비교
			{
				return obj2.getValue().compareTo(obj1.getValue());
			}
		});
		//System.out.println(list_entries.size());
		if(list_entries.size()>3){
			while(list_entries.size()>3) { // index 초과하는거 삭제 3순위 까지 남기기
				int size = list_entries.size();
				list_entries.remove(size-1);
			}
		}
		//System.out.println(list_entries.size());
		//여기서 부터 출력
		System.out.println();
		System.out.println();
		System.out.println("----------CalcSim 결과----------");
		for(Entry<String, Float> entry : list_entries) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
		
		if(hm.size()==0) {
			for(int k=0;k<3;k++) {
				System.out.println(templist.get(k) + " : 0.0");
			}
		}
		
		else if(hm.size()==1) {
			for(int k=0;k<2;k++) {
				System.out.println(templist.get(k) + " : 0.0");
			}
		}
		
		else if(hm.size()==2) {
			for(int k=0;k<1;k++) {
				System.out.println(templist.get(k) + " : 0.0");
			}
		}
	}

}
