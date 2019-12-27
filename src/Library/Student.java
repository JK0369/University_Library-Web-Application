package Library;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Student {

	int sno;
	String sname;
	int year;
	String dept;
	String id;
	String pw;

	// �Ű����� ���� �л� ��ü ������
	public Student() {}

	// �Ű����� �մ� �л� ��ü ������
	public Student(int sno, String sname, int year, String dept, String id, String pw) {
		this.sno = sno;
		this.sname = sname;
		this.year = year;
		this.dept = dept;
		this.id = id;
		this.pw = pw;
	}

	// �л������� ���ڿ��� ��ȯ
	public String toString() {
		return "   �й�: " + sno + ", �̸� : " + sname + ", �г� : " + year + ", �а�: " 
				+ dept + ", ID: " + id;
	}

	// �л����� ���
	public void output() {
		System.out.println(this.toString());
	}

	// ID���� �л� �˻�
	public static Student findAccount(String ID) {
		return DB.findStudent(ID);
	}
	
	public void doWork() {
		int menu=0;
		while(menu != 5)
		{
			System.out.println("\n\n ��-------<< ����� ���� >>-----��");
			System.out.println(" �� (�۾��� ID: " + this.id + "  �л� �̸�: " + this.sname+") ��");			
			System.out.println(" ��         1. �˻�                      ��");
			System.out.println(" ��         2. �ݳ�                      ��");	
			System.out.println(" ��         3. ����                      ��");
			System.out.println(" ��         4. ������ ���            ��");
			System.out.println(" ��         5. ����                      ��");
			System.out.println(" ��-------------------------��");
			System.out.print(" **���ϴ� �۾��� �����Ͻÿ� > ");
			
			menu = SkScanner.getInt() ;
			
			try {

			  switch (menu)  {
				case 1: 
					findBook();
					break;

				case 2:
					System.out.println("�� �ݳ� ��û ��");
					DB.returnBook(sno);
					break;

				case 3:
					extendBook();
					break;
					
				case 4:	
					ResultSet rs = DB.getRSBorrowStudent(sno);
					if(!rs.next()) {
						System.out.println(" �� �������� å�� �����ϴ�.");
						break;
					}
					System.out.println("\n\n��--------------------------------------------------------<< ������ ��� >>--------------------------------------------------------��");
					rs.beforeFirst();
					DB.printRS(rs);
					System.out.print("��-----------------------------------------------------------------------------------------------------------------------------��");
					break;
					
				case 5:	
					break;

				default:
					System.err.println(" ** ����: �߸��� �޴� �����Դϴ�.");
				}
			}
			catch (Exception e) {
				System.err.println("\n "+ e.getMessage());
			}

		}
	}

	// ������ å ���� �ϴ� �޼ҵ�
	private void extendBook() throws Exception {
		ResultSet tmpRS = DB.getRSFromBorrow(this.sno);
		
		if(!tmpRS.next()) {
			System.out.println(" ** ������ å�� �������� �ʽ��ϴ�");
			return;
		}
		
		tmpRS.beforeFirst();
		
		System.out.println("\n\n��--------------------------------------------------------<< ���� ��Ȳ >>--------------------------------------------------------��");
		DB.printRS(tmpRS);
		System.out.print("��---------------------------------------------------------------------------------------------------------------------------��");

		int menu=0;
		while (menu != -1) {
			System.out.print("\n ���� ��û å ��ȣ �Է�(������ �� -1)>");
			menu = SkScanner.getInt();
			
			String findISBN = getISBNFromBorrowStudnet(tmpRS, menu);
			
			switch (menu) {
			case -1: // ���� �۾� ����
				break;

			default :
				DB.extendBook(findISBN); // ��� ������ ������ ��ȣ�� å�� �����ϴ� �۾�
				break;
			}
		}
	}

	// �л��� ������ å�� ������ å�� isbn�� ��ȯ�ϴ� �޼ҵ�
	private String getISBNFromBorrowStudnet(ResultSet tmpRS, int select) {
		try {
			tmpRS.beforeFirst();
			int cnt=0;
			while(tmpRS.next()) {
				cnt++;
				if(cnt==select) return tmpRS.getString("isbn");
			}
		} catch (SQLException e) {
			System.err.println("** SQL exec error in getISBNFromBorrowStudnet: " + e.getMessage() );
		}
		return null;
	}

	public void findBook() {
		int menu=0;
		while(menu != 5)
		{
			System.out.println("\n\n ��-------<< ���� �˻� >>---------��");
			System.out.println(" �� ( �۾��� ID: " + this.id + "  �л� �̸�: " + this.sname + " )  ��");
			System.out.println(" ��      1. ISBN�˻�                          ��");
			System.out.println(" ��      2. �α���� ���� �˻�               ��");			
			System.out.println(" ��      3. �̹��� ����Ʈ ����               ��");
			System.out.println(" ��      4. ��ü ���� ���                     ��");
			System.out.println(" ��      5. ����                                   ��");
			System.out.println(" ��----------------------------��");
			System.out.print(" ** ���ϴ� �۾��� �����Ͻÿ� > ");
			
			menu = SkScanner.getInt() ;
			
			try {
				String type = "";
				switch (menu) {
				case 1: // �������� ���� �˻�
					type = "�������� ���� �˻�";

					System.out.print("\n �� å�� ISBN�Է� >");
					
					String findISBN = SkScanner.getString();
					ResultSet rs = DB.getRSFindBook(findISBN);
					if(!rs.next()) {
						System.out.println(" �� ISBN�� �߸� �Է� �Ͽ����ϴ� ��");
						continue;
					}
					borrowBook(type, rs); // å ����
					break;

				case 2: // �α���� ���� �˻�
					type = "�α���� ���� �˻�";
					rs = DB.getRSFindBestSeller();
					borrowBook(type, rs);
					break;

				case 3: // �̹��� ����Ʈ ����
					type = "�̹��� ����Ʈ ���� �˻�";
					rs = DB.getRSBestSellerThisMonth();
					borrowBook(type, rs);
					break;
					
				case 4:	
					type = "��� ���� �˻�";
					rs = DB.getRSAllBook();
					borrowBook(type, rs);
					break;
					
				case 5:	
					break;

				default:
					System.err.println(" ** ����: �߸��� �޴� �����Դϴ�.");

				}
			}
			catch (Exception e) {
				System.err.println("\n "+ e.getMessage());
			}

		}
	}

	// type�̶�� ������ ������� RS�� �޾Ƽ�, �װ����� �л��� ������ ��û�ϴ� �޼ҵ�
	public void borrowBook(String type, ResultSet RS) throws Exception {
		
		int menu=0;
		while (menu != -1) {
			// �˻��� ��� ���
			if(type.equals("�α���� ���� �˻�")) RS=DB.getRSFindBestSeller();
			else if(type.equals("�̹��� ����Ʈ ���� �˻�")) DB.getRSAllBook();
			else RS=DB.getRSAllBook();
			
			System.out.println("\n��--------------------------------------------------------<< "+type+" ��� >>--------------------------------------------------------��");
			DB.printRS(RS); // ��������� �޾Ƽ� �� ������ ���
			System.out.print("��-----------------------------------------------------------------------------------------------------------------------------------��");
			System.out.print("\n ** ���� ��û�� ��ȣ �Է�(����:-1�Է�)>");
			menu = SkScanner.getInt();
			
			switch (menu) {
			case -1: // ���� �۾� ����
				return;
				
			default :
				DB.borrowBook(RS, menu, this.sno); // RS�� ������ ��ȣ�� å�� �뿩�ϴ� �۾�
				break;
			}
		}
	}

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}
	
}