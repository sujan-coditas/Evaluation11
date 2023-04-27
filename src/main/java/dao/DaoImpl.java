package dao;

import model.Account;
import model.Bank;
import model.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static controller.Main.emf;

public class DaoImpl implements Dao {
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

    public void insertData() throws IOException {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        //User Details
        System.out.println("Enter User Name");
        String uname = bf.readLine();
        System.out.println("Enter Today's date");
        Date accOpen = Date.valueOf(bf.readLine());

        User user = new User();
        user.setUname(uname);
        user.setAccountDate(accOpen);

        em.persist(user);

        //Account Details
        System.out.println("How many accounts you want to create");
        int accountCount = Integer.parseInt(bf.readLine());

        List<Account> accountList = new ArrayList<Account>();

        for (int i = 0; i < accountCount; i++) {
            Account account = new Account();
            System.out.println("Enter accountType (savings/current)");
            String accountType = bf.readLine();
            if (accountType.equalsIgnoreCase("savings")) {
                System.out.println("Enter Balance ");
                int balance = Integer.parseInt(bf.readLine());

                System.out.println("enter status");
                String status = bf.readLine();

                account.setAccountType(accountType);
                account.setBalance(balance);
                account.setStatus(status);
                accountList.add(account);
                account.setUser(user);


            } else if (accountType.equalsIgnoreCase("current")) {
                System.out.println("Enter company name ");
                String compName = bf.readLine();
                System.out.println("GST No: ");
                int gst = Integer.parseInt(bf.readLine());
                System.out.println("Enter Balance ");
                int balance = Integer.parseInt(bf.readLine());
                System.out.println("enter status");
                String status = bf.readLine();


                account.setCompanyName(compName);
                account.setGstNo(gst);
                account.setAccountType(accountType);
                account.setBalance(balance);
                account.setStatus(status);
                accountList.add(account);
                account.setUser(user);


            } else {
                System.out.println("enter correct spelling");
            }
            em.persist(account);
        }

        em.getTransaction().commit();
        System.out.println("data added");
        em.close();
    }

    public void updateData() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            System.out.println("Enter User Id:");
            int userId = Integer.parseInt(bf.readLine());

            User user = em.find(User.class, userId);
            if (user == null) {
                System.out.println("User not found");
            } else {
                System.out.println("Enter new name:");
                String newName = bf.readLine();
                user.setUname(newName);
                em.merge(user);
                em.getTransaction().commit();
                System.out.println("User name updated successfully!");
            }
        } catch (Exception ex) {
            em.getTransaction().rollback();
            System.err.println("Error updating user name: " + ex.getMessage());
        } finally {
            em.close();
        }

    }

    public void deleteData() throws IOException {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        System.out.println("Enter User Id :");
        int Userid = Integer.parseInt(bf.readLine());


        User user = em.find(User.class, Userid);
        if (user == null) {
            System.out.println(" not found");
        } else {
            em.remove(user);
            em.getTransaction().commit(); // commit the transaction before closing the EntityManager
            System.out.println("Data deleted !!");
        }
        em.close();
    }


    public void insertBank() throws IOException {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        System.out.println("Enter bank Name");
        String bankName = bf.readLine();

        Bank bank = new Bank();
        bank.setBname(bankName);

        em.persist(bank);
        em.getTransaction().commit();
        em.close();

    }

    public void doMapping() throws IOException {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        System.out.println("Enter account id");
        int accId = Integer.parseInt(bf.readLine());


        Account account = em.find(Account.class, accId);
        List<Bank> bankList = new ArrayList<Bank>();

        System.out.println("how many Banks  you want to open account ? ");
        int accCount = Integer.parseInt(bf.readLine());


        for (int i = 0; i < accCount; i++) {
            System.out.println("enter Bank id :");
            int bid = Integer.parseInt(bf.readLine());


            Bank bank = em.find(Bank.class, bid);
            bankList.add(bank);
        }


        account.setBank(bankList);
        em.persist(account);
        em.getTransaction().commit();
        em.close();
        System.out.println("Mapping done ");
    }

    public void query1() {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = emf.getCriteriaBuilder();

        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);

        ParameterExpression<Date> dateParam = cb.parameter(Date.class);
        cq.select(root)
                .where(cb.lessThan(root.get("accountDate"), dateParam));

        TypedQuery<User> query = em.createQuery(cq);
        query.setParameter(dateParam, Date.valueOf("2023-01-01"));

        List<User> users = query.getResultList();

        for (User user : users) {
            System.out.println(user.getUname() + "  " + user.getAccountDate());
        }
        em.close();
    }


    public void updateAccountStatus() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            System.out.println("Enter threshold date (YYYY-MM-DD): ");
            String thresholdDateStr = bf.readLine();
            LocalDate thresholdDate = LocalDate.parse(thresholdDateStr);
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> userRoot = cq.from(User.class);
            Join<User, Account> accountJoin = userRoot.join("account");
            Predicate balancePredicate = cb.greaterThan(accountJoin.get("balance"), 100000);
            Predicate datePredicate = cb.lessThan(userRoot.get("accountDate"), Date.valueOf(thresholdDate));
            Predicate finalPredicate = cb.and(balancePredicate, datePredicate);
            cq.select(userRoot).distinct(true).where(finalPredicate);
            List<User> usersToUpdate = em.createQuery(cq).getResultList();
            for (User user : usersToUpdate) {
                for (Account account : user.getAccount()) {
                    if (account.getBalance() > 100000) {
                        account.setStatus("Premium");
                        em.persist(account);
                    }
                }
            }
            em.getTransaction().commit();
            System.out.println("Account status updated successfully!");
        } catch (Exception ex) {
            em.getTransaction().rollback();
            System.err.println("Error updating account status: " + ex.getMessage());
        } finally {
            em.close();
        }
    }

    public void checkAccountBalance() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        EntityManager em2 = emf.createEntityManager();
        em2.getTransaction().begin();

        try {
            System.out.println("Enter account ID : ");
            int aid = Integer.parseInt(br.readLine());

            Account account = em2.find(Account.class, aid);
            String accountType = account.getAccountType();

            System.out.println("Enter the number of months the account has not been activated:");
            int monthsInactive = Integer.parseInt(br.readLine());

            if (accountType.equalsIgnoreCase("Current")) {
                int balance = account.getBalance();
                if (balance < 100000) {
                    System.out.println("Insufficient balance");

                    // Calculate the penalty
                    int penalty = monthsInactive * 250;

                    // Deduct the penalty from the balance
                    balance -= penalty;
                    account.setBalance(balance);
                    em2.merge(account);
                }
            } else if (accountType.equalsIgnoreCase("Savings")) {
                int balance = account.getBalance();
                if (balance < 10000) {
                    System.out.println("Insufficient balance");

                    // Calculate the penalty
                    int penalty = monthsInactive * 150;

                    // Deduct the penalty from the balance
                    balance -= penalty;
                    account.setBalance(balance);
                    em2.merge(account);
                }
            }
            em2.getTransaction().commit();
            em2.close();
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}



