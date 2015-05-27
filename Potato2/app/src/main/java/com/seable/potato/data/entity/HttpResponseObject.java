package com.seable.potato.data.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yaohu on 15/5/11.
 */
public class HttpResponseObject<T> implements Serializable {
    private boolean success;
    private T data;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    class Data<T>{
        String totalCount;
        T rows;

        public String getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(String totalCount) {
            this.totalCount = totalCount;
        }

        public T getRows() {
            return rows;
        }
    }
}
