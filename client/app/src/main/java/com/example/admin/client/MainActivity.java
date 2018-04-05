package com.example.admin.client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.InCallService;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.admin.server.ICompleteCallback;
import com.example.admin.server.IDetectManager;
import com.example.admin.server.Result;

public class MainActivity extends AppCompatActivity {
    private  final String TAG = MainActivity.class.getSimpleName();

    private IDetectManager mDetectManager;
    private boolean mBound = false;
    private Button mBtnDetct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnDetct = this.findViewById(R.id.btn_detect);
        mBtnDetct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mDetectManager.detect("file://assets/sample.jpg", serviceCallback);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //不可以在OnCreate里调用
        bindService();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(mBound){
            unbindService(mServiceConnection);
            mBound = false;
        }
    }

    private ICompleteCallback.Stub serviceCallback = new ICompleteCallback.Stub() {
        @Override
        public void complete(Result result) {
            Log.e(TAG, result.toString());

            Toast toast=Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
    };

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mDetectManager = IDetectManager.Stub.asInterface(iBinder);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mDetectManager = null;
            mBound = false;
        }
    };

    private void bindService(){
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.admin.server", "com.example.admin.server.DetectService"));
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }
}
