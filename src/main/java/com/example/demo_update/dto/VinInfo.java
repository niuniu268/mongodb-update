package com.example.demo_update.dto;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("table1")
public class VinInfo {
    @Id
    private String id;
    private String vin;
    private String field1;
    private String field2;
    private String field3;
    private String field4;


    public VinInfo () {
    }

    public VinInfo (String id, String field1, String field2, String field3, String field4) {
        this.id = id;
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
    }

    public VinInfo (String id, String vin, String field1, String field2, String field3, String field4) {
        this.id = id;
        this.vin = vin;
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
    }


    public String getId () {
        return id;
    }


    public void setId (String id) {
        this.id = id;
    }


    public String getField1 () {
        return field1;
    }


    public void setField1 (String field1) {
        this.field1 = field1;
    }


    public String getField2 () {
        return field2;
    }


    public void setField2 (String field2) {
        this.field2 = field2;
    }


    public String getField3 () {
        return field3;
    }


    public void setField3 (String field3) {
        this.field3 = field3;
    }


    public String getField4 () {
        return field4;
    }


    public void setField4 (String field4) {
        this.field4 = field4;
    }

    public String toString () {
        return "VinInfo{id = " + id + ", field1 = " + field1 + ", field2 = " + field2 + ", field3 = " + field3 + ", field4 = " + field4 + "}";
    }


    public String getVin () {
        return vin;
    }


    public void setVin (String vin) {
        this.vin = vin;
    }
}
