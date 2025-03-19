package com.github.evseevda.swoyotesttask.core.ui.input;

public interface UserInputReader {

    int readInt();
    int readInt(String message);
    String readString();
    String readString(String message);

}
