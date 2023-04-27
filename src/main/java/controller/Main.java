package controller;

import dao.DaoImpl;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static EntityManagerFactory emf= Persistence.createEntityManagerFactory("sujan");
    public static void main(String[] args) throws IOException {
        BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
        DaoImpl implement= new DaoImpl();
        boolean flag=true;
        while(flag){
            System.out.println("Enter your choice :");
            System.out.println("1. Insert Data");
            System.out.println("2. Insert Bank details");
            System.out.println("3. Do Mapping");
            System.out.println("4. Query 1 (account open before 1 jan 2023)");
            System.out.println("5. Query 2 update status type ");
            System.out.println("7. Delete User ");
            System.out.println("8. Condition 3 ");

            System.out.println("0. EXIT");

            int choice= Integer.parseInt(bf.readLine());
            switch(choice) {
                case 1:
                    implement.insertData();
                    break;
                case 2:
                    implement.insertBank();
                    break;
                case 3:
                    implement.doMapping();
                    break;
                case 4:
                    implement.query1();
                    break;

                case 5:
                    implement.updateAccountStatus();
                    break;
                case 6:
                    implement.updateData();
                    break;

                case 7:
                    implement.deleteData();
                    break;
                case 8:
                    implement.checkAccountBalance();
                    break;

                case 0:
                    emf.close();
                    flag = false;
                    System.out.println("Exit !!");
                    break;
                default:
                    System.out.println("enter valid option !");
            }
        }
    }
}
/*
* Enter your choice :
1. Insert Data
2. Insert Bank details
3. Do Mapping
4. Query 1 (account open before 1 jan 2023)
5. Query 2 update status type
7. Delete User
8. Condition 3
0. EXIT
1
Enter User Name
arya
Enter Today's date
2023-09-09
How many accounts you want to create
2
Enter accountType (savings/current)
savings
Enter Balance
4500
enter status
regular
Enter accountType (savings/current)
current
Enter company name
reaj
GST No:
234
Enter Balance
59000
enter status
regular
data added
Enter your choice :
1. Insert Data
2. Insert Bank details
3. Do Mapping
4. Query 1 (account open before 1 jan 2023)
5. Query 2 update status type
7. Delete User
8. Condition 3
0. EXIT
2
Enter bank Name
maharashtra gramin
*
* 4
sujan  2022-09-09
Enter your choice :
1. Insert Data
2. Insert Bank details
3. Do Mapping
4. Query 1 (account open before 1 jan 2023)
5. Query 2 update status type
7. Delete User
8. Condition 3
0. EXIT
5
Enter threshold date (YYYY-MM-DD):
2021-07-08
Account status updated successfully!
Enter your choice :
1. Insert Data
2. Insert Bank details
3. Do Mapping
4. Query 1 (account open before 1 jan 2023)
5. Query 2 update status type
7. Delete User
8. Condition 3
0. EXIT
8
Enter account ID :
7
Enter the number of months the account has not been activated:
2
false
59000
Insufficient balance
* */