package rubo.statelayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import rubo.statelayout.LayerLayout.OnRefreshListener;

public class MainActivity extends AppCompatActivity {

    LayerLayout mStateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStateLayout = (LayerLayout) findViewById(R.id.main_rootView);
        mStateLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mStateLayout.changeLayer(2);
            }
        });
    }


    public void hideContent(View view) {
        mStateLayout.changeLayer(4);
    }
}
