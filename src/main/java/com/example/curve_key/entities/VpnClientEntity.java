package com.example.curve_key.entities;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Data
@Entity
@Table(name = "vpn_client", schema = "public", catalog = "curve_key")
public class VpnClientEntity {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "client_seq",
            sequenceName = "vpn_client_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_seq")
    private int id;
    @Basic
    @Length(min = 3, message = "Длинна наименования клиента должна быть не менее 3-х символов")
    @Column(name = "client_name")
    private String clientName;
    @Basic
    @Column(name = "allowed_address")
    private String allowedAddress;
    @Column(name = "private_key")
    private String privateKey;
    @Column(name = "public_key")
    private String publicKey;
    @Column(name = "preshared_key")
    private String presharedKey;
    @Column(name = "date_generate")
    private Instant dateGenerate;
    @Basic
    @Column(name = "allowed_ips")
    private String allowedIps;
    @JoinColumn(name = "vpn_server")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private VpnServerEntity vpnServer;
    @JoinColumn(name = "company")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CompanyEntity company;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VpnClientEntity that = (VpnClientEntity) o;
        return id == that.id && Objects.equals(clientName, that.clientName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientName);
    }
}
