package com.example.hp.livewallpaper.model;

import java.util.Locale;

public class CatagoryItem {
    public String name;
    public String imagelink;
    CatagoryItem ()
    {

    }

    public CatagoryItem(String name,String imagelink)
    {
        this.name=name;
        this.imagelink=imagelink;

    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name=name;
    }
    public String getImagelink()
    {

        return imagelink;
    }
    public void setImagelink(String imagelink)
    {
        this.imagelink=imagelink;

    }

}
