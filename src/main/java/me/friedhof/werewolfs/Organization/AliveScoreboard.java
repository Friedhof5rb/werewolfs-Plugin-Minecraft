package me.friedhof.werewolfs.Organization;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class AliveScoreboard extends ScoreboardBuilder{




    public AliveScoreboard(Player player, ArrayList<String> alive) {
        super(player, ChatColor.GREEN + "Lebendig",alive);
    }

    @Override
    public void createScoreboard() {
        for(int i = 0; i< super.alive.size();i++){
            setScore(super.alive.get(i),i);
        }

    }


}
