package org.egs.sampleStore.service;

import org.egs.sampleStore.dao.UserRepository;
import org.egs.sampleStore.dto.LoginDto;
import org.egs.sampleStore.dto.UserDto;
import org.egs.sampleStore.entity.User;
import org.egs.sampleStore.enums.BlockStatus;
import org.egs.sampleStore.enums.Role;
import org.egs.sampleStore.exception.CustomException;
import org.egs.sampleStore.mapper.UserMapper;
import org.egs.sampleStore.security.AutherizationHeader;
import org.egs.sampleStore.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.Base64;

@Service
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private JwtTokenProvider jwtTokenProvider;

    private AutherizationHeader autherizationHeader;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, JwtTokenProvider jwtTokenProvider
    ,AutherizationHeader autherizationHeader) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtTokenProvider = jwtTokenProvider;
        this.autherizationHeader = autherizationHeader;
    }

    public UserDto saveUser(UserDto userDto) {
        String basicBase64format
                = Base64.getEncoder()
                .encodeToString(userDto.getPassword().getBytes());
        userDto.setPassword(basicBase64format);
        return userMapper.entityToDto(userRepository.save(userMapper.dtoToEntoty(userDto)));
    }


    public User loadUserByUsername(String s) {
        return userRepository.findUserByUsername(s);
    }

    public UserDto findByUsernameAndPassword(UserDto userDto) {
        User user = userRepository.findByUsernameAndPassword(userDto.getUsername(), userDto.getPassword());
        if(user != null) {
            return userMapper.entityToDto(user);
        }
        return null;
    }

    public LoginDto login(UserDto userDto){
        String basicBase64format
                = Base64.getEncoder()
                .encodeToString(userDto.getPassword().getBytes());
        userDto.setPassword(basicBase64format);
        UserDto user = findByUsernameAndPassword(userDto);
        if(user.getBlockStatus() == BlockStatus.BLOCKED){
            throw new CustomException("you are blocked babe" , HttpStatus.FORBIDDEN);
        }
        String token = null;
        if(user != null) {
           token = jwtTokenProvider.createToken(userDto.getUsername(), userDto.getRole());
        }else{
            throw new CustomException("the user didnt find" , HttpStatus.NOT_FOUND);
        }
        return new LoginDto(userDto.getUsername() , token);
    }

    public UserDto blockUser(UserDto userDto){
        User user = userRepository.findUserByUsername(userDto.getUsername());
        if(user.getRole() == Role.ROLE_ADMIN){
            throw new CustomException("you cant block an admin user" , HttpStatus.FORBIDDEN);
        }
        user.setBlockStatus(BlockStatus.BLOCKED);
        return userMapper.entityToDto(userRepository.save(user));
    }

    public UserDto unblockUser(UserDto userDto){
        User user = userRepository.findUserByUsername(userDto.getUsername());
        user.setBlockStatus(BlockStatus.UNBLOCKED);
        return userMapper.entityToDto(userRepository.save(user));
    }

    public UserDto findByUsername(String username){
        User user = userRepository.findUserByUsername(username);
        if(user != null){
            return userMapper.entityToDto(user);
        }
        return null;
    }
}
