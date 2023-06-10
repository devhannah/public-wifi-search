package wifi.com.dto;

/*
+---------------------+--------------+------+-----+---------+-------+
| Field               | Type         | Null | Key | Default | Extra |
+---------------------+--------------+------+-----+---------+-------+
| X_SWIFI_MGR_NO      | varchar(50)  | NO   | PRI | NULL    |       |
| DISTANCE            | varchar(100) | YES  |     | NULL    |       |
| X_SWIFI_WRDOFC      | varchar(100) | YES  |     | NULL    |       |
| X_SWIFI_MAIN_NM     | varchar(100) | YES  |     | NULL    |       |
| X_SWIFI_ADRES1      | varchar(100) | YES  |     | NULL    |       |
| X_SWIFI_ADRES2      | varchar(100) | YES  |     | NULL    |       |
| X_SWIFI_INSTL_FLOOR | varchar(100) | YES  |     | NULL    |       |
| X_SWIFI_INSTL_TY    | varchar(100) | YES  |     | NULL    |       |
| X_SWIFI_INSTL_MBY   | varchar(100) | YES  |     | NULL    |       |
| X_SWIFI_SVC_SE      | varchar(100) | YES  |     | NULL    |       |
| X_SWIFI_CMCWR       | varchar(100) | YES  |     | NULL    |       |
| X_SWIFI_CNSTC_YEAR  | varchar(100) | YES  |     | NULL    |       |
| X_SWIFI_INOUT_DOOR  | varchar(10)  | YES  |     | NULL    |       |
| X_SWIFI_REMARS3     | varchar(100) | YES  |     | NULL    |       |
| LAT                 | varchar(100) | YES  |     | NULL    |       |
| LNT                 | varchar(100) | YES  |     | NULL    |       |
| WORK_DTTM           | varchar(50)  | YES  |     | NULL    |       |
| BOOKMARK_ID         | int(11)      | YES  | MUL | NULL    |       |
+---------------------+--------------+------+-----+---------+-------+
*/


public class Wifi {
	
	private String wifiMgrNo;
	// 관리번호
	private String distance;
	// 거리
	private String brg;
	// 자치구
	private String name;
	// 와이파이명 
	private String add1;
	// 도로명주소
	private String add2;
	// 상세주소
	private String floor;
	// 층
	private String install;
	// 설치유형
	private String installBy;
	// 설치기관
	private String service;
	// 서비스구분
	private String network;
	// 망종류
	private String year;
	// 설치년도
	private String inOut;
	// 실내외
	private String env;
	// 접속환경
	private String lat;
	// x좌표
	private String lnt;
	// y좌표 
	private String installDate;
	// 작업 일자
	private int bookmarkId;
	// 북마크
	
	@Override
	public String toString() {
		return "Wifi [wifiMgrNo=" + wifiMgrNo + ", distance=" + distance + ", brg=" + brg + ", name=" + name + ", add1="
				+ add1 + ", add2=" + add2 + ", floor=" + floor + ", install=" + install + ", installBy=" + installBy
				+ ", service=" + service + ", network=" + network + ", year=" + year + ", inOut=" + inOut + ", env="
				+ env + ", lat=" + lat + ", lnt=" + lnt + ", installDate=" + installDate + ", bookmarkId=" + bookmarkId
				+ "]";
	}
	
	public Wifi() {}

	public Wifi(String wifiMgrNo, String distance, String brg, String name, String add1, String add2, String floor,
			String install, String installBy, String service, String network, String year, String inOut, String env,
			String lat, String lnt, String installDate, int bookmarkId) {
		super();
		this.wifiMgrNo = wifiMgrNo;
		this.distance = distance;
		this.brg = brg;
		this.name = name;
		this.add1 = add1;
		this.add2 = add2;
		this.floor = floor;
		this.install = install;
		this.installBy = installBy;
		this.service = service;
		this.network = network;
		this.year = year;
		this.inOut = inOut;
		this.env = env;
		this.lat = lat;
		this.lnt = lnt;
		this.installDate = installDate;
		this.bookmarkId = bookmarkId;
	}

	public String getWifiMgrNo() {
		return wifiMgrNo;
	}

	public void setWifiMgrNo(String wifiMgrNo) {
		this.wifiMgrNo = wifiMgrNo;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getBrg() {
		return brg;
	}

	public void setBrg(String brg) {
		this.brg = brg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdd1() {
		return add1;
	}

	public void setAdd1(String add1) {
		this.add1 = add1;
	}

	public String getAdd2() {
		return add2;
	}

	public void setAdd2(String add2) {
		this.add2 = add2;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getInstall() {
		return install;
	}

	public void setInstall(String install) {
		this.install = install;
	}

	public String getInstallBy() {
		return installBy;
	}

	public void setInstallBy(String installBy) {
		this.installBy = installBy;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getInOut() {
		return inOut;
	}

	public void setInOut(String inOut) {
		this.inOut = inOut;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLnt() {
		return lnt;
	}

	public void setLnt(String lnt) {
		this.lnt = lnt;
	}

	public String getInstallDate() {
		return installDate;
	}

	public void setInstallDate(String installDate) {
		this.installDate = installDate;
	}

	public int getBookmarkId() {
		return bookmarkId;
	}

	public void setBookmarkId(int bookmarkId) {
		this.bookmarkId = bookmarkId;
	}
	
	
	
	
}
