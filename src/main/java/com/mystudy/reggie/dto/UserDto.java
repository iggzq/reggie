package com.mystudy.reggie.dto;

import com.mystudy.reggie.entity.User;
import lombok.Data;

@Data
public class UserDto extends User {
    private String code;
}
