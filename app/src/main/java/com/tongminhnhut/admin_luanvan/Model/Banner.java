package com.tongminhnhut.admin_luanvan.Model;

public class Banner {
    private String Name;
    private String Image ;
    private String ID;

    public Banner(String name, String image, String ID) {
        Name = name;
        Image = image;
        this.ID = ID;
    }

    public Banner() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
