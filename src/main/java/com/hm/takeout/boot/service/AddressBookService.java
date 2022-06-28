package com.hm.takeout.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hm.takeout.boot.entity.AddressBook;

public interface AddressBookService extends IService<AddressBook> {
    void updateIdDefault(Long id);
    AddressBook getDefault();
}
