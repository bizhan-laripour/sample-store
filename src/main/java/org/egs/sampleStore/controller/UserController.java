package org.egs.sampleStore.controller;

import org.egs.sampleStore.dto.LoginDto;
import org.egs.sampleStore.dto.UserDto;
import org.egs.sampleStore.enums.Role;
import org.egs.sampleStore.exception.CustomException;
import org.egs.sampleStore.security.AutherizationHeader;
import org.egs.sampleStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private UserService userService;

    private AutherizationHeader autherizationHeader;

    @Autowired
    public UserController(UserService userService , AutherizationHeader autherizationHeader){
        this.userService = userService;
        this.autherizationHeader = autherizationHeader;

    }

    @RequestMapping(method = RequestMethod.POST , path ="/api/users/signup")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.saveUser(userDto));
    }

    @RequestMapping(method = RequestMethod.POST , path ="/api/users/login")
    public ResponseEntity<LoginDto> login(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.login(userDto));
    }

    @RequestMapping(method = RequestMethod.POST , path ="/api/users/block_user")
    public ResponseEntity<UserDto> blockUser(@RequestBody UserDto userDto){
        String role = autherizationHeader.getRoleFromToken();
        if(role.equals(Role.ROLE_ADMIN.getAuthority())) {
            return ResponseEntity.ok(userService.blockUser(userDto));
        }
        throw new CustomException("just admin role can block the users" , HttpStatus.FORBIDDEN);
    }

    @RequestMapping(method = RequestMethod.POST , path = "/api/users/unblock_user")
    public ResponseEntity<UserDto> unBlockUser(@RequestBody UserDto userDto){
        String role = autherizationHeader.getRoleFromToken();
        if(role.equals(Role.ROLE_ADMIN.getAuthority())){
            return ResponseEntity.ok(userService.unblockUser(userDto));
        }
        throw new CustomException("just admin role can block the users" , HttpStatus.FORBIDDEN);
    }


}
