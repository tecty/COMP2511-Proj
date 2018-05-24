package achievement;

import save.GameSave;

public class IWantPerfectAc implements Achievement {
    @Override
    public String getName() {
        return "I Want Perfect";
    }

    @Override
    public String getDescription() {
        return "Solve a puzzle with 3 stars gained.";
    }

    @Override
    public String getPhotoUrl() {
        return ".img";
    }

    @Override
    public boolean isAchieved(GameSave save) {
        // achieve when one of the level is achieved by three star
        for (int level = 0; level < save.getLevelCleared(); level++) {
            if (save.getLevel(level).userStar()==3)
                return true;
        }
        return false;
    }
}
