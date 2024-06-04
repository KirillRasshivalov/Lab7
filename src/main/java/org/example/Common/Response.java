package org.example.Common;

import java.io.Serializable;

public class Response implements Serializable {
    String answer;

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }
}
