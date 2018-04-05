// DetectManager.aidl
package com.example.admin.server;

import com.example.admin.server.ICompleteCallback;

interface IDetectManager {
    void detect(String path, ICompleteCallback callback);
}
