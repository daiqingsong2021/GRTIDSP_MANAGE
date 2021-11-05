package com.grtids.gateway.common;


import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
/**
 * @Author daiqingsong
 * @Date 2021/10
 **/
public class IpAddrKeyResolver implements KeyResolver {
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        String ip=exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
        System.out.println("客户端IP="+ip);
        return Mono.just(ip);
    }
}