package main;

public class GameManager {
    public enum PhaseCategory { MAP, BATTLE, OTHER }
    public enum GamePhase {
        WAITING(PhaseCategory.MAP), MAP_MOVE(PhaseCategory.MAP),
        BATTLE_START(PhaseCategory.BATTLE), BATTLE_COMMAND(PhaseCategory.BATTLE),
        SELECT_SKILL(PhaseCategory.BATTLE), SELECT_ITEM(PhaseCategory.BATTLE),
        SELECT_ENEMY(PhaseCategory.BATTLE), SHOWING_MESSAGES_BATTLE(PhaseCategory.OTHER),
        SHOWN_MESSAGES_BATTLE(PhaseCategory.OTHER);
        
        public final PhaseCategory category;
        GamePhase(PhaseCategory c) { this.category = c; }
    }

    private GamePhase currentPhase = GamePhase.MAP_MOVE;

    public GamePhase getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(GamePhase phase) {
        this.currentPhase = phase;
    }

    public boolean isInBattle() {
        return currentPhase.category == PhaseCategory.BATTLE || currentPhase.category == PhaseCategory.OTHER;
    }
}