package com.grtids.gateway.filter;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
/**
 * @Author daiqingsong
 * @Date 2021/10
 **/
@Component
public class TokenFilter implements GlobalFilter, Ordered {
    Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    @Override
    public int getOrder() {
        return 120;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("TokenFilter开始............");
        URI uri = exchange.getRequest().getURI();
        return chain.filter(exchange);
//		if (testUrl(uri.getPath())) {
//			return chain.filter(exchange);
//		}
//		logger.info("" + uri);
//		// 拦截的逻辑。根据具体业务逻辑做拦截。
//		Map<String, String> map = getAllHeadersRequest(exchange.getRequest());
//		String token = map.get("token");
//		if (!StringUtils.isEmpty(token)) {
//			String consoleKey = SystemConstants.CONSOLE_USER_PREFIX + token;
//			if (RedisUtils.pairIsExist(consoleKey)) {
//				return chain.filter(exchange);
//			}
//		}
//		logger.info("token is empty...");
//		// 设置status和body
//		return Mono.defer(() -> {
//			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);// 设置status
//			final ServerHttpResponse response = exchange.getResponse();
//			BusinessResult data = new BusinessResult();
//			data.setCode("1000");
//			data.setMessage("Token不存在或验证不通过");
//			byte[] datas = JsonUtil.getJsonString4JavaPOJO(data).getBytes(StandardCharsets.UTF_8);
//			DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(datas);
//			response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
//			logger.info("TokenFilter拦截非法请求，没有检测到token............");
//			return response.writeWith(Flux.just(buffer));// 设置body
//		});
    }

    private boolean testUrl(String url) {
        if (url.contains("/doc.html") || url.contains("/swagger-ui.html") || url.contains("/error")
                || url.contains("/file/download") || url.contains("/v2/api-docs") || url.contains("/swagger-resources/")
                || url.contains("/webjars/springfox-swagger-ui/") || url.contains("/auth-api/login")
                || url.contains("/back-api/index") || url.contains("/statics/")) {
            return true;
        } else {
            return false;
        }
    }

    private Map getAllParamtersRequest(ServerHttpRequest request) {
        logger.info("getAllParamtersRequest开始............");
        Map map = new HashMap();
        MultiValueMap<String, String> paramNames = request.getQueryParams();
        Iterator it = paramNames.keySet().iterator();
        while (it.hasNext()) {
            String paramName = (String) it.next();
            List<String> paramValues = paramNames.get(paramName);
            if (paramValues.size() >= 1) {
                String paramValue = paramValues.get(0);
                logger.info("request参数：" + paramName + ",值：" + paramValue);
                map.put(paramName, paramValue);
            }
        }
        return map;
    }

    private Map getAllHeadersRequest(ServerHttpRequest request) {
        logger.info("getAllHeadersRequest开始............");
        Map map = new HashMap();
        HttpHeaders hearders = request.getHeaders();
        Iterator it = hearders.keySet().iterator();
        while (it.hasNext()) {
            String keyName = (String) it.next();
            List<String> headValues = hearders.get(keyName);
            if (headValues.size() >= 1) {
                String kvalue = headValues.get(0);
                logger.info("request header的key：" + keyName + ",值：" + kvalue);
                map.put(keyName, kvalue);
            }
        }
        return map;
    }
}
