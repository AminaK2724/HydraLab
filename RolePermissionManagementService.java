// Copyright (c) Microsoft Corporation.
// Licensed under the MIT License.

package com.microsoft.hydralab.center.service;

import com.microsoft.hydralab.center.repository.RolePermissionRelationRepository;
import com.microsoft.hydralab.common.entity.center.RolePermissionRelation;
import com.microsoft.hydralab.common.entity.center.SysPermission;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RolePermissionManagementService {
    //save role & permission relation <roleId, SysPermission>
    private final Map<String, Set<SysPermission>> rolePermissionListMap = new ConcurrentHashMap<>();

    @Resource
    RolePermissionRelationRepository relationRepository;
    @Resource
    SysPermissionService sysPermissionService;
    @Resource
    SysRoleService sysRoleService;

    @PostConstruct
    public void initList() {
        List<RolePermissionRelation> relationList = relationRepository.findAll();
        relationList.forEach(relation -> {
            Set<SysPermission> permissionList = rolePermissionListMap.computeIfAbsent(relation.getRoleId(), k -> new HashSet<>());
            SysPermission permission = sysPermissionService.queryPermissionById(relation.getPermissionId());
            if (permission != null) {
                permissionList.add(permission);
            }
        });
    }

    public RolePermissionRelation addRolePermissionRelation(String roleId, String permissionId) {
        Set<SysPermission> permissions = rolePermissionListMap.computeIfAbsent(roleId, k -> new HashSet<>());
        permissions.add(sysPermissionService.queryPermissionById(permissionId));

        RolePermissionRelation rolePermissionRelation = new RolePermissionRelation(roleId, permissionId);
        return relationRepository.save(rolePermissionRelation);
    }

    public void deleteRolePermissionRelation(RolePermissionRelation relation) {
        Set<SysPermission> permissions = rolePermissionListMap.get(relation.getRoleId());
        if (permissions == null) {
            return;
        }
        permissions.remove(sysPermissionService.queryPermissionById(relation.getPermissionId()));

        relationRepository.delete(relation);
    }

    public RolePermissionRelation queryRelation(String roleId, String permissionId) {
        return relationRepository.findByRoleIdAndPermissionId(roleId, permissionId).orElse(null);
    }

    public List<SysPermission> queryPermissionsByRole(String roleId) {
        Set<SysPermission> permissions = rolePermissionListMap.get(roleId);
        if (permissions == null) {
            return null;
        }

        return new ArrayList<>(permissions);
    }
}
