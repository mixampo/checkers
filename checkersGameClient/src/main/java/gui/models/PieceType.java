package gui.models;

public enum PieceType {
    RED(1), BLUE(-1);

    final int moveDir;

    public int getMoveDir() {
        return moveDir;
    }

    PieceType(int moveDir) {
        this.moveDir = moveDir;
    }
}
