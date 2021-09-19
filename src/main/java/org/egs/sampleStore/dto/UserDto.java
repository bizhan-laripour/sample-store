package org.egs.sampleStore.dto;

import org.egs.sampleStore.enums.BlockStatus;
import org.egs.sampleStore.enums.Role;

import java.util.List;

public class UserDto {

    private Integer id;

    private String name;

    private String lastName;

    private String username;

    private String password;

    private Role role;

    private String email;

    private BlockStatus blockStatus;

//    private List<CommentDto> comments;

   // private BlockStatus blockStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BlockStatus getBlockStatus() {
        return blockStatus;
    }

    public void setBlockStatus(BlockStatus blockStatus) {
        this.blockStatus = blockStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
