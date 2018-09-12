import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Driver {

	public int run(String employeeList, String recordList, String fileName, String startDate, String endDate) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat dt = new SimpleDateFormat("HH:mm");
		
		ArrayList<Employee> employees = getEmployees(employeeList);

		Date startTime = dt.parse(startDate);
		Date endTime = dt.parse(endDate);
		Date overTime = dt.parse(endDate.replace("00", "30"));
		Date lunchTimeStart = dt.parse("12:00");
		Date lunchTimeEnd = dt.parse("13:00");
		
		System.out.println(overTime.toString());
		getRecords(employees, recordList);

		return computePayroll(employees, fileName, startTime, endTime, overTime, lunchTimeStart, lunchTimeEnd);

	}

	public ArrayList<Employee> getEmployees(String employeeList) {
		try {

			ArrayList<Employee> employees = new ArrayList<Employee>();
			BufferedReader br = new BufferedReader(new FileReader(employeeList));
			String line = br.readLine();

			while ((line = br.readLine()) != null) {
				String[] arr = line.split(",");

				employees.add(new Employee(arr[1], arr[0], Double.parseDouble(arr[2])));
			}
			br.close();
			return employees;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void getRecords(ArrayList<Employee> employees, String recordList) {
		try {

			BufferedReader br = new BufferedReader(new FileReader(recordList));
			String line = br.readLine();
			SimpleDateFormat dt = new SimpleDateFormat("HH:mm");

			while ((line = br.readLine()) != null) {
				String[] arr = line.split(",");
				for (Employee e : employees) {
					if (e.getId().equals(arr[2])) {
						String time = arr[3].split(" ")[1];
						e.addRecord(new Record(arr[0], arr[2], arr[4].replace("C/", ""), dt.parse(time), arr[3]));
					}
				}

			}
			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public int computePayroll(ArrayList<Employee> employees, String fileName, Date startTime, Date endTime, Date overTime, Date lunchTimeStart, 
			Date lunchTimeEnd){
		BufferedWriter br ;
		try {
			br = new BufferedWriter(new FileWriter(fileName));
			br.append("id,name,rate,in,out,time(Total),time(Computed),Pay,Total");
			br.newLine();
			for (Employee e : employees) {
				
				for (int i = 0; i+1 < e.getRecords().size(); i++) {

					Record r = e.getRecords().get(i);

					if (r.getInOurOut().equals("In")) {
						//get in and out
						int j = i + 1;
						
						Record o = e.getRecords().get(j);
						
						while (j < e.getRecords().size() && !o.getInOurOut().equals("Out")) {
							j++;
							if(j < e.getRecords().size())
								o = e.getRecords().get(j);
						}
						j++;
						if (j < e.getRecords().size()) {
							
							o = e.getRecords().get(j);
							
							while (j < e.getRecords().size() && !o.getInOurOut().equals("In")) {
								j++;
								if(j < e.getRecords().size())
									o = e.getRecords().get(j);
							}
			
							o = e.getRecords().get(j-1);
							i = j-1;
						}
						else i = j-1;
						
						//compute hours
						
						double in = 0;
						double out = 0;
						double diff = 0;
						double breaks = 0;

						if (r.getTime().before(startTime))
							in = startTime.getTime();
						else {
							if(r.getTime().after(lunchTimeEnd)) {
								breaks+=60*1000*60;
							}
							in = r.getTime().getTime();
						}
							
						
							

						if(o.getTime().before(endTime)) {
							
							if(o.getTime().before(startTime)) {
								
								out = o.getTime().getTime();
								in -= out;
								out = 24*1000*60*60;
								breaks += 120*1000*60;
							}
							else {
								
								if(o.getTime().before(lunchTimeEnd)) {
									if(o.getTime().after(lunchTimeStart)) 
										out = lunchTimeStart.getTime();
									else
										out = o.getTime().getTime();
								}
								else {
									out = o.getTime().getTime();
									breaks += 60*1000*60;
								}
										
							}
								
						}	
						else {
							breaks += 60*1000*60;
							if(o.getTime().after(overTime)) {
								
								out = o.getTime().getTime();
								breaks += 30*1000*60;
							}
							else
								out = endTime.getTime();
						}
							
						diff = (out - in - breaks) / (1000.0 * 60.0) ;
						
						double diffTotal;
						if(o.getTime().after(r.getTime()))
							diffTotal = (o.getTime().getTime() - r.getTime().getTime()) / (1000.0 * 60.0);
						else
							diffTotal = (r.getTime().getTime() - o.getTime().getTime()) / (1000.0 * 60.0);
						
						double pay = diff * (e.getRate()/8/60);
						e.addTotal(pay);
						
						br.append(e.getId() + "," + e.getName() + "," + e.getRate() + "," + r.getTimeString() + "," + o.getTimeString() + "," + ((int)diffTotal/60) + ":" + ((int)diffTotal%60) + "," + ((int)diff/60) + ":" + ((int)diff%60) + "," + pay);
						br.newLine();

					}
					
				}
				if(e.getRecords().size() > 0) {
					br.append(" , , , , , , , ,"+e.getTotal());
					br.newLine();
				}
				
				
			}
			
			double total =0;
			for (Employee e : employees)
				total+=e.getTotal();
			br.append(" , , , , , , , ,"+total);
			br.newLine();
			br.close();
			return 0;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return 1;
		}
		
	}

}
