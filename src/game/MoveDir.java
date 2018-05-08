package game;

public enum  MoveDir {
    HORIZONTAL(-1), VERTICAL(1);

    final int dir;
    MoveDir(int i) {
        this.dir = i ;
    }
}
