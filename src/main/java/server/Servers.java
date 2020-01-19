package server;

import utils.DosCmd;

import java.util.ArrayList;
import java.util.List;

public class Servers {

    public List<Integer> AppiumPortList;
    public List<Integer> BootstrapPortList;
    public List<String> deviceList;
    public String path = "D:";
    public  String osName = System.getProperty("os.name");
    /**
     * 获取所有设备信息
     * @return
     */
    public List<String> getDevices(){
        deviceList = new ArrayList<String>();
        DosCmd dosCmd = new DosCmd();
        List<String> list = dosCmd.exeCmdConsole("adb devices");
        if(list.size() > 2){
            //解析console字符
            for(int i = 1; i < list.size() - 1;i++){
                String deviceString = list.get(i).split("\t")[0];
                deviceList.add(deviceString);
            }
        }else {
            System.out.println("当前没有设备连接或设备连接状态不正确");
        }
        return deviceList;
    }

    /**
     * 获取生成的端口list
     * @param portStart
     * @return
     */
    public List<Integer> getPortList(int portStart){
        List<Integer> portList = new ArrayList<Integer>();
        List<String> deviceList = getDevices();
        //没有设备连接
        int total = deviceList.size();
        if( total > 0){
            Port p = new Port();
            portList = p.generatePortList(portStart,total);
        }
        return portList;
    }

    /**
     * 生成启动服务端的命令
     * @param
     */
    public List<String> generateServerCommand(){
        List<String> commandList = new ArrayList<String>();
        AppiumPortList = getPortList(10000);
        BootstrapPortList = getPortList(20000);
        for(int i = 0; i < AppiumPortList.size(); i++ ){
            String serverCommand = "appium -p " + AppiumPortList.get(i) + " -bp " + BootstrapPortList.get(i) +
                    " -U " + deviceList.get(i) + " > " + path + "/logs/" + deviceList.get(i).split(":")[0] + ".log";
            System.out.println(serverCommand);
            commandList.add(serverCommand);
        }
        return commandList;
    }

    /**
     * 启动所有服务
     */
    public Boolean startServers(){
        List<String> commandlist = generateServerCommand();
        DosCmd dosCmd = new DosCmd();
        boolean flag = false;
        if(commandlist.size() > 0){
            for(String command : commandlist){
                dosCmd.exeCmd(command,0);
            }
            flag = true;
        }
        return flag;
    }

    /**
     * 清理启动的appium服务
     * @param
     */
    public Boolean killServer(){
        String command = "taskkill -F -PID node.exe";
        if (osName.toUpperCase().contains("WIN")){
            command = "taskkill -F -PID node.exe";
        }else {
            command = "taskkill -F -PID node.exe";
        }
        DosCmd dosCmd = new DosCmd();
        if(dosCmd.exeCmd(command)){
            return true;
        }else {
            return false;
        }
    }

    public static void main(String[] args) {
        Servers servers = new Servers();
//        servers.startServers();
        servers.killServer();
    }
}
