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
@NamedQueries({ @NamedQuery(name = "Cp.findAll", query = "select o from Cp o order by o.cpName"),
                @NamedQuery(name = "Cp.findByCpId", query = "select o from Cp o where LOWER(o.cpId) like :cp_id")
                })
public class Cp {
    @Column(name = "CP_ID", length = 20)
    private String cpId;
    @Column(name = "CP_NAME", length = 50)
    private String cpName;
    @Id
    @SequenceGenerator(name = "SEQ_GENERATOR", 
                       sequenceName = "SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, 
                    generator = "SEQ_GENERATOR") 
    @Column(nullable = false)
    private Long id;

    public Cp() {
    }

    public Cp(String cpId, String cpName, Long id) {
        this.cpId = cpId;
        this.cpName = cpName;
        this.id = id;
    }

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
