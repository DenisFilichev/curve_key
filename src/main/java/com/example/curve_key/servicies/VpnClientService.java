package com.example.curve_key.servicies;

import com.example.curve_key.entities.CompanyEntity;
import com.example.curve_key.entities.TasksEntity;
import com.example.curve_key.entities.VpnClientEntity;
import com.example.curve_key.entities.VpnServerEntity;
import com.example.curve_key.repositories.CompanyRepository;
import com.example.curve_key.repositories.TaskRepository;
import com.example.curve_key.repositories.VpnClientRepository;
import com.example.curve_key.repositories.VpnServerRepository;
import com.example.curve_key.utils.IpGenerate;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VpnClientService {

    private final VpnClientRepository clientRepository;
    private final CompanyRepository companyRepository;
    private final VpnServerRepository serverRepository;
    private final TaskRepository taskRepository;
    private final VpnServerService serverService;

    @Autowired
    public VpnClientService(VpnClientRepository clientRepository, CompanyRepository companyRepository, VpnServerRepository serverRepository, VpnServerService serverService, TaskRepository taskRepository) {
        this.clientRepository = clientRepository;
        this.companyRepository = companyRepository;
        this.serverRepository = serverRepository;
        this.serverService = serverService;
        this.taskRepository = taskRepository;
    }

    public Set<VpnClientEntity> findAll(){
        return new HashSet<>(clientRepository.findAll());
    }

    @Transactional
    public VpnClientEntity findById(int id){
        VpnClientEntity client = clientRepository.findById(id).orElse(null);
        Hibernate.initialize(client.getVpnServer());
        Hibernate.initialize(client.getCompany());
        return client;
    }

    @Transactional
    public VpnClientEntity save(VpnClientEntity client, int companyId, int serverId){
        CompanyEntity company = companyRepository.findById(companyId).orElse(null);
        VpnServerEntity server = serverRepository.findById(serverId).orElse(null);
        if(company!=null && server!=null){
            client.setCompany(company);
            client.setVpnServer(server);
            String ip = IpGenerate.getIp(company.getNetwork(), company.getClients().stream().map(VpnClientEntity::getAllowedAddress).collect(Collectors.toList()));
            if (ip != null && !ip.isEmpty()) {
                ip += "/" + company.getNetwork().split("/")[1];
                if(client.getAllowedAddress()==null || client.getAllowedAddress().isEmpty()) client.setAllowedAddress(ip);
                client = clientRepository.save(client);
                company.getClients().add(client);
            }
        }
        return client;
    }

    @Transactional
    public void saveTask(TasksEntity task){
        taskRepository.save(task);
    }

    public VpnServerService getServerService() {
        return serverService;
    }

}
