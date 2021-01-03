package com.example.comwmtmurali.bean.users;

import com.example.comwmtmurali.bean.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("users")
    @Expose
    private List<User> users = null;
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}
