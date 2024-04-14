package xyz.mrkwcode.aiimusicserver.utils.TcosUtil;


import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.exception.MultiObjectDeleteException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class TcosUtil {
    private static final String UTIL_LOCAL_DIR = "src/main/java/xyz/mrkwcode/aiimusicserver/utils/TcosUtil/";

    // 1 初始化用户身份信息（secretId, secretKey）。
    // SECRETID 和 SECRETKEY 请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
    private static final String SECRET_ID;//用户的 SecretId，建议使用子账号密钥，授权遵循最小权限指引，降低使用风险。子账号密钥获取可参见 https://cloud.tencent.com/document/product/598/37140

    static {
        try {
            SECRET_ID = LoadFileConfig.readSecretInfo(new FileInputStream(UTIL_LOCAL_DIR + "config/config.yml")).get("secret-id").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String SECRET_KEY;//用户的 SecretKey，建议使用子账号密钥，授权遵循最小权限指引，降低使用风险。子账号密钥获取可参见 https://cloud.tencent.com/document/product/598/37140
    static {
        try {
            SECRET_KEY = LoadFileConfig.readSecretInfo(new FileInputStream(UTIL_LOCAL_DIR + "config/config.yml")).get("secret-key").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static final String REGION_NAME = "ap-nanjing";
    private static final String BUCKET_NAME = "private-1301858127";

    public static COSClient initClient() {

        COSCredentials cred = new BasicCOSCredentials(SECRET_ID, SECRET_KEY);
        // 2 设置 bucket 的地域, COS 地域的简称请参见 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(REGION_NAME);
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        return new COSClient(cred, clientConfig);
    }

    public static String uploadFile(String path, String key, InputStream input) throws CosClientException {
        COSClient cosClient = initClient();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 上传的流如果能够获取准确的流长度，则推荐一定填写 content-length
        // 如果确实没办法获取到，则下面这行可以省略，但同时高级接口也没办法使用分块上传了
        // objectMetadata.setContentLength(inputStreamLength);

        PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, path + key, input, objectMetadata);
        // 设置存储类型（如有需要，不需要请忽略此行代码）, 默认是标准(Standard), 低频(standard_ia)
        // 更多存储类型请参见 https://cloud.tencent.com/document/product/436/33417
        putObjectRequest.setStorageClass(StorageClass.Standard);

        String url = "";
        try {
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            System.out.println(putObjectResult.getRequestId());
            url = "https://" + BUCKET_NAME + ".cos." + REGION_NAME + ".myqcloud.com/" + path + URLEncoder.encode(key, Charset.defaultCharset().displayName());
            url = url.replaceAll("\\+", "%20");
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 确认本进程不再使用 cosClient 实例之后，关闭即可
        cosClient.shutdown();
        return url;
    }

    public static String removeObject(String oldUrl) throws CosClientException, UnsupportedEncodingException {
        COSClient cosClient = initClient();
        String key = URLDecoder.decode(oldUrl.substring(("https://" + BUCKET_NAME + ".cos." + REGION_NAME + ".myqcloud.com/").length()), Charset.defaultCharset().displayName());
        try {
            boolean objectExists = cosClient.doesObjectExist(BUCKET_NAME, key);
            if(objectExists) {
                cosClient.deleteObject(BUCKET_NAME, key);
            } else {
                return "文件不存在";
            }
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
        cosClient.shutdown();
        return key + "删除成功";
    }


    public static String removeObject(ArrayList<String> oldUrls) {
        COSClient cosClient = initClient();
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(BUCKET_NAME);
        ArrayList<DeleteObjectsRequest.KeyVersion> keyList = new ArrayList<>();
        oldUrls.forEach(url -> {
            try {
                String key = URLDecoder.decode(url.substring(("https://" + BUCKET_NAME + ".cos." + REGION_NAME + ".myqcloud.com/").length()), Charset.defaultCharset().displayName());
                boolean objectExists = cosClient.doesObjectExist(BUCKET_NAME, key);
                if(objectExists) {
                    keyList.add(new DeleteObjectsRequest.KeyVersion(key));
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        deleteObjectsRequest.setKeys(keyList);
        try {
            DeleteObjectsResult deleteObjectsResult = cosClient.deleteObjects(deleteObjectsRequest);
            List<DeleteObjectsResult.DeletedObject> deleteObjectResultArray = deleteObjectsResult.getDeletedObjects();
        } catch (MultiObjectDeleteException mde) {
            // 如果部分删除成功部分失败, 返回 MultiObjectDeleteException
            List<DeleteObjectsResult.DeletedObject> deleteObjects = mde.getDeletedObjects();
            List<MultiObjectDeleteException.DeleteError> deleteErrors = mde.getErrors();
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
        // 确认本进程不再使用 cosClient 实例之后，关闭即可
        cosClient.shutdown();
        return "删除成功";
    }
}
