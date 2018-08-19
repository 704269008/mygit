import com.jns.utils.CosDemo;
import com.qcloud.cos.COSClient;

import java.io.File;
import java.util.List;

public class cosTest {
    public static  void main(String[] args){
        COSClient cosClient=CosDemo.getCosClient();
        File file=new File("E:\\报告\\课题2\\图片\\20392337-1_b_0.jpg");
        List<String> list=CosDemo.getKey(cosClient);
        for(String key:list){
System.out.println(key);
String url=CosDemo.getUrl(CosDemo.getCosClient(),key);
System.out.println("url"+url);
        }
    }
}
