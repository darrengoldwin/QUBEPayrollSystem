import java.util.Date;

public class Record {

	private String department;
	private String id;
	private String inOurOut;
	private Date time;
	private String timeString;
	
	public Record(String department, String id, String inOurOut, Date time, String timeString) {
		super();
		this.department = department;
		this.id = id;
		this.inOurOut = inOurOut;
		this.time = time;
		this.timeString = timeString;
	}

	public String getTimeString() {
		return timeString;
	}

	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInOurOut() {
		return inOurOut;
	}

	public void setInOurOut(String inOurOut) {
		this.inOurOut = inOurOut;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	
	
	
	
}
