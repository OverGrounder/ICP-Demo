package com.example.tommy.demoapp10;

public class Session {
    private int port_NUM;
    private String chat_NAME;
    private String stream_NAME;
    private String host_ADDRESS;
    private String application_NAME;

    public Session(){};
    public Session (int port_num, String chat_name, String stream_name, String host_address,
                    String application_name) {

        this.application_NAME = application_name;
        this.chat_NAME = chat_name;
        this.host_ADDRESS = host_address;
        this.port_NUM = port_num;
        this.stream_NAME = stream_name;

    }

    public void setPort_NUM(int port_NUM){this.port_NUM=port_NUM;}
    public void setChat_NAME(String chat_NAME){this.chat_NAME=chat_NAME;}
    public void setHost_ADDRESS(String host_ADDRESS){this.host_ADDRESS=host_ADDRESS;}
    public void setStream_NAME(String stream_NAME){this.stream_NAME=stream_NAME;}
    public void setApplication_NAME(String application_NAME) { this.application_NAME = application_NAME;}

    public int getPORT_NUM() {
        return port_NUM;
    }
    public String getSTREAM_NAME() {
        return stream_NAME;
    }
    public String getAPPLICATION_NAME() {
        return application_NAME;
    }
    public String getHOST_ADDRESS() {
        return host_ADDRESS;
    }
    public String getCHAT_NAME() {
        return chat_NAME;
    }
}
