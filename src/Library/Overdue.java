package Library;
import java.sql.Date;

public class Overdue {
	int sno;
	String isbn;
	java.sql.Date first; // 대출일
	int howlong; // 연체일수
	
	// 매개변수 없는 연체 객체 생성자
	public Overdue() {}

	// 매개변수 잇는 연체 객체 생성자
	public Overdue(int sno, String isbn, java.sql.Date first, int howlong) {
		this.sno = sno;
		this.isbn = isbn;
		this.first = first;
		this.howlong = howlong;
	}

	// 연체정보를 문자열로 반환
	public String toString() {
		return "   학번: " + sno + ", isbn : " + isbn + ", 대출일 : " + first + ", 연체일수: " 
				+ howlong;
	}

	// 연체정보 출력
	public void output() {
		System.out.println(this.toString());
	}
}
