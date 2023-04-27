package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int aid;
    private String accountType;
    private int balance;
    private  String status;
    private String CompanyName;
    private int gstNo;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Bank> bank;

}