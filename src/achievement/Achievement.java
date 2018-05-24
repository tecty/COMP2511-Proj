package achievement;

import save.GameSave;

/***
 * Use save file to decide whether the achieve
 * is achieved.
 */
public interface Achievement {
    public String getName();
    public String getDescription();
    public String getPhotoUrl();
    public boolean isAchieved(GameSave save);
}
