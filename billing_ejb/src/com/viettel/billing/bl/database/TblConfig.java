package com.viettel.billing.bl.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "TblConfig.findAll", query = "select o from TblConfig o"),
                @NamedQuery(name = "TblConfig.findByKey", query = "select o from TblConfig o where LOWER(o.key) = :key")
                })
@Table(name = "TBL_CONFIG")
public class TblConfig {
    @Id
    @SequenceGenerator(name = "SEQ_GENERATOR", sequenceName = "SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GENERATOR") 
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false, unique = true, length = 50)
    private String key;
    @Column(name = "VALUE_NUMBER")
    private Long valueNumber;
    @Column(name = "VALUE_STRING", length = 200)
    private String valueString;

    public TblConfig() {
    }

    public TblConfig(Long id, String key, Long valueNumber, String valueString) {
        this.id = id;
        this.key = key;
        this.valueNumber = valueNumber;
        this.valueString = valueString;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getValueNumber() {
        return valueNumber;
    }

    public void setValueNumber(Long valueNumber) {
        this.valueNumber = valueNumber;
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }
}
