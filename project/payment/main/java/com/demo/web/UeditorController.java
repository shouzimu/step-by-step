package com.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/ueditor")
public class UeditorController {

    @RequestMapping("/config")
    @ResponseBody
    public Object getConfig(String action, HttpServletRequest request) {
        switch (action) {
            case "config": {
                Map<String, Object> config = new HashMap<>();
                config.put("imageActionName", "upload");
                config.put("imageFieldName", "upfile");
                String[] allows = {".png", ".jpg", ".jpeg", ".gif", ".bmp"};
                config.put("imageAllowFiles", allows);
                config.put("imageUrlPrefix", "");
                return config;
            }
            case "upload": {
                if (request instanceof MultipartHttpServletRequest) {
                    MultipartFile upfile =
                        ((MultipartHttpServletRequest) request).getFile("upfile");
                    return upload(upfile);
                }
            }
        }

        return null;
    }


    /**
     * 上传文件
     *
     * @param upfile
     * @return
     */
    private Map<String, Object> upload(MultipartFile upfile) {
        Map<String, Object> res = new HashMap<>();
        try {
            String name = upfile.getOriginalFilename();

            String filename = UUID.randomUUID().toString().replaceAll("-", "")
                + name.substring(name.lastIndexOf("."));

            InputStream in = upfile.getInputStream();
            //TODO 需替换

            res.put("name", name);
            res.put("url", "https://ad.12306.cn/res/delivery/0001/2016/12/13/201612131716334564.jpg");
            res.put("state", "SUCCESS"); //返回数据中必须有state为SUCCESS
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

}
