package co.fddd.www.moran.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.view.View;

import java.util.HashMap;

import co.fddd.www.moran.API.UserApi;
import co.fddd.www.moran.Env;
import co.fddd.www.moran.model.SuccessMessage;
import co.fddd.www.moran.model.UserModel;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class BaseFragment extends Fragment implements View.OnClickListener, Callback<SuccessMessage> {
    protected OnFragmentInteractionListener mCallback;
    private Call<SuccessMessage> call;
    protected HashMap<String, String> inputs;
    protected Retrofit mAuthTask = null;

    public void showToast(String msg){
        mCallback.onToastShow(msg);
    }

    public void showProgress(Boolean show){
        mCallback.onProgressShow(show);
    }

    @Override
    public void onResponse(Response<SuccessMessage> response, Retrofit retrofit) {

    }

    @Override
    public void onFailure(Throwable t) {
        showProgress(false);
        showToast("failed");
    }

    public interface OnFragmentInteractionListener {
        void onToastShow(String msg);
        void onProgressShow(Boolean show);
    }

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onClick(View v) {
        switchContent(v);
    }


    public void switchContent(View view) {
    }

    protected boolean isEmailValid(String email) {
        return email.contains("@");
    }

    protected boolean isPasswordValid(String password) {
        return (password.length() >= 6 && password.length() <=20);
    }

    private Integer validator(){
        if (inputs.containsKey("email") && inputs.containsKey("username") &&
                inputs.containsKey("password") && inputs.containsKey("gbid"))
            return 4;

        if ( inputs.containsKey("email") && inputs.containsKey("password"))
            return 2;
        return 0;
    }

    protected Call<SuccessMessage> sendApi() {
        Retrofit mAuthTask = new Retrofit.Builder()
                .baseUrl(Env.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
                .build();
        UserApi service = mAuthTask.create(UserApi.class);
        Integer validateResult = validator();
        if (validateResult == 2){
            call = service.login(inputs.get("email"), inputs.get("password"));
        }
        else if (validateResult == 4) {
            call = service.register(inputs.get("email"), inputs.get("username"),
                    inputs.get("password"), inputs.get("gbid"));
        }
        else
            throw new IllegalArgumentException();
        return call;
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            mCallback = (OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

}
