package com.itheima.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RRHolder {
    public static final ThreadLocal<HttpServletRequest> REQUEST_THREADLOCAL = new ThreadLocal<>();
    public static final ThreadLocal<HttpServletResponse> RESPONSE_THREADLOCAL = new ThreadLocal<>();
}
