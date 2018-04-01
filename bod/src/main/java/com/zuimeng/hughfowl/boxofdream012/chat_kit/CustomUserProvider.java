package com.zuimeng.hughfowl.boxofdream012.chat_kit;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.LCChatProfileProvider;
import cn.leancloud.chatkit.LCChatProfilesCallBack;

/**
 * Created by hughfowl on 2018/3/26.
 */

public class CustomUserProvider implements LCChatProfileProvider {

    private static CustomUserProvider customUserProvider;

    public synchronized static CustomUserProvider getInstance() {
        if (null == customUserProvider) {
            customUserProvider = new CustomUserProvider();
        }
        return customUserProvider;
    }

    private CustomUserProvider() {
    }

    private static List<LCChatKitUser> partUsers = new ArrayList<LCChatKitUser>();
    private void queryUser(){
        final AVQuery<AVUser> avQuery = new AVQuery<>("_User");
        avQuery.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(final List<AVUser> userlist, AVException e) {
                for (int i = 0;i<userlist.size();i++){
                    final AVQuery<AVObject> query = new AVQuery<>("User_avater");
                    query.whereEqualTo("user_id", userlist.get(i).getMobilePhoneNumber());
                    final int finalI = i;
                    query.findInBackground(new FindCallback<AVObject>() {
                        @Override
                        public void done(List<AVObject> list, AVException e) {
                            try {
                                URL url = new URL((list.get(0).getAVFile("image").getUrl()));
                                partUsers.add(new LCChatKitUser(list.get(0).getString("user_id"), userlist.get(finalI).getUsername(), String.valueOf(url)));

                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }
    @Override
    public void fetchProfiles(List<String> list, LCChatProfilesCallBack lcChatProfilesCallBack) {
        queryUser();
        List<LCChatKitUser> userList = new ArrayList<LCChatKitUser>();
        for (String userId : list) {
            for (LCChatKitUser user : partUsers) {
                if (user.getUserId().equals(userId)) {
                    userList.add(user);
                    break;
                }
            }
        }
        lcChatProfilesCallBack.done(userList, null);
    }
    public List<LCChatKitUser> getAllUsers() {
        return partUsers;
    }

}
