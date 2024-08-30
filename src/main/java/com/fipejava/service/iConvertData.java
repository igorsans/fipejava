package com.fipejava.service;

import java.util.List;

public interface iConvertData {
    <T> T getData(String json, Class<T> classe);

    <T> List<T> getDataList(String json, Class<T> classe);
}
