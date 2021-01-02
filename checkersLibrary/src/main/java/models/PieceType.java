package models;

public enum PieceType {
    RED(1), WHITE(-1);

    final int moveDir;

    public int getMoveDir() {
        return moveDir;
    }

    PieceType(int moveDir) {
        this.moveDir = moveDir;
    }
}
