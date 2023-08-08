package com.example.curve_key.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "company", schema = "public", catalog = "curve_key")
public class CompanyEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "com_seq",
            sequenceName = "company_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "com_seq")
    private int id;
    @Basic
    @Column(name = "company_name")
    private String companyName;
    @Basic
    @Column(name = "network")
    private String network;
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private Set<VpnClientEntity> clients;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyEntity that = (CompanyEntity) o;
        return id == that.id && Objects.equals(companyName, that.companyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companyName);
    }
}
