package Model;

import Model.Resources.Resource;

public class TradeRequest {
    private Resource resource;
    private int amount;
    private String message;
    private int price;
    private Government governmentThatRequested;
    private Government governmentThatHasBeenAsked;
    private int id;

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
}
