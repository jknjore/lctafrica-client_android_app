package com.lctapp.lct.Classes;

public class Variables {
    private static String dependantNumber;
    private static int dependantChecker = 0;
    private static int imageStatus = 0;

    public Variables() {
    }

    public static String getDependantNumber() {
        return dependantNumber;
    }

    public static void setDependantNumber(String dependantNumber) {
        Variables.dependantNumber = dependantNumber;
    }

    public static int getDependantChecker() {
        return dependantChecker;
    }

    public static void setDependantChecker(int dependantChecker) {
        Variables.dependantChecker = dependantChecker;
    }

    public static int getImageStatus() {
        return imageStatus;
    }

    public static void setImageStatus(int imageStatus) {
        Variables.imageStatus = imageStatus;
    }
}

