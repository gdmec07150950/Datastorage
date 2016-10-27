package com.example.administrator.datastorage;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText accountEditText, passwordEditText;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accountEditText = (EditText) findViewById(R.id.account);
        passwordEditText = (EditText) findViewById(R.id.password);
        resultTextView = (TextView) findViewById(R.id.result);
    }

    public void spWrite(View v) {

        SharedPreferences user = getSharedPreferences("user", MODE_APPEND);
        SharedPreferences.Editor editor = user.edit();
        editor.putString("account", accountEditText.getText().toString());
        editor.putString("passwork", passwordEditText.getText().toString());
        editor.commit();
        Toast.makeText(this, "SharedPreferences写入成功", Toast.LENGTH_LONG);

    }

    public void spRead(View v) {
        SharedPreferences user = getSharedPreferences("user", MODE_PRIVATE);
        String acount = user.getString("account", "没有这个键值！");
        String passwork = user.getString("passwork", "没有这个键值！");
        resultTextView.setText("帐号:" + acount + "\n" + "密码:" + passwork);
        Toast.makeText(this, "SharedPreferences读取成功", Toast.LENGTH_LONG).show();
    }

    public void ROMWrite(View v) {
        String account = accountEditText.getText().toString();
        String passwork = passwordEditText.getText().toString();
        try {
            FileOutputStream fos = openFileOutput("user.txt", MODE_APPEND);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(account + ":" + passwork);
            bw.flush();
            fos.close();
            Toast.makeText(this, "ROM写入成功！", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ROMRead(View v) {
        String acount = accountEditText.getText().toString();
        String passwork = passwordEditText.getText().toString();
        try {
            FileInputStream fis = openFileInput("user.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();
            String s;
            while ((s = br.readLine()) != null) {
                sb.append(s + "\n");
            }
            fis.close();
            accountEditText.setText(sb);
            Toast.makeText(this, "ROM读取成功！", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SDWrite(View v) {
        String str = accountEditText.getText().toString() + ":" + passwordEditText.getText().toString();
        String sdCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
        String filename = sdCardRoot + "/test.txt";
        File file = new File(filename);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(str.getBytes());
            fos.flush();
            fos.close();
            Toast.makeText(this, "SD卡写入成功！", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SDRead(View v) {
        String sdCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
        String filename = sdCardRoot + "/test.txt";
        File file = new File(filename);
        int length = (int) file.length();
        byte[] b = new byte[length];
        try {
            FileInputStream fis = new FileInputStream(file);
            fis.read(b, 0, length);
            fis.close();
            accountEditText.setText(new String(b));
            Toast.makeText(this, "SD卡读取成功！", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}






