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
@NamedQueries({ @NamedQuery(name = "Action.findAll", query = "select o from Action o"),
                @NamedQuery(name = "Action.findByName", query = "select o from Action o where LOWER(o.name) = LOWER(:name)")
                })
public class Action {
    @Id
    @SequenceGenerator(name = "SEQ_GENERATOR", 
                       sequenceName = "SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, 
                    generator = "SEQ_GENERATOR") 
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false, length = 20)
    private String name;

    public Action() {
    }

    public Action(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
