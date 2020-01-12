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
    		    // ����̹� �ε�
    		 try {
    	         Class.forName("org.gjt.mm.mysql.Driver");
    	 
    	 	} catch ( java.lang.ClassNotFoundException e ) {
    	         System.err.println("** Driver loaderror in loadConnect: " + e.getMessage() );
    	         e.printStackTrace(); 
    		}
    		
    	 	try {
    	         // �����ϱ� - library �����ͺ��̽��� ����
    			 String URL = "jdbc:mysql://localhost:3306/library?useUnicode=true&characterEncoding=utf8";
    	         con  = DriverManager.getConnection(URL, "root", "onlyroot");	
    	         System.out.println("���� �Ϸ�");
    		} catch( SQLException ex ) {
    	         System.err.println("** connection error in loadConnect: " + ex.getMessage() );
    	         ex.printStackTrace();
    	 	}	       
    }
    
    // �־��� ���̵��  �н������� �л��� Ž���Ͽ� �����ϸ� �ش� �л� ��ü�� ��ȯ
    // Ž�� ���н�  null ��ȯ
    public static Student findStudent(String ID, String password) {
       	try {                      
            // SQL ���ǹ��� �����Ѵ�.

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

	// �־��� ���̵��� �л��� Ž���Ͽ� �����ϸ� �ش� �л� ��ü�� ��ȯ
    // Ž�� ���н�  null ��ȯ
   static Student findStudent(String ID) {
       	try {                      
            // SQL ���ǹ��� �����Ѵ�.
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
    
	//  ResultSet��ü�� student ������ ����Ǿ� ���� �� �̸� Student ��ü�� ��ȯ�ϴ� �޼ҵ�
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

	// �л��� å �ݳ� ���� �� ȣ��Ǵ� �޼ҵ�
	public static void returnBook(int sno) {		
		String sql;
		try {
			// Statement ���� 
			stmt = con.createStatement();
			
			// �������� å�� ���� ���
			rs = getRSBorrowStudent(sno);
			if(!rs.next()) {
				System.out.println(" �� �������� å�� �����ϴ�.");
				return;
			}

			// �ش� �л��� �������� ���� ���� ���
			rs.beforeFirst();
			System.out.println("\n\n��--------------------------------------------------------<< �������� å ���� ��� >>--------------------------------------------------------��");
			DB.printRS(rs); // ��������� �޾Ƽ� ������ִ� �޼ҵ�
			System.out.print("��-----------------------------------------------------------------------------------------------------------------------------------��");
			rs.beforeFirst();
			
			System.out.print("\n ** �ݳ��� ���ϴ� ������ ��ȣ�� �����ϼ��� > ");
			int select = SkScanner.getInt();
			
			String findISBN = "";
			// �ݳ� �Ϸ��� å�� isbn ã�� ��
			while(select-- != 0) {
				if(rs.next() == false) {
					findISBN = "";
					break;
				}
				findISBN = rs.getString("isbn");
			}
			
			if(findISBN.equals("")) {
				System.out.println(" �� �߸��� ��ȣ ����");
				return;
			}
			
			// �л��� å�� �ݳ��� -> �ݳ� Ȯ�� �����ڴ�  ����� ���� ª�� �����ڰ� ����
			int mno = DB.getNewComerNormalManager();
						
			// borrow ���̺��� �ݳ��� ������Ʈ
			sql = "update borrow \n"
					+ "set back = curdate(), mno = "+mno+" \n"
					+ "where isbn = "+findISBN+";";
			int tmp = stmt.executeUpdate(sql);
			checkSuccess(tmp, "borrow", "back");
			
			// ������ �̿뿩��(avail)�� ������Ʈ ���ִ� ����
			sql = "update book \n"
				   +"set avail = 1 \n"
				   +"where isbn = "+findISBN+";";

			tmp = stmt.executeUpdate(sql);
			checkSuccess(tmp, "book", "avail");
			
			// ������ ��¥�� ���ϴ� ����
			java.sql.Date first = Date.valueOf("9999-12-29");
			rs.beforeFirst();
			while(rs.next()) {
				String tmp_isbn = rs.getString("isbn");
				if(tmp_isbn.equals(findISBN)) first=rs.getDate("first");
			}
			
			// ��ü���� ��� overdue ���̺��� �����ϴ� ����
			sql = "delete \n"
				   +"FROM overdue \n"
				   +"where sno = "+sno+" and isbn = "+findISBN+" and first ="+first+";";
			tmp = stmt.executeUpdate(sql);
			
			System.out.println("\n\n�� �ݳ��Ϸ� �Ǿ����ϴ�  ��");
			
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
	// ���� ����� ª�� �������� mno ��ȯ
	public static int getNewComerNormalManager() {
		ResultSet tmpRS = null;
		int mno=0;
		
		try {                      
            // SQL ���ǹ��� ����
			String sql = "select * from Manager;" ;
			stmt = con.createStatement();
			tmpRS = stmt.executeQuery(sql);
						
			int min = 9999;
			
			while(tmpRS.next()) {
				int tmp = tmpRS.getInt("career");
				// A.compareTo(B) => A-B ��Һ�
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

	// �ش� �л��� �������� ���� ���� ���
	public static ResultSet getRSBorrowStudent(int sno) {
		ResultSet tmpRS = null;
       	try {                      
            // SQL ���ǹ��� ����
			String sql = "select * from borrow where sno="+sno+" and back is null;" ;
			stmt = con.createStatement();			
			tmpRS = stmt.executeQuery(sql);
    	} catch( SQLException ex ) {             
                System.err.println("** SQL exec error in getRSBorrowStudent: " + ex.getMessage() );
    	}
		return tmpRS;
	}

	// ��������� �޾Ƽ� �� ������� ������ ����� �ִ� �޼ҵ�
	// ���� ó�� - ������տ� ������ ���� ���
	public static void printRS(ResultSet tmpRS) throws Exception {
		
		// ��� ������ ��� �ִ� ���
		try {
			tmpRS.beforeFirst();
			if (tmpRS.next() == false) {
				Exception ex = new Exception("** ��� ������ ��� �ֽ��ϴ�");
				throw ex;
			}
			
			tmpRS.beforeFirst();
			// ��� ������ ������� ���� ���
			// ��������� column������ �˾Ƴ� �Ŀ�, ���
			ResultSetMetaData rsm = tmpRS.getMetaData();
			int cnt = rsm.getColumnCount();
			int num=0;
			while(tmpRS.next()) {
				System.out.print("#"+(++num)+". ");
				for(int i=1;i<=cnt;i++) {
					String title = rsm.getColumnLabel(i);
					Object obj = tmpRS.getObject(i);
					
					// �н������ ��� ����
					if(title.equals("pw")) continue;
					
					// �ѱ�ȭ�ϴ� �۾�
					switch (title) {
					case "sno": title = "�й�"; break;
					case "sname": title = "�̸�"; break;
					case "year": title = "�г�"; break;
					case "dept": title = "�а�"; break;
					case "isbn": title = "ISBN"; break;
					case "author": title = "�۾���"; break;
					case "bname": title = "���� �̸�"; break;
					case "publisher": title = "���ǻ�"; break;
					case "bno": title = "��Ϲ�ȣ"; break;
					case "cnt": title = "����Ƚ��"; break;
					case "avail": title = "���⿩��"; 
						if((boolean)obj) obj="���Ⱑ��"+"";
						else obj="����Ұ�"+""; 
						break;						
					case "rrn": title = "�ֹι�ȣ"; break;
					case "mname": title = "�̸�"; break;
					case "career": title = "���"; break;
					case "mno": title = "���"; break;
					case "dateOfEntry": title = "�Ի���"; break;
					case "first": title = "������"; break;
					case "howlong": title = "��ü�ϼ�"; break;
					case "penalty": title = "��ü��"; break;
					case "deadline": title = "�ݳ�������"; break;
					case "extraCnt": title = "����Ƚ��"; break;
					case "back": title = "�ݳ���"; break;
					case "late": title = "��ü����"; break;
					case "count(borrow.sno)": title = "����Ƚ��"; break;
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

	// isbn�� �޾Ƽ�  å ���� ��������� �ݳ��ϴ� �޼ҵ�
	public static ResultSet getRSFindBook(String isbn) {
		ResultSet tmpRS = null;
		
		try {                      
			
            // SQL ���ǹ��� ����
			String sql = "select bname as å, author as ����, publisher as ���ǻ�, bno as å��ȣ, isbn, avail, cnt as �뿩Ƚ��  from book where isbn="+isbn+";";
			stmt = con.createStatement();
			
			tmpRS = prStmt.executeQuery(sql);
			tmpRS.beforeFirst();
    	} catch( SQLException ex ) {
                System.err.println("** SQL exec error in getRSFindBook: " + ex.getMessage() );
    	}
		
		return tmpRS;
	}

	// �α���� ���� �˻�(��ü�Ⱓ)�Ͽ� ��������� ��ȯ�ϴ� �޼ҵ�
	public static ResultSet getRSFindBestSeller() {
		ResultSet tmpRS = null;
		try {                      
			
            // SQL ���ǹ��� ����
			String sql = "select bname as å, author as ����, publisher as ���ǻ�, bno as å��ȣ, isbn, avail, cnt as �뿩Ƚ�� "
				    +"from book "
				    +"order by cnt desc;";

			stmt = con.createStatement();
			
			tmpRS = stmt.executeQuery(sql);

    	} catch( SQLException ex ) {             
                System.err.println("** SQL exec error in getRSFindBestSeller: " + ex.getMessage() );
    	}
		return tmpRS;
	}

	// �α���� ���� �˻�(�̹���)�Ͽ� ��������� ��ȯ�ϴ� �޼ҵ�
	public static ResultSet getRSBestSellerThisMonth() {
		ResultSet tmpRS = null;
		try {                      
			
            // SQL ���ǹ��� ����
			String sql = "select bname as å, author as ����, publisher as ���ǻ�, bno as å��ȣ, book.isbn, avail, count(borrow.isbn) as �뿩Ƚ�� "
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
	// �뿩�ϴ� �޼ҵ� (���ǻ��� : ������ �ִ� ��Ȯ��,, avail==1 / �뿩�� avail=0 / ����Ƚ�� +1 / �ݳ������� ������Ʈ)
	// ���� : �Ϸ翡 �� ����� ���� å�� �� �� ���� �� �� ����
	public static void borrowBook(ResultSet tmpRS, int select, int sno) {
		
		try {                      
			tmpRS.beforeFirst();
			// ����� �л��� ������ isbn�� bno���� Ž��
			int count=0;
			String select_isbn="";
			while(tmpRS.next()) {
				count++;
				if(count==select) {
					select_isbn=tmpRS.getString("isbn");
					if(tmpRS.getInt("avail")==0) { 
						System.out.println("���� �Ұ� ���� �Դϴ�");
						return;
					}
					break;
				}
			}
			
            // borrow ���̺� �߰�
			String sql = "insert into "
					+ "borrow values("+sno+","+select_isbn+",curdate(),date_add(curdate(),interval 14 day),0,null,null,0);";
			stmt = con.createStatement();
			
			int result = stmt.executeUpdate(sql);
			checkSuccess(result, "borrow", "borrow"); // update���� ���������� �۵��ߴ��� üũ
			
	    	// ������ �̿뿩�� ������Ʈ
	    	sql = "update book "
	    	        +"set avail = 0 " 
	    	        +"where isbn = "+select_isbn+";";
	    	
			stmt = con.createStatement();
			
			result = stmt.executeUpdate(sql);
			checkSuccess(result, "book", "avail"); // update���� ���������� �۵��ߴ��� üũ
	    	
			// ���� Ƚ�� +1
			sql =  "update book "
			        +"set cnt = cnt+1 "
			        +"where isbn = "+select_isbn+";";
			stmt = con.createStatement();
			
			result = stmt.executeUpdate(sql);
			checkSuccess(result, "book", "cnt"); // update���� ���������� �۵��ߴ��� üũ
	    	
			// ������ å�� �ݳ��������� ������Ʈ �ϴ� ����
			sql =  "update borrow "
			        +"set deadline = date_add(first, interval 14 day);";
			stmt = con.createStatement();
			
			result = stmt.executeUpdate(sql);
			checkSuccess(result, "borrow", "deadline"); // update���� ���������� �۵��ߴ��� üũ
			
			System.out.println(" �� �����û �Ǿ����ϴ� ��");
    	} catch( SQLException ex ) {             
                System.err.println("\n** SQL exec error in borrowBook " + ex.getMessage() );
    	}
	}*/
	public static int borrowBook(ResultSet tmpRS, int sno) {
		
		try {                      
			tmpRS.beforeFirst();
			tmpRS.first();
			// ����� �л��� ������ isbn�� bno���� Ž��
			String select_isbn="";
			select_isbn=tmpRS.getString("isbn");
					if(tmpRS.getInt("avail")==0) { 
						return -1;
					}
			
            // borrow ���̺� �߰�
			String sql = "insert into "
					+ "borrow values("+sno+","+select_isbn+",curdate(),date_add(curdate(),interval 14 day),0,null,null,0);";
			stmt = con.createStatement();
			
			int result = stmt.executeUpdate(sql);
			checkSuccess(result, "borrow", "borrow"); // update���� ���������� �۵��ߴ��� üũ
			
	    	// ������ �̿뿩�� ������Ʈ
	    	sql = "update book "
	    	        +"set avail = 0 " 
	    	        +"where isbn = "+select_isbn+";";
	    	
			stmt = con.createStatement();
			
			result = stmt.executeUpdate(sql);
			checkSuccess(result, "book", "avail"); // update���� ���������� �۵��ߴ��� üũ
	    	
			// ���� Ƚ�� +1
			sql =  "update book "
			        +"set cnt = cnt+1 "
			        +"where isbn = "+select_isbn+";";
			stmt = con.createStatement();
			
			result = stmt.executeUpdate(sql);
			checkSuccess(result, "book", "cnt"); // update���� ���������� �۵��ߴ��� üũ
	    	
			// ������ å�� �ݳ��������� ������Ʈ �ϴ� ����
			sql =  "update borrow "
			        +"set deadline = date_add(first, interval 14 day);";
			stmt = con.createStatement();
			
			result = stmt.executeUpdate(sql);
			checkSuccess(result, "borrow", "deadline"); // update���� ���������� �۵��ߴ��� üũ
			
			
    	} catch( SQLException ex ) {             
                System.err.println("\n** SQL exec error in borrowBook " + ex.getMessage() );
    	}
		return 1;
	}

	
	public static ResultSet getRSHistory(int sno) {
		ResultSet tmpRS = null;
		
		try {                      			
			// sno �й��� �л� ����
			String sql = "select Book.bname as ������, Borrow.first as ������, Borrow.back as �ݳ���"
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
		
	// update���� ���������� �۵��ϴ��� üũ�ϴ� �޼ҵ�
	public static void checkSuccess(int result, String table, String name) {
    	if (result <= 0)       	
    		System.err.println("  >> not Inserted or Update New "+table+"\n\n"); 
	}
	
	// sno �л��� ���� ��Ȳ ������� ��ȯ�ϴ� �޼ҵ� 
	public static ResultSet getRSFromBorrow(int sno) {
		ResultSet tmpRS = null;
		
		try {                      			
			// sno �й��� �л� ����
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

	// �л��� �������� ȸ�������ϴ� �޼ҵ�
	public static boolean register(String ID, String password) {
		// ID�� �̹� �����Ѵٸ� �ٸ� ID�Է� �ϵ��� ����
		if(isExistID(ID)) {
			System.out.println("** �Է��Ͻ� ID�� �̹� �����մϴ�.\n");
			return false;
		}
		Student newStudent = new Student();
		newStudent.id=ID;
		newStudent.pw=getPrivatePW(password);
		
		System.out.println("\n-- ����� ���� �Է� --");
		System.out.print(" �� �й� > ");
		newStudent.sno = SkScanner.getInt();
		System.out.print(" �� �̸� > ");
		newStudent.sname = SkScanner.getString();
		System.out.print(" �� �а� > ");
		newStudent.dept = SkScanner.getString();
		System.out.print(" �� �г� > ");
		newStudent.year = SkScanner.getInt();
		
		ResultSet tmpRS = DB.getRSFromStudentBySno(newStudent.sno);
		try {
			if(tmpRS.next()) {
				System.out.println(" �ܡ� �̹� �����ϴ� �й� �Դϴ�  �ܡ�\n");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("** SQL exec error in register: " + e.getMessage() );
		}
		// �л� ���̺� ����
		insertStudent(newStudent);
		
		System.out.println(" �� ȸ�� ��� �Ϸ�  ��\n");
		return true;
	}

	// sno�� ���� �л����̺��� ��������� ��ȯ
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

	// �л� ���̺� ����
	public static void insertStudent(Student newStudent) {
       	try {                      
            // SQL ���ǹ��� �����Ѵ�.
       		String sql = "insert into student values(?,?,?,?,?,?);";
			prStmt = con.prepareStatement(sql);
			prStmt.setInt(1, newStudent.sno);
			prStmt.setString(2, newStudent.sname);
			prStmt.setInt(3, newStudent.year);
			prStmt.setString(4, newStudent.dept);
			prStmt.setString(5, newStudent.id);
			prStmt.setString(6, getPrivatePW(newStudent.pw));
			int result = prStmt.executeUpdate();  
			checkSuccess(result, "student", "student"); // update���� ���������� �۵��ߴ��� üũ
		
    	} catch( SQLException ex ) {             
                System.err.println("** SQL exec error in insertStudent: " + ex.getMessage() );
    	}
	}

	// password ��ȣȭ
	// PI�� �̿��� ��ȣȭ : �н����� ���ڿ��� PI�� �� �Ҽ��� ���� �ƽ�Ű �ڵ� ���ڰ����� ���ذ��� ��ȣȭ
	public static String getPrivatePW(String password) {
		BigDecimal PI = new BigDecimal(Math.PI);
		int len = password.length();
		char[] chs = password.toCharArray();
		
		// �����ϳ��ϳ��� ��ȣȭ �ڵ�
		String result="";
		int current = 0;
		while(current < len) {
			
			PI = PI.multiply(new BigDecimal(10)); // 31.41592...
			PI = PI.remainder(new BigDecimal(10)); // 1.41592...
			String tmp = PI.setScale(0, BigDecimal.ROUND_FLOOR) +""; // 1
			int val = Integer.parseInt(tmp);
			
			// ���� �ϳ��ϳ��� PI�Ҽ��� �� �ϳ��� ��
			int result_val=((int)chs[current]) + val;
			result_val%=128; // �ƽ�Ű �ڵ� ���� �ʰ��� ��츦 ���
			result+=(char)result_val+"";
			current++;			
		}
		return result;
	}

	// ID�� �̹� �����ϴ��� üũ�ϴ� �޼ҵ�
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

	// ������ ���̺��� RS�� ��ȯ�ϴ� �޼ҵ�
	public static ResultSet getRSFromManager() {
		ResultSet tmpRS = null;
		try {                      			
			// sno �й��� �л� ����
			String sql = "select * "
					+ "from manager ;";

			stmt = con.createStatement();			
			tmpRS = stmt.executeQuery(sql);
		
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in getRSFromManager: " + ex.getMessage() );
    	}
		return tmpRS;
	}

	// �л� ���̺��� RS�� ��ȯ�ϴ� �޼ҵ�
	public static ResultSet getRSFromStudent() {
		ResultSet tmpRS = null;
		try {                      			
			// sno �й��� �л� ����
			String sql = "select * "
					+ "from student;";

			stmt = con.createStatement();			
			tmpRS = stmt.executeQuery(sql);
		
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in getRSFromStudent: " + ex.getMessage() );
    	}
		return tmpRS;
	}

	// id�� password�� ���� Manager��ü ��ȯ
	public static Manager findManager(String ID, String password) {
		
       	try {                      
            // SQL ���ǹ��� �����Ѵ�.
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
	
	//  ResultSet��ü�� Manager ������ ����Ǿ� ���� �� �̸� Manager ��ü�� ��ȯ�ϴ� �޼ҵ�
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

	// root�������� �۾� : �����ڵ��� ��� ����
	public static void updateCareer() {
		try {                      			
			String sql = "update manager "
				    +"set career = career +1;";

			stmt = con.createStatement();
			boolean check = stmt.execute(sql);
			
			if(check)
				checkSuccess(-1, "Manager", "career"); // update���� ���������� �۵��ߴ��� üũ

    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in updateCareer: " + ex.getMessage() );
    	}
	}

	// root�������� �۾� : ������ ����(ID�� password�޾Ƽ� ����)
	public static boolean createManager(String ID, String password) {
		// ID�� �̹� �����Ѵٸ� �ٸ� ID�Է� �ϵ��� ����
		if(isExistID(ID)) {
			System.out.println("** �Է��Ͻ� ID�� �̹� �����մϴ�.\n");
			return false;
		}
		NormalManager newManager = new NormalManager();
		newManager.id=ID;
		newManager.pw=getPrivatePW(password);
		

		System.out.println("\n ��---<< ������ ���� �Է�  >>---��   ");
		System.out.print(" �� �ֹι�ȣ > ");
		newManager.rrn = SkScanner.getString();
		System.out.print(" �� �̸� > ");
		newManager.mname = SkScanner.getString();
		System.out.print(" �� ��� > ");
		newManager.career = SkScanner.getInt();
		System.out.print(" �� ��� > ");
		newManager.mno = SkScanner.getInt();
		System.out.print(" �� �Ի��� > ");
		newManager.dateOfEntry = java.sql.Date.valueOf(SkScanner.getString());
		
		ResultSet tmpRS = DB.getRSFromManagerByMno(newManager.mno);
		try {
			if(tmpRS.next()) {
				System.out.println(" �ܡ� �̹� �����ϴ� ��� �Դϴ�  �ܡ�\n");
				return false;
			}
		} catch (SQLException e) {
			System.err.println("** SQL exec error in createManager: " + e.getMessage() );
		}
		
		// ������ ���̺� ����
		insertManager(newManager);
		return true;
	}

	// mno�� ������ ������ ã�Ƽ� ��������� ��ȯ�ϴ� �޼ҵ�
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

	// ������ ���̺� ������ ���� �����ϴ� �޼ҵ�
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
			checkSuccess(result, "Manager", "Manager"); // update���� ���������� �۵��ߴ��� üũ
		
    	} catch( SQLException ex ) {             
                System.err.println("** SQL exec error in insertManager: " + ex.getMessage() );
    	}
	}

	// root������ ���� : ������ ����
	public static void deleteManager(int mno) {
		try { 		
			String sql = "delete from manager "
					+"where mno="+mno+";";
			
			stmt = con.createStatement();			
			int result = stmt.executeUpdate(sql);
			if(result<=0) {
				System.out.println("* �Է��Ͻ�"+mno+"�� ����� ���� �����ڴ� �����ϴ�");
				return;
			}
			System.out.println(" �� "+mno+"����� ���� �����ڰ� ���� �Ǿ����ϴ� ��\n");
			
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in deleteManager: " + ex.getMessage() );
    	}
	}

	// root������ ���� : ������ ��� Ȯ��
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

	// �Ϲ� ������ ���� : �뿩 ������ ���� ���
	public static void printBorrowableBook() {
		try { 		
			String sql = "select bno as �ѹ�, bname as ������, author as ����, publisher as ���ǻ�, isbn "
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

	// �Ϲ� ������ ���� : �̹����� ����Ʈ ���� ���
	public static void printBestSellerThisMonth() {
		rs = getRSBestSellerThisMonth();
		try {
			printRS(rs);
		} catch (Exception e) {
			System.err.println("* error in printRS, "+e.getMessage() );		
			}
	}

	// �Ϲ� ������ ���� & �л��� ���� : ����Ʈ ���� ���(��� �Ⱓ)
	public static void printBestSeller() {
		rs = getRSFindBestSeller();
		try {
			printRS(rs);
		} catch (Exception e) {
			System.err.println("* error in printRS, "+e.getMessage() );		
			}
	}

	// �������� ���� : ��� �л� ���� ���
	public static void printStudnetList() {
		rs = DB.getRSFromStudent();
		try {
			printRS(rs);
		} catch (Exception e) {
			System.err.println("* error in printRS, "+e.getMessage() );
		}
		
	}

	// �������� ���� : �а��� ���� ��Ȳ
	public static void printBorrowByDept() {
		try { 		
			String sql =  "select dept as �а�, count(borrow.sno) as ����Ƚ�� "
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
			String sql =  "select dept as �а�, count(borrow.sno) as ����Ƚ�� "
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


	// �������� ���� : ������ ����Ʈ
	public static void printBestReader() {
		try { 		
			String sql = "select borrow.sno as �й�, sname as �̸�, count(borrow.sno) as ����Ƚ�� "
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
			String sql = "select borrow.sno as �й�, sname as �л���, count(borrow.sno) as �뿩Ƚ�� "
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

	// �������� ���� : �г⺰ ���� ����Ʈ
	public static void printBorrowByYear() {
		try { 		
			String sql =  "select year as �г�, count(borrow.sno) as ����Ƚ��"
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
			String sql =  "select year as �г�, count(borrow.sno) as ����Ƚ�� "
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

	// �������� ���� : ��ü���� ���� ���
	public static void printOverdueBookList() {
		try { 		
			String sql = "select book.bno as �ѹ�, bname as ������, author as ����, publisher as  ���ǻ�, borrow.isbn "
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
			
            // SQL ���ǹ��� ����
			String sql = "select book.bno as �ѹ�, bname as ������, author as ����, publisher as ���ǻ�, borrow.isbn "
				    +"from book, overdue, borrow "
				    +"where book.isbn = overdue.isbn and overdue.isbn = borrow.isbn order by bno asc;";
				
			stmt = con.createStatement();			
			tmpRS = stmt.executeQuery(sql);

    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in getRSAllBook: " + ex.getMessage());
    	}
		return tmpRS;
	}

	// �������� ���� : ��ü���� �л� ���
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
			
            // SQL ���ǹ��� ����
			String sql = "select overdue.sno as �й�, sname as �̸�, year as �г�, dept as �а�, penalty as ��ü�� "
				    +"from student, overdue "
				    +"where overdue.sno=student.sno order by student.sno asc;";
			
			stmt = con.createStatement();			
			tmpRS = stmt.executeQuery(sql);

    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in getRSAllBook: " + ex.getMessage());
    	}
		return tmpRS;
	}

	// �������� ���� : ��ü ó��
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
				System.out.println(" �� ��ü�ڴ� �����ϴ�  ��");
				return;
			}
			
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in processOverdue: " + ex.getMessage() );
    	}
	}

	// �������� ���� : �л��� �г� ����
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
	

	// �л��� ���� : ��� å ����� ��������� ��ȯ
	public static ResultSet getRSAllBook() {
		
		ResultSet tmpRS = null;
		try {                      
			
            // SQL ���ǹ��� ����
			String sql = "select bno as �ѹ�, bname as ������, author as ����, publisher as ���ǻ�, isbn , avail as �̿뿩�� "
				    +"from book order by bno asc;";

			stmt = con.createStatement();			
			tmpRS = stmt.executeQuery(sql);

    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in getRSAllBook: " + ex.getMessage());
    	}
		return tmpRS;
	}
	
	// �Ϲ� ������ ���� : �� ���� ��� ��� 
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
	 		   // Statement ���� 
	 		   stmt = con.createStatement();
	 		            
	 			   
	 		   rs = stmt.executeQuery(sql);  

	 	   } catch( SQLException ex ) 	    {
	 		   System.err.println("** SQL exec error in selectQuery() : " + ex.getMessage() );
	 	   }
	 			
	 	   return rs;
	 		
	    }
	
    public static Vector selectDistinctDepts() {
     	// �־��� dept�� ���� �л����� �˻�
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
			
			String sql = "select bno as �ѹ�, bname as ������, author as ����, publisher as ���ǻ�, isbn , avail as �̿뿩�� "
					+ "from book where avail =" +true+";";

			stmt = con.createStatement();			
			tmpRS = stmt.executeQuery(sql);
		
    	} catch( SQLException ex ) {             
    		System.err.println("** SQL exec error in getRSFromStudent: " + ex.getMessage() );
    	}
		return tmpRS;
	}

}
