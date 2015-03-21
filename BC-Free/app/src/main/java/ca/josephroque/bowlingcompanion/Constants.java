package ca.josephroque.bowlingcompanion;

/**
 * Created by josephroque on 15-03-13.
 * <p/>
 * Location ca.josephroque.bowlingcompanion
 * in project Bowling Companion
 */
public class Constants
{
    // PREFERENCES
    /** Identifier for SharedPreferences of app */
    public static final String PREFS = "ca.josephroque.bowlingcompanion";
    /** Identifier for most recently selected bowler id, stored in preferences */
    public static final String PREF_RECENT_BOWLER_ID = "RBI";
    /** Identifier for most recently selected league id, stored in preferences */
    public static final String PREF_RECENT_LEAGUE_ID = "RLI";
    /** Identifier for custom set bowler id, stored in preferences */
    public static final String PREF_QUICK_BOWLER_ID = "QBI";
    /** Identifier for custom set league id, stored in preferences */
    public static final String PREF_QUICK_LEAGUE_ID = "QLI";
    /** Identifier for set theme color, stored in preferences */
    public static final String PREF_THEME_COLORS = "TC";
    /** Identifier for set theme variation, stored in preferences */
    public static final String PREF_THEME_LIGHT = "TL";

    // EXTRAS
    /** Identifies the name of a bowler as an extra */
    public static final String EXTRA_NAME_BOWLER = "EBN";
    /** Identifies the name of a league as an extra */
    public static final String EXTRA_NAME_LEAGUE = "ENL";
    /** Identifies the name of a series as an extra */
    public static final String EXTRA_NAME_SERIES = "ENS";
    /** Identifies the id of a bowler as an extra */
    public static final String EXTRA_ID_BOWLER = "EIB";
    /** Identifies the id of a league as an extra */
    public static final String EXTRA_ID_LEAGUE = "EIL";
    /** Identifies the id of a series as an extra */
    public static final String EXTRA_ID_SERIES = "EIS";
    /** Identifies state of event mode as an extra */
    public static final String EXTRA_EVENT_MODE = "EEM";
    /** Identifies the number of games as an extra */
    public static final String EXTRA_NUMBER_OF_GAMES = "ENOG";
    /** Identifies an array of ids of games */
    public static final String EXTRA_ARRAY_GAME_IDS = "EAGI";
    /** Identifies an array of ids of frames */
    public static final String EXTRA_ARRAY_FRAME_IDS = "EAFI";
    /** Identifies an array of booleans to indicate if games are locked */
    public static final String EXTRA_ARRAY_GAME_LOCKED = "EAGL";
    /** Identifies an array of booleans to indicate if games have manual scores */
    public static final String EXTRA_ARRAY_MANUAL_SCORE_SET = "EAMSS";
    /** Identifies a boolean which indicates  if the game settings are open */
    public static final String EXTRA_SETTINGS_OPEN = "ESO";
    /** Identifies a boolean which indicates if the game settings are disabled */
    public static final String EXTRA_SETTINGS_DISABLED = "ESD";

    // REGULAR EXPRESSIONS
    /** Regular Expression to match regular names */
    public static final String REGEX_NAME = "^[A-Za-z]+[ A-Za-z]*[A-Za-z]*$";
    /** Regular Expression to match regular names with numbers */
    public static final String REGEX_LEAGUE_EVENT_NAME = "^[A-Za-z0-9]+[ A-Za-z0-9]*[A-Za-z0-9]*$";

    // GAMES
    /** Number of frames in a game */
    public static final byte NUMBER_OF_FRAMES = 10;
    /** Last frame in a game */
    public static final byte LAST_FRAME = 9;
    /** Maximum number of games in an event */
    public static final byte MAX_NUMBER_EVENT_GAMES = 20;
    /** Maximum number of games in a league */
    public static final byte MAX_NUMBER_LEAGUE_GAMES = 5;

    //SCORING VALUES
    /** Symbol representing a strike */
    public static final String BALL_STRIKE = "X";
    /** Symbol representing a spare */
    public static final String BALL_SPARE = "/";
    /** Symbol representing a 'left' */
    public static final String BALL_LEFT = "L";
    /** Symbol representing a 'right' */
    public static final String BALL_RIGHT = "R";
    /** Symbol representing an 'ace' */
    public static final String BALL_ACE = "A";
    /** Symbol representing a 'chop off' */
    public static final String BALL_CHOP_OFF = "C/O";
    /** Symbol representing a 'split' */
    public static final String BALL_SPLIT = "Sp";
    /** Symbol representing a 'head pin' */
    public static final String BALL_HEAD_PIN = "Hp";
    /** Symbol representing a 'head pin + 2 pin' */
    public static final String BALL_HEAD_PIN_2 = "H2";
    /** Symbol representing an empty frame */
    public static final String BALL_EMPTY = "-";
    /** Array representing the state of all pins as being knocked down */
    public static final boolean[] FRAME_PINS_DOWN = {true, true, true, true, true};

    // NAMES
    /** Maximum length of a regular name */
    public static final byte NAME_MAX_LENGTH = 30;
    /** Name of a default league available to every bowler */
    public static final String NAME_OPEN_LEAGUE = "Open";

    // FRAGMENTS
    /** Tag to identify instances of BowlerFragment */
    public static final String FRAGMENT_BOWLERS = "BowlerFragment";
    /** Tag to identify instances of LeagueEventFragment */
    public static final String FRAGMENT_LEAGUES = "LeagueEventFragment";
    /** Tag to identify instances of SeriesFragment */
    public static final String FRAGMENT_SERIES = "SeriesFragment";
}