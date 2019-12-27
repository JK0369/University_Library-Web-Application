package Library;
import java.sql.Date;
import java.sql.ResultSet;

public class Borrow {
	int sno; 
	String isbn;
	java.sql.Date date; // ������
	java.sql.Date deadline; // �ݳ�������
	int extraCnt; // ����ȸ��
	int mno; // �ݳ��ڻ��
	java.sql.Date back; // �ݳ���
	int late; // ��ü����
	
	// �Ű����� ���� ��ü ��ü ������
	public Borrow() {}

	// �Ű����� �մ� ��ü ��ü ������
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

	// ��ü������ ���ڿ��� ��ȯ
	public String toString() {
		return "   ��ü�� �л��� �й�: " + sno + ", isbn : " + isbn + ",  ������ : " + date 
				+ ", �ݳ� ������: " + deadline +  ", ���� ȸ�� : " + extraCnt + ", �ݳ��ڻ�� : "
				+ mno + ", �ݳ��� : " + back + ", ��ü���� : " + late;
	}

	// ��ü���� ���
	public void output() {
		System.out.println(this.toString());
	}
}
