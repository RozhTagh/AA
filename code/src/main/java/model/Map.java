package model;

//TODO

public enum Map {
    ONE(1, 600, 400, 300),
    TWO(2, 500, 300, 200),
    THREE(3, 400, 200, 100);


    private final int mapNumber;
    private final int delayLev1;
    private final int delayLev2;
    private final int delayLev3;

    Map(int mapNumber, int delayLev1, int delayLev2, int delayLev3) {
        this.mapNumber = mapNumber;
        this.delayLev1 = delayLev1;
        this.delayLev2 = delayLev2;
        this.delayLev3 = delayLev3;
    }

    public int getMapNumber() {
        return mapNumber;
    }

    public int getDelayLev1() {
        return delayLev1;
    }

    public int getDelayLev2() {
        return delayLev2;
    }

    public int getDelayLev3() {
        return delayLev3;
    }

    public int getDelayByLevel(int level){
        if(level == 1)
            return this.delayLev1;
        else if(level == 2)
            return delayLev2;
        else return delayLev3;
    }
}
