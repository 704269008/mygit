package picture;

import com.jns.utils.OssDemo;

import java.io.File;

public class uploadTest {
    public static void main(String args[]){
        OssDemo ossDemo=new OssDemo();
        //bucket名    自定义上传文件的名字，   文件在本地的具体路径，\ 用 \\代替
        ossDemo.upload("does", "1.png", new File("D:\\kjk.png"));
    }
}
