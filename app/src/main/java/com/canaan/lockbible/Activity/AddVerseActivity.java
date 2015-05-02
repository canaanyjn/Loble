package com.canaan.lockbible.Activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.canaan.lockbible.Fragment.AddVerseFragment;
import com.canaan.lockbible.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by canaan on 2015/4/10 0010.
 */
public class AddVerseActivity extends BaseActivity{
    Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_verse_layout);
        mToolBar = (Toolbar)findViewById(R.id.at_add_verse_toolbar);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("增加经文");
        mToolBar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addFragment();
    }

    private void addFragment(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_add_container_layout);
        if (fragment == null){
            fragment = new AddVerseFragment();
            fm.beginTransaction().add(R.id.fragment_add_container_layout, fragment)
                    .commit();
        }
    }

}
