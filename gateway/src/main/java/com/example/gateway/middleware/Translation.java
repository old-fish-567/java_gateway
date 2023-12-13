package com.example.gateway.middleware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
@Configuration
public class Translation {

    public boolean validUsername(String input) {
        return input.equals("admin");
    }

    public boolean validServiceName(String input) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]{6,128}$");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public boolean validRule(String input) {
        Pattern pattern = Pattern.compile("^\\S+$");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public boolean validURLRewrite(String input) {
        if (input.isEmpty()) {
            return true;
        }
        String[] parts = input.split(",");
        for (String ms : parts) {
            if (ms.split(" ").length != 2) {
                return false;
            }
        }
        return true;
    }

    public boolean validHeaderTransform(String input) {
        if (input.isEmpty()) {
            return true;
        }
        String[] parts = input.split(",");
        for (String ms : parts) {
            if (ms.split(" ").length != 3) {
                return false;
            }
        }
        return true;
    }

    public boolean validIPPortList(String input) {
        String[] parts = input.split(",");
        for (String ms : parts) {
            if (!Pattern.matches("^\\S+:\\d+$", ms)) {
                return false;
            }
        }
        return true;
    }

    public boolean validIPList(String input) {
        if (input.isEmpty()) {
            return true;
        }
        String[] parts = input.split(",");
        for (String item : parts) {
            if (!Pattern.matches("\\S+", item)) {
                return false;
            }
        }
        return true;
    }

    public boolean validWeightList(String input) {
        String[] parts = input.split(",");
        for (String ms : parts) {
            if (!Pattern.matches("^\\d+$", ms)) {
                return false;
            }
        }
        return true;
    }

}
