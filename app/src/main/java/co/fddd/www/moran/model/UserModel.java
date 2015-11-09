package co.fddd.www.moran.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by Administrator on 2015/10/22 0022.
 */

public class UserModel {
    @SerializedName("key")
    @Expose
    private String key;

    @SerializedName("user_name")
    @Expose
    private String username;

    @SerializedName("user_id")
    @Expose
    private String id;

    @SerializedName("login_times")
    @Expose
    private Integer loginTimes;

    @SerializedName("last_login")
    @Expose
    private String lastLogin;

    @Expose
    private String avatar;

    @SerializedName("project_id")
    @Expose
    private String projectId;

    public Integer getLoginTimes() {
        return loginTimes;
    }

    public void setLoginTimes(Integer loginTimes) {
        this.loginTimes = loginTimes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
