/* 
 * Copyright 2012 Share.Ltd  All rights reserved.
 * Share.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @HttpException.java - 2012-12-26 ����10:14:30 - rock
 */

package com.sharegroup.jiguang.http;

public class HttpException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HttpException(String msg) {
        super(msg);
    }
	HttpException(Exception msg) {
        super(msg);
    }
	HttpException(String string,int Errcode) {
        super(string);
    }

}
