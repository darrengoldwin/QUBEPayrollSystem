import java.util.ArrayList;

public class Employee {

	private String name;
	private String id;
	private double rate;
	private double total;
	private ArrayList<Record> records;
	
	public Employee(String name, String id, double rate) {
		this.total = 0;
		this.id = id;
		this.name = name;
		this.rate = rate;
		records = new ArrayList<Record>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void addRecord(Record r) {
		this.records.add(r);
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}
	
	public ArrayList<Record> getRecords() {
		return records;
	}
	public void addTotal(double pay) {
		this.total +=pay;
	}
	public double getTotal() {
		return total;
	}

}
