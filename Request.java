import java.io.*;
import java.net.*;


class Request implements Serializable {
    private String requestType;
    private Integer numTickets; 
    private String originIP;
    private Integer originPort;
    private Boolean hasRedirected;
    private Boolean sucessfullyProcessed;
    private Integer remaining;
     
    public Request(Boolean type, Integer num){
        this.numTickets = num;
        if (type){
            this.requestType = "movie";
        }else{
            this.requestType = "play";
        }
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

    public void setSucessfullyProcessed(Boolean setter){
        sucessfullyProcessed = setter;
    }

    public Boolean getSucessfullyProcessed(){
        return sucessfullyProcessed;
    }

    public void setRemaining(Integer remaining){
        this.remaining = remaining;
    }

    public Integer getRemaining(){
        return this.remaining;
    }


}