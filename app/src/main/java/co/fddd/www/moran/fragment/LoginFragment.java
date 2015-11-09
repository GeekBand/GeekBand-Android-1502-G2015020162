package co.fddd.www.moran.fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
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
import co.fddd.www.moran.model.ErrorMessage;
import co.fddd.www.moran.model.SuccessMessage;
import co.fddd.www.moran.model.UserModel;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class LoginFragment extends BaseFragment{
    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;

    private SharedPreferences.Editor editor;

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment LoginFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static LoginFragment newInstance(String param1, String param2) {
//        LoginFragment fragment = new LoginFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEmailView = (EditText) view.findViewById(R.id.email);

        mPasswordView = (EditText) view.findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.email_sign_in_button || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
//
        Button mEmailSignInButton = (Button) view.findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

//        mLoginFormView = view.findViewById(R.id.login_form);
//        mProgressView = view.findViewById(R.id.login_progress);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        TextView button = (TextView) view.findViewById(R.id.to_register);
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void switchContent(View view) {
        final FragmentManager fragmentManager = getFragmentManager();
        final RegisterFragment registerFragment = (RegisterFragment) fragmentManager
                .findFragmentByTag("register");
        final FragmentTransaction transaction = fragmentManager.beginTransaction();

//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        if (registerFragment == null) {
            transaction.hide(this).add(R.id.fragment_container, new RegisterFragment()); // 隐藏当前的fragment，add下一个到Activity中
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            transaction.hide(this).show(registerFragment).commit(); // 隐藏当前的fragment，显示下一个
        }
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

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
            inputs.put("password", password);
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
            HashMap<String, Object> dict = message.getData();
            editor.putString("token", (String) dict.get("token"));
            editor.putString("userId", (String) dict.get("user_id"));
            editor.putString("avatar", (String) dict.get("avatar"));
            editor.commit();
            showToast(message.getMessage());
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
