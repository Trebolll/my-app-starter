package com.example.config.util;

public class AspectConstant {
    public static final String requestLogAttribute = "requestLogId";
    public static final String audit  = "@annotation(com.example.config.annotation.Audit)";
    public static final String logs = "@annotation(com.example.config.annotation.Log)";
}
