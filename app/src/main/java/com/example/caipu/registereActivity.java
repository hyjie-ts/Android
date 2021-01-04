package com.example.caipu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.caipu.db.MyDBHelper;

public class registereActivity extends AppCompatActivity {

    private EditText name;
    private EditText password;
    private MyDBHelper dbHelper;
    private Button resg;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        dbHelper = new MyDBHelper(this,"UserStore.db",null,2);

        name = (EditText)findViewById(R.id.rg_username);
        password = (EditText)findViewById(R.id.register_password);
        resg = (Button)findViewById(R.id.bt_register);
        login = (Button)findViewById(R.id.btn_login) ;
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(registereActivity.this,LoginActivity.class);
//                startActivity(intent);
                finish();
            }
        });
        resg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newname = name.getText().toString();
                String pw = password.getText().toString();
                if(newname.equals("")||pw.equals("")){
                    Toast.makeText(registereActivity.this, "请正确输入", Toast.LENGTH_SHORT).show();
                } else {

                    if (register(newname, pw)) {
                        Toast.makeText(registereActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }
            }
        });


    }

    //向数据库插入数据
    public boolean register(String username,String password){
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        /*String sql = "insert into userData(name,password) value(?,?)";
        Object obj[]={username,password};
        db.execSQL(sql,obj);*/
        ContentValues values=new ContentValues();
        values.put("name",username);
        values.put("password",password);
        db.insert("userData",null,values);
        db.close();
        //db.execSQL("insert into userData (name,password) values (?,?)",new String[]{username,password});
        return true;
    }


}
