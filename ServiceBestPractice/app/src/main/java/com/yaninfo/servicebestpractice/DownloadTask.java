package com.yaninfo.servicebestpractice;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author: zhangyan
 * @Description:
 * 泛型参数String：传入一个字符串给后台任务
 * 第一个泛型参数Integer：表示整形数据为进度显示单位
 * 第二个泛型参数Integer：整型数据反馈执行结果
 * @Date: 2019/3/22 11:28
 * @Version: 1.0
 */
public class DownloadTask extends AsyncTask<String, Integer, Integer> {

    private static final int TYPE_SUCCESS = 0; //下载成功
    private static final int TYPE_FAILED = 1; //下载失败
    private static final int TYPE_PAUSED = 2; //下载暂停
    private static final int TYPE_CANCELED = 3; //下载取消

    //监听器，回调下载状态参数
    private DownloadListener mDownloadListener;

    private boolean isCancelled = false;
    private boolean isPaused = false;

    private int lastProcess;

    //构造方法，获取监听器
    public DownloadTask(DownloadListener downloadListener) {
        mDownloadListener = downloadListener;
    }

    /**
     * 执行后台具体的下载逻辑
     * @param params
     * @return
     */
    @Override
    protected Integer doInBackground(String... params) {
        //在后台执行下载操作
        InputStream inputStream = null;
        RandomAccessFile savedFile = null;
        File file = null;
        try {
            long downloadedLength = 0;//记录下已经下载的文件长度
            String downloadUrl = params[0];//从传入的参数获取下载地址

            //从地址最后的"/"后截取文件名
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            //下载到Environment.DIRECTORY_DOWNLOADS即SD卡的downloads目录
            String directory = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS).getPath();
            file = new File(directory, fileName);//组装成完整文件路径，创建文件

            //判断文件存不存在
            if (file.exists()) {
                //如果存在的话读取已经下载的字节数，便于后面的断点续传功能
                downloadedLength = file.length();
            }
            //获取下载文件的总长度
            long contentLength = getContentLength(downloadUrl);
            //文件长度不对，直接下载失败
            if (contentLength <= 0) {
                return TYPE_FAILED;
            } else if (downloadedLength == contentLength) {  //如果下载文件大小和已下载文件大小相同，说明已下完
                return TYPE_SUCCESS;
            }

            //网络请求，这里用到同步GET请求
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("RANGE", "bytes=" + downloadedLength + "-")  //告诉服务器要从哪一个字节开始下载
                    .url(downloadUrl)
                    .build();

            // 调用newCall()方法来创建一个Call对象，并调用execute()方法来发送请求并获取服务器端数据
            Response response = okHttpClient.newCall(request).execute();

            //处理请求结果
            if (response != null) {
                inputStream = response.body().byteStream();
                savedFile = new RandomAccessFile(file, "rw");
                savedFile.seek(downloadedLength); //跳过已下载的字节
                byte[] b = new byte[1024];
                int length;
                int total = 0;
                while ((length = inputStream.read(b)) != -1) {
                    if (isCancelled) {
                        return TYPE_CANCELED;
                    } else if (isPaused) {
                        return TYPE_PAUSED;
                    } else {
                        total += length;
                        savedFile.write(b, 0, length);
                        //计算已经下载的百分比
                        int progress = (int) ((total + downloadedLength) * 100 / contentLength);
                        // 更新进度条
                        publishProgress(progress);
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (savedFile != null) {
                    savedFile.close();
                }
                if (isCancelled && (file != null)) {
                    file.delete();
                }
            } catch (Exception e) {
            }
        }
        return TYPE_FAILED;
    }

    /**
     * 更新当前下载进度，当前的下载进度和上一次的下载进度比较，如果有变化就调用onProgress()方法来通知下载进度更新
     * @param values
     */
    @Override
    protected void onProgressUpdate(Integer...values) {
        int process = values[0];
        if (process > lastProcess) {
            mDownloadListener.onProgress(process);
            lastProcess = process;
        }
    }

    /**
     * 通知最终的下载结果
     * @param status
     */
    @Override
    protected void onPostExecute(Integer status) {
        switch (status) {
            case TYPE_SUCCESS:
                mDownloadListener.onSuccess();
                break;
            case TYPE_FAILED:
                mDownloadListener.onFailed();
                break;
            case TYPE_PAUSED:
                mDownloadListener.onPaused();
                break;
            case TYPE_CANCELED:
                mDownloadListener.onCanceled();
                break;
            default:
                break;
        }
    }

    public void pauseDownload() {
        isPaused = true;
    }

    public void cancelDownload() {
        isCancelled = true;
    }

    //获取下载文件的总长度
    private long getContentLength(String downloadUrl) {
        try {
            //网络请求
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(downloadUrl)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                long contentLength = response.body().contentLength();
                response.close();
                return contentLength;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
