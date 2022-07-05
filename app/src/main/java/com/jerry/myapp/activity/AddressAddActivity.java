package com.jerry.myapp.activity;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jerry.myapp.R;
import com.jerry.myapp.util.ParseTokenUtils;
import com.jerry.myapp.util.StringUtils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressAddActivity extends BaseActivity{
    private ImageButton leftReturn3;
    private EditText adrAddName;
    private EditText adrAddPhone;
    private EditText adrDetail;
    private Switch switch1;
    private TextView adrAddSave;
    private ImageButton clearDetail;
    private boolean isCheck = false;
    @Override
    protected int initLayout() {
        return R.layout.activity_address_add;
    }

    @Override
    protected void initView() {
        leftReturn3 = findViewById(R.id.left_return3);
        adrAddName = findViewById(R.id.adr_add_name);
        adrAddPhone = findViewById(R.id.adr_add_phone);
        adrDetail = findViewById(R.id.adr_detail);
        switch1 = findViewById(R.id.switch1);
        adrAddSave = findViewById(R.id.adr_add_save);
        clearDetail = findViewById(R.id.clear_detail);

        switch1.setChecked(false);
    }

    @Override
    protected void initData() {
        //返回注册界面的按钮
        leftReturn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(AddressActivity.class);
            }
        });
        //情况详细地址内容
        clearDetail.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adrDetail.setText("");
                    }
                }
        );
        //得知滑动按钮的状态
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean check){
                isCheck = check;
            }
        }
        );
        //提交地址
        adrAddSave.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Address address1 = new Address();
                        String name = adrAddName.getText().toString().trim();
                        String telephone = adrAddPhone.getText().toString().trim();
                        String Address = adrDetail.getText().toString().trim();
                        address1.setName(name);
                        address1.setTelephone(telephone);
                        address1.setAddress(Address);
                        if (isCheck){
                            address1.setDefaultAddress(1);
                        }else{
                            address1.setDefaultAddress(0);
                        }
                        //调用
                        addAddressTo(address1);
                    }
                }
        );


    }
    private void addAddressTo(Address add){
        navigateTo(AddressAddActivity.class);

        if (StringUtils.isEmpty(add.getName())) {
            Toast.makeText(getApplicationContext(),"收件人姓名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(add.getTelephone())) {
            Toast.makeText(getApplicationContext(),"电话号码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(add.getAddress())) {
            Toast.makeText(getApplicationContext(),"详细地址不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isMobile(add.getTelephone())){
            Toast.makeText(getApplicationContext(),"电话号码格式不正确",Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("name", add.getName());
        params.put("telephone", add.getTelephone());
        params.put("address", add.getAddress());
        params.put("defaultAddress", add.getDefaultAddress());
        String token = getStringFromSp("token");
        ParseTokenUtils parse = new ParseTokenUtils();
        params.put("token",parse);
    }
    //手机号11位正则验证
    public static boolean isMobile(String mobile) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(16[5,6])|(17[0-8])|(18[0-9])|(19[1、5、8、9]))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    //地址详情表
    public static class Address{
        private String name;
        private String telephone;
        private String address;
        private Integer defaultAddress;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Integer getDefaultAddress() {
            return defaultAddress;
        }

        public void setDefaultAddress(Integer defaultAddress) {
            this.defaultAddress = defaultAddress;
        }
    }
}
