package com.swlc.gadgetmart.backend.main.repo;



import com.swlc.gadgetmart.backend.main.dto.UserDto;

import java.util.List;

public interface UserRepo {

    UserDto authenticateUser(String username) throws Exception;

    boolean createUser(UserDto user) throws Exception;

    UserDto findUser(String userName, String userType) throws Exception;

    boolean updateUser(UserDto userDTO) throws Exception;

    List<UserDto> getAllUsers() throws Exception;
}
