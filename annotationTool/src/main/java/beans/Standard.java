package beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Standard {

	private String json = null;
	private String name = null;
	private Integer pk;
	
	public Integer getPk() {
		return pk;
	}
	public void setPk(Integer pk) {
		this.pk = pk;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
