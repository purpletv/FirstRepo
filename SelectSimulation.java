import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Scanner;
import java.lang.reflect.Field;


public class SelectSimulation {

	public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException, NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		EmployeeList el = new EmployeeList();
		ParseFile.parse("./Employehub.csv");
		ParseFile.read(el);
		//for (Employee e : el)
		//	System.out.println(e);
		Class EmpDes = Class.forName("Employee");
		// Method m[] = new Method[args.length];
		String exp[];
		Field field[] = new Field[args.length];
		String con[] = new String[args.length];
		String value[] = new String[args.length];
		//System.out.print(args[0]);
		for (int i = 0; i < args.length; i++) {
			
			exp = args[i].split("\\.", 0);
			field[i] = EmpDes.getField(exp[0]);
			con[i] = exp[1];
			value[i] = exp[2];

		}
		for (Employee e : el) {
			boolean f = true;
			for (int i = 0; i < args.length && f; i++) {
				// f=(m[i].invoke(e) > value[i]);
				//System.out.print(field[i].get(e));
				f = field[i].get(e).toString().compareTo(value[i]) == 0;
			}
			if (f)
				System.out.println(e);
		}

	}

}

class Employee {
	public int id;
	String name, email, phone, hiredate, job_id;
	public int salary;
	int commision;
	int manager;
	int dept_id;

	public Employee(int id, String name, String email, String phone, String hiredate, String job_id, int salary,
			int commision, int manager, int dept_id) {
		// super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.hiredate = hiredate;
		this.job_id = job_id;
		this.salary = salary;
		this.commision = commision;
		this.manager = manager;
		this.dept_id = dept_id;
		// this.job_id=job_id;
	}

	public static Employee createObject(String s) {
		String[] fields = s.split(",", 0);
		int id = Integer.parseInt(fields[0]);
		String name = fields[1] + " " + fields[2];
		String email = fields[3];
		String phn = fields[4];
		String hd = fields[5];
		String job_id = fields[6];
		int sal = Integer.parseInt(fields[7]);
		int commision;
		try {
			commision = Integer.parseInt(fields[8]);
		} catch (Exception e) {
			commision = -1;
		}
		int mid ;
		try{
			mid=Integer.parseInt(fields[9]);
			}
		catch(Exception e){
			mid=-1;
		}
		int dept_id = Integer.parseInt(fields[10]);
		return new Employee(id, name, email, phn, hd, job_id, sal, commision, mid, dept_id);

	}
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getHiredate() {
		return hiredate;
	}

	public String getJob_id() {
		return job_id;
	}

	public int getSalary() {
		return salary;
	}

	public int getCommision() {
		return commision;
	}

	public int getManager() {
		return manager;
	}

	public int getDept_id() {
		return dept_id;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + ", hiredate="
				+ hiredate + ", job_id=" + job_id + ", salary=" + salary + ", commision=" + commision + ", manager="
				+ manager + ", dept_id=" + dept_id + "]";
	}
}

class EmployeeList extends LinkedList<Employee> {

}

class ParseFile {
	static File file;
	static Scanner sc;

	public static void parse(String path) throws FileNotFoundException {
		file = new File(path);
		sc = new Scanner(file);

	}

	public static void read(EmployeeList el) {
		String record;
		sc.nextLine();
		while (sc.hasNextLine()) {
			record=sc.nextLine();
			el.add(Employee.createObject(record));
		}
	}

}
