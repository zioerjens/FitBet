package com.example.zioerjens.fitbet;

import android.widget.ArrayAdapter;

public class Land {
    private String landName;
    private String landId;
    private String fifaCode;
    private String flag;
    private String emoji;
    private String emojiString;
    private String iso2;




    public Land(String landName, String landId, String fifaCode, String flag, String emoji, String emojiString, String iso2) {
        this.landName = landName;
        this.landId = landId;
        this.fifaCode = fifaCode;
        this.flag = flag;
        this.emoji = emoji;
        this.emojiString = emojiString;
        this.iso2 = iso2;


    }

    @Override
    public String toString() {
        return emojiString+" "+landName;
    }

    public String getLandName() {
        return landName;
    }

    public String getLandId() {
        return landId;
    }

    public String getFifaCode() {
        return fifaCode;
    }

    public String getFlag() {
        return flag;
    }

    public String getEmoji() {
        return emoji;
    }

    public String getEmojiString() {
        return emojiString;
    }

    public String getIso2() {
        return iso2;
    }


}
