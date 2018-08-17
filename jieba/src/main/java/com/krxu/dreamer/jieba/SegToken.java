package com.krxu.dreamer.jieba;

public class SegToken {
    public String word;

    public String prop;

    public int startOffset;

    public int endOffset;


    public SegToken(String word, int startOffset, int endOffset) {
        this.word = word;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
    }

    public String getWord() {
        return word;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    @Override
    public String toString() {
        return "SegToken{" +
                "word='" + word + '\'' +
                ", prop='" + prop + '\'' +
                ", startOffset=" + startOffset +
                ", endOffset=" + endOffset +
                '}';
    }
}
