package com.microsoft.hydralab.common.entity.center;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class UserInterfaceData {
    private Map<String, Boolean> teamAdminMap = new HashMap<>();
    private List<String> authorities = new ArrayList<>();
    private String accessToken;
    private boolean teamAdmin;
}
