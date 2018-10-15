package com.mossesbasket.automation.model.consumerconfig;

public class ConsumerConfigRes {

    private ConsumerConfigResCommon common;
    private ConsumerConfigResAndroid android;
    private ConsumerConfigResIOS ios;

    public ConsumerConfigResCommon getCommon() {
        return common;
    }

    public ConsumerConfigResAndroid getAndroid() {
        return android;
    }

    public ConsumerConfigResIOS getIos() {
        return ios;
    }
}
