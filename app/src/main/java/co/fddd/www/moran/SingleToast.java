package co.fddd.www.moran;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


public class SingleToast {

    Context mContext;
    Toast mToast;

    public SingleToast(Context mContext) {
        this.mContext = mContext;
        mToast = new Toast(mContext);
        mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
    }

    public void showButtomToast(String msg) {
        mToast.setText(msg);
        mToast.setGravity(Gravity.BOTTOM, 0, 64);
        mToast.show();
    }

    public void showButtomToast(int messageId) {
        mToast.setText(messageId);
        mToast.setGravity(Gravity.BOTTOM, 0, 64);
        mToast.show();
    }

    public void showMiddleToast(int id) {
        mToast.setText(id);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    public void showMiddleToast(String msg) {
        mToast.setText(msg);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }


}
