package Library;

// Manager 상속받은 rootManager 클래스
public class RootManager extends Manager {
	RootManager() {}
	RootManager(String rrn, String mname, int career, String id, String pw, int mno, java.sql.Date dateOfEntry) {
		super(rrn, mname, career, id, pw, mno, dateOfEntry);
	}
	
	public void doWork() {
		int menu=0;
		while(menu != 5)
		{
			System.out.println("\n\n ●-------------<< Root 관리자 업무 >>--------------●");
			System.out.println(" ●      ( 작업자 ID: " + this.id + ",  작업자 이름: " + this.mname + " )      ●");
			System.out.println(" ●                1. 관리자 경력 증가                                  ●");
			System.out.println(" ●                2. 관리자 추가                                         ●"); 
			System.out.println(" ●                3. 관리자 삭제                                         ●"); 
			System.out.println(" ●                4. 관리자 목록                                         ●"); 
			System.out.println(" ●                5. 종료                                                   ●");
			System.out.println(" ●---------------------------------------------●");
			System.out.print(" ** 원하는 작업을 선택하시오 > ");
			
			menu = SkScanner.getInt();
			
			try {

			  switch (menu)  {
				case 1: 
					DB.updateCareer();
					System.out.println(" ● 관리자 경력이 1년 증가 하였습니다 ●");
					break;
				case 2:
					createManager();
					System.out.println(" ● 관리자가 추가 되었습니다 ●");
					break;
				case 3:
					System.out.println("● ---------------------------------<< 관리자 목록  >>---------------------------------●"); 
					deleteManager();
					break;
				case 4:
					System.out.println("● ---------------------------------<< 관리자 목록  >>---------------------------------●"); 					
					DB.printManagerList();
					System.out.println("● -------------------------------------------------------------------------------●");
					break;
				case 5:	
					System.out.println("\n ●● 시스템 종료합니다.");
					break;

				default:
					System.out.println(" ** 오류: 잘못된 메뉴 선택입니다.");
				}
			}
			catch (Exception e) {
				System.err.println(e.getMessage()+"입사일을 잘못 입력했습니다 (올바른 입사일 입력 예시 : 2019-04-28)");
			}

		}
	}
	
	// 관리자 삭제 메소드
	private void deleteManager() {
		// 기존 관리자 중, 선택하여 삭제
		DB.printManagerList();
		System.out.println("● -------------------------------------------------------------------------------●");
		
		// 삭제할 관리자의 mno입력
		int menu=0;
		while (menu != -1) {
			System.out.print(" ** 삭제할 관리자의 사번입력(종료할 시 -1)>");
			menu = SkScanner.getInt();
			
			switch (menu) {
			case -1: //  종료
				break;
				
			default :
				DB.deleteManager(menu);
				break;
			}
		}
	}
	
	private static void createManager() {
		System.out.println("\n ●-----<< 관리자 생성  >>-----●   ");
		
		// 회원 가입 : 중복 id인지 체크, 반복문으로 구현
		boolean success = false;
		while(!success) {
			System.out.print(" ● 부여될 ID > ");
			String ID = SkScanner.getString();
			System.out.print(" ● 부여될 password > ");
			String password = SkScanner.getString();

			success = DB.createManager(ID, password);
			System.out.println(" ●-----------------------●   ");
		}
	}
}
