package Library;
import java.sql.Date;

public class Overdue {
	int sno;
	String isbn;
	java.sql.Date first; // ������
	int howlong; // ��ü�ϼ�
	
	// �Ű����� ���� ��ü ��ü ������
	public Overdue() {}

	// �Ű����� �մ� ��ü ��ü ������
	public Overdue(int sno, String isbn, java.sql.Date first, int howlong) {
		this.sno = sno;
		this.isbn = isbn;
		this.first = first;
		this.howlong = howlong;
	}

	// ��ü������ ���ڿ��� ��ȯ
	public String toString() {
		return "   �й�: " + sno + ", isbn : " + isbn + ", ������ : " + first + ", ��ü�ϼ�: " 
				+ howlong;
	}

	// ��ü���� ���
	public void output() {
		System.out.println(this.toString());
	}
}
