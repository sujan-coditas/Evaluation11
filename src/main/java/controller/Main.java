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
