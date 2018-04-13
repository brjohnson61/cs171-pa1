import java.io.*;
import java.net.*;


class Request implements Serializable {
    private String requestType;
    private Integer numTickets; 
    private String originIP;
    private Integer originPort;
    private Boolean hasRedirected;
     
    public Request(String type, Integer num){
        this.numTickets = num;
        this.requestType = type;
    }
    
    public String getRequestType(){
        return requestType;
    }

    public void setRequestType(String type){
        this.requestType = type;
    }
    
    public Integer getNumTickets(){
        return numTickets;
    }

    public void setNumTickets(Integer num){
        this.numTickets = num;
    }

    public Integer getOriginalPort(){
        return originPort;
    }

    public void setOriginalPort(Integer originPort){
        this.originPort = originPort;
    }

    public String getOriginalIP(){
        return originIP;
    }

    public void setOriginalIP(String originIP){
        this.originIP = originIP;
    }

    public Boolean getHasRedirected(){
        return hasRedirected;
    }

    public void setHasRedirected(Boolean hasRedirected){
        this.hasRedirected = hasRedirected;
    }


}