package com.grtids.gateway.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @Author daiqingsong
 * @Date 2021/10
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RedisTokenUser implements Serializable {
	private static final long serialVersionUID = -7279149331328765178L;
	private String token;
}
