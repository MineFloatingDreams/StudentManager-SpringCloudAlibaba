package com.my.cloud.filter;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.my.cloud.common.code.CommonResultCode;
import com.my.cloud.common.pojo.CommonResult;
import com.my.cloud.common.util.JWTUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Slf4j
@Component
public class TokenFilter extends AbstractGatewayFilterFactory<Object> {

    /**
     * 拦截并返回自定义的json字符串
     */
    private Mono<Void> denyAccess(ServerWebExchange exchange, CommonResult<String> resultCode) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        //这里在返回头添加编码，否则中文会乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        byte[] bytes = JSON.toJSONBytes(resultCode, SerializerFeature.WriteMapNullValue);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String token = request.getHeaders().getFirst("token");
            //检查token是否为空
            if (StringUtils.isEmpty(token)) {
                return denyAccess(exchange, new CommonResult<>(CommonResultCode.TOKEN_NULL, "token为空"));
            }
            Claims claims = JWTUtils.checkJWT(token);
            //token有误
            if (claims == null) {
                return denyAccess(exchange, new CommonResult<>(CommonResultCode.TOKEN_EXPIRED, "token过期"));
            }
            //token无误，将用户信息设置进header中,传递到下游服务
            Long userId = JWTUtils.getId(token);
            Consumer<HttpHeaders> headers = httpHeaders -> {
                httpHeaders.add("userId", String.valueOf(userId));
            };
            request.mutate().headers(headers).build();
            return chain.filter(exchange);
        };
    }
}