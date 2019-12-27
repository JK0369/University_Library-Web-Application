package Library;
import java.sql.ResultSet;

public class LibrarySystem {
	static Object user;
	
	public static void main(String args[]) {
		DB.loadConnect();
/*	    while(true) 
	    	doWork();*/
	}
	
/*	public static void doWork() {
		int menu=0;
		while(menu != 3)
		{
			System.out.println("\n\n ●--<< Welcome Library!! >>--●");
			System.out.println(" ●         1. 로그인                        ●");
			System.out.println(" ●         2. 회원가입                     ●");			
			System.out.println(" ●         3. 종료                           ●");
			System.out.println(" ●---------------------------●");
			System.out.print(" ** 원하는 작업을 선택하시오 > ");
			menu = SkScanner.getInt();
			
			try {
				switch (menu) {
				case 1:
					loginAndDoWork();
					break;
					
				case 2:
					register();
					break;

				case 3:	
					System.out.println("\n ●● 시스템 종료합니다.");
					System.exit(0);

				default:
					System.out.println(" ●● 오류: 잘못된 메뉴 선택입니다.");
				}
			}
			catch (Exception e) {
				System.err.println("\n "+ e.getMessage());
			}

		}
	}*/

	public static void register() {
		System.out.println("\n -- 회원 가입 --   ");
		
		// 회원 가입 : 중복 id인지 체크, 반복문으로 구현
		boolean success = false;
		while(!success) {
			System.out.print(" ● 사용할 ID > ");
			String ID = SkScanner.getString();
			System.out.print(" ● 사용할 password > ");
			String password = SkScanner.getString();
			
			success = DB.register(ID, password);
		}
	}

	public static Object loginAndDoWork(String id, String ps) {
		DB.loadConnect();
		int avail=3;
		do {
			// user : 셋 중 하나 (학생, root관리자, 일반관리자에 따라 일 수행)
			user = login(id, ps);
			if (user==null) {
				avail--;
				if(avail<=0) {
					System.out.println("\n -- 3번 틀렸으므로 시스템 종료합니다 --");
					System.exit(0);
				}
				System.out.println(" ●● 오류: ID 또는 비밀번호 오류 ●●");
			}
			else { // 로그인 되었다면 업무 실행
				
				// 업무 수행  : 각각 학생, 관리자, root관리자
				if(user instanceof Student) return ((Student)user);				
				else if(user instanceof Manager && !((Manager) user).id.equals("root")) return ((NormalManager) user);
				else return ((RootManager) user);
				
			}
			
		} while (user==null);
		return avail;
	}

	public static Object login(String id, String ps) {
/*		System.out.println("\n ●----<< 로그인 >>----●   ");

		System.out.print(" ● ID > ");
		String ID = SkScanner.getString();
		System.out.print(" ● password > ");
		String password = SkScanner.getString();
		System.out.println(" ●------------------●   ");*/
		
		// 사용자가 누구인지 체크 : 학생, 일반관리자, root관리자
		user = DB.findStudent(id, ps); if(user != null) return user;
		user = DB.findManager(id, ps); if(user != null) return user;
		
		// 어떤 유저인지 못찾은 경우 = null값
		return null;
	}
}