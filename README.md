# android-AIDL

演示使用AIDL跨进程通信

##Server：

1. AIDL可以找到java文件，例如Result.java

```
sourceSets {
    main {
        java.srcDirs = ['src/main/java', 'src/main/aidl']
    }
}
```

2.方向类型

```
//导入所需要使用的非默认支持数据类型的包
import com.example.admin.server.Result;

interface ICompleteCallback {
    /**
     * 除了基本数据类型，其他类型的参数都需要标上方向类型：in(输入), out(输出), inout(输入输出)
     */
    void complete(in Result result);
}
```

Client:

1. copy所有Server的AIDL文件夹到main

2. BindService 要在Onstart里调用， 在OnCreate里调用会造成ServiceConnection不能回调

3. 回调参数写法

```
  private ICompleteCallback.Stub serviceCallback = new ICompleteCallback.Stub() {
        @Override
        public void complete(Result result) {
            Log.e(TAG, result.toString());

            Toast toast=Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
    };
```
