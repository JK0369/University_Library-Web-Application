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
			System.out.println("\n\n ��--<< Welcome Library!! >>--��");
			System.out.println(" ��         1. �α���                        ��");
			System.out.println(" ��         2. ȸ������                     ��");			
			System.out.println(" ��         3. ����                           ��");
			System.out.println(" ��---------------------------��");
			System.out.print(" ** ���ϴ� �۾��� �����Ͻÿ� > ");
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
					System.out.println("\n �ܡ� �ý��� �����մϴ�.");
					System.exit(0);

				default:
					System.out.println(" �ܡ� ����: �߸��� �޴� �����Դϴ�.");
				}
			}
			catch (Exception e) {
				System.err.println("\n "+ e.getMessage());
			}

		}
	}*/

	public static void register() {
		System.out.println("\n -- ȸ�� ���� --   ");
		
		// ȸ�� ���� : �ߺ� id���� üũ, �ݺ������� ����
		boolean success = false;
		while(!success) {
			System.out.print(" �� ����� ID > ");
			String ID = SkScanner.getString();
			System.out.print(" �� ����� password > ");
			String password = SkScanner.getString();
			
			success = DB.register(ID, password);
		}
	}

	public static Object loginAndDoWork(String id, String ps) {
		DB.loadConnect();
		int avail=3;
		do {
			// user : �� �� �ϳ� (�л�, root������, �Ϲݰ����ڿ� ���� �� ����)
			user = login(id, ps);
			if (user==null) {
				avail--;
				if(avail<=0) {
					System.out.println("\n -- 3�� Ʋ�����Ƿ� �ý��� �����մϴ� --");
					System.exit(0);
				}
				System.out.println(" �ܡ� ����: ID �Ǵ� ��й�ȣ ���� �ܡ�");
			}
			else { // �α��� �Ǿ��ٸ� ���� ����
				
				// ���� ����  : ���� �л�, ������, root������
				if(user instanceof Student) return ((Student)user);				
				else if(user instanceof Manager && !((Manager) user).id.equals("root")) return ((NormalManager) user);
				else return ((RootManager) user);
				
			}
			
		} while (user==null);
		return avail;
	}

	public static Object login(String id, String ps) {
/*		System.out.println("\n ��----<< �α��� >>----��   ");

		System.out.print(" �� ID > ");
		String ID = SkScanner.getString();
		System.out.print(" �� password > ");
		String password = SkScanner.getString();
		System.out.println(" ��------------------��   ");*/
		
		// ����ڰ� �������� üũ : �л�, �Ϲݰ�����, root������
		user = DB.findStudent(id, ps); if(user != null) return user;
		user = DB.findManager(id, ps); if(user != null) return user;
		
		// � �������� ��ã�� ��� = null��
		return null;
	}
}