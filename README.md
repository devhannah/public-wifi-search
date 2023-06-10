# Public Wifi Search

데이터 베이스 / 자바 기반 Pure 자바 프로젝트 과제

##  프로젝트 소개

내 위치 기반 공공 와이파이 정보를 제공하는 웹서비스

## ⚙️ 개발 환경 

- JDK 1.8
- Dynamic web module version : 3.1
- Tomcat -v : 8.5
- Database: MariaDB

## 🗂 파일 설명
### - Controller files -
***
##### WifiController.java
공공 와이파이 정보를 받아오는 부분, load-wifi.jsp에 content 넘겨주기
##### WifiService.java
1. Open API 호출 시, DB 저장
2. 내 위치 기반 주변 와이파이 20개 출력하기
##### wifiDetail.java
1. 선택한 와이파이 상세 정보 가져오기 
2. 북마크 그룹 정보를 넘겨 받고 북마크 추가하기를 통해 해당 와이파이에 BOOKMARK_ID 값 설정
##### HistoryService.java
1. 조회한 위치를 저장
2. 선택한 위치를 삭제
##### HistoryList.java
조회한 위치 목록 가져오기
##### BMGroupList.java
북마크 그룹 목록 가져오기
##### BMGroupInsert.java
북마크 그룹 추가하기
##### BMGroupEdit.java
북마크 그룹 수정하기
##### BMGroupDelete.java
북마크 그룹 삭제하기
##### BMList.java
북마크 목록 가져오기 (와이파이와 북마크를 BOOKMARK_ID를 통해 조인)
##### BMEdit.java
북마크 삭제하기 (해당 와이파이의 BOOKMARK_ID = null로 바꿔주기)

***
### - jsp files - 
##### Wifi jsp 
index.jsp, load-wifi.jsp, wifi-detail.jsp
##### History jsp
history.jsp
##### Bookmark jsp
1. bookmark-group.jsp, bookmark-group-add.jsp, bookmark-group-edit.jsp,
2. bookmark-list.jsp, bookmark-delete.jsp

***
### - dao files -
TestDatabase.java

dao를 두고 database connection 관리 목적 생성, 추후 더 공부해서 재구성 목표

***
### - dto files -
Bookmark.java, History.java, Wifi.java

***
