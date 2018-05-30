package com.example.zioerjens.fitbet;

public class Land {
    String landName;
    String landId;
    String fifaCode;
    String flag;
    String emoji;
    String emojiString;
    String iso2;

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
}
