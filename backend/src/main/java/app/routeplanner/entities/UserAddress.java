package app.routeplanner.entities;

import lombok.Data;

@Data
public class UserAddress {
    private String address;
    private int type;

    public UserAddress(){

    }

    public UserAddress(String address, int type){
        this.address = address;
        this.type = type;
    }
}
