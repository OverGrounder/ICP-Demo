package com.example.tommy.demoapp10;

import java.io.Serializable;

// TODO: Add User's Bitmap (프로필 사진) -> 최소 seller는 필요.

public class User implements Serializable {
    private String name, nickname, address;
    private Boolean isSeller;

    User() {}
    User(String name_, String nickname_, String address_, Boolean isSeller_) {
        name = name_;
        nickname = nickname_;
        address = address_;
        isSeller = isSeller_;
    }

    public void setNAME(String name_) { name = name_; }
    public void setNICKNAME(String nickname_) { nickname = nickname_; }
    public void setADDRESS(String address_) { address = address_; }
    public void setISSELLER(boolean isSeller_) { isSeller = isSeller_; }

    public String getNAME() { return name; }
    public String getNICKNAME() { return nickname; }
    public String getADDRESS() { return address; }
    public Boolean getISSELLER() { return isSeller; }
}
