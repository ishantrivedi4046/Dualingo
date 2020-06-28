package com.example.dualingo;

public class word {

    private String eng;
    private String miwok;
    private static final int id=-1;
    private int res=id;
    private int media;

    public word(String s,String s1,int r)
    {
        this.eng=s;
        this.miwok=s1;
        media=r;
    }

    public word(String s,String s1,int r,int r1)
    {
        this.eng=s;
        this.miwok=s1;
        this.res=r;
        this.media=r1;
    }
    public String getDefaultTranslation()
    {
        return eng;
    }
    public String getMiwoktranslation()
    {
        return miwok;
    }
    public int getImageresource()
    {
        return res;
    }
    public int getMediaResource()
    {
        return media;
    }
    public boolean hasImage()
    {
        return (res!=id);
    }
}
