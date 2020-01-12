package Library;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Vector;

public class DB {
	public static Vector<String> buffer = new Vector<String>();  
	
	static  Connection con         = null;
    static  Statement stmt         = null;
    static  ResultSet rs           = null;
    static  PreparedStatement prStmt  = null;
 
    public static void loadConnect()  {
    		    // 드라이버 로딩
    		 try {
    	         Class.forName("org.gjt.mm.mysql.Driver");
    	 
    	 	} catch ( java.lang.ClassNotFoundException e ) {
    	         System.err.println("** Driver loaderror in loadConnect: " + e.getMessage() );
    	         e.printStackTrace(); 
    		}
    		
    	 	try {
    	         // 연결하기 - library 데이터베이스와 연결
    			 String URL = "jdbc:mysql://localhost:3306/library?useUnicode=true&characterEncoding=utf8";
    	         con  = DriverManager.getConnection(URL, "root", "onlyroot");	
    	         System.out.println("연결 완료");
    		} catch( SQLException ex ) {
    	         System.err.println("** connection error in loadConnect: " + ex.getMessage() );
    	         ex.printStackTrace();
    	 	}	       
    }
    
    // 주어진 아이디와  패스워드의 학생을 탐색하여 성공하면 해당 학생 객체를 반환
    // 탐색 실패시  null 반환
    public static Student findStudent(String ID, String password) {
       	try {                      
            // SQL 질의문을 수행한다.

			String sql = "select * from Student where id=? and pw=?;" ;
			prStmt = con.prepareStatement(sql);
			System.out.println(sql);
			
			prStmt.setString(1, ID);
			prStmt.setString(2, getPrivatePW(password));
			
			rs = prStmt.executeQuery();  
			if (rs.next())  {
			   	    Student student = getStudentFromRS(rs);
					return student;
			}
    	} catch( SQLException ex ) {             
                System.err.println("** SQL exec error in findStudent: " + ex.getMessage() );
    	}
   	
    	return null;
    }

	// 주어진 아이디의 학생을 탐색하여 성공하면 해당 학생 객체를 반환
    // 탐색 실패시  null 반환
   static Student findStudent(String ID) {
       	try {                      
            // SQL 질의문을 수행한다.
			String sql = "select * from Studnet where id=?;" ;
			prStmt = con.prepareStatement(sql);
 
			prStmt.setString(1, ID);
			
			rs = prStmt.executeQuery();  
			if (rs.next())  {
			   	    Student student = getStudentFromRS(rs);
					return student;
			}			
    	} catch( SQLException ex ) {             
                System.err.println("** SQL exec error in findStudent: " + ex.getMessage() );
    	}
   	
    	return null;
    }
    
	//  ResultSet객체에 student 투플이 저장되어 있을 때 이를 Student 객체로 변환하는 메소드
	static Student getStudentFromRS(ResultSet rs) {  
		Student student = null;
	
		try {
			String ID = rs.getString("id");
			student = new Student();	
			student.sno = rs.getInt("sno");
			student.sname = rs.getString("sname");
			student.year = rs.getInt("year");
			student.dept = rs.getString("dept");
			student.id = ID;
			student.pw = rs.getString("pw");

		} catch( SQLException ex ) 	    {
		    System.err.println("** SQL exec error in getStudentrFromRS: " + ex.getMessage() );
		}
	
	    return student;
	}

