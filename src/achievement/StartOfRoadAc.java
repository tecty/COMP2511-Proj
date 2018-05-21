package achievement;

import save.GameSave;

public class StartOfRoadAc implements  Achievement {
    @Override
    public String getName() {
        return "Start Of the Road";
    }

    @Override
    public String getDescription() {
        return "Finish one puzzle";
    }

    @Override
    public String getPhotoUrl() {
        return ".img";
    }


    @Override
    public boolean isAchieved(GameSave save) {
        // only need to finish one game
        return  save.getLevelCleared()>= 1;
    }
}
