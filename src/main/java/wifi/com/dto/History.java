package wifi.com.dto;

import java.time.LocalDateTime;
import java.util.Date;

/*
+------------+--------------+------+-----+---------+----------------+
| Field      | Type         | Null | Key | Default | Extra          |
+------------+--------------+------+-----+---------+----------------+
| HISTORY_ID | int(11)      | NO   | PRI | NULL    | auto_increment |
| LAT        | varchar(100) | YES  |     | NULL    |                |
| LNT        | varchar(100) | YES  |     | NULL    |                |
| VIEW_DATE  | timestamp    | YES  |     | NULL    |                |
+------------+--------------+------+-----+---------+----------------+
 * */

public class History {
	
	private int historyId;
	// history ID
	private String lat;
	// x좌표
	private String lnt;
	// y좌표
	private Date viewDate;
	// 조회일시
	private String name;
	// 와이파이명
	private String wifiMgrNO;
	// 관리번호
	private Wifi wifi;
	// 와이파이 
	
	@Override
	public String toString() {
		return "History [historyId=" + historyId + ", lat=" + lat + ", lnt=" + lnt
				+ ", viewDate=" + viewDate + ", wifi=" + wifi + "]";
	}

	public int getHistoryId() {
		return historyId;
	}

	public void setHistoryId(int historyId) {
		this.historyId = historyId;
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

	

	public Date getViewDate() {
		return viewDate;
	}

	public void setViewDate(Date viewDate) {
		this.viewDate = viewDate;
	}

	public Wifi getWifi() {
		return wifi;
	}

	public void setWifi(Wifi wifi) {
		this.wifi = wifi;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWifiMgrNO() {
		return wifiMgrNO;
	}

	public void setWifiMgrNO(String wifiMgrNO) {
		this.wifiMgrNO = wifiMgrNO;
	}
	
	

}
