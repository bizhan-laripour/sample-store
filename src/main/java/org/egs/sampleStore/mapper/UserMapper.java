package org.egs.sampleStore.mapper;

import org.egs.sampleStore.dto.UserDto;
import org.egs.sampleStore.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {




    public User dtoToEntoty(UserDto userDto){
       User user = new User();
       user.setId(userDto.getId());
       user.setEmail(userDto.getEmail());
       user.setName(userDto.getName());
       user.setLastName(userDto.getLastName());
       user.setUsername(userDto.getUsername());
       user.setPassword(userDto.getPassword());
       user.setRole(userDto.getRole());
       user.setBlockStatus(userDto.getBlockStatus());
       return user;
    }

    public UserDto entityToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setLastName(user.getLastName());
        userDto.setPassword(user.getPassword());
        userDto.setName(user.getName());
        userDto.setRole(user.getRole());
        userDto.setBlockStatus(user.getBlockStatus());
        return userDto;
    }

    public List<User> dtosToEntities(List<UserDto> userDtos){
        List<User> result = new ArrayList<>();
        for(UserDto obj : userDtos){
            result.add(dtoToEntoty(obj));
        }
        return result;
    }

    public List<UserDto> entitiesToDtos(List<User> users){
        List<UserDto> result = new ArrayList<>();
        for(User obj : users){
            result.add(entityToDto(obj));
        }
        return result;
    }
}
