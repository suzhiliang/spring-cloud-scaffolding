package com.szl.springcloudweb.web.controller;

import com.szl.feignapi.demo.DemoFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @ClassName: DemoController
 * @Author: SZL
 * @Date: 2021/12/24 13:57
 * @Description: TODO
 * @Version 1.0
 */

@RestController
@RequestMapping("demoTest")
public class DemoController {

    @Autowired
    private DemoFeignClient demoFeignClient;

    @GetMapping("/helloWorld")
    public String helloWorld(){

        return demoFeignClient.echo("天王盖地虎");
    }


}
