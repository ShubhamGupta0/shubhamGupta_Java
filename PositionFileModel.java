
package model;

import com.google.gson.annotations.SerializedName;

public class PositionFileModel {
   

private String instrument;
private int account;
private String accountType;
private int quantity;

public void setInstrument(String instrument){
	this.instrument=instrument;
}
public void setAccount(int account){
	this.account=account;
}
public void setAccountType(String accountType){
	this.accountType=accountType;
}
public void setQuantity(int quantity){
	this.quantity=quantity;
}

public String getInstrument(){
	return this.instrument;
}
public int getAccount(){
	return this.account;
}
public String getAccountType(){
	return this.accountType;
}
public int getQuantity(){
	return this.quantity;
}
}