	// 학생이 책 반납 했을 떄 호출되는 메소드
	public static void returnBook(int sno) {		
		String sql;
		try {
			// Statement 생성 
			stmt = con.createStatement();
			
			// 대출중인 책이 없는 경우
			rs = getRSBorrowStudent(sno);
			if(!rs.next()) {
				System.out.println(" ● 대출중인 책이 없습니다.");
				return;
			}

			// 해당 학생의 대출중인 도서 정보 출력
			rs.beforeFirst();
			System.out.println("\n\n●--------------------------------------------------------<< 대출중인 책 정보 목록 >>--------------------------------------------------------●");
			DB.printRS(rs); // 결과집합을 받아서 출력해주는 메소드
			System.out.print("●-----------------------------------------------------------------------------------------------------------------------------------●");
			rs.beforeFirst();
			
			System.out.print("\n ** 반납을 원하는 도서의 번호를 선택하세요 > ");
			int select = SkScanner.getInt();
			
			String findISBN = "";
			// 반납 하려는 책의 isbn 찾는 것
			while(select-- != 0) {
				if(rs.next() == false) {
					findISBN = "";
					break;
				}
				findISBN = rs.getString("isbn");
			}
			
			if(findISBN.equals("")) {
				System.out.println(" ● 잘못된 번호 선택");
				return;
			}
			
			// 학생이 책을 반납함 -> 반납 확인 관리자는  경력이 가장 짧은 관리자가 관리
			int mno = DB.getNewComerNormalManager();
						
			// borrow 테이블의 반납일 업데이트
			sql = "update borrow \n"
					+ "set back = curdate(), mno = "+mno+" \n"
					+ "where isbn = "+findISBN+";";
			int tmp = stmt.executeUpdate(sql);
			checkSuccess(tmp, "borrow", "back");
			
			// 도서의 이용여부(avail)를 업데이트 해주는 쿼리
			sql = "update book \n"
				   +"set avail = 1 \n"
				   +"where isbn = "+findISBN+";";

			tmp = stmt.executeUpdate(sql);
			checkSuccess(tmp, "book", "avail");
			
			// 대출한 날짜를 구하는 쿼리
			java.sql.Date first = Date.valueOf("9999-12-29");
			rs.beforeFirst();
			while(rs.next()) {
				String tmp_isbn = rs.getString("isbn");
				if(tmp_isbn.equals(findISBN)) first=rs.getDate("first");
			}
			
			// 연체자의 경우 overdue 테이블에서 제거하는 쿼리
			sql = "delete \n"
				   +"FROM overdue \n"
				   +"where sno = "+sno+" and isbn = "+findISBN+" and first ="+first+";";
			tmp = stmt.executeUpdate(sql);
			
			System.out.println("\n\n● 반납완료 되었습니다  ●");
			
		} catch(SQLException ex) {
	        System.err.println("** SQL exec error in returnBook : " + ex.getMessage() );
		} catch (Exception e) {
			System.err.println("** error in printRS, "+e.getMessage());
		}
	}
	public static String getDate() {
		String current = (""+LocalDate.now()+"");
		
		return current;
	}
	// 가장 경력이 짧은 관리자의 mno 반환
	public static int getNewComerNormalManager() {
		ResultSet tmpRS = null;
		int mno=0;
		
		try {                      
            // SQL 질의문을 수행
			String sql = "select * from Manager;" ;
			stmt = con.createStatement();
			tmpRS = stmt.executeQuery(sql);
						
			int min = 9999;
			
			while(tmpRS.next()) {
				int tmp = tmpRS.getInt("career");
				// A.compareTo(B) => A-B 대소비교
				if(min > tmp) {
					min=tmp;
					mno=tmpRS.getInt("mno");
				}
			}
			
    	} catch( SQLException ex ) {             
                System.err.println("** SQL exec error in getNewComerNormalManager: " + ex.getMessage() );
    	}
		return mno;
	}

	// 해당 학생의 대출중인 도서 정보 출력
	public static ResultSet getRSBorrowStudent(int sno) {
		ResultSet tmpRS = null;
       	try {                      
            // SQL 질의문을 수행
			String sql = "select * from borrow where sno="+sno+" and back is null;" ;
			stmt = con.createStatement();			
			tmpRS = stmt.executeQuery(sql);
    	} catch( SQLException ex ) {             
                System.err.println("** SQL exec error in getRSBorrowStudent: " + ex.getMessage() );
    	}
		return tmpRS;
	}

	// 결과집합을 받아서 그 결과집합 내용을 출력해 주는 메소드
	// 예외 처리 - 결과집합에 정보가 없는 경우
	public static void printRS(ResultSet tmpRS) throws Exception {
		
		// 결과 집합이 비어 있는 경우
		try {
			tmpRS.beforeFirst();
			if (tmpRS.next() == false) {
				Exception ex = new Exception("** 결과 집합이 비어 있습니다");
				throw ex;
			}
			
			tmpRS.beforeFirst();
			// 결과 집합이 비어있지 않은 경우
			// 결과집합의 column개수를 알아낸 후에, 출력
			ResultSetMetaData rsm = tmpRS.getMetaData();
			int cnt = rsm.getColumnCount();
			int num=0;
			while(tmpRS.next()) {
				System.out.print("#"+(++num)+". ");
				for(int i=1;i<=cnt;i++) {
					String title = rsm.getColumnLabel(i);
					Object obj = tmpRS.getObject(i);
					
					// 패스워드는 출력 안함
					if(title.equals("pw")) continue;
					
					// 한글화하는 작업
					switch (title) {
					case "sno": title = "학번"; break;
					case "sname": title = "이름"; break;
					case "year": title = "학년"; break;
					case "dept": title = "학과"; break;
					case "isbn": title = "ISBN"; break;
					case "author": title = "글쓴이"; break;
					case "bname": title = "도서 이름"; break;
					case "publisher": title = "출판사"; break;
					case "bno": title = "등록번호"; break;
					case "cnt": title = "대출횟수"; break;
					case "avail": title = "대출여부"; 
						if((boolean)obj) obj="대출가능"+"";
						else obj="대출불가"+""; 
						break;						
					case "rrn": title = "주민번호"; break;
					case "mname": title = "이름"; break;
					case "career": title = "경력"; break;
					case "mno": title = "사번"; break;
					case "dateOfEntry": title = "입사일"; break;
					case "first": title = "대출일"; break;
					case "howlong": title = "연체일수"; break;
					case "penalty": title = "연체료"; break;
					case "deadline": title = "반납예정일"; break;
					case "extraCnt": title = "연장횟수"; break;
					case "back": title = "반납일"; break;
					case "late": title = "연체여부"; break;
					case "count(borrow.sno)": title = "대출횟수"; break;
					}
					
					if(i==cnt) System.out.print(title+":"+obj);
					else System.out.print(title+":"+obj+", ");
				}
				System.out.println();
			}
			
		}catch(Exception e) {
			System.err.println("error : in printRS, "+e.getMessage());
		}
	}

	// isbn을 받아서  책 정보 결과집합을 반납하는 메소드
	public static ResultSet getRSFindBook(String isbn) {
		ResultSet tmpRS = null;
		
		try {                      
			
            // SQL 질의문을 수행
			String sql = "select bname as 책, author as 저자, publisher as 출판사, bno as 책번호, isbn, avail, cnt as 대여횟수  from book where isbn="+isbn+";";
			stmt = con.createStatement();
			
			tmpRS = prStmt.executeQuery(sql);
			tmpRS.beforeFirst();
    	} catch( SQLException ex ) {
                System.err.println("** SQL exec error in getRSFindBook: " + ex.getMessage() );
    	}
		
		return tmpRS;
	}

	// 인기순위 도서 검색(전체기간)하여 결과집합을 반환하는 메소드
	public static ResultSet getRSFindBestSeller() {
		ResultSet tmpRS = null;
		try {                      
			
            // SQL 질의문을 수행
			String sql = "select bname as 책, author as 저자, publisher as 출판사, bno as 책번호, isbn, avail, cnt as 대여횟수 "
				    +"from book "
				    +"order by cnt desc;";

			stmt = con.createStatement();
			
			tmpRS = stmt.executeQuery(sql);

    	} catch( SQLException ex ) {             
                System.err.println("** SQL exec error in getRSFindBestSeller: " + ex.getMessage() );
    	}
		return tmpRS;
	}

	// 인기순위 도서 검색(이번달)하여 결과집합을 반환하는 메소드
	public static ResultSet getRSBestSellerThisMonth() {
		ResultSet tmpRS = null;
		try {                      
			
            // SQL 질의문을 수행
			String sql = "select bname as 책, author as 저자, publisher as 출판사, bno as 책번호, book.isbn, avail, count(borrow.isbn) as 대여횟수 "
				    +"from borrow, book "
				    +"where borrow.isbn = book.isbn  and ( first > LAST_DAY(NOW() - interval 1 month) AND first <= LAST_DAY(NOW()) ) "
				    +"group by borrow.isbn;";

			stmt = con.createStatement();
			
			tmpRS = stmt.executeQuery(sql);

    	} catch( SQLException ex ) {             
                System.err.println("** SQL exec error in getRSBestSellerThisMonth: " + ex.getMessage() );
    	}
		return tmpRS;
	}
/*
	// 대여하는 메소드 (주의사항 : 빌릴수 있는 지확인,, avail==1 / 대여후 avail=0 / 대출횟수 +1 / 반납예정일 업데이트)
	// 주의 : 하루에 한 사람은 같은 책을 두 번 대출 할 수 없음
	public static void borrowBook(ResultSet tmpRS, int select, int sno) {
		
		try {                      
			tmpRS.beforeFirst();
			// 목록중 학새이 선택한 isbn과 bno먼저 탐색
			int count=0;
			String select_isbn="";
			while(tmpRS.next()) {
				count++;
				if(count==select) {
					select_isbn=tmpRS.getString("isbn");
					if(tmpRS.getInt("avail")==0) { 
						System.out.println("대출 불가 도서 입니다");
						return;
					}
					break;
				}
			}
			
            // borrow 테이블에 추가
			String sql = "insert into "
					+ "borrow values("+sno+","+select_isbn+",curdate(),date_add(curdate(),interval 14 day),0,null,null,0);";
			stmt = con.createStatement();
			
			int result = stmt.executeUpdate(sql);
			checkSuccess(result, "borrow", "borrow"); // update문이 정상적으로 작동했는지 체크
			
	    	// 도서의 이용여부 업데이트
	    	sql = "update book "
	    	        +"set avail = 0 " 
	    	        +"where isbn = "+select_isbn+";";
	    	
			stmt = con.createStatement();
			
			result = stmt.executeUpdate(sql);
			checkSuccess(result, "book", "avail"); // update문이 정상적으로 작동했는지 체크
	    	
			// 대출 횟수 +1
			sql =  "update book "
			        +"set cnt = cnt+1 "
			        +"where isbn = "+select_isbn+";";
			stmt = con.createStatement();
			
			result = stmt.executeUpdate(sql);
			checkSuccess(result, "book", "cnt"); // update문이 정상적으로 작동했는지 체크
	    	
			// 대출한 책의 반납예정일을 업데이트 하는 쿼리
			sql =  "update borrow "
			        +"set deadline = date_add(first, interval 14 day);";
			stmt = con.createStatement();
			
			result = stmt.executeUpdate(sql);
			checkSuccess(result, "borrow", "deadline"); // update문이 정상적으로 작동했는지 체크
			
			System.out.println(" ● 대출신청 되었습니다 ●");
    	} catch( SQLException ex ) {             
                System.err.println("\n** SQL exec error in borrowBook " + ex.getMessage() );
    	}
	}*/
	public static int borrowBook(ResultSet tmpRS, int sno) {
		
		try {                      
			tmpRS.beforeFirst();
			tmpRS.first();
			// 목록중 학새이 선택한 isbn과 bno먼저 탐색
			String select_isbn="";
			select_isbn=tmpRS.getString("isbn");
					if(tmpRS.getInt("avail")==0) { 
						return -1;
					}
			
            // borrow 테이블에 추가
			String sql = "insert into "
					+ "borrow values("+sno+","+select_isbn+",curdate(),date_add(curdate(),interval 14 day),0,null,null,0);";
			stmt = con.createStatement();
			
			int result = stmt.executeUpdate(sql);
			checkSuccess(result, "borrow", "borrow"); // update문이 정상적으로 작동했는지 체크
			
	    	// 도서의 이용여부 업데이트
	    	sql = "update book "
	    	        +"set avail = 0 " 
	    	        +"where isbn = "+select_isbn+";";
	    	
			stmt = con.createStatement();
			
			result = stmt.executeUpdate(sql);
			checkSuccess(result, "book", "avail"); // update문이 정상적으로 작동했는지 체크
	    	
			// 대출 횟수 +1
			sql =  "update book "
			        +"set cnt = cnt+1 "
			        +"where isbn = "+select_isbn+";";
			stmt = con.createStatement();
			
			result = stmt.executeUpdate(sql);
			checkSuccess(result, "book", "cnt"); // update문이 정상적으로 작동했는지 체크
	    	
			// 대출한 책의 반납예정일을 업데이트 하는 쿼리
			sql =  "update borrow "
			        +"set deadline = date_add(first, interval 14 day);";
			stmt = con.createStatement();
			
			result = stmt.executeUpdate(sql);
			checkSuccess(result, "borrow", "deadline"); // update문이 정상적으로 작동했는지 체크
			
			
    	} catch( SQLException ex ) {             
                System.err.println("\n** SQL exec error in borrowBook " + ex.getMessage() );
    	}
		return 1;
	}

	
	public static ResultSet getRSHistory(int sno) {
		ResultSet tmpRS = null;
		
		try {                      			
			// sno 학번의 학생 정보
			String sql = "select Book.bname as 도서명, Borrow.first as 대출일, Borrow.back as 반납일"
					+ "from Borrow, Book "
					+ "where Borrow.isbn = Book.isbn and Borrow.sno = "+sno+";";

			stmt = con.createStatement();
			
			tmpRS = stmt.executeQuery(sql);
			
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in getRSHistory: " + ex.getMessage() );
    	}
		return tmpRS;
	}
	public static boolean Extend(int sno, String isbn)
	{
		boolean check1 = false;
		boolean check2 = false;
		boolean check3 = false;
		
		int result1 = ExtendStep1(sno, isbn);
		int result2 = ExtendStep2(sno, isbn);
		
		if(result1 < 1)
			check1 = true;
		if(result2 < 1)
			check2 = true;
		
		check3 = check1 && check2;
		
		return check3;
		
	}
	public static int ExtendStep1(int sno, String isbn) {
		int check = 0;
		try {                      			
			String sql = "update borrow "
				    +"set extraCnt = extraCnt +1 "
					+"where sno="+sno+" and isbn="+isbn+" and back is null and late = 0 and extraCnt < 2;";
			

			stmt = con.createStatement();
			check = stmt.executeUpdate(sql);
			
			

    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in updateCareer: " + ex.getMessage() );
    	}
		return check;
	}
	public static int ExtendStep2(int sno, String isbn) {
		int check = 0;
		try {                      			
			String sql = "update borrow "
				    +"set deadline = DATE_ADD(deadline, INTERVAL 14 DAY) "
					+"where sno="+sno+" and isbn="+isbn+" and back is null and late = 0 and extraCnt < 2;";
			

			stmt = con.createStatement();
			check = stmt.executeUpdate(sql);
			

    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in updateCareer2: " + ex.getMessage() );
    	}
		return check;
	}
		
	// update문이 정상적으로 작동하는지 체크하는 메소드
	public static void checkSuccess(int result, String table, String name) {
    	if (result <= 0)       	
    		System.err.println("  >> not Inserted or Update New "+table+"\n\n"); 
	}
	
	// sno 학생의 대출 현황 결과집합 반환하는 메소드 
	public static ResultSet getRSFromBorrow(int sno) {
		ResultSet tmpRS = null;
		
		try {                      			
			// sno 학번의 학생 정보
			String sql = "select * "
					+ "from borrow "
					+ "where sno = "+sno+" and back is null;";

			stmt = con.createStatement();
			
			tmpRS = stmt.executeQuery(sql);
			
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in getRSFromBorrow: " + ex.getMessage() );
    	}
		return tmpRS;
	}

	// 학생이 도서관에 회원가입하는 메소드
	public static boolean register(String ID, String password) {
		// ID가 이미 존재한다면 다른 ID입력 하도록 유도
		if(isExistID(ID)) {
			System.out.println("** 입력하신 ID는 이미 존재합니다.\n");
			return false;
		}
		Student newStudent = new Student();
		newStudent.id=ID;
		newStudent.pw=getPrivatePW(password);
		
		System.out.println("\n-- 사용자 정보 입력 --");
		System.out.print(" ● 학번 > ");
		newStudent.sno = SkScanner.getInt();
		System.out.print(" ● 이름 > ");
		newStudent.sname = SkScanner.getString();
		System.out.print(" ● 학과 > ");
		newStudent.dept = SkScanner.getString();
		System.out.print(" ● 학년 > ");
		newStudent.year = SkScanner.getInt();
		
		ResultSet tmpRS = DB.getRSFromStudentBySno(newStudent.sno);
		try {
			if(tmpRS.next()) {
				System.out.println(" ●● 이미 존재하는 학번 입니다  ●●\n");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("** SQL exec error in register: " + e.getMessage() );
		}
		// 학생 테이블에 삽입
		insertStudent(newStudent);
		
		System.out.println(" ● 회원 등록 완료  ●\n");
		return true;
	}

	// sno를 통해 학생테이블의 결과집합을 반환
	public static ResultSet getRSFromStudentBySno(int sno) {
		ResultSet tmpRS = null;
		try {                
			String sql = "select * "
					+ "from student "
					+ "where sno = "+sno+";";

			stmt = con.createStatement();
			
			tmpRS = stmt.executeQuery(sql);
			
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in getRSFromStudentBySno: " + ex.getMessage() );
    	}
		
		return tmpRS;
	}

	// 학생 테이블에 삽입
	public static void insertStudent(Student newStudent) {
       	try {                      
            // SQL 질의문을 수행한다.
       		String sql = "insert into student values(?,?,?,?,?,?);";
			prStmt = con.prepareStatement(sql);
			prStmt.setInt(1, newStudent.sno);
			prStmt.setString(2, newStudent.sname);
			prStmt.setInt(3, newStudent.year);
			prStmt.setString(4, newStudent.dept);
			prStmt.setString(5, newStudent.id);
			prStmt.setString(6, getPrivatePW(newStudent.pw));
			int result = prStmt.executeUpdate();  
			checkSuccess(result, "student", "student"); // update문이 정상적으로 작동했는지 체크
		
    	} catch( SQLException ex ) {             
                System.err.println("** SQL exec error in insertStudent: " + ex.getMessage() );
    	}
	}

	// password 암호화
	// PI를 이용한 암호화 : 패스워드 문자열에 PI의 각 소숫점 값을 아스키 코드 문자값으로 더해가며 암호화
	public static String getPrivatePW(String password) {
		BigDecimal PI = new BigDecimal(Math.PI);
		int len = password.length();
		char[] chs = password.toCharArray();
		
		// 문자하나하나를 암호화 코딩
		String result="";
		int current = 0;
		while(current < len) {
			
			PI = PI.multiply(new BigDecimal(10)); // 31.41592...
			PI = PI.remainder(new BigDecimal(10)); // 1.41592...
			String tmp = PI.setScale(0, BigDecimal.ROUND_FLOOR) +""; // 1
			int val = Integer.parseInt(tmp);
			
			// 문자 하나하나를 PI소수점 값 하나와 합
			int result_val=((int)chs[current]) + val;
			result_val%=128; // 아스키 코드 값을 초과한 경우를 대비
			result+=(char)result_val+"";
			current++;			
		}
		return result;
	}

	// ID가 이미 존재하는지 체크하는 메소드
	public static boolean isExistID(String ID) {
		ResultSet studentRS = getRSFromStudent();
		ResultSet managerRS = getRSFromManager();		
		
		try {
			while(studentRS.next()) if(studentRS.getString("id").equals(ID)) return true;
			while(managerRS.next()) if(managerRS.getString("id").equals(ID)) return true;
		} catch (SQLException e) {
			System.out.println("**error in isExistID, " + e.getMessage());
		}
		return false;
	}

	// 관리자 테이블의 RS를 반환하는 메소드
	public static ResultSet getRSFromManager() {
		ResultSet tmpRS = null;
		try {                      			
			// sno 학번의 학생 정보
			String sql = "select * "
					+ "from manager ;";

			stmt = con.createStatement();			
			tmpRS = stmt.executeQuery(sql);
		
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in getRSFromManager: " + ex.getMessage() );
    	}
		return tmpRS;
	}

	// 학생 테이블의 RS를 반환하는 메소드
	public static ResultSet getRSFromStudent() {
		ResultSet tmpRS = null;
		try {                      			
			// sno 학번의 학생 정보
			String sql = "select * "
					+ "from student;";

			stmt = con.createStatement();			
			tmpRS = stmt.executeQuery(sql);
		
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in getRSFromStudent: " + ex.getMessage() );
    	}
		return tmpRS;
	}

	// id와 password를 통해 Manager객체 반환
	public static Manager findManager(String ID, String password) {
		
       	try {                      
            // SQL 질의문을 수행한다.
			String sql = "select * from Manager where id=? and pw=?;" ;
			prStmt = con.prepareStatement(sql);
 
			prStmt.setString(1, ID);
			prStmt.setString(2, getPrivatePW(password));
			rs = prStmt.executeQuery();

			if (rs.next()) {
				Manager manager = getManagerFromRS(rs);
				return manager;
			}
    	} catch( SQLException ex ) {             
                System.err.println("** SQL exec error in findManager: " + ex.getMessage() );
    	}
   	
    	return null;
	}
	
	//  ResultSet객체에 Manager 투플이 저장되어 있을 때 이를 Manager 객체로 변환하는 메소드
	public static Manager getManagerFromRS(ResultSet rs) {
		Manager manager = null;
		
		try {		
			if(rs.getString("id").equals("root")) manager = new RootManager();
			else manager = new NormalManager();

			manager.rrn = rs.getString("rrn");
			manager.mname = rs.getString("mname");
			manager.career = rs.getInt("career");
			manager.id = rs.getString("id");
			manager.pw = rs.getString("pw");
			manager.mno = rs.getInt("mno");
			manager.dateOfEntry = rs.getDate("dateOfEntry");

		} catch (SQLException ex) {
			System.err.println("** SQL exec error in getManagerFromRS: " + ex.getMessage());
		}

		return manager;
	}

	// root관리자의 작업 : 관리자들의 경력 증가
	public static void updateCareer() {
		try {                      			
			String sql = "update manager "
				    +"set career = career +1;";

			stmt = con.createStatement();
			boolean check = stmt.execute(sql);
			
			if(check)
				checkSuccess(-1, "Manager", "career"); // update문이 정상적으로 작동했는지 체크

    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in updateCareer: " + ex.getMessage() );
    	}
	}

	// root관리자의 작업 : 관리자 생성(ID와 password받아서 생성)
	public static boolean createManager(String ID, String password) {
		// ID가 이미 존재한다면 다른 ID입력 하도록 유도
		if(isExistID(ID)) {
			System.out.println("** 입력하신 ID는 이미 존재합니다.\n");
			return false;
		}
		NormalManager newManager = new NormalManager();
		newManager.id=ID;
		newManager.pw=getPrivatePW(password);
		

		System.out.println("\n ●---<< 관리자 정보 입력  >>---●   ");
		System.out.print(" ● 주민번호 > ");
		newManager.rrn = SkScanner.getString();
		System.out.print(" ● 이름 > ");
		newManager.mname = SkScanner.getString();
		System.out.print(" ● 경력 > ");
		newManager.career = SkScanner.getInt();
		System.out.print(" ● 사번 > ");
		newManager.mno = SkScanner.getInt();
		System.out.print(" ● 입사일 > ");
		newManager.dateOfEntry = java.sql.Date.valueOf(SkScanner.getString());
		
		ResultSet tmpRS = DB.getRSFromManagerByMno(newManager.mno);
		try {
			if(tmpRS.next()) {
				System.out.println(" ●● 이미 존재하는 사번 입니다  ●●\n");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("** SQL exec error in createManager: " + e.getMessage() );
		}
		
		// 관리자 테이블에 삽입
		insertManager(newManager);
		return true;
	}

	// mno로 관리자 정보를 찾아서 결과집합을 반환하는 메소드
	public static ResultSet getRSFromManagerByMno(int mno) {
		ResultSet tmpRS = null;
		try {                
			String sql = "select * "
					+ "from manager "
					+ "where mno = "+mno+";";

			stmt = con.createStatement();
			
			tmpRS = stmt.executeQuery(sql);
			
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in getRSFromManagerByMno: " + ex.getMessage() );
    	}
		
		return tmpRS;
	}

	// 관리자 테이블에 관리자 정보 삽입하는 메소드
	public static void insertManager(NormalManager newManager) {
		try {                      
       		String sql = "insert into Manager values(?,?,?,?,?,?,?);";	
			prStmt = con.prepareStatement(sql);
 
			prStmt.setString(1, newManager.rrn);
			prStmt.setString(2, newManager.mname);
			prStmt.setInt(3, newManager.career);
			prStmt.setString(4, newManager.id);
			prStmt.setString(5, getPrivatePW(newManager.pw));
			prStmt.setInt(6, newManager.mno);
			prStmt.setDate(7, newManager.dateOfEntry);
			
			int result = prStmt.executeUpdate();  
			checkSuccess(result, "Manager", "Manager"); // update문이 정상적으로 작동했는지 체크
		
    	} catch( SQLException ex ) {             
                System.err.println("** SQL exec error in insertManager: " + ex.getMessage() );
    	}
	}

	// root관리자 업무 : 관리자 삭제
	public static void deleteManager(int mno) {
		try { 		
			String sql = "delete from manager "
					+"where mno="+mno+";";
			
			stmt = con.createStatement();			
			int result = stmt.executeUpdate(sql);
			if(result<=0) {
				System.out.println("* 입력하신"+mno+"의 사번을 갖는 관리자는 없습니다");
				return;
			}
			System.out.println(" ● "+mno+"사번을 갖는 관리자가 삭제 되었습니다 ●\n");
			
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in deleteManager: " + ex.getMessage() );
    	}
	}

	// root관리자 업무 : 관리자 목록 확인
	public static void printManagerList() {
		try { 		
			String sql = "select * from manager;";
						
			stmt = con.createStatement();
			ResultSet tmpRS = stmt.executeQuery(sql);
			
			printRS(tmpRS);
			
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in printManagerList: " + ex.getMessage() );
    	} catch (Exception e) {
			System.err.println("* error in printRS, "+e.getMessage() );
		}
		
	}

	// 일반 관리자 업무 : 대여 가능한 도서 출력
	public static void printBorrowableBook() {
		try { 		
			String sql = "select bno as 넘버, bname as 도서명, author as 저자, publisher as 출판사, isbn "
				    +"from book where avail = 1 order by bno asc;";

			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);			
			printRS(rs);
			
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in printBorrowableBook: " + ex.getMessage() );
    	} catch (Exception e) {
    		System.err.println("* error in printRS, "+e.getMessage() );
		}
	}

	// 일반 관리자 업무 : 이번달의 베스트 셀러 출력
	public static void printBestSellerThisMonth() {
		rs = getRSBestSellerThisMonth();
		try {
			printRS(rs);
		} catch (Exception e) {
			System.err.println("* error in printRS, "+e.getMessage() );		
			}
	}

	// 일반 관리자 업무 & 학생의 업무 : 베스트 셀러 출력(모든 기간)
	public static void printBestSeller() {
		rs = getRSFindBestSeller();
		try {
			printRS(rs);
		} catch (Exception e) {
			System.err.println("* error in printRS, "+e.getMessage() );		
			}
	}

	// 관리자의 업무 : 모든 학생 정보 출력
	public static void printStudnetList() {
		rs = DB.getRSFromStudent();
		try {
			printRS(rs);
		} catch (Exception e) {
			System.err.println("* error in printRS, "+e.getMessage() );
		}
		
	}

	// 관리자의 업무 : 학과별 대출 현황
	public static void printBorrowByDept() {
		try { 		
			String sql =  "select dept as 학과, count(borrow.sno) as 대출횟수 "
				    +"from borrow, student "
				    +"where borrow.sno = student.sno "
				    +"group by dept;";
					
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);			
			printRS(rs);
			
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in printBorrowByDept: " + ex.getMessage() );
    	} catch (Exception e) {
    		System.err.println("* error in printRS, "+e.getMessage() );
		}
	}
	public static ResultSet getRSBorrowByDept() {
		ResultSet tmpRS = null;
		try {                
			String sql =  "select dept as 학과, count(borrow.sno) as 대출횟수 "
				    +"from borrow, student "
				    +"where borrow.sno = student.sno "
				    +"group by dept;";

			stmt = con.createStatement();
			
			tmpRS = stmt.executeQuery(sql);
			
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in getRSFromManagerByMno: " + ex.getMessage() );
    	}
		
		return tmpRS;
	}


	// 관리자의 업무 : 독서왕 리스트
	public static void printBestReader() {
		try { 		
			String sql = "select borrow.sno as 학번, sname as 이름, count(borrow.sno) as 대출횟수 "
				    +"from student, borrow "
				    +"where student.sno = borrow.sno and year <= 4 "
				    +"group by borrow.sno;";
					
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);	
			printRS(rs);
			
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in printBestReader: " + ex.getMessage() );
    	} catch (Exception e) {
    		System.err.println("* error in printRS, "+e.getMessage() );
		}	
	}
	public static ResultSet getRSBestReader() {
		ResultSet tmpRS = null;
		try {                
			String sql = "select borrow.sno as 학번, sname as 학생명, count(borrow.sno) as 대여횟수 "
				    +"from student, borrow "
				    +"where student.sno = borrow.sno and year <= 4 "
				    +"group by borrow.sno;";

			stmt = con.createStatement();
			
			tmpRS = stmt.executeQuery(sql);
			
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in getRSFromManagerByMno: " + ex.getMessage() );
    	}
		
		return tmpRS;
		
	}

	// 관리자의 업무 : 학년별 대출 리스트
	public static void printBorrowByYear() {
		try { 		
			String sql =  "select year as 학년, count(borrow.sno) as 대출횟수"
				    +"from borrow, student "
				    +"where borrow.sno = student.sno "
				    +"group by year;";

			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);			
			printRS(rs);
			
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in printBorrowByYear: " + ex.getMessage() );
    	} catch (Exception e) {
    		System.err.println("* error in printRS, "+e.getMessage() );
		}	
	}
	public static ResultSet getRSBorrowByYear() {
		ResultSet tmpRS = null;
		try {                
			String sql =  "select year as 학년, count(borrow.sno) as 대출횟수 "
				    +"from borrow, student "
				    +"where borrow.sno = student.sno "
				    +"group by year;";

			stmt = con.createStatement();
			
			tmpRS = stmt.executeQuery(sql);
			
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in getRSFromManagerByMno: " + ex.getMessage() );
    	}
		
		return tmpRS;
	}

	// 관리자의 업무 : 연체중인 도서 목록
	public static void printOverdueBookList() {
		try { 		
			String sql = "select book.bno as 넘버, bname as 도서명, author as 저자, publisher as  출판사, borrow.isbn "
				    +"from book, overdue, borrow "
				    +"where book.isbn = overdue.isbn and overdue.isbn = borrow.isbn order by bno asc;";
					
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);			
			printRS(rs);
			
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in printOverdueBookList: " + ex.getMessage() );
    	} catch (Exception e) {
    		System.err.println("* error in printRS, "+e.getMessage() );
		}
	}
public static ResultSet getRSOverdueBookList() {
		
		ResultSet tmpRS = null;
		try {                      
			
            // SQL 질의문을 수행
			String sql = "select book.bno as 넘버, bname as 도서명, author as 저자, publisher as 출판사, borrow.isbn "
				    +"from book, overdue, borrow "
				    +"where book.isbn = overdue.isbn and overdue.isbn = borrow.isbn order by bno asc;";
				
			stmt = con.createStatement();			
			tmpRS = stmt.executeQuery(sql);

    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in getRSAllBook: " + ex.getMessage());
    	}
		return tmpRS;
	}

	// 관리자의 업무 : 연체중인 학생 목록
	public static void printOverdueStudentList() {
		try {
			String sql = "select overdue.sno, sname, year, dept, id, penalty "
				    +"from student, overdue "
				    +"where overdue.sno=student.sno order by student.sno asc;";
					
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);	
			printRS(rs);
			
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in printOverdueStudentList: " + ex.getMessage() );
    	} catch (Exception e) {
    		System.err.println("* error in printRS, "+e.getMessage() );
		}
	}
	
	public static ResultSet getRSOverdueStudentList() {
		ResultSet tmpRS = null;
		try {                      
			
            // SQL 질의문을 수행
			String sql = "select overdue.sno as 학번, sname as 이름, year as 학년, dept as 학과, penalty as 연체료 "
				    +"from student, overdue "
				    +"where overdue.sno=student.sno order by student.sno asc;";
			
			stmt = con.createStatement();			
			tmpRS = stmt.executeQuery(sql);

    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in getRSAllBook: " + ex.getMessage());
    	}
		return tmpRS;
	}

	// 관리자의 업무 : 연체 처리
	public static void processOverdue() {
		try { 		
			String sql = "replace "
				    +"into overdue(sno, isbn, first, howlong, penalty) "
				    +"select borrow.sno, isbn, first, to_days(now())-to_days(first), (to_days(now())-to_days(deadline)) * 100 "
				    +"from borrow, student "
				    +"where borrow.sno = student.sno and borrow.deadline < current_date and back is null; ";

			stmt = con.createStatement();
			int result = stmt.executeUpdate(sql);
			if(result<=0) {
				System.out.println(" ● 연체자는 없습니다  ●");
				return;
			}
			
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in processOverdue: " + ex.getMessage() );
    	}
	}

	// 관리자의 업무 : 학생의 학년 갱신
	public static void updateStudentByYear() {
		try { 		
			String sql = "update student "
				    +"set year = year + 1;";

			stmt = con.createStatement();			
			int result = stmt.executeUpdate(sql);

	
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in updateStudentByYear: " + ex.getMessage() );
    	}
	}
	public static void updateStudentByYear2() {
		try { 		
			String sql = "update student "
				    +"set year = year - 1;";

			stmt = con.createStatement();			
			int result = stmt.executeUpdate(sql);
	
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in updateStudentByYear: " + ex.getMessage() );
    	}
	}
	

	// 학생의 업무 : 모든 책 목록의 결과집합을 반환
	public static ResultSet getRSAllBook() {
		
		ResultSet tmpRS = null;
		try {                      
			
            // SQL 질의문을 수행
			String sql = "select bno as 넘버, bname as 도서명, author as 저자, publisher as 출판사, isbn , avail as 이용여부 "
				    +"from book order by bno asc;";

			stmt = con.createStatement();			
			tmpRS = stmt.executeQuery(sql);

    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in getRSAllBook: " + ex.getMessage());
    	}
		return tmpRS;
	}
	
	// 일반 관리자 업무 : 총 도서 목록 출력 
	public static void printAllBook() {
		try { 		
			String sql = "select bno, bname, author, publisher, isbn "
				    +"from book order by bno asc;";

			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);			
			printRS(rs);
			
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in printAllBook: " + ex.getMessage() );
    	} catch (Exception e) {
    		System.err.println("* error in printRS, "+e.getMessage() );
		}
	}
	
	   public static ResultSet selectQuery(String sql) { 
	 	   try {
	 		   // Statement 생성 
	 		   stmt = con.createStatement();
	 		            
	 			   
	 		   rs = stmt.executeQuery(sql);  

	 	   } catch( SQLException ex ) 	    {
	 		   System.err.println("** SQL exec error in selectQuery() : " + ex.getMessage() );
	 	   }
	 			
	 	   return rs;
	 		
	    }
	
    public static Vector selectDistinctDepts() {
     	// 주어진 dept에 대한 학생정보 검색
     	String sql = "select distinct dept from student;";
     	System.out.println("   >> SQL : " + sql + "\n");
 		   
     	ResultSet rs = selectQuery(sql);
     	Vector depts = new Vector();
     	String dept;
     	
     	try {
     		while(rs.next()) {
     			depts.add(dept=rs.getString("dept"));
     		   	System.out.println("   >> SQL : dept=" + dept + "\n");
    	}
  	   } catch( SQLException ex ) {
 		   System.err.println("** SQL exec error in selectDistinctDept() : " + ex.getMessage() );	   
 	   }	

     	return depts;
    }
    
	public static ResultSet getRSRemainBook() {
		ResultSet tmpRS = null;
		try {                      			
			
			String sql = "select bno as 넘버, bname as 도서명, author as 저자, publisher as 출판사, isbn , avail as 이용여부 "
					+ "from book where avail =" +true+";";

			stmt = con.createStatement();			
			tmpRS = stmt.executeQuery(sql);
		
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in getRSFromStudent: " + ex.getMessage() );
    	}
		return tmpRS;
	}

}
