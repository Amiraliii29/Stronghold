package Model;

public class TradeRequest {
    private Resource resource;
    private int amount;
    private String message;
    private int price;
    private Government governmentThatRequested;
    private Government governmentThatHasBeenAsked;
    private int id;
    private String acceptanceMessage;
    private boolean isSeenByTargetUser = false;
    private int isAccepted = 2;
    private boolean isDonate;

    public void setAccepted(int accepted) {
        isAccepted = accepted;
    }

    public int isAccepted() {
        return isAccepted;
    }

    public boolean isSeenByTargetUser() {
        return isSeenByTargetUser;
    }

    public void setSeenByTargetUser(boolean seenByTargetUser) {
        isSeenByTargetUser = seenByTargetUser;
    }

    public TradeRequest(Resource resource , int amount , int price , String message ,
                        Government governmentThatHasBeenAsked , int id , boolean isDonate) {
        this.resource = resource;
        this.amount = amount;
        this.price = price;
        this.message = message;
        this.governmentThatRequested = DataBase.getMyGovernment();
        this.governmentThatHasBeenAsked = governmentThatHasBeenAsked;
        this.id = id;
        this.isDonate = isDonate;
    }

    public boolean isDonate() {
        return isDonate;
    }


    public Resource getResource() {
        return resource;
    }

    public int getAmount() {
        return amount;
    }

    public String getMessage() {
        return message;
    }

    public Government getGovernmentThatRequested() {
        return governmentThatRequested;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public void setAcceptanceMessage(String acceptanceMessage) {
        this.acceptanceMessage = acceptanceMessage;
    }

    public Government getGovernmentThatHasBeenAsked() {
        return governmentThatHasBeenAsked;
    }

    public String getAcceptanceMessage() {
        return acceptanceMessage;
    }
}
