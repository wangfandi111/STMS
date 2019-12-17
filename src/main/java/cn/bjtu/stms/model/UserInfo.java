package cn.bjtu.stms.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "user_info")
public class UserInfo {
    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_jobid")
    private String userJobid;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_role")
    private Integer userRole;

}