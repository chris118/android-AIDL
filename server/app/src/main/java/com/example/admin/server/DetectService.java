package com.example.admin.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class DetectService extends Service {
    private  final String TAG = DetectService.class.getSimpleName();

    private IDetectManager.Stub mIBinder = new IDetectManager.Stub() {
        @Override
        public void detect(String path, ICompleteCallback callback) throws RemoteException {
            Log.e(TAG, "detect start");
            Result result = new Result();
            result.setMessage("testing");
            callback.complete(result);
        }
    };

    /**
     * 客户端与服务端绑定时的回调，返回 mIBinder 后客户端就可以通过它远程调用服务端的方法，即实现了通讯
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }
}
