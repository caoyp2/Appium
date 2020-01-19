package utils;

import server.Port;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DosCmd {
    private String osName = System.getProperty("os.name");

    /**
     * 执行cmd命令且获取执行命令后打印日志
     * @param cmd
     * @return
     */
    public List<String> exeCmdConsole(String cmd){
        List<String> list = new ArrayList<String>();
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = null;
            System.out.println(cmd);
            if(osName.toUpperCase().contains("WIN")){
                process = runtime.exec("cmd /c " + cmd);
            }
            InputStream is = process.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while((line = br.readLine()) != null){
                list.add(line);
            }
            //销毁进程
            process.waitFor();
            process.destroy();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 执行不获取打印日志的命令且仅执行命令
     */
    public Boolean exeCmd(String cmdString){
        List<String> list = new ArrayList<String>();
        boolean flag = true;
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = null;
            System.out.println(cmdString);
            if (osName.toUpperCase().contains("WIN")) {
                process = runtime.exec("cmd /c " + cmdString);
            }
            System.out.println("命令：" + cmdString + "执行完成！");
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 执行性完命令后检查端口
     * @param cmdString
     * @return
     */
    public Boolean exeCmd(String cmdString,int portIndex){
        List<String> list = new ArrayList<String>();
        boolean flag = true;
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = null;
            System.out.println(cmdString);
            if (osName.toUpperCase().contains("WIN")) {
                process = runtime.exec("cmd /c " + cmdString);
            }
            //执行完命令后检查端口是否被占用，来判断命令是否执行成功
            int appiumPort = StringUtil.getNumByString(cmdString,portIndex);
            if(appiumPort > 0){
                Port port = new Port();
                int count = 0;
                while(!port.isPortUsed(appiumPort)){
                    //不能一直循环下去
                    if(count > 100){
                        flag = false;
                        break;
                    }
                    Thread.sleep(1000);
                    count ++;
                }
            }
            System.out.println("命令：" + cmdString + "执行完成！");
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}
