package model;

public enum Level {
    ONE(1, 3000, 1.2, 7),
    TWO(2, 2000, 1.5, 5),
    THREE(3, 1500, 1.8, 3);

    private final int levelNumber;
    private final int rotationSpeed;
    private final double windSpeed;
    private final int iceTimer;

    private Level(int levelNumber, int rotationSpeed, double windSpeed, int iceTimer){
        this.levelNumber = levelNumber;
        this.rotationSpeed = rotationSpeed;
        this.windSpeed = windSpeed;
        this.iceTimer = iceTimer;
    }

    public Level getLevelByNumber(int levelNumber){
        for(Level level: Level.values()){
            if(level.levelNumber == levelNumber)
                return level;
        }
        return null;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public int getRotationSpeed() {
        return rotationSpeed;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public int getIceTimer() {
        return iceTimer;
    }
}
