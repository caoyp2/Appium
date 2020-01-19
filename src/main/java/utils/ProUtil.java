package utils;


import java.io.*;
import java.util.Properties;

public class ProUtil {
    public String filePath;
    public Properties properties;

    public ProUtil(String filePath){
        this.filePath = filePath;
        this.properties = readProperties();
    }

    //读取配置文件
    public Properties readProperties(){
        Properties properties = new Properties();
        try {
            InputStream is = new FileInputStream(this.filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
            properties.load(br);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    //获取指定key的值
    public String getValueBykey(String key){
        String value = "";
        if(this.properties.containsKey(key)){
            value = this.properties.getProperty(key);
        }
        return value;
    }

    public void setKeyValue(String key,String value) {
        if(this.properties == null){
            this.properties = new Properties();
        }
        try {
            OutputStream os = new FileOutputStream(this.filePath);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,"utf-8"));
            this.properties.setProperty(key,value);
            this.properties.store(bw,key + "=" + value);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        ProUtil proUtil = new ProUtil("test.properties");
        System.out.println(proUtil.properties.getProperty("username"));
        proUtil.setKeyValue("age","26");
        System.out.println(proUtil.properties.getProperty("age"));

    }
}
