package server;

import utils.DosCmd;

import java.util.ArrayList;
import java.util.List;

public class Port {
    public String osName = System.getProperty("os.name");
    /**
     * 判断端口是否被占用
     */
    public Boolean isPortUsed(int portNum){
        List<String> portDes = new ArrayList<String>();
        boolean flag = true;
        Runtime r = Runtime.getRuntime();
        String command = "";
        //判断是否为win平台
        if(osName.toUpperCase().contains("WIN")){
            command = "netstat -an | findstr " + portNum;
        }else {
            command = "netstat -ano | grep " + portNum;
        }
        DosCmd dosCmd = new DosCmd();
        portDes = dosCmd.exeCmdConsole(command);
        if(portDes.size() > 0){
            System.out.println("端口号：" + portNum + " 已被占用！");
            flag = true;
        }else {
            flag = false;
        }
        return flag;
    }

    /**
     * 生成可用的端口号
     * @param
     */
    public List<Integer>  generatePortList(int total){
       return  generatePortList(5000,total);
    }

    /**
     * 生成可用的端口号
     * @param
     */
    public List<Integer>  generatePortList(int portStart , int total){
        List<Integer> portList = new ArrayList<Integer>();
        //若portStart + total 太大，则直接return
        if((portStart + total) > 60000){
            System.out.println("端口号起始值设置过大");
            return portList;
        }
        while(portList.size() < total){
            //若端口没有被占用
            if(!isPortUsed(portStart)){
                portList.add(portStart);
            }
            //判断一哈起始端口+total的最大值，避免超出范围
            if((portStart + total - portList.size()) > 60000){
                break;
            }
            portStart ++;
        }
        return portList;
    }

    public static void main(String[] args) {
        Port p = new Port();
        System.out.println(p.isPortUsed(1521));
    }
}
