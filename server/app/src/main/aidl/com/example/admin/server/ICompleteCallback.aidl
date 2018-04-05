// ICompleteCallback.aidl
package com.example.admin.server;

import com.example.admin.server.Result;

interface ICompleteCallback {
    /**
     * 除了基本数据类型，其他类型的参数都需要标上方向类型：in(输入), out(输出), inout(输入输出)
     */
    void complete(in Result result);
}
