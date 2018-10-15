package com.mossesbasket.automation.model.consumerconfig;

import java.util.List;

public class ConsumerConfigResCommon {

    private List<ConsumerConfigItem> itemCategories;
    private List<ConsumerConfigItem> special;
    private List<ConsumerConfigItem> itemConditions;
    private List<String> supportCurrencies;

    public List<ConsumerConfigItem> getItemCategories() {
        return itemCategories;
    }

    public List<ConsumerConfigItem> getSpecial() {
        return special;
    }

    public List<ConsumerConfigItem> getItemConditions() {
        return itemConditions;
    }

    public List<String> getSupportCurrencies() {
        return supportCurrencies;
    }
}
