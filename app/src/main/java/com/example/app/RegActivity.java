package com.example.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private EditText name;
    private EditText phone;
    Button ok;
    private String selected="";
    private RadioGroup rgsex;
    private TextInputLayout mainLayout,phoneLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mainLayout=findViewById(R.id.name_layout);
        phoneLayout=findViewById(R.id.phone_layout);

        name=findViewById(R.id.et_name);
        phone=findViewById(R.id.et_phone);
        rgsex=findViewById(R.id.rg);

        CheckBox cbAndroid=findViewById(R.id.Android);
        CheckBox cbJava=findViewById(R.id.Java);
        CheckBox cbMath=findViewById(R.id.shuxue);
        CheckBox cbEnglish=findViewById(R.id.yingyu);

        ok=findViewById(R.id.btnYes);

        cbAndroid.setOnCheckedChangeListener(this);
        cbJava.setOnCheckedChangeListener(this);
        cbMath.setOnCheckedChangeListener(this);
        cbEnglish.setOnCheckedChangeListener(this);
        ok.setOnClickListener(this);

        final Intent intent=getIntent();
        String username=intent.getStringExtra("username");

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        CheckBox checkBox=(CheckBox) buttonView;
        if(isChecked){
            selected+=checkBox.getText().toString()+",";
        }else{
            selected=selected.replace(checkBox.getText().toString()+",","");
        }
        Snackbar.make(mainLayout,selected,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        String names=name.getText().toString().trim();
        String phones=phone.getText().toString().trim();

    /*    String sex = "男";
        int id=rgsex.getCheckedRadioButtonId();
        if (id==R.id.rb_girl){
            sex="女";
        }*/
        if(!validatePhone(phones)){
            phoneLayout.setError("请输入正确的手机号");
            phone.setText("");
            ok.requestFocus();
            return;
        }
        int id=rgsex.getCheckedRadioButtonId();
        RadioButton btn=findViewById(id);
        String sex=btn.getText().toString();

        String info="用户名："+names+",手机号："+phones+",性别："+sex+"\n喜欢的课程："+selected;
        Snackbar.make(mainLayout,info,Snackbar.LENGTH_LONG).setAction("确定",new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Toast.makeText(RegActivity.this,"信息已确定",Toast.LENGTH_SHORT).show();
            }
        }).show();
    }
    private static  final  String PHONE_PATTERN="^1[3-9]\\d{9}$";
    private boolean validatePhone(String Phone) {
        Pattern pattern= Pattern.compile(PHONE_PATTERN);
        Matcher matcher=pattern.matcher(Phone);
        return matcher.matches();
    }
}
