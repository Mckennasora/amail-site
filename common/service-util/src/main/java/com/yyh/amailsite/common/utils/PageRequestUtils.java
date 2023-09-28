package com.yyh.amailsite.common.utils;


import org.springframework.data.domain.*;

public class PageRequestUtils {

    public static Sort pageRequestSortTime(String createTimeSortStr,String updateTimeSortStr){
        Sort.Order createTimeSortOrder = null;
        if (createTimeSortStr == null || createTimeSortStr.equals("asc")) {
            createTimeSortOrder = Sort.Order.asc("createTime");
        } else {
            createTimeSortOrder = Sort.Order.desc("createTime");
        }
        Sort.Order updateTimeSortOrder = null;
        if (updateTimeSortStr == null || updateTimeSortStr.equals("asc")) {
            updateTimeSortOrder = Sort.Order.asc("updateTime");
        } else {
            updateTimeSortOrder = Sort.Order.desc("updateTime");
        }
        return Sort.by(createTimeSortOrder, updateTimeSortOrder);
    }
}
