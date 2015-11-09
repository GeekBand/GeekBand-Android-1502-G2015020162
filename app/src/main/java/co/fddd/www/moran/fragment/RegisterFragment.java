package co.fddd.www.moran.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.HashMap;

import co.fddd.www.moran.R;
import co.fddd.www.moran.SingleToast;
import co.fddd.www.moran.model.ErrorMessage;
import co.fddd.www.moran.model.SuccessMessage;
import co.fddd.www.moran.model.UserModel;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class RegisterFragment extends BaseFragment{
    // UI references.
    private EditText mUsernameView;
    private EditText mEmailView;
    private EditText mPassword1View;
    private EditText mPassword2View;

    private SingleToast mSingleToast;
    public void showMiddleToast(String msg) {
        mSingleToast.showMiddleToast(msg);
    }
//    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
//    public static RegisterFragment newInstance(String param1, String param2) {
//        RegisterFragment fragment = new RegisterFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_register, container, false);
        TextView button = (TextView) view.findViewById(R.id.to_login);
        button.setOnClickListener(this);

        mEmailView = (EditText) view.findViewById(R.id.email);
        mUsernameView = (EditText) view.findViewById(R.id.username);
        mPassword1View = (EditText) view.findViewById(R.id.password1);
        mPassword2View = (EditText) view.findViewById(R.id.password2);
        mPassword2View.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.email_sign_in_button || id == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });
//
        Button registerButton = (Button) view.findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
        return view;
    }

    private void backToLogin() {
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        final LoginFragment loginFragment = (LoginFragment) fragmentManager
                .findFragmentByTag("login");
        transaction.hide(this).show(loginFragment).commit();
    }

    @Override
    public void switchContent(View view) {
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        final LoginFragment loginFragment = (LoginFragment) fragmentManager
                .findFragmentByTag("login");
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        if (loginFragment == null) {
            transaction.hide(this).add(R.id.fragment_container, new LoginFragment()); // 隐藏当前的fragment，add下一个到Activity中
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            transaction.hide(this).show(loginFragment).commit(); // 隐藏当前的fragment，显示下一个
        }
    }
    // TODO: Rename method, update argument and hook method into UI event

    private void attemptRegister() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mEmailView.setError(null);
        mPassword1View.setError(null);
        mPassword2View.setError(null);
        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String username = mUsernameView.getText().toString();
        String password1 = mPassword1View.getText().toString();
        String password2 = mPassword2View.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password1) && !isPasswordValid(password1)) {
            mPassword1View.setError(getString(R.string.error_invalid_password));
            focusView = mPassword1View;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!password1.equals(password2)) {
            mPassword1View.setError(getString(R.string.error_inconsistent_password));
            focusView = mPassword2View;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;}

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            inputs = new HashMap<>();
            inputs.put("email", email);
            inputs.put("username", username);
            inputs.put("password", password1);
            inputs.put("gbid", "GeekBand-I150003");
            mCallback.onProgressShow(true);
            Call<SuccessMessage> call = sendApi();
            call.enqueue(this);
        }
    }

    @Override
    public void onResponse(Response<SuccessMessage> response, Retrofit retrofit) {
        mCallback.onProgressShow(false);
        if (response.isSuccess()) {
            SuccessMessage message = response.body();
            showToast(message.getMessage());
            backToLogin();
        }
        else{
            // handle request errors yourself
            ResponseBody errorBody = response.errorBody();
            Gson gson = new Gson();

            //convert java object to JSON format
            ErrorMessage message = null;
            try {
                message = gson.fromJson(errorBody.string(), ErrorMessage.class);
                showToast(message.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
