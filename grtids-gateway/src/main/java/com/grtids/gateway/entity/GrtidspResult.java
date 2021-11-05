package com.grtids.gateway.entity;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

/**
 *
 * @Description: 自定义响应数据结构，将JSON数据封装到一个对象中 这个类是提供给门户
 * @author daiqingsong 2021-10
 */
@Data
public class GrtidspResult<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 定义jackson对象
	private static final ObjectMapper MAPPER = new ObjectMapper();
	// 响应编号
	private String code;
	// 响应消息
	private String msg;

	// 响应中的数据
	private T data; // 返回的数据

	public GrtidspResult() {

	}

	// 3参构造函数
	public GrtidspResult(String code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	// 2参构造函数
	public GrtidspResult(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	// 2参构造函数
	public GrtidspResult(String code, T data) {
		this.code = code;
		this.data = data;
	}

	// 1参构造函数
	public GrtidspResult(T data) {
		this.code = "0000";
		this.msg = "请求成功";
		this.data = data;
	}

	/**
	 * 失败的构造函数
	 * 
	 * @param codeMsg codeMsg
	 */
	private GrtidspResult(GrtidspErrorCode code) {
		if (code != null) {
			this.code = code.getCode();
			this.msg = code.getMessage();
		}
	}

	public static <T> GrtidspResult<T> build(String code, String msg, T data) {
		return new GrtidspResult<T>(code, msg, data); // 3个参数
	}

	public static <T> GrtidspResult<T> success(T data) {
		return new GrtidspResult<T>(data); // 1个参数
	}

	public static <T> GrtidspResult<T> fail(GrtidspErrorCode code) {
		return new GrtidspResult<T>(code); // data为空进行构造
	}

	/**
	 *
	 * @Description: 将json结果集转化为LeeJSONResult对象 需要转换的对象是一个类
	 * @param jsonData
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> GrtidspResult<T> formatToPojo(String jsonData, Class<?> clazz) throws Exception {
		if (clazz == null) {
			return MAPPER.readValue(jsonData, GrtidspResult.class);
		}
		JsonNode jsonNode = MAPPER.readTree(jsonData);
		JsonNode data = jsonNode.get("data");
		Object obj = null;
		if (clazz != null) {
			if (data.isObject()) {
				obj = MAPPER.readValue(data.traverse(), clazz);
			} else if (data.isTextual()) {
				obj = MAPPER.readValue(data.asText(), clazz);
			}
		}
		return (GrtidspResult<T>) build(jsonNode.get("code").toString(), jsonNode.get("msg").asText(), obj);
	}

	/**
	 *
	 * @Description: 没有object对象的转化
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> GrtidspResult<T> format(String json) throws Exception {
		return MAPPER.readValue(json, GrtidspResult.class);
	}

	/**
	 *
	 * @Description: Object是集合转化 需要转换的对象是一个list
	 * @param jsonData
	 * @param clazz
	 * @return
	 *
	 */
	@SuppressWarnings("unchecked")
	public static <T> GrtidspResult<T> formatToList(String jsonData, Class<?> clazz) throws Exception {
		JsonNode jsonNode = MAPPER.readTree(jsonData);
		JsonNode data = jsonNode.get("data");
		Object obj = null;
		if (data.isArray() && data.size() > 0) {
			obj = MAPPER.readValue(data.traverse(), MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
		}
		return (GrtidspResult<T>) build(jsonNode.get("status").toString(), jsonNode.get("msg").asText(), obj);
	}
}
