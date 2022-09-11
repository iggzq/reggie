package com.mystudy.reggie.service.serivceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mystudy.reggie.entity.AddressBook;
import com.mystudy.reggie.mapper.AddressBookMapper;
import com.mystudy.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
