package communication;

public class MessageTypes {
    public static final String REGISTER = "REGISTER_PLAYER";
    public static final String REGISTERED = "REGISTERED_PLAYER";
    public static final String REGISTERED_OPPONENT = "REGISTERED_OPPONENT";
    public static final String ERROR = "ERROR";
    public static final String PLACE_PIECE_PLAYER = "PLACE_PIECE_PLAYER";
    public static final String PLACE_PIECE_OPPONENT = "PLACE_PIECE_OPPONENT";
    public static final String MOVE_PIECE_PLAYER = "MOVE_PIECE_PLAYER";
    public static final String MOVE_PIECE_OPPONENT = "MOVE_PIECE_OPPONENT";
    public static final String HIT_PIECE_PLAYER = "HIT_PIECE_PLAYER";
    public static final String HIT_PIECE_OPPONENT = "HIT_PIECE_OPPONENT";

    public static final String SET_PLAYER_TURN = "SET_PLAYER_TURN";
    public static final String NOTIFY_READY = "NOTIFY_READY";
    public static final String NOTIFY_START = "NOTIFY_START";
    public static final String SHOW_WINNER = "SHOW_WINNER";
}
