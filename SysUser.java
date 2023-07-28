package com.microsoft.hydralab.common.entity.center;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "sys_user")
public class SysUser {
    @Id
    private String userId = UUID.randomUUID().toString();

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "mail_address", nullable = false, unique = true)
    private String mailAddress;

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "default_team_id")
    private String defaultTeamId;

    @Column(name = "default_team_name")
    private String defaultTeamName;
}
