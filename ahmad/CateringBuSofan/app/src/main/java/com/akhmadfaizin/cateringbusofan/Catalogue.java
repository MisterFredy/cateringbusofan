package com.akhmadfaizin.cateringbusofan;

/**
 * Created by root on 4/25/18.
 */

public class Catalogue {
    private String name;
    private int image;

    public Catalogue(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
