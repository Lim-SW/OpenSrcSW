# OpenSrcSW
2021 1학기 오픈소스SW입문 201411309 임석원

-2021.03.24 3주차 까지의 내용 최종 완성 + PUSH 실습

 └ collection.xml 생성 (태그지우기)
 
 └ index.xml 생성 (형태소작업)

-2021.03.25 4주차 까지의 내용 최종 완성 + PUSH 완성

 └ 다큐먼트에 등장 안하는 가중치 0.0 으로 부여
   [삭제 : 안부여 하는걸로 롤백 (주석처리함)]
 
 └ index.post 생성 (역파일)

 └ tf-idf 자연로그로 재계산 및 소수점 셋째 자리에서 반올림함
 
 └ float값을 포함한 리스트 형태로 value 값 변경

-2021.04.01 5주차 까지 내용 완성 + PUSH

 └ searcher.java (CalcSim) 완성
 
 └ sort 하여 3순위까지 출력 (동일값일시 다큐먼트 번호 빠른거 먼저)

-2021.04.16 6~7주차 까지 내용 완성 + PUSH

 └ 임의로 충돌 발생후 Calcsim2로 함수이름 변경후 임시 병합
 
 └ 병합후 master에서 Innerproduct 함수 feature branch의 원래내용으로 새로 생성

 └ Calcsim2 를 원래 함수이름 Calcsim 으로 변경하고 함수 내에서 Innerproduct 메소드를 불러와 사용

 └ 최종 출력은 Cosine similarity로 출력
