package com.yaninfo;

/**
 * @Author: zhangyan
 * @Date: 2019/3/25 15:02
 * @Description: 回调接口
 * @Version: 1.0
 */
public interface HttpCallbackListener {

    void onFinish(String response);

    void onError(Exception e);

}
