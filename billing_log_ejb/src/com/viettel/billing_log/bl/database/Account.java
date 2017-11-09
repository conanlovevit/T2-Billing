package com.viettel.billing_log.bl.database;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
@NamedQueries({ @NamedQuery(name = "Account.findAll", query = "select o from Account o"),
                @NamedQuery(name = "Account.findByAccountId", query = "select o from Account o where LOWER(o.accountId) = LOWER(:account_id)"),
                @NamedQuery(name = "Account.findByAccountUsername", query = "select o from Account o where LOWER(o.accountUsername) like :username")
                })
public class Account {
    @Column(name = "ACCOUNT_ID", nullable = false, length = 20)
    private String accountId;
    @Column(name = "ACCOUNT_USERNAME", nullable = false, length = 50)
    private String accountUsername;
    @Id
    @SequenceGenerator(name = "SEQ_GENERATOR", 
                       sequenceName = "SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, 
                    generator = "SEQ_GENERATOR") 
    @Column(nullable = false)
    private Long id;
    
    public Account() {
    }

    public Account(String accountId, String accountUsername, Long id) {
        this.accountId = accountId;
        this.accountUsername = accountUsername;
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountUsername() {
        return accountUsername;
    }

    public void setAccountUsername(String accountUsername) {
        this.accountUsername = accountUsername;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
