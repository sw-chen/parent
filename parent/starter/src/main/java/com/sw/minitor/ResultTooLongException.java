package com.sw.minitor;

public class ResultTooLongException extends RuntimeException {
    public ResultTooLongException(int maxLeng) {
        super(String.format("输出结果超出最大长度限制：%d", maxLeng), null, false, false);
    }
}
