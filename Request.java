

class Request implements Serializable{
    private String requestType;
    private int numTickets; 
    private String originIP;
    private int originPort;
    private bool hasRedirected;
     
    public Request(String type, int num){
        this.numTickets = num;
        this.requestType = type;
    }
    
    public String getRequestType(){
        return requestType;
    }

    public void setRequestType(String type){
        this.requestType = type
    }
    
    public int getNumTickets(){
        return numTickets;
    }

    public void setNumTickets(int num){
        this.numTickets = num
    }

    public String getOriginPort(){
        return originPort;
    }

    public void setOriginPort(int originPort){
        this.originPort = originPort
    }

    public String getOriginIP(){
        return originIP;
    }

    public void setOriginIP(String originIP){
        this.originIP = originIP;
    }

    public bool getHasRedirected(){
        return hasRedirected;
    }

    public void setHasRedirected(bool hasRedirected){
        this.hasRedirected = hasRedirected
    }


}