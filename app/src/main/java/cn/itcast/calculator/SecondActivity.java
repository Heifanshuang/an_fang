package cn.itcast.calculator;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout2);

        // 初始化TextView
        textView = findViewById(R.id.textView);

        Bundle bundle = getIntent().getExtras(); // 获取Intent中携带的Bundle对象
        String account = bundle.getString("account"); // 根据键"account"取账号
        String password = bundle.getString("password"); // 根据键"password"取密码

        // 显示传递过来的数据
        String message = "账号: " + account + "\n" +
                        "密码: " + password;
        textView.setText(message);
    }
}