package Library;
import java.sql.Date;

public class Manager {
	String rrn; // 주민번호
	String mname;
	int career;
	String id;
	String pw;
	int mno; // 사번
	java.sql.Date dateOfEntry; // 입사일
	
	// 매개변수 없는 관리자 객체 생성자
		public Manager() {}

		// 매개변수 잇는 관리자 객체 생성자
		public Manager(String rrn, String mname, int career, String id, String pw, int mno, java.sql.Date dateOfEntry) {
			this.rrn = rrn;
			this.mname = mname;
			this.career = career;
			this.id = id;
			this.pw = pw;
			this.mno = mno;
			this.dateOfEntry = dateOfEntry;
		}

		// 관리자정보를 문자열로 반환
		public String toString() {
			return "   주민번호: " + rrn + ", 관리자이름 : " + mname +", 경력 : "+career+ ", id : " + id + ", 사번: " 
					+ mno +"   입사일: " + dateOfEntry;
		}

		// 관리자정보 출력
		public void output() {
			System.out.println(this.toString());
		}

		public String getRrn() {
			return rrn;
		}

		public void setRrn(String rrn) {
			this.rrn = rrn;
		}

		public String getMname() {
			return mname;
		}

		public void setMname(String mname) {
			this.mname = mname;
		}

		public int getCareer() {
			return career;
		}

		public void setCareer(int career) {
			this.career = career;
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

		public int getMno() {
			return mno;
		}

		public void setMno(int mno) {
			this.mno = mno;
		}

		public java.sql.Date getDateOfEntry() {
			return dateOfEntry;
		}

		public void setDateOfEntry(java.sql.Date dateOfEntry) {
			this.dateOfEntry = dateOfEntry;
		}
}
