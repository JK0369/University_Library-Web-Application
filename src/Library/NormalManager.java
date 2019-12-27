package Library;

// Manager ��� ���� �Ϲݰ����� Ŭ����
public class NormalManager extends Manager {
	public NormalManager(){}
	public NormalManager(String rrn, String mname, int career, String id, String pw, int mno, java.sql.Date dateOfEntry){
		super(rrn, mname, career, id, pw, mno, dateOfEntry);
	}
	
	public void doWork() {
		int menu=0;
		while(menu != 4)
		{
			System.out.println("\n ��------------<< Normal ������ ���� >>-------------��");
			System.out.println(" �� ( �۾��� ID: " + this.id + "  Normal ������ �̸�: " + this.mname + " ) ��");
			System.out.println(" ��                1. ���� ����                                            ��");
			System.out.println(" ��                2. ����� ����                                         ��");
			System.out.println(" ��                3. ��ü ����                                            ��");
			System.out.println(" ��                4. ����                                                   ��");
			System.out.println(" ��---------------------------------------------��");
			System.out.print(" ** ���ϴ� �۾��� �����Ͻÿ� > ");

			menu = SkScanner.getInt();
			
			try {
			  switch (menu)  {
				case 1: 
					System.out.println("\n\n< ���� ���� >");
					informBook();
					break;
				case 2:
					System.out.println("\n\n< ����� ���� >");
					informUser();
					break;
				case 3:
					System.out.println("\n\n< ��ü���� >");
					informOverdue();
					break;
				case 4:	
					break;

				default:
					System.out.println(" ** ����: �߸��� �޴� �����Դϴ�.");
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
			System.out.println("\n ��------------<< Normal ������ ���� >>-------------��");
			System.out.println(" �� ( �۾��� ID: " + this.id + "  Normal ������ �̸�: " + this.mname + " ) ��");
			System.out.println(" ��         1. �� ���� ���                                                        ��");
			System.out.println(" ��         2. �뿩 ���� ���� ���                                              ��");
			System.out.println(" ��         3. �̹����� ����Ʈ����                                             ��");
			System.out.println(" ��         4. �α� ���� ���                                                     ��");
			System.out.println(" ��         5. ����                                                                   ��");
			System.out.println(" ��---------------------------------------------��");
			System.out.print(" ** ���ϴ� �۾��� �����Ͻÿ� > ");

			menu = SkScanner.getInt();
			
			try {
			  switch (menu)  {
				case 1:
					System.out.println("\n��-----------------------------------------------------------<< �� ����  ��� >>-----------------------------------------------------------��");
					DB.printAllBook();
					System.out.print("��------------------------------------------------------------------------------------------------------------------------------------��\n");
					break;
				case 2:
					System.out.println("\n��-------------------------------------------------------<< �뿩 ���� ���� ��� >>-------------------------------------------------------��");
					DB.printBorrowableBook();
					System.out.print("��--------------------------------------------------------------------------------------------------------------------------------��\n");
					break;
				case 3:
					System.out.println("\n��--------------------------------------------------------<< �̹����� ����Ʈ ���� >>--------------------------------------------------------��");
					DB.printBestSellerThisMonth();
					System.out.print("��----------------------------------------------------------------------------------------------------------------------------------��\n");
					break;
				case 4:
					System.out.println("\n��----------------------------------------------------------<< �α� ���� ��� >>-----------------------------------------------------------��");
					DB.printBestSeller();
					System.out.print("��-----------------------------------------------------------------------------------------------------------------------------------��\n");
					break;
				case 5:	
					break;
				default:
					System.err.println(" ** ����: �߸��� �޴� �����Դϴ�.");
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
			System.out.println("\n ��------------<< Normal ������ ���� >>-------------��");
			System.out.println(" �� ( �۾��� ID: " + this.id + "  Normal ������ �̸�: " + this.mname + " ) ��");
			System.out.println(" ��                1. �л� ����                                            ��");
			System.out.println(" ��                2. ������ ���� ���                                  ��");
			System.out.println(" ��                3. ����                                                   ��");
			System.out.println(" ��---------------------------------------------��");
			System.out.print(" ** ���ϴ� �۾��� �����Ͻÿ� >> ");

			menu = SkScanner.getInt();
			
			try {
			  switch (menu)  {
				case 1: 
					informStudent();
					break;
				case 2:
					System.out.println("��------------------------------------<< ������ ���� ��� >>-----------------------------------��");
					DB.printManagerList();
					System.out.println("��---------------------------------------------------------------------------------------��\n");
					break;
				case 3:	
					break;
				default:
					System.out.println(" ** ����: �߸��� �޴� �����Դϴ�.");
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
			System.out.println("\n ��------------<< Normal ������ ���� >>-------------��");
			System.out.println(" �� ( �۾��� ID: " + this.id + "  Normal ������ �̸�: " + this.mname + " ) ��");
			System.out.println(" ��                1. �л� ���                                            ��");
			System.out.println(" ��                2. �а��� ���� ��Ȳ                                  ��");
			System.out.println(" ��                3. ������ ���                                         ��"); 
			System.out.println(" ��                4. �г⺰ ���� ��Ȳ                                  ��");		
			System.out.println(" ��                5. �л����� �г� ����                               ��");
			System.out.println(" ��                6. ����                                                   ��");
			System.out.println(" ��---------------------------------------------��");
			System.out.print(" ** ���ϴ� �۾��� �����Ͻÿ� > ");

			menu = SkScanner.getInt();
			
			try {
			  switch (menu)  {
				case 1: 
					System.out.println("\n\n��---------------------<< �л� ��� >>----------------------��");
					DB.printStudnetList();
					System.out.print("��--------------------------------------------------------��\n");
					break;
				case 2:
					System.out.println("\n\n��-------------<< �а��� ���� ��Ȳ >>--------------��");
					DB.printBorrowByDept();
					System.out.print("��-------------------------------------------��\n");
					break;
				case 3:
					System.out.println("\n\n��----------------<< ������ ���  >>-----------------��");
					DB.printBestReader();
					System.out.print("��-----------------------------------------------��\n");
					break;
				case 4:
					System.out.println("\n\n��-------------<< �г⺰ ���� ��Ȳ  >>-------------��");
					DB.printBorrowByYear();
					System.out.print("��-------------------------------------------��\n");
					break;
				case 5:	
					System.out.println("\n\n�� << �л����� �г� ������Ʈ >> ��");
					DB.updateStudentByYear();
					System.out.println("\n�ܡ� �г��� +1 ���� �Ǿ����ϴ� �ܡ�\n");
					break;
				case 6:	
					break;
				default:
					System.out.println(" ** ����: �߸��� �޴� �����Դϴ�.");
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
			System.out.println("\n ��------------<< Normal ������ ���� >>-------------��");
			System.out.println(" �� ( �۾��� ID: " + this.id + "  Normal ������ �̸�: " + this.mname + " ) ��");
			System.out.println(" ��                1. ��ü���� ���� ���                               ��");
			System.out.println(" ��                2. ��ü�� ����                                         ��"); 
			System.out.println(" ��                3. ��üó�� �� ����                                  ��");
			System.out.println(" ��                4. ����                                                   ��");
			System.out.println(" ��---------------------------------------------��");
			System.out.print(" ** ���ϴ� �۾��� �����Ͻÿ� > ");

			menu = SkScanner.getInt();
			
			try {
			  switch (menu)  {
				case 1: 
					System.out.println("\n\n��-----------------------------------------------------<< ��ü���� ���� ��� ��� >>-----------------------------------------------------��");
					DB.printOverdueBookList();
					System.out.print("��------------------------------------------------------------------------------------------------------------------------------��\n");
					break;
				case 2:

					System.out.println("\n\n��------------------------------------<< ��ü�� ���� >>------------------------------------------��");
					DB.printOverdueStudentList();
					System.out.print("��-------------------------------------------------------------------------------------------��\n");
					break;
				case 3:
					DB.processOverdue();
					System.out.println("\n�ܡ� ��ü ó�� �� ���� �Ǿ����ϴ� �ܡ�\n");
					break;
				case 4:	
					break;
				default:
					System.out.println(" ** ����: �߸��� �޴� �����Դϴ�.");
				}
			}
			catch (Exception e) {
				System.err.println("\n "+ e.getMessage());
			}

		}
		
	}
}
