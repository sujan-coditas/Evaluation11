package model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Bank {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int bid;
    private String bname;
    @ManyToMany( mappedBy = "bank",cascade = CascadeType.ALL)
    private List<Account> account;

}

