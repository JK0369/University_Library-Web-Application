package Library;

// Manager ��ӹ��� rootManager Ŭ����
public class RootManager extends Manager {
	RootManager() {}
	RootManager(String rrn, String mname, int career, String id, String pw, int mno, java.sql.Date dateOfEntry) {
		super(rrn, mname, career, id, pw, mno, dateOfEntry);
	}
	
	public void doWork() {
		int menu=0;
		while(menu != 5)
		{
			System.out.println("\n\n ��-------------<< Root ������ ���� >>--------------��");
			System.out.println(" ��      ( �۾��� ID: " + this.id + ",  �۾��� �̸�: " + this.mname + " )      ��");
			System.out.println(" ��                1. ������ ��� ����                                  ��");
			System.out.println(" ��                2. ������ �߰�                                         ��"); 
			System.out.println(" ��                3. ������ ����                                         ��"); 
			System.out.println(" ��                4. ������ ���                                         ��"); 
			System.out.println(" ��                5. ����                                                   ��");
			System.out.println(" ��---------------------------------------------��");
			System.out.print(" ** ���ϴ� �۾��� �����Ͻÿ� > ");
			
			menu = SkScanner.getInt();
			
			try {

			  switch (menu)  {
				case 1: 
					DB.updateCareer();
					System.out.println(" �� ������ ����� 1�� ���� �Ͽ����ϴ� ��");
					break;
				case 2:
					createManager();
					System.out.println(" �� �����ڰ� �߰� �Ǿ����ϴ� ��");
					break;
				case 3:
					System.out.println("�� ---------------------------------<< ������ ���  >>---------------------------------��"); 
					deleteManager();
					break;
				case 4:
					System.out.println("�� ---------------------------------<< ������ ���  >>---------------------------------��"); 					
					DB.printManagerList();
					System.out.println("�� -------------------------------------------------------------------------------��");
					break;
				case 5:	
					System.out.println("\n �ܡ� �ý��� �����մϴ�.");
					break;

				default:
					System.out.println(" ** ����: �߸��� �޴� �����Դϴ�.");
				}
			}
			catch (Exception e) {
				System.err.println(e.getMessage()+"�Ի����� �߸� �Է��߽��ϴ� (�ùٸ� �Ի��� �Է� ���� : 2019-04-28)");
			}

		}
	}
	
	// ������ ���� �޼ҵ�
	private void deleteManager() {
		// ���� ������ ��, �����Ͽ� ����
		DB.printManagerList();
		System.out.println("�� -------------------------------------------------------------------------------��");
		
		// ������ �������� mno�Է�
		int menu=0;
		while (menu != -1) {
			System.out.print(" ** ������ �������� ����Է�(������ �� -1)>");
			menu = SkScanner.getInt();
			
			switch (menu) {
			case -1: //  ����
				break;
				
			default :
				DB.deleteManager(menu);
				break;
			}
		}
	}
	
	private static void createManager() {
		System.out.println("\n ��-----<< ������ ����  >>-----��   ");
		
		// ȸ�� ���� : �ߺ� id���� üũ, �ݺ������� ����
		boolean success = false;
		while(!success) {
			System.out.print(" �� �ο��� ID > ");
			String ID = SkScanner.getString();
			System.out.print(" �� �ο��� password > ");
			String password = SkScanner.getString();

			success = DB.createManager(ID, password);
			System.out.println(" ��-----------------------��   ");
		}
	}
}
