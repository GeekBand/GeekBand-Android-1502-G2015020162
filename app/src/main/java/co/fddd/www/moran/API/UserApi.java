package co.fddd.www.moran.API;

import co.fddd.www.moran.model.SuccessMessage;
import co.fddd.www.moran.model.UserModel;
import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Administrator on 2015/10/22 0022.
 */
public interface UserApi {
    @FormUrlEncoded
    @POST("user/login/")
    Call<SuccessMessage> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("user/register/")
    Call<SuccessMessage> register(
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password,
            @Field("gbid") String gbid
    );
}
