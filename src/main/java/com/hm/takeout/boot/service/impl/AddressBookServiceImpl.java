package com.hm.takeout.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hm.takeout.boot.entity.AddressBook;
import com.hm.takeout.boot.mapper.AddressBookMapper;
import com.hm.takeout.boot.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
    @Autowired
    AddressBookMapper addressBookMapper;
    @Transactional
    @Override
    public void updateIdDefault(Long id) {
        addressBookMapper.updateByIdDefault();
        addressBookMapper.updateIdDefaultById(id);
    }

    @Override
    public AddressBook getDefault() {
        AddressBook aDefault = addressBookMapper.getDefault();
        return aDefault;
    }
}
