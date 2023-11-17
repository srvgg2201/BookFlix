package com.project.bookflix.controllers;

import com.project.bookflix.dtos.SignUpRequestDto;
import com.project.bookflix.dtos.SignUpResponseDto;
import com.project.bookflix.models.ResponseStatus;
import com.project.bookflix.models.User;
import com.project.bookflix.services.UserService;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private UserService userService;
    UserController(UserService userService) {
        this.userService = userService;
    }
    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
       User user =  userService.signUp(signUpRequestDto.getEmail(), signUpRequestDto.getPassword());
       SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
       signUpResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
       signUpResponseDto.setUserId(user.getId());
       return signUpResponseDto;
    }
}
