package co.fddd.www.moran.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import co.fddd.www.moran.R;
import co.fddd.www.moran.SingleToast;
import co.fddd.www.moran.fragment.BaseFragment;
import co.fddd.www.moran.fragment.LoginFragment;
import me.zhanghai.android.materialprogressbar.IndeterminateProgressDrawable;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity
        implements BaseFragment.OnFragmentInteractionListener {

    private SingleToast mSingleToast;
//    @InjectView(R.id.indeterminate_progress_large_library)
    private ProgressBar indeterminateProgressLarge;
    private FrameLayout fragmentContainer;
    private BaseFragment fragment;

    public void showButtomToast(String msg) {
        mSingleToast.showButtomToast(msg);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mSingleToast = new SingleToast(this);
//        ViewCompat.isAttachedToWindow();
        fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
        indeterminateProgressLarge = (ProgressBar) findViewById(R.id.progress_bar);
        indeterminateProgressLarge.setIndeterminateDrawable(
                new IndeterminateProgressDrawable(this));
        indeterminateProgressLarge.setVisibility(View.GONE);
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            fragment = new LoginFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            fragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    public void onToastShow(String msg) {
        showButtomToast(msg);
    }

    @Override
    public void onProgressShow(Boolean show) {
        showProgress(show);
    }



    /**
     * Shows the progress UI and hides the login form.
     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        fragmentContainer.setVisibility(show ? View.GONE: View.VISIBLE);
        indeterminateProgressLarge.setVisibility(show ? View.VISIBLE : View.GONE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//            fragment.setVisibility(show ? View.GONE : View.VISIBLE);
//            fragment.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    fragment.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });
//
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mProgressView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
//        } else {
//            // The ViewPropertyAnimator APIs are not available, so simply show
//            // and hide the relevant UI components.
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
////            fragment.setVisibility(show ? View.GONE : View.VISIBLE);
//        }
    }
}

