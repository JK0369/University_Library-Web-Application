package Library;
import java.sql.Date;

public class Manager {
	String rrn; // �ֹι�ȣ
	String mname;
	int career;
	String id;
	String pw;
	int mno; // ���
	java.sql.Date dateOfEntry; // �Ի���
	
	// �Ű����� ���� ������ ��ü ������
		public Manager() {}

		// �Ű����� �մ� ������ ��ü ������
		public Manager(String rrn, String mname, int career, String id, String pw, int mno, java.sql.Date dateOfEntry) {
			this.rrn = rrn;
			this.mname = mname;
			this.career = career;
			this.id = id;
			this.pw = pw;
			this.mno = mno;
			this.dateOfEntry = dateOfEntry;
		}

		// ������������ ���ڿ��� ��ȯ
		public String toString() {
			return "   �ֹι�ȣ: " + rrn + ", �������̸� : " + mname +", ��� : "+career+ ", id : " + id + ", ���: " 
					+ mno +"   �Ի���: " + dateOfEntry;
		}

		// ���������� ���
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
