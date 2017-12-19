package com.roshine.lookbar;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aidldemo.ITestAidlInterface;
import com.example.aidldemo.Person;
import com.roshine.lookbar.mvp.base.BaseActivity;

import java.util.List;

import butterknife.BindView;

/**
 * @author Roshine
 * @date 2017/12/7 16:17
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class TestAidlActivity extends BaseActivity {
    @BindView(R.id.et_a)
    EditText etA;
    @BindView(R.id.et_b)
    EditText etB;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.btn_result_in)
    Button btnResultIn;
    @BindView(R.id.btn_result_out)
    Button btnResultOut;
    @BindView(R.id.btn_result_inout)
    Button btnResultInout;

    private ITestAidlInterface testAidlInterface;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_aidl;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        initService();
        testAdd();
    }

    private void testAdd() {
        btnResultIn.setOnClickListener(view -> {
            try {
                if(!TextUtils.isEmpty(etA.getText()) && !TextUtils.isEmpty(etB.getText())){
                    Person person = new Person();
                    person.setName(etA.getText().toString());
                    person.setAge(Integer.valueOf(etB.getText().toString()));
                    String person1 = testAidlInterface.inPerson(person);
                    Log.d("Roshine", "结果："+person1);
                    tvResult.setText(person1);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        btnResultOut.setOnClickListener(view -> {
            try {
                if(!TextUtils.isEmpty(etA.getText()) && !TextUtils.isEmpty(etB.getText())){
                    Person person = new Person();
                    person.setName(etA.getText().toString());
                    person.setAge(Integer.valueOf(etB.getText().toString()));
                    String person1 = testAidlInterface.outPerson(person);
                    Log.d("Roshine", "结果："+person1);
                    tvResult.setText(person1);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        btnResultInout.setOnClickListener(view -> {
            try {
                if(!TextUtils.isEmpty(etA.getText()) && !TextUtils.isEmpty(etB.getText())){
                    Person person = new Person();
                    person.setName(etA.getText().toString());
                    person.setAge(Integer.valueOf(etB.getText().toString()));
                    String person1 = testAidlInterface.inOutPerson(person);
                    Log.d("Roshine", "结果："+person1);
                    tvResult.setText(person1);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    private void initService() {
        Intent intent = new Intent();
        intent.setAction("com.example.testaidl");

        Intent explicitFromImplicitIntent = createExplicitFromImplicitIntent(this, intent);
        bindService(explicitFromImplicitIntent,conn, Context.BIND_AUTO_CREATE);
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            testAidlInterface = ITestAidlInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    /**
     * 兼容Android5.0中service的intent一定要显性声明
     *
     * @param context
     * @param implicitIntent
     * @return
     */
    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        //通过queryIntentActivities()方法，查询Android系统的所有具备ACTION_MAIN和CATEGORY_LAUNCHER
        //的Intent的应用程序，点击后，能启动该应用，说白了就是做一个类似Home程序的简易Launcher 。
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }
}
