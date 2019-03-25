package com.yaninfo.servicebestpractice;

/**
 * @Author: zhangyan
 * @Description: 回调接口，用于对下载过程中的各种状态进行监听和回调
 * @Date: 2019/3/22 11:24
 * @Version: 1.0
 */
public interface DownloadListener {

    /**
     * 通知下载进度
     *
     * @param progress
     */
    void onProgress(int progress);

    /**
     * 通知下载成功事件
     */
    void onSuccess();

    /**
     * 通知下载失败事件
     */
    void onFailed();

    /**
     * 通知下载暂停事件
     */
    void onPaused();

    /**
     * 通知下载取消事件
     */
    void onCanceled();
}
