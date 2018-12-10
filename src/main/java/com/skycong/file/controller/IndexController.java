package com.skycong.file.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author RMC 2018/11/23 12:45
 */
@Controller
public class IndexController {


    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/")
    String index() {
        return "userLogin";
    }


    @RequestMapping("/{path}")
    String index(@PathVariable("path") String path) {
        if (path != null) {
            path = path.trim();
        }
        return path;
    }

    @GetMapping(value = "go", produces = "text/html;charset=utf-8")
    @ResponseBody
    String go(@RequestParam("s") String url) {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        String body = forEntity.getBody();
        LOGGER.info("================================================================================");
        LOGGER.info("{}", body);
        return body;
    }

}
