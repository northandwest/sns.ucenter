package com.bucuoa.passport.base.utils;


public enum DateTimePatternEnum {
	YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"), YYYYMM("yyyyMM"), YYYY("YYYY"), MM_POINT_DD("MM.dd"), YYYY_MM_DD(
			"yyyy-MM-dd");

	private String pattern;

	private DateTimePatternEnum(String pattern) {
		this.pattern = pattern;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
