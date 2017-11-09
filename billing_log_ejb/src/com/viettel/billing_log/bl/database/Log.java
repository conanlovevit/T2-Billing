package com.viettel.billing_log.bl.database;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity
@NamedQueries({ @NamedQuery(name = "Log.findAll", query = "select o from Log o"),
                @NamedQuery(name = "Log.findByAccount", query = "select o from Log o where o.account = :account order by o.creationDatetime desc"),
                @NamedQuery(name = "Log.findByAccountAndService", query = "select o from Log o where o.account = :account and o.content.cp = :cp order by o.creationDatetime desc"),
                })
public class Log {
    @Column(name = "CREATION_DATETIME", nullable = false)
    private Timestamp creationDatetime;
    @Column(name = "EXPIRED_DATETIME")
    private Timestamp expiredDatetime;
    @Id
    @SequenceGenerator(name = "SEQ_GENERATOR", 
                       sequenceName = "SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, 
                    generator = "SEQ_GENERATOR") 
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private Long money;
    @ManyToOne
    @JoinColumn(name = "ACTION_ID")
    private Action action;
    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;
    @ManyToOne
    @JoinColumn(name = "CONTENT_ID")
    private Content content;

    public Log() {
    }

    public Log(Account account, Action action, Content content, Timestamp creationDatetime, Timestamp expiredDatetime,
               Long id, Long money) {
        this.account = account;
        this.action = action;
        this.content = content;
        this.creationDatetime = creationDatetime;
        this.expiredDatetime = expiredDatetime;
        this.id = id;
        this.money = money;
    }


    public Timestamp getCreationDatetime() {
        return creationDatetime;
    }

    public void setCreationDatetime(Timestamp creationDatetime) {
        this.creationDatetime = creationDatetime;
    }

    public Timestamp getExpiredDatetime() {
        return expiredDatetime;
    }

    public void setExpiredDatetime(Timestamp expiredDatetime) {
        this.expiredDatetime = expiredDatetime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
