package com.example.curve_key.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "vpn_server", schema = "public", catalog = "curve_key")
public class VpnServerEntity {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "server_seq",
            sequenceName = "vpn_server_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "server_seq")
    private int id;
    @Basic
    @Column(name = "servername")
    private String servername;
    @Basic
    @Column(name = "endpoint")
    private String endpoint;
    @Basic
    @Column(name = "public_key")
    private String publicKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VpnServerEntity that = (VpnServerEntity) o;
        return id == that.id && Objects.equals(endpoint, that.endpoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, endpoint);
    }
}
