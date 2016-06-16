package rubo.statelayout;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class LayerLayout extends FrameLayout implements OnClickListener {

    private View mNetErrorContainer;

    private View mLoadingContainer;

    private View mLoadingErrorContainer;

    private View mNoInfoContainer;

    private ViewGroup mContentContainer;

    private OnRefreshListener mOnRefreshListener;

    public LayerLayout(Context context) {
        this(context, null);
    }

    public LayerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_layer, this);

        mNetErrorContainer = findViewById(R.id.layer_netErrorContainer);
        findViewById(R.id.layer_netErrorCheck).setOnClickListener(this);

        mLoadingContainer = findViewById(R.id.layer_loadingContainer);

        mLoadingErrorContainer = findViewById(R.id.layer_loadingErrorContainer);
        findViewById(R.id.layer_loadingErrorReload).setOnClickListener(this);

        mNoInfoContainer = findViewById(R.id.layer_noInoContainer);

        mContentContainer = (ViewGroup) findViewById(R.id.layer_contentContainer);

    }

    public void changeLayer(int type) {
        if (type == 0) {
            showContentLayer();
        } else if (type == 1) {
            showNoInfoLayer();
        } else if (type == 2) {
            showNetErrorLayer();
        } else if (type == 3){
            showLoadingLayer();
        } else if (type ==4 ){
            showLoadingErrorLayer();
        }
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (isContainer(child)) {
            super.addView(child, params);
        } else {
            mContentContainer.addView(child, params);
        }
    }

    private boolean isContainer(View view) {
        return view.getId() == R.id.layer_noInoContainer || view.getId() == R.id.layer_loadingContainer
                || view.getId() == R.id.layer_contentContainer || view.getId() == R.id.layer_loadingErrorContainer
                || view.getId() == R.id.layer_netErrorContainer;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.layer_loadingErrorReload) {
            if (mOnRefreshListener != null) {
                mOnRefreshListener.onRefresh();
            }
        } else if (v.getId() == R.id.layer_netErrorCheck) {
            Intent netSettings = new Intent(Settings.ACTION_WIFI_SETTINGS);
            if (netSettings.resolveActivity(v.getContext().getPackageManager()) != null) {
                v.getContext().startActivity(netSettings);
                showLoadingErrorLayer();
            }
        }
    }

    private void showLoadingLayer() {
        hideAllLayer();
        mLoadingContainer.setVisibility(View.VISIBLE);
    }

    private void showLoadingErrorLayer() {
        hideAllLayer();
        mLoadingErrorContainer.setVisibility(View.VISIBLE);
    }

    private void showNetErrorLayer() {
        hideAllLayer();
        mNetErrorContainer.setVisibility(View.VISIBLE);
    }

    private void showNoInfoLayer() {
        hideAllLayer();
        mNoInfoContainer.setVisibility(View.VISIBLE);
    }

    private void showContentLayer() {
        hideAllLayer();
        mContentContainer.setVisibility(View.VISIBLE);
    }

    private void hideAllLayer() {
        mLoadingContainer.setVisibility(View.GONE);
        mNetErrorContainer.setVisibility(View.GONE);
        mNoInfoContainer.setVisibility(View.GONE);
        mLoadingErrorContainer.setVisibility(View.GONE);
        mContentContainer.setVisibility(View.GONE);
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        mOnRefreshListener = listener;
    }

    public interface OnRefreshListener {
        void onRefresh();
    }
}
