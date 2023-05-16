package me.friedhof.werewolfs.Organization;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;

public abstract class ScoreboardBuilder {

    protected final Scoreboard scoreboard;
    protected final Objective objective;

    protected final Player player;

    protected final  ArrayList<String> alive;

    public ScoreboardBuilder(Player player,String displayName, ArrayList<String> alive) {
        this.player = player;
        this.alive = alive;
        if(player.getScoreboard().equals(Bukkit.getScoreboardManager().getMainScoreboard())){
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
        this.scoreboard = player.getScoreboard();

        if(this.scoreboard.getObjective("display") != null){

            this.scoreboard.getObjective("display").unregister();
        }
        this.objective = this.scoreboard.registerNewObjective("display", "dummy", displayName);
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        createScoreboard();
    }

    public void setDisplayName(String displayName){
        this.objective.setDisplayName(displayName);
    }

    public void setScore(String content, int score){
        this.objective.getScore(content).setScore(score);
    }

    public void removeScore(String content){
        this.scoreboard.resetScores(content);
    }

    public abstract void createScoreboard();






}
