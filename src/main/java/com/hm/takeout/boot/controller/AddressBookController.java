package com.hm.takeout.boot.controller;

import com.hm.takeout.boot.common.BaseContext;
import com.hm.takeout.boot.common.R;
import com.hm.takeout.boot.entity.AddressBook;
import com.hm.takeout.boot.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/addressBook")
@RestController()
@Slf4j
public class AddressBookController {
    @Autowired
    AddressBookService addressBookService;
    /**
     * 进入查询地址，然后回显界面
     */
    @GetMapping("/list")
    public R<List<AddressBook>> list(){
        List<AddressBook> bookList = addressBookService.list();
        return R.success(bookList);
    }

    /**
     * 新增界面
     * @param addressBook
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody AddressBook addressBook){
        log.info("新增{}",addressBook.toString());
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookService.save(addressBook);
        return R.success("添加成功");
    }

    /**
     * 修改
     * @param addressBook
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody AddressBook addressBook){
        log.info("修改{}",addressBook.toString());
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookService.updateById(addressBook);
        return R.success("修改成功");
    }
    /**
     * 修改默认值
     * @param addressBook
     * @return
     */
    @PutMapping("/default")
    public R<String> updatedefault(@RequestBody AddressBook addressBook){
        log.info("设置默认地址");
        Long id = addressBook.getId();
        addressBookService.updateIdDefault(id);
        return R.success("修改成功");
    }

    /**
     * 修改页面回显
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<AddressBook> updateaddressBook(@PathVariable("id") Long id){
        AddressBook addressBook = addressBookService.getById(id);
        return R.success(addressBook);
    }

    /**
     * 删除地址
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam("ids") Long id){
        log.info("id:"+id);
        addressBookService.removeById(id);
        return R.success("删除成功");
    }

    /**
     * 查看默认地址
     * @return
     */
    @GetMapping("/default")
    public R<AddressBook> defaultget(){
        AddressBook aDefault = addressBookService.getDefault();
        return R.success(aDefault);
    }
}
