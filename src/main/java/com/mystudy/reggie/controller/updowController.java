package com.mystudy.reggie.controller;

import com.mystudy.reggie.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传下载
 */
@RestController
@RequestMapping("/common")
public class updowController {

    @Value("${reggie.path}")
    private String basePath;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {

        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        //使用UUID 重新生成文件名，防止文件名重复
        String s = UUID.randomUUID().toString() + substring;
        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //file会被自动装配并将照片保存到临时文件中
        try {
            file.transferTo(new File(basePath + s));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(s);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse httpServletResponse) throws IOException {
        FileInputStream fileInputStream = null;
        ServletOutputStream outputStream = null;
        try{
            fileInputStream = new FileInputStream(new File(basePath + name));
            outputStream = httpServletResponse.getOutputStream();

            httpServletResponse.setContentType("image/jpeg");

            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }

            fileInputStream.close();
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
