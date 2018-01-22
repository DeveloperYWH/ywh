package com.zuimeng.hughfowl.latee.ec.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by hughfowl on 2017/9/15.
 */
@Entity(nameInDb = "user_profile")
public class UserProfile {

    @Id
    private long userId = 0;
    private String name = null;
    @Generated(hash = 1881994408)
    public UserProfile(long userId, String name) {
        this.userId = userId;
        this.name = name;
    }
    @Generated(hash = 968487393)
    public UserProfile() {
    }
    public long getUserId() {

        return this.userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    }

