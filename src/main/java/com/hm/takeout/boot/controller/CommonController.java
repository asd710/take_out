package com.hm.takeout.boot.controller;

import com.hm.takeout.boot.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {
    /**
     * 上传文件
     * @param file
     * @return
     */
    @Value("${reggie.path}")
    private String basePath;
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {
        log.info(file.toString());
        //获取源文件名
        String originalFilename = file.getOriginalFilename();
        //获取源文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //生成文件名  采用UUID防止重复
        String fileName = UUID.randomUUID().toString() + suffix;
        //判断文件是否存在，如不存在创建文件
        File dir = new File(basePath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        //将文件存储到指定位置
        file.transferTo(new File(basePath + fileName));
        return R.success(fileName);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try {
            //输入流，从输入流中读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
            //输出流，通过输出流将图片回显给浏览器，在浏览器中展示
            ServletOutputStream servletOutputStream = response.getOutputStream();
            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1){
                servletOutputStream.write(bytes,0,len);
                servletOutputStream.flush();
            }
            //关闭流资源
            servletOutputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
