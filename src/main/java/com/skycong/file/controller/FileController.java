package com.skycong.file.controller;

import com.skycong.file.dao.model.UserModel;
import com.skycong.file.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author RMC 2018/11/20 12:50
 */
@Controller
public class FileController {

    private static final String SEPARATOR = File.separator;

    @Value("${disk-save-path}")
    private String DISK_SAVE_PATH;
    @Value("${domain}")
    private String DOMAIN;


    @Autowired
    private UserService userService;


    /**
     * 上传文件
     */
    @RequestMapping(value = "upload")
    @ResponseBody
    String uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        String uploadFileName = file.getOriginalFilename();
        String fileType;
        int lastIndexOf = uploadFileName.lastIndexOf(".");
        if (lastIndexOf < 0) {
            fileType = "";
        } else {
            fileType = uploadFileName.substring(lastIndexOf);
        }
        String dir = generateFileDir();
        String newFileName = generateUUID() + fileType;
        File directory = new File(DISK_SAVE_PATH + dir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        //创建文件
        File saveFile = new File(DISK_SAVE_PATH + dir + newFileName);
        try {
            if (!saveFile.exists()) {
                saveFile.createNewFile();
            }
            //复制
            file.transferTo(saveFile);
            String res = DOMAIN + dir + newFileName;
            return res.replaceAll("\\\\", "/");
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @GetMapping("showAllFiles")
    String showAllFiles(Model model) {
        UserModel loginUser1 = userService.getLoginUser();
        if (loginUser1 == null) {
            model.addAttribute("tip", "请先登录");
            return "redirect:/userLogin";
        }
        Set<String> fileList = new HashSet<>();
        getAllFileList(new File(DISK_SAVE_PATH), fileList);
        List<String> collect = fileList.stream().map(f -> f.replace(DISK_SAVE_PATH, DOMAIN).replaceAll("\\\\", "/")).collect(Collectors.toList());
        model.addAttribute("fileList", collect);
        return "fileList";
    }


    /**
     * 生成文件目录，
     *
     * @return eg： /2018/9/23/
     */
    private String generateFileDir() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String yyyy = String.valueOf(localDateTime.getYear());
        String mm = String.valueOf(localDateTime.getMonthValue());
        String dd = String.valueOf(localDateTime.getDayOfMonth());
        return SEPARATOR +
                yyyy + SEPARATOR +
                mm + SEPARATOR +
                dd + SEPARATOR;
    }

    /**
     * 随机生成
     */
    private String generateUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "").toLowerCase();
    }

    private void getAllFileList(File directory, Set<String> fileListPath) {
        if (directory != null && directory.exists()) {
            if (directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        getAllFileList(file, fileListPath);
                    }
                }
            } else {
                fileListPath.add(directory.getPath());
            }
        }
    }

}
