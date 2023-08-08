package com.example.curve_key.utils;

import com.example.curve_key.entities.VpnClientEntity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class CreateFileWithKey {

    private String text = "[Interface]\n" +
            "Address = %s \n" +
            "PrivateKey = %s\n" +
            "\n" +
            "[Peer]\n" +
            "PublicKey = %s\n" +
            "PresharedKey = %s\n" +
            "AllowedIPs = %s\n" +
            "Endpoint = %s";

    public File createNewFile(VpnClientEntity client) throws IOException {
        File file = null;
        if(client!=null && client.getVpnServer()!=null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd_HH.mm.ss");
            file = new File(client.getClientName() + "_" + Instant.now().atZone(ZoneId.of("Europe/Moscow").normalized()).format(formatter) + ".conf");
            file.createNewFile();
            text = String.format(text,
                    client.getAllowedAddress(),
                    client.getPrivateKey(),
                    client.getVpnServer().getPublicKey(),
                    client.getPresharedKey(),
                    client.getAllowedIps(),
                    client.getVpnServer().getEndpoint());
            try (FileWriter writer = new FileWriter(file)){
                writer.write(text);
            }
        }
        return file;
    }


}
