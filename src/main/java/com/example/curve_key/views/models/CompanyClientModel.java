package com.example.curve_key.views.models;

import com.example.curve_key.entities.CompanyEntity;
import com.example.curve_key.entities.VpnClientEntity;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CompanyClientModel {

    private int companyId;
    private String companyName;
    private boolean companyActive;
    private int clientId;
    private String clientName;
    private String allowedAddress;
    private String privateKey;
    private String publicKey;
    private String presharedKey;
    private boolean clientActive;
    private int vpnServer;
    private String servername;
    private String endpoint;

    private CompanyClientModel(CompanyEntity company, VpnClientEntity client){
        companyId = company.getId();
        companyName = company.getCompanyName();
        clientId = client!=null ? client.getId() : 0;
        clientName = client!=null ? client.getClientName() : "";
        allowedAddress = client!=null ? client.getAllowedAddress() : "";
        privateKey = client!=null ? client.getPrivateKey() : "";
        publicKey = client!=null ? client.getPublicKey() : "";
        presharedKey = client!=null ? client.getPresharedKey() : "";
        vpnServer = client!=null ? client.getVpnServer().getId() : 0;
        servername = client!=null ? client.getVpnServer().getServername() : "";
        endpoint = client!=null ? client.getVpnServer().getEndpoint() : "";
    }

    public static Set<CompanyClientModel> getInstancies(Set<CompanyEntity> companies){
        Set<CompanyClientModel> sets = new HashSet<>();
        companies.forEach(company -> {
            if(company.getClients()!=null && company.getClients().size()>0)
            company.getClients().forEach(client -> {
                sets.add(new CompanyClientModel(company, client));
            });
            else sets.add(new CompanyClientModel(company, null));
        });
        return sets;
    }
}
