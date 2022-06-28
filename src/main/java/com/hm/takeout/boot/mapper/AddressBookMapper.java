package com.hm.takeout.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hm.takeout.boot.entity.AddressBook;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AddressBookMapper extends BaseMapper<AddressBook> {
    @Update("update address_book set is_default = 0 where is_default = 1")
    void updateByIdDefault();
    @Update("update address_book set is_default = 1 where id = #{id}")
    void updateIdDefaultById(Long id);
    @Select("select * from address_book where is_default = 1")
    AddressBook getDefault();
}
