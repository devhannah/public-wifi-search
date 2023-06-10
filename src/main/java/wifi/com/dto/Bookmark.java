package wifi.com.dto;

import java.util.Date;

/*
+-------------+--------------+------+-----+---------+----------------+
| Field       | Type         | Null | Key | Default | Extra          |
+-------------+--------------+------+-----+---------+----------------+
| BOOKMARK_ID | int(11)      | NO   | PRI | NULL    | auto_increment |
| BM_NAME     | varchar(100) | YES  |     | NULL    |                |
| BM_ORDER    | int(11)      | YES  |     | NULL    |                |
| BM_ENROLL   | timestamp    | YES  |     | NULL    |                |
| BM_EDIT     | timestamp    | YES  |     | NULL    |                |
+-------------+--------------+------+-----+---------+----------------+
*/

public class Bookmark {

	private int bookmarkId;
	
	private String bookmarkName;
	
	private int bookmarkOrder;
	
	private Date bookmarkEnroll;
	
	private Date bookmarkEdit;
	
	private Wifi wifi;

	@Override
	public String toString() {
		return "Bookmark [bookmarkId=" + bookmarkId + ", bookmarkName=" + bookmarkName + ", bookmarkOrder="
				+ bookmarkOrder + ", bookmarkEnroll=" + bookmarkEnroll + ", bookmarkEdit=" + bookmarkEdit
				+  "]";
	}

	public int getBookmarkId() {
		return bookmarkId;
	}

	public void setBookmarkId(int bookmarkId) {
		this.bookmarkId = bookmarkId;
	}

	public String getBookmarkName() {
		return bookmarkName;
	}

	public void setBookmarkName(String bookmarkName) {
		this.bookmarkName = bookmarkName;
	}

	public int getBookmarkOrder() {
		return bookmarkOrder;
	}

	public void setBookmarkOrder(int bookmarkOrder) {
		this.bookmarkOrder = bookmarkOrder;
	}

	public Date getBookmarkEnroll() {
		return bookmarkEnroll;
	}

	public void setBookmarkEnroll(Date bookmarkEnroll) {
		this.bookmarkEnroll = bookmarkEnroll;
	}

	public Date getBookmarkEdit() {
		return bookmarkEdit;
	}

	public void setBookmarkEdit(Date bookmarkEdit) {
		this.bookmarkEdit = bookmarkEdit;
	}
	
}
