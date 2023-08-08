package com.example.curve_key.utils;

import com.example.curve_key.entities.CompanyEntity;
import com.example.curve_key.entities.TasksEntity;
import com.example.curve_key.entities.VpnClientEntity;
import com.example.curve_key.servicies.VpnClientService;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {

    private final VpnClientService clientService;
    private VpnClientEntity client;
    private Map<String, String> keies = new HashMap<>();
    private File file;

    public Dispatcher(VpnClientService clientService, VpnClientEntity client){
        this.clientService = clientService;
        this.client = client;
    }

    public File generateKeies(){
        GeneratorKey generatorKey = new GeneratorKey();
        keies = generatorKey.getKeyPair();
        client.setPrivateKey(keies.get(GeneratorKey.PRIMARYKEY));
        client.setPublicKey(keies.get(GeneratorKey.PUBLICKEY));
        client.setPresharedKey(keies.get(GeneratorKey.SHAREDSECRET));
        client.setDateGenerate(Instant.now());
        CreateFileWithKey fileWithKey = new CreateFileWithKey();
        try {
            file = fileWithKey.createNewFile(client);
            TasksEntity task = new TasksEntity();
            task.setDateGenerate(Instant.now());
            task.setTaskText(getTaskText());
            task.setActive(true);
            clientService.saveTask(task);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return file;
    }

    private String getTaskText(){
        String source = "add allowed-address=%s comment=%s interface=%s public-key=\"%s\" preshared-key=\"%s\"";

        CompanyEntity company = client.getCompany();
        return String.format(source,
                client.getAllowedAddress().replaceAll("\\s[/]\\d+$", "/32"),
                client.getClientName() + "/" + company.getCompanyName(),
                client.getVpnServer().getServername(),
                keies.get(GeneratorKey.PUBLICKEY),
                keies.get(GeneratorKey.SHAREDSECRET));
    }
}
