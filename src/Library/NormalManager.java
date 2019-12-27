package Library;

// Manager 상속 받은 일반관리자 클래스
public class NormalManager extends Manager {
	public NormalManager(){}
	public NormalManager(String rrn, String mname, int career, String id, String pw, int mno, java.sql.Date dateOfEntry){
		super(rrn, mname, career, id, pw, mno, dateOfEntry);
	}
	
	public void doWork() {
		int menu=0;
		while(menu != 4)
		{
			System.out.println("\n ●------------<< Normal 관리자 업무 >>-------------●");
			System.out.println(" ● ( 작업자 ID: " + this.id + "  Normal 관리자 이름: " + this.mname + " ) ●");
			System.out.println(" ●                1. 도서 정보                                            ●");
			System.out.println(" ●                2. 사용자 정보                                         ●");
			System.out.println(" ●                3. 연체 정보                                            ●");
			System.out.println(" ●                4. 종료                                                   ●");
			System.out.println(" ●---------------------------------------------●");
			System.out.print(" ** 원하는 작업을 선택하시오 > ");

			menu = SkScanner.getInt();
			
			try {
			  switch (menu)  {
				case 1: 
					System.out.println("\n\n< 도서 정보 >");
					informBook();
					break;
				case 2:
					System.out.println("\n\n< 사용자 정보 >");
					informUser();
					break;
				case 3:
					System.out.println("\n\n< 연체정보 >");
					informOverdue();
					break;
				case 4:	
					break;

				default:
					System.out.println(" ** 오류: 잘못된 메뉴 선택입니다.");
				}
			}
			catch (Exception e) {
				System.err.println(e.getMessage());
			}

		}
	}
	private void informBook() {
		int menu=0;
		while(menu != 5)
		{
			System.out.println("\n ●------------<< Normal 관리자 업무 >>-------------●");
			System.out.println(" ● ( 작업자 ID: " + this.id + "  Normal 관리자 이름: " + this.mname + " ) ●");
			System.out.println(" ●         1. 총 도서 목록                                                        ●");
			System.out.println(" ●         2. 대여 가능 도서 목록                                              ●");
			System.out.println(" ●         3. 이번달의 베스트셀러                                             ●");
			System.out.println(" ●         4. 인기 도서 목록                                                     ●");
			System.out.println(" ●         5. 종료                                                                   ●");
			System.out.println(" ●---------------------------------------------●");
			System.out.print(" ** 원하는 작업을 선택하시오 > ");

			menu = SkScanner.getInt();
			
			try {
			  switch (menu)  {
				case 1:
					System.out.println("\n●-----------------------------------------------------------<< 총 도서  목록 >>-----------------------------------------------------------●");
					DB.printAllBook();
					System.out.print("●------------------------------------------------------------------------------------------------------------------------------------●\n");
					break;
				case 2:
					System.out.println("\n●-------------------------------------------------------<< 대여 가능 도서 목록 >>-------------------------------------------------------●");
					DB.printBorrowableBook();
					System.out.print("●--------------------------------------------------------------------------------------------------------------------------------●\n");
					break;
				case 3:
					System.out.println("\n●--------------------------------------------------------<< 이번달의 베스트 셀러 >>--------------------------------------------------------●");
					DB.printBestSellerThisMonth();
					System.out.print("●----------------------------------------------------------------------------------------------------------------------------------●\n");
					break;
				case 4:
					System.out.println("\n●----------------------------------------------------------<< 인기 도서 목록 >>-----------------------------------------------------------●");
					DB.printBestSeller();
					System.out.print("●-----------------------------------------------------------------------------------------------------------------------------------●\n");
					break;
				case 5:	
					break;
				default:
					System.err.println(" ** 오류: 잘못된 메뉴 선택입니다.");
				}
			}
			catch (Exception e) {
				System.err.println(e.getMessage());
			}

		}
	}
	private void informUser() {
		int menu=0;
		while(menu != 3)
		{
			System.out.println("\n ●------------<< Normal 관리자 업무 >>-------------●");
			System.out.println(" ● ( 작업자 ID: " + this.id + "  Normal 관리자 이름: " + this.mname + " ) ●");
			System.out.println(" ●                1. 학생 정보                                            ●");
			System.out.println(" ●                2. 관리자 정보 목록                                  ●");
			System.out.println(" ●                3. 종료                                                   ●");
			System.out.println(" ●---------------------------------------------●");
			System.out.print(" ** 원하는 작업을 선택하시오 >> ");

			menu = SkScanner.getInt();
			
			try {
			  switch (menu)  {
				case 1: 
					informStudent();
					break;
				case 2:
					System.out.println("●------------------------------------<< 관리자 정보 목록 >>-----------------------------------●");
					DB.printManagerList();
					System.out.println("●---------------------------------------------------------------------------------------●\n");
					break;
				case 3:	
					break;
				default:
					System.out.println(" ** 오류: 잘못된 메뉴 선택입니다.");
				}
			}
			catch (Exception e) {
				System.err.println("\n "+ e.getMessage());
			}

		}
	}
	
