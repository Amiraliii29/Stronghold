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

    public TradeRequest(Resource resource , int amount , int price , String message ,
                        Government governmentThatHasBeenAsked , int id) {
        this.resource = resource;
        this.amount = amount;
        this.price = price;
        this.message = message;
        this.governmentThatRequested = DataBase.getCurrentGovernment();
        this.governmentThatHasBeenAsked = governmentThatHasBeenAsked;
        this.id = id;
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
