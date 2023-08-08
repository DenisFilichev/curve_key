package com.example.curve_key.servicies;

import com.example.curve_key.entities.VpnServerEntity;
import com.example.curve_key.repositories.VpnServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VpnServerService {

    private final VpnServerRepository serverRepository;


    @Autowired
    public VpnServerService(VpnServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    public Set<VpnServerEntity> findAll(){
        return new HashSet<>(serverRepository.findAll());
    }

    public VpnServerEntity save(VpnServerEntity server){
        System.out.println(server);
        return serverRepository.save(server);
    }

    public Map<String, VpnServerEntity> getAllServer(){
        List<VpnServerEntity> servers = serverRepository.findAll();
        return servers.stream().collect(Collectors.toMap(VpnServerEntity::getEndpoint, server -> server));
    }
}