	private void informStudent() {
		int menu=0;
		while(menu != 6)
		{
			System.out.println("\n ●------------<< Normal 관리자 업무 >>-------------●");
			System.out.println(" ● ( 작업자 ID: " + this.id + "  Normal 관리자 이름: " + this.mname + " ) ●");
			System.out.println(" ●                1. 학생 목록                                            ●");
			System.out.println(" ●                2. 학과별 대출 현황                                  ●");
			System.out.println(" ●                3. 독서왕 목록                                         ●"); 
			System.out.println(" ●                4. 학년별 대출 현황                                  ●");		
			System.out.println(" ●                5. 학생들의 학년 갱신                               ●");
			System.out.println(" ●                6. 종료                                                   ●");
			System.out.println(" ●---------------------------------------------●");
			System.out.print(" ** 원하는 작업을 선택하시오 > ");

			menu = SkScanner.getInt();
			
			try {
			  switch (menu)  {
				case 1: 
					System.out.println("\n\n●---------------------<< 학생 목록 >>----------------------●");
					DB.printStudnetList();
					System.out.print("●--------------------------------------------------------●\n");
					break;
				case 2:
					System.out.println("\n\n●-------------<< 학과별 대출 현황 >>--------------●");
					DB.printBorrowByDept();
					System.out.print("●-------------------------------------------●\n");
					break;
				case 3:
					System.out.println("\n\n●----------------<< 독서왕 목록  >>-----------------●");
					DB.printBestReader();
					System.out.print("●-----------------------------------------------●\n");
					break;
				case 4:
					System.out.println("\n\n●-------------<< 학년별 대출 현황  >>-------------●");
					DB.printBorrowByYear();
					System.out.print("●-------------------------------------------●\n");
					break;
				case 5:	
					System.out.println("\n\n● << 학생들의 학년 업데이트 >> ●");
					DB.updateStudentByYear();
					System.out.println("\n●● 학년이 +1 갱신 되었습니다 ●●\n");
					break;
				case 6:	
					break;
				default:
					System.out.println(" ** 오류: 잘못된 메뉴 선택입니다.");
				}
			}
			catch (Exception e) {
				System.err.println("\n "+ e.getMessage());
			}

		}
	}
	private void informOverdue() {
		int menu=0;
		while(menu != 4)
		{			
			System.out.println("\n ●------------<< Normal 관리자 업무 >>-------------●");
			System.out.println(" ● ( 작업자 ID: " + this.id + "  Normal 관리자 이름: " + this.mname + " ) ●");
			System.out.println(" ●                1. 연체중인 도서 목록                               ●");
			System.out.println(" ●                2. 연체자 정보                                         ●"); 
			System.out.println(" ●                3. 연체처리 및 갱신                                  ●");
			System.out.println(" ●                4. 종료                                                   ●");
			System.out.println(" ●---------------------------------------------●");
			System.out.print(" ** 원하는 작업을 선택하시오 > ");

			menu = SkScanner.getInt();
			
			try {
			  switch (menu)  {
				case 1: 
					System.out.println("\n\n●-----------------------------------------------------<< 연체중인 도서 목록 출력 >>-----------------------------------------------------●");
					DB.printOverdueBookList();
					System.out.print("●------------------------------------------------------------------------------------------------------------------------------●\n");
					break;
				case 2:

					System.out.println("\n\n●------------------------------------<< 연체자 정보 >>------------------------------------------●");
					DB.printOverdueStudentList();
					System.out.print("●-------------------------------------------------------------------------------------------●\n");
					break;
				case 3:
					DB.processOverdue();
					System.out.println("\n●● 연체 처리 및 갱신 되었습니다 ●●\n");
					break;
				case 4:	
					break;
				default:
					System.out.println(" ** 오류: 잘못된 메뉴 선택입니다.");
				}
			}
			catch (Exception e) {
				System.err.println("\n "+ e.getMessage());
			}

		}
		
	}
}
