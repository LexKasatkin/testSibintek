package lex.test.sibintek;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lex on 2/4/17.
 */

public class ListUser {
    ArrayList<User> userArrayList;
    ListUser(){
        userArrayList=new ArrayList<>();
    }
    public void addUser(User user){
        userArrayList.add(user);
    }
    public void setUsersFromJSONString(String jsonString) {
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(jsonString);
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    User user = new User();
                    JSONObject jsonObjectUser = jsonArray.getJSONObject(i);
                    if (jsonObjectUser.has("login")) {
                        user.setLogin(jsonObjectUser.getString("login"));
                    } else {
                        user.setLogin("");
                    }
                    if (jsonObjectUser.has("url")) {
                        user.setUrl(jsonObjectUser.getString("url"));
                    } else {
                        user.setUrl("");
                    }
                    if (jsonObjectUser.has("id")) {
                        user.setId(jsonObjectUser.getString("id"));
                    } else {
                        user.setId("");
                    }
                    if (jsonObjectUser.has("avatar_url")) {
                        user.setAvatar_url(jsonObjectUser.getString("avatar_url"));
                    } else {
                        user.setAvatar_url("");
                    }
                    if (jsonObjectUser.has("site_admin")) {
                        user.setSite_admin(jsonObjectUser.getString("site_admin"));
                    } else {
                        user.setSite_admin("");
                    }
                    if (jsonObjectUser.has("company")) {
                        user.setCompany(jsonObjectUser.getString("company"));
                    } else {
                        user.setCompany("");
                    }
                    if (jsonObjectUser.has("url")) {
                        user.setUrl(jsonObjectUser.getString("url"));
                    } else {
                        user.setUrl("");
                    }
                    if (jsonObjectUser.has("e-mail")) {
                        user.setEmail(jsonObjectUser.getString("email"));
                    } else {
                        user.setEmail("");
                    }
                    if (jsonObjectUser.has("location")) {
                        user.setLocation(jsonObjectUser.getString("location"));
                    } else {
                        user.setLocation("");
                    }
                    if (jsonObjectUser.has("blog")) {
                        user.setBlog(jsonObjectUser.getString("blog"));
                    } else {
                        user.setBlog("");
                    }
                    if (jsonObjectUser.has("public_repos")) {
                        user.setPublic_repos(jsonObjectUser.getString("public_repos"));
                    } else {
                        user.setPublic_repos("");
                    }
                    userArrayList.add(user);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    }
