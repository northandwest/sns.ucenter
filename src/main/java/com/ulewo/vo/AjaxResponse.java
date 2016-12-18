package com.ulewo.vo;

import org.apache.commons.lang.StringUtils;

import com.ulewo.enums.ResponseCode;

public class AjaxResponse<T> {
	private ResponseCode responseCode;
	private String errorMsg;
	private T data;

	public ResponseCode getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(ResponseCode responseCode) {
		this.responseCode = responseCode;
	}

	public String getErrorMsg() {
		if (StringUtils.isEmpty(errorMsg)) {
			this.errorMsg = responseCode.getDesc();
		}
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
