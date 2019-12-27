package Library;

public class Book {
	String isbn;
	String author;
	String bname;
	String publisher;
	String bno;
	int cnt; // ���� ȸ��
	boolean avail;
	
	// �Ű����� ���� ���� ��ü ������
	public Book() {}

	// �Ű����� �մ� ���� ��ü ������
	public Book(String isbn, String author, String bname, String publisher,
			String bno, int cnt, boolean avail) {
		this.isbn = isbn;
		this.author = author;
		this.bname = bname;
		this.publisher = publisher;
		this.bno = bno;
		this.cnt = cnt;
		this.avail = avail;
	}

	// ���������� ���ڿ��� ��ȯ
	public String toString() {
		return "   isbn: " + isbn + ", �۰� : " + author + ",  ���� : " + bname 
				+ "   ���ǻ�: " + publisher + "   å��ȣ: " + bno + "   ���� ȸ��: " + cnt
				+ "   avail: " + avail;
	}

	// �������� ���
	public void output() {
		System.out.println(this.toString());
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getBno() {
		return bno;
	}

	public void setBno(String bno) {
		this.bno = bno;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public boolean isAvail() {
		return avail;
	}

	public void setAvail(boolean avail) {
		this.avail = avail;
	}
}
