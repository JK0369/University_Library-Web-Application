package Library;
import java.sql.Date;
import java.sql.ResultSet;

public class Borrow {
	int sno; 
	String isbn;
	java.sql.Date date; // 대출일
	java.sql.Date deadline; // 반납예정일
	int extraCnt; // 연장회수
	int mno; // 반납자사번
	java.sql.Date back; // 반납일
	int late; // 연체여부
	
	// 매개변수 없는 연체 객체 생성자
	public Borrow() {}

	// 매개변수 잇는 연체 객체 생성자
	public Borrow(int sno, String isbn, java.sql.Date date, 
			java.sql.Date deadline, int extraCnt, int mno, java.sql.Date back, int late) {
		this.sno = sno;
		this.isbn = isbn;
		this.date = date;
		this.deadline = deadline;
		this.extraCnt = extraCnt;
		this.mno = mno;
		this.back = back;
		this.late = late;
	}

	// 연체정보를 문자열로 반환
	public String toString() {
		return "   연체한 학생의 학번: " + sno + ", isbn : " + isbn + ",  대출일 : " + date 
				+ ", 반납 예정일: " + deadline +  ", 연장 회수 : " + extraCnt + ", 반납자사번 : "
				+ mno + ", 반납일 : " + back + ", 연체여부 : " + late;
	}

	// 연체정보 출력
	public void output() {
		System.out.println(this.toString());
	}
}
