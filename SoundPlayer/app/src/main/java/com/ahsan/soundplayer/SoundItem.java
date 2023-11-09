package com.ahsan.soundplayer;

public class SoundItem {

    private int imageSource;

    private String text1;
    private String text2;

    public SoundItem(int imageSource, String text1, String text2) {
        this.imageSource = imageSource;
        this.text1 = text1;
        this.text2 = text2;
    }

    public int getImageSource() {
        return imageSource;
    }

    public String getText1() {
        return text1;
    }

    public String getText2() {
        return text2;
    }
}
