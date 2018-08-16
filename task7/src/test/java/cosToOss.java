import com.aliyun.oss.OSSClient;
import com.jns.utils.CosDemo;
import com.jns.utils.OssDemo;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.COSObjectInputStream;
import com.qcloud.cos.model.GetObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class cosToOss {
    public static void main(String[] args) throws IOException {
        COSClient cosClient=CosDemo.getCosClient();
        OSSClient ossClient=OssDemo.getOssClient();
        //获取列表中文件名
        List<String> list=CosDemo.getKey();
        COSObjectInputStream cosObjectInputStream;
        InputStream inputStream;
        for(String str:list){
            //通过GetObjectRequest获取Object
            GetObjectRequest getObjectRequest=new GetObjectRequest("does-1257257036",str);
            COSObject cosObject=cosClient.getObject(getObjectRequest);
            //获取输入流包含该对象的内容。
            cosObjectInputStream=cosObject.getObjectContent();
            inputStream=cosObjectInputStream;
            //上传文件流
            OssDemo.updInputStream(inputStream,str);
            inputStream.close();
        }
        cosClient.shutdown();
        OssDemo.closeOosClient(ossClient);
    }
}
