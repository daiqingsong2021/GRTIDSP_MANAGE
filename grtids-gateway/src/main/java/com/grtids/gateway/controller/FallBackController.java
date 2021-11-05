package com.grtids.gateway.controller;

import com.grtids.gateway.entity.GrtidspErrorCode;
import com.grtids.gateway.entity.GrtidspResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author daiqingsong
 * @Date 2021/10
 **/
@RestController
public class FallBackController {
 
    @RequestMapping(value = "/fallback")
    public GrtidspResult<String> fallBackController() {
    	return GrtidspResult.fail(GrtidspErrorCode.GATEWAY_ERROR);
    }
}
