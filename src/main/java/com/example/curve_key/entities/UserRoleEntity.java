package com.example.curve_key.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "user_role", schema = "public", catalog = "curve_key")
public class UserRoleEntity {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "role_seq",
            sequenceName = "user_role_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    private int id;
    @Basic
    @Column(name = "role_name")
    private String roleName;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleEntity that = (UserRoleEntity) o;
        return id == that.id && Objects.equals(roleName, that.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleName);
    }
}
