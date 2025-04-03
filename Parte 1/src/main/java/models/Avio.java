package models;

import java.util.HashMap;

public class Avio {
    String id;
    String model;
    String companyia;

    public Avio(String id, String model, String companyia) {
        this.id = id;
        this.model = model;
        this.companyia = companyia;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getCompanyia() {
        return companyia;
    }
    public void setCompanyia(String companyia) {
        this.companyia = companyia;
    }
}
