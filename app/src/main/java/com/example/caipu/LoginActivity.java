package com.example.caipu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.caipu.db.MyDBHelper;

public class LoginActivity extends AppCompatActivity {

    private MyDBHelper dbHelper;
    private EditText username;
    private EditText userpassword;
    private Button rg;
    private Button lg;
    SharedPreferences sprfMain;
    SharedPreferences.Editor editorMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        dbHelper = new MyDBHelper(this, "UserStore.db", null, 2);
        rg = (Button) findViewById(R.id.btn_register);
        lg = (Button) findViewById(R.id.login);
        lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = (EditText) findViewById(R.id.et_username);
                userpassword = (EditText) findViewById(R.id.et_password);
                String userName = username.getText().toString().trim();
                String passWord = userpassword.getText().toString().trim();
                if(userName.equals("")||passWord.equals("")){
                    Toast.makeText(LoginActivity.this, "请正确输入", Toast.LENGTH_SHORT).show();
                }else if (login(userName, passWord)) {
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    resetSprfMain();
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, registereActivity.class);
                startActivity(intent);
            }
        });
    }

    public void resetSprfMain(){
        sprfMain= PreferenceManager.getDefaultSharedPreferences(this);
        editorMain=sprfMain.edit();
        editorMain.putBoolean("main",true);
        editorMain.commit();
    }

    //验证登录
    public boolean login(String username,String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select * from userData where name=? and password=?";
        Cursor cursor = db.rawQuery(sql, new String[] {username, password});
//        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }


}
