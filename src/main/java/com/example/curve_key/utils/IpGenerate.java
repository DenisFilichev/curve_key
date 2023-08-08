package com.example.curve_key.utils;

import org.apache.commons.net.util.SubnetUtils;

import java.util.List;
import java.util.stream.Collectors;

public class IpGenerate {

    public static String getIp(String network, List<String> allowedAddress){
        allowedAddress = allowedAddress.stream().map(e -> e.split("/")[0]).collect(Collectors.toList());
        SubnetUtils su = new SubnetUtils(network);
        String[] allIpps = su.getInfo().getAllAddresses();
        for(String ip : allIpps){
            if(!allowedAddress.contains(ip)) return ip;
        }
        return "";
    }
}
