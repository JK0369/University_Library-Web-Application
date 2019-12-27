package Library;

public class Book {
	String isbn;
	String author;
	String bname;
	String publisher;
	String bno;
	int cnt; // 대출 회수
	boolean avail;
	
	// 매개변수 없는 도서 객체 생성자
	public Book() {}

	// 매개변수 잇는 도서 객체 생성자
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

	// 도서정보를 문자열로 반환
	public String toString() {
		return "   isbn: " + isbn + ", 작가 : " + author + ",  제목 : " + bname 
				+ "   출판사: " + publisher + "   책번호: " + bno + "   대출 회수: " + cnt
				+ "   avail: " + avail;
	}

	// 도서정보 출력
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
