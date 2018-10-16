package com.mossesbasket.automation.model.tokengenerate;

import java.util.List;

public class TokenGenerateBaseModel {
    private String status;
    private String msg;
    private List<Token> res;

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public List<Token> getRes() {
        return res;
    }
}
