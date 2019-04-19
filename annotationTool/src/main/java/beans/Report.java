package beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Report {
	
	private int hospital_id = -1;
	private String uploader = null;
	private String date = null;
	private String content = null;
	private String comments = "";
	private int pk;
	
	public String getPkString() {
		return Integer.toString(pk);
	}
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	public int getHospital_id() {
		return hospital_id;
	}
	public void setHospital_id(int hospital_id) {
		this.hospital_id = hospital_id;
	}
	public String getUploader() {
		return uploader;
	}
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	

}
