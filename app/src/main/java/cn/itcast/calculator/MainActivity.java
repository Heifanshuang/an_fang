package cn.itcast.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnJump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout1);

        // 初始化按钮
        btnJump = findViewById(R.id.btn_jump);

        // 设置点击监听器
        btnJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建Intent对象
                Intent intent = new Intent(MainActivity.this, SecondActivity.class); // 设置跳转目标Activity
                
                Bundle bundle = new Bundle(); // 创建Bundle对象（键值对容器，类似Map）
                bundle.putString("account", "江小白"); // 存入账号（键：account，值：字符串）
                bundle.putString("password", "123456"); // 存入密码（键：password，值：字符串）
                
                intent.putExtras(bundle); // 将Bundle对象封装到Intent中
                startActivity(intent); // 执行跳转
            }
        });
    }
}
