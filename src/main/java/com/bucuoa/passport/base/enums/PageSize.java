package com.bucuoa.passport.base.enums;

/**
 * 一页显示条数
 * 
 * @author luo.hl
 * @date 2013-12-10 上午11:52:19
 * @version 3.0
 * @copyright www.bucuoa.com
 */
public enum PageSize {
	SIZE5(5), SIZE10(10), SIZE8(8), SIZE15(15), SIZE20(20), SIZE30(30), SIZE40(
			40), SIZE50(50),SIZE100(100);
	int size;

	private PageSize(int size) {
		this.size = size;
	}

	public int getSize() {
		return this.size;
	}
}
