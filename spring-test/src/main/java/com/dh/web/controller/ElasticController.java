package com.dh.web.controller;

import com.dh.es.ElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("elastic")
public class ElasticController {

    @Autowired
    private ElasticService elasticService;

    @GetMapping("")
    public Object drugs(@RequestParam String keyword) {
        return elasticService.queryDrug(keyword);
    }

}
