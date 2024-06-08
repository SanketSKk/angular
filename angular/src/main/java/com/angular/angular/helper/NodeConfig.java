package com.angular.angular.helper;

import java.io.File;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class NodeConfig {
	
	
	public static ProcessBuilder getLocation(String path,String cmd) {
		
		String userHome = System.getProperty("user.home");
        String npmGlobalBin = userHome + "/.npm-global/bin";
        ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", cmd);
		pb.directory(new File(path));
        Map<String, String> env = pb.environment();
        String existingPath = env.get("PATH");
        env.put("PATH", "/usr/local/bin:" + npmGlobalBin + ":" + existingPath);
        System.out.println("PATH: " + env.get("PATH"));
		
		return pb;
	}

}
