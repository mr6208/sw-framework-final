package com.swfinal.exception;

import lombok.Getter;
@Getter
public class CustomException extends RuntimeException {
    private static final long serialVersionId = 1L;
    private final String REPL_CD;
    private final String REPL_MSG;
    private final String REPL_PAGE_MSG;
    public CustomException(String replCd, String replMsg, String replPageMsg) {
        super(replMsg);
        this.REPL_CD = replCd;
        this.REPL_MSG = replMsg;
        this.REPL_PAGE_MSG = replPageMsg;
    }
}
