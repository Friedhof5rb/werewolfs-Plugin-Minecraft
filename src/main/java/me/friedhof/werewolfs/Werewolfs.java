package me.friedhof.werewolfs;

import me.friedhof.werewolfs.Events.MyListener;
import me.friedhof.werewolfs.Organization.GameManager;
import me.friedhof.werewolfs.commands.GUICommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Werewolfs extends JavaPlugin {
    private static Werewolfs instance;
    private GameManager gameManager;
    @Override
    public void onLoad(){
        instance = this;
    }

    @Override
    public void onEnable() {
        gameManager = new GameManager(this);
        getServer().getPluginManager().registerEvents(new MyListener(), this);

        getCommand("gui").setExecutor(new GUICommand());


        System.out.println("Werwolf-Plugin gestartet!");
    }

    @Override
    public void onDisable() {
        System.out.println("Werwolf-Plugin beendet!");
    }


    public GameManager getGameManager() {
        return gameManager;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public static Werewolfs getInstance(){
        return instance;
    }



}
