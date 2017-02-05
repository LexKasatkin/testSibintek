package lex.test.sibintek;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lex on 2/4/17.
 */

public class User {
    String login;
    String id;
    String avatar_url;
    String url;
    String type;
    String site_admin;
    String public_repos;
    String email;
    String location;
    String blog;
    String company;
    String name;

    public void setLogin(String login) {
        this.login = login;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublic_repos(String public_repos) {
        this.public_repos = public_repos;
    }

    public String getBlog() {
        return blog;
    }

    public String getCompany() {
        return company;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getPublic_repos() {
        return public_repos;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSite_admin(String site_admin) {
        this.site_admin = site_admin;
    }

    public String getLogin() {
        return login;
    }

    public String getId() {
        return id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }

    public String getSite_admin() {
        return site_admin;
    }

    public void setUser(String jsonString){
        try {
            JSONObject jsonObjectUser=new JSONObject(jsonString);
            if (jsonObjectUser.has("login")) {
                this.setLogin(jsonObjectUser.getString("login"));
            } else {
                this.setLogin("");
            }
            if (jsonObjectUser.has("url")) {
                this.setUrl(jsonObjectUser.getString("url"));
            } else {
                this.setUrl("");
            }
            if (jsonObjectUser.has("id")) {
                this.setId(jsonObjectUser.getString("id"));
            } else {
                this.setId("");
            }
            if (jsonObjectUser.has("avatar_url")) {
                this.setAvatar_url(jsonObjectUser.getString("avatar_url"));
            } else {
                this.setAvatar_url("");
            }
            if (jsonObjectUser.has("site_admin")) {
                this.setSite_admin(jsonObjectUser.getString("site_admin"));
            } else {
                this.setSite_admin("");
            }
            if (jsonObjectUser.has("company")) {
                this.setCompany(jsonObjectUser.getString("company"));
            } else {
                this.setCompany("");
            }
            if (jsonObjectUser.has("url")) {
                this.setUrl(jsonObjectUser.getString("url"));
            } else {
                this.setUrl("");
            }
            if (jsonObjectUser.has("e-mail")) {
                this.setEmail(jsonObjectUser.getString("email"));
            } else {
                this.setEmail("");
            }
            if (jsonObjectUser.has("location")) {
                this.setLocation(jsonObjectUser.getString("location"));
            } else {
                this.setLocation("");
            }
            if (jsonObjectUser.has("blog")) {
                this.setBlog(jsonObjectUser.getString("blog"));
            } else {
                this.setBlog("");
            }
            if (jsonObjectUser.has("public_repos")) {
                this.setPublic_repos(jsonObjectUser.getString("public_repos"));
            } else {
                this.setPublic_repos("");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
