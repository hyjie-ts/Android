package com.example.caipu;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;


public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    //UI Object
    private RadioButton rbtnChannel, rbtnMessage, rbtnMy, rbtnMore;
    private RadioGroup rgroupTabMenu;

    //Fragment Object
    private VideoFragment fg1;
    private CookbookFragment fg2;
    private MessageFragment fg3;
    private MeFragment fg4;
    private FragmentManager fManager;

    SharedPreferences sprfMain;
    SharedPreferences.Editor editorMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sprfMain= PreferenceManager.getDefaultSharedPreferences(this);
        editorMain=sprfMain.edit();

        //.getBoolean("main",false)；当找不到"main"所对应的键值是默认返回false
        if(!sprfMain.getBoolean("main",false)){
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);

        }


        setContentView(R.layout.activity_main);

        fManager = getSupportFragmentManager();
        bindViews();

        rbtnChannel.setChecked(true);
    }

    //UI组件初始化与事件绑定
    private void bindViews() {
        rgroupTabMenu = findViewById(R.id.main_rgroupTabMenu);
        rbtnChannel = findViewById(R.id.main_rbtnChannel);
        rbtnMessage = findViewById(R.id.main_rbtnMessage);
        rbtnMore = findViewById(R.id.main_rbtnMore);
        rbtnMy = findViewById(R.id.main_rbtnMy);

        rgroupTabMenu.setOnCheckedChangeListener(this);
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fg1 != null)fragmentTransaction.hide(fg1);
        if(fg2 != null)fragmentTransaction.hide(fg2);
        if(fg3 != null)fragmentTransaction.hide(fg3);
        if(fg4 != null)fragmentTransaction.hide(fg4);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (checkedId){
            case R.id.main_rbtnChannel:
                if(fg1 == null){
                    fg1 = new VideoFragment();
                    fTransaction.add(R.id.ly_content,fg1);
                }else{
                    fTransaction.show(fg1);
                }
                break;
            case R.id.main_rbtnMessage:
                if(fg2 == null){
                    fg2 = new CookbookFragment();
                    fTransaction.add(R.id.ly_content,fg2);
                }else{
                    fTransaction.show(fg2);
                }
                break;
            case R.id.main_rbtnMore:
                if(fg3 == null){
                    fg3 = new MessageFragment();
                    fTransaction.add(R.id.ly_content,fg3);
                }else{
                    fTransaction.show(fg3);
                }
                break;
            case R.id.main_rbtnMy:
                if(fg4 == null){
                    fg4 = new MeFragment();
                    fTransaction.add(R.id.ly_content,fg4);
                }else{
                    fTransaction.show(fg4);
                }
                break;
        }
        fTransaction.commit();
    }
}
