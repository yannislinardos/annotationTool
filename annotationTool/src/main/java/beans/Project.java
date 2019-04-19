package beans;

public class Project {

	private String name = null;
	private String creator = null;
	private String date = null;
	private boolean finished = false;
	private int pk = -1;
	
	private String[] usernames;
	private String[] standards;
	private String[] collections;
	private String[] reports;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public boolean isFinished() {
		return finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	public int getPk() {
		return pk;
	}
	public void setPk(int pk) {
		this.pk = pk;
	}
	

	public String[] getUsernames() {
		return usernames;
	}
	public void setUsernames(String[] usernames) {
		this.usernames = usernames;
	}
	public String[] getStandards() {
		return standards;
	}
	public void setStandards(String[] standards) {
		this.standards = standards;
	}
	public String[] getCollections() {
		return collections;
	}
	public void setCollections(String[] collections) {
		this.collections = collections;
	}
	public String[] getReports() {
		return reports;
	}
	public void setReports(String[] reports) {
		this.reports = reports;
	}
	
}
