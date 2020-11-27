package ru.shemich.letovpoem_bot.utils;

import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum Emojis {
    SPARKLES(EmojiParser.parseToUnicode(":sparkles:")),
    SCROLL(EmojiParser.parseToUnicode(":scroll:")),
    MAGE(EmojiParser.parseToUnicode(":mage:")),
    EYE(EmojiParser.parseToUnicode(":eye_in_speech_bubble:"));

    private String emojiName;

    @Override
    public String toString() {
        return emojiName;
    }
}
