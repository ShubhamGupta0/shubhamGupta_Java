package model;

import com.google.gson.annotations.SerializedName;
import org.codehaus.jackson.annotate.JsonProperty;

public class TransactionModel {
    @SerializedName(value = "transactionId", alternate = {"TransactionId"})
    private int transactionId;
    @SerializedName(value = "instrument", alternate = {"Instrument"})
    private String instrument;
    @SerializedName(value = "transactionType", alternate = {"TransactionType"})
    private String transactionType;
    @SerializedName(value = "transactionQuantity", alternate = {"TransactionQuantity"})
    private int transactionQuantity;


    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public int getTransactionQuantity() {
        return transactionQuantity;
    }

    public void setTransactionQuantity(int transactionQuantity) {
        this.transactionQuantity = transactionQuantity;
    }
}
