package com.sw.minitor;

public class MonitorConfig {
    private boolean beforeLog = true;
    private boolean afterReturn = true;
    private boolean afterThrow = true;
    private int maxResultLength = 1024 * 4;


    public boolean isBeforeLog() {
        return beforeLog;
    }

    public void setBeforeLog(boolean beforeLog) {
        this.beforeLog = beforeLog;
    }

    public boolean isAfterReturn() {
        return afterReturn;
    }

    public void setAfterReturn(boolean afterReturn) {
        this.afterReturn = afterReturn;
    }

    public boolean isAfterThrow() {
        return afterThrow;
    }

    public void setAfterThrow(boolean afterThrow) {
        this.afterThrow = afterThrow;
    }

    /**
     * 接口返回结果Json字符串最大字符数，超过部分将截掉；默认：4k
     * @return
     */
    public int getMaxResultLength() {
        return maxResultLength;
    }

    public void setMaxResultLength(int maxResultLength) {
        this.maxResultLength = maxResultLength;
    }
}
