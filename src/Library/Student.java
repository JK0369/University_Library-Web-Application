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

	// 매개변수 없는 학생 객체 생성자
	public Student() {}

	// 매개변수 잇는 학생 객체 생성자
	public Student(int sno, String sname, int year, String dept, String id, String pw) {
		this.sno = sno;
		this.sname = sname;
		this.year = year;
		this.dept = dept;
		this.id = id;
		this.pw = pw;
	}

	// 학생정보를 문자열로 반환
	public String toString() {
		return "   학번: " + sno + ", 이름 : " + sname + ", 학년 : " + year + ", 학과: " 
				+ dept + ", ID: " + id;
	}

	// 학생정보 출력
	public void output() {
		System.out.println(this.toString());
	}

	// ID통해 학생 검색
	public static Student findAccount(String ID) {
		return DB.findStudent(ID);
	}
	
	public void doWork() {
		int menu=0;
		while(menu != 5)
		{
			System.out.println("\n\n ●-------<< 사용자 업무 >>-----●");
			System.out.println(" ● (작업자 ID: " + this.id + "  학생 이름: " + this.sname+") ●");			
			System.out.println(" ●         1. 검색                      ●");
			System.out.println(" ●         2. 반납                      ●");	
			System.out.println(" ●         3. 연장                      ●");
			System.out.println(" ●         4. 대출중 목록            ●");
			System.out.println(" ●         5. 종료                      ●");
			System.out.println(" ●-------------------------●");
			System.out.print(" **원하는 작업을 선택하시오 > ");
			
			menu = SkScanner.getInt() ;
			
			try {

			  switch (menu)  {
				case 1: 
					findBook();
					break;

				case 2:
					System.out.println("● 반납 신청 ●");
					DB.returnBook(sno);
					break;

				case 3:
					extendBook();
					break;
					
				case 4:	
					ResultSet rs = DB.getRSBorrowStudent(sno);
					if(!rs.next()) {
						System.out.println(" ● 대출중인 책이 없습니다.");
						break;
					}
					System.out.println("\n\n●--------------------------------------------------------<< 대출중 목록 >>--------------------------------------------------------●");
					rs.beforeFirst();
					DB.printRS(rs);
					System.out.print("●-----------------------------------------------------------------------------------------------------------------------------●");
					break;
					
				case 5:	
					break;

				default:
					System.err.println(" ** 오류: 잘못된 메뉴 선택입니다.");
				}
			}
			catch (Exception e) {
				System.err.println("\n "+ e.getMessage());
			}

		}
	}

	// 대출한 책 연장 하는 메소드
	private void extendBook() throws Exception {
		ResultSet tmpRS = DB.getRSFromBorrow(this.sno);
		
		if(!tmpRS.next()) {
			System.out.println(" ** 대출한 책이 존재하지 않습니다");
			return;
		}
		
		tmpRS.beforeFirst();
		
		System.out.println("\n\n●--------------------------------------------------------<< 대출 현황 >>--------------------------------------------------------●");
		DB.printRS(tmpRS);
		System.out.print("●---------------------------------------------------------------------------------------------------------------------------●");

		int menu=0;
		while (menu != -1) {
			System.out.print("\n 연장 신청 책 번호 입력(종료할 시 -1)>");
			menu = SkScanner.getInt();
			
			String findISBN = getISBNFromBorrowStudnet(tmpRS, menu);
			
			switch (menu) {
			case -1: // 대출 작업 종료
				break;

			default :
				DB.extendBook(findISBN); // 결과 집합중 선택한 번호의 책을 연장하는 작업
				break;
			}
		}
	}

	// 학생이 연장할 책을 선택한 책의 isbn을 반환하는 메소드
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
			System.out.println("\n\n ●-------<< 도서 검색 >>---------●");
			System.out.println(" ● ( 작업자 ID: " + this.id + "  학생 이름: " + this.sname + " )  ●");
			System.out.println(" ●      1. ISBN검색                          ●");
			System.out.println(" ●      2. 인기순위 도서 검색               ●");			
			System.out.println(" ●      3. 이번달 베스트 셀러               ●");
			System.out.println(" ●      4. 전체 도서 목록                     ●");
			System.out.println(" ●      5. 종료                                   ●");
			System.out.println(" ●----------------------------●");
			System.out.print(" ** 원하는 작업을 선택하시오 > ");
			
			menu = SkScanner.getInt() ;
			
			try {
				String type = "";
				switch (menu) {
				case 1: // 제목으로 도서 검색
					type = "제목으로 도서 검색";

					System.out.print("\n ● 책의 ISBN입력 >");
					
					String findISBN = SkScanner.getString();
					ResultSet rs = DB.getRSFindBook(findISBN);
					if(!rs.next()) {
						System.out.println(" ● ISBN을 잘못 입력 하였습니다 ●");
						continue;
					}
					borrowBook(type, rs); // 책 대출
					break;

				case 2: // 인기순위 도서 검색
					type = "인기순위 도서 검색";
					rs = DB.getRSFindBestSeller();
					borrowBook(type, rs);
					break;

				case 3: // 이번달 베스트 셀러
					type = "이번달 베스트 셀러 검색";
					rs = DB.getRSBestSellerThisMonth();
					borrowBook(type, rs);
					break;
					
				case 4:	
					type = "모든 도서 검색";
					rs = DB.getRSAllBook();
					borrowBook(type, rs);
					break;
					
				case 5:	
					break;

				default:
					System.err.println(" ** 오류: 잘못된 메뉴 선택입니다.");

				}
			}
			catch (Exception e) {
				System.err.println("\n "+ e.getMessage());
			}

		}
	}

	// type이라는 유형의 결과집합 RS를 받아서, 그곳에서 학생이 대출을 신청하는 메소드
	public void borrowBook(String type, ResultSet RS) throws Exception {
		
		int menu=0;
		while (menu != -1) {
			// 검색한 목록 출력
			if(type.equals("인기순위 도서 검색")) RS=DB.getRSFindBestSeller();
			else if(type.equals("이번달 베스트 셀러 검색")) DB.getRSAllBook();
			else RS=DB.getRSAllBook();
			
			System.out.println("\n●--------------------------------------------------------<< "+type+" 결과 >>--------------------------------------------------------●");
			DB.printRS(RS); // 결과집합을 받아서 그 정보를 출력
			System.out.print("●-----------------------------------------------------------------------------------------------------------------------------------●");
			System.out.print("\n ** 대출 신청할 번호 입력(종료:-1입력)>");
			menu = SkScanner.getInt();
			
			switch (menu) {
			case -1: // 대출 작업 종료
				return;
				
			default :
				DB.borrowBook(RS, menu, this.sno); // RS중 선택한 번호의 책을 대여하는 작업
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