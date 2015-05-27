package com.seable.potato.data.entity.tigerentity;

import java.util.ArrayList;

/**
 * Created by yaohu on 15/5/20.
 */
public class HttpBaseQuery<T> {

    private boolean success;
    private BaseData<T> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public BaseData<T> getData() {
        return data;
    }

    public void setData(BaseData<T> data) {
        this.data = data;
    }

    public class BaseData<T>{
        int totalCount;
        ArrayList<T> rows;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public ArrayList<T> getRows() {
            return rows;
        }

        public void setRows(ArrayList<T> rows) {
            this.rows = rows;
        }
    }
}
