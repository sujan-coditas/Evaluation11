package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int uid;
    private String uname;
    private Date accountDate;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Account> account;

}
