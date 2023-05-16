package me.friedhof.werewolfs.Organization;

import com.sun.tools.javac.code.Attribute;
import me.friedhof.werewolfs.Werewolfs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameManager {



    ArrayList<String> inGame = new ArrayList<String>();


    ArrayList<String> alive = new ArrayList<String>();
    ArrayList<String> isGameMaster = new ArrayList<String>();
    ArrayList<String> canSeeEveryone = new ArrayList<String>();
    ArrayList<String> HuntersThatCanRespawn = new ArrayList<String>();
    HashMap<String,String> votedFor = new HashMap<String,String>();

    HashMap<String,ArrayList<String>> voteResults = new HashMap<String, ArrayList<String>>();




    HashMap<String,String> wwvotedFor = new HashMap<String,String>();

    HashMap<String,ArrayList<String>> wwvoteResults = new HashMap<String, ArrayList<String>>();




    HashMap<String,String> Roles = new HashMap<String,String>();

    HashMap<String,String> accused = new HashMap<String,String>();

    public boolean poolMade = false;



    public boolean anyoneAtAllCanVote = false;
    public boolean anyoneAtAllCanAccuse= false;


    public boolean accusedContains(String name){

        for( String key : accused.keySet()){
            if(accused.get(key).equals(name)){
                return true;
            }
        }
        return false;


    }



    public void voteResult(){

        for(String s : votedFor.keySet()){

            String value = votedFor.get(s);

            if(value != null){
                   voteResults.put(value,new ArrayList<String>());
            }

        }

        for(String s : voteResults.keySet()) {
            for(String s2 : votedFor.keySet()) {

                if(votedFor.get(s2).equals(s)) {
                    voteResults.get(s).add(s2);
                }
            }

        }
        int biggestNumber = 0;
        String votedOut = "";
        for(String s : voteResults.keySet()){
            if(voteResults.get(s).size() > biggestNumber){
                biggestNumber = voteResults.get(s).size();
                votedOut = s;
            }
        }
        boolean once = false;
        boolean twice = false;
        for(String s : voteResults.keySet()) {
            if(voteResults.get(s).size() == biggestNumber){
                if(!once){
                    once = true;
                    continue;
                }else{
                    twice = true;
                    break;
                }
            }
        }

        int delay = 0;
        for(String s : votedFor.keySet()) {
            executeCommand("say "+ s + " hat für " + votedFor.get(s) + " gevotet.", delay);
            delay += 10;
        }
        executeCommand("say ---------------------------------------------------------------------------------------------", delay);
        delay += 10;
        for(String s : voteResults.keySet()) {
            executeCommand("say Für "+ s + " haben " + voteResults.get(s).size() + " Personen gevotet.", delay);
            delay += 10;
        }



        if(!twice){

            //TODO killcode?
            setDead(votedOut);
            executeCommand("say " + votedOut + " ist raus.",delay);
        }



    }


    public void wwvoteResult() {

        int wwcount = 0;
        for(String s : Roles.keySet()){
            if(Roles.get(s).equalsIgnoreCase("Werwolf")){
                wwcount++;
            }
        }

       int wwvotedcount = 0;
        for(String s: wwvotedFor.keySet()){
            if(wwvotedFor.get(s) != null){
                wwvotedcount++;
            }
        }

        if(wwvotedcount >= wwcount){

            for(String s : wwvotedFor.keySet()){

                String value = wwvotedFor.get(s);

                if(value != null){
                    wwvoteResults.put(value,new ArrayList<String>());
                }

            }

            for(String s : wwvoteResults.keySet()) {
                for(String s2 : wwvotedFor.keySet()) {

                    if(wwvotedFor.get(s2).equals(s)) {
                        wwvoteResults.get(s).add(s2);
                    }
                }

            }
            int biggestNumber = 0;
            String votedOut = "";
            for(String s : wwvoteResults.keySet()){
                if(wwvoteResults.get(s).size() > biggestNumber){
                    biggestNumber = wwvoteResults.get(s).size();
                    votedOut = s;
                }
            }
            boolean once = false;
            boolean twice = false;
            for(String s : wwvoteResults.keySet()) {
                if(wwvoteResults.get(s).size() == biggestNumber){
                    if(!once){
                        once = true;
                        continue;
                    }else{
                        twice = true;
                        break;
                    }
                }
            }
            int delay = 0;
            for(String s : votedFor.keySet()) {
                for(String s2 : Roles.keySet()){
                    if(Roles.get(s2).equalsIgnoreCase("Werwolf")){
                       Bukkit.getPlayer(s2).sendMessage(s + " hat für " + votedFor.get(s) + " gevotet.");
                    }
                }

                delay += 10;
            }
            for(String s2 : Roles.keySet()){
                if(Roles.get(s2).equalsIgnoreCase("Werwolf")){
                    Bukkit.getPlayer(s2).sendMessage("---------------------------------------------------------------------------------------------");
                }
            }

            delay += 10;
            for(String s : voteResults.keySet()) {

                for(String s2 : Roles.keySet()){
                    if(Roles.get(s2).equalsIgnoreCase("Werwolf")){
                        Bukkit.getPlayer(s2).sendMessage("Für "+ s + " haben " + voteResults.get(s).size() + " Personen gevotet.");
                    }
                }
                delay += 10;
            }


            for(String s2 : Roles.keySet()){
                if(Roles.get(s2).equalsIgnoreCase("Werwolf")){
                    executeCommand("clear " + s2 ,10);
                }
            }


            if(!twice){

                for(String s2 : Roles.keySet()){
                    if(Roles.get(s2).equalsIgnoreCase("Werwolf")){
                        executeCommand("give "+ s2+ " minecraft:wooden_sword{display:{Name:'{\"text\":\"KillerSchwert\"}'}}",20);
                    }
                }

            }





        }





    }



    //Rollen: dorfi, ww, jäger, hexe, seher

    //TODO voting at day and at night
    //TODO witch, hunter, seer, wws


    boolean isDay = true;



    boolean isGameRunning = false;
    Werewolfs plugin;
    ConsoleCommandSender console;
    public GameManager(Werewolfs plugin) {
        this.plugin = plugin;
        this.console = Bukkit.getServer().getConsoleSender();
    }

    public void addToGame(String player){
        if(!inGame.contains(player)) {
            inGame.add(player);
        }
    }
    public void removeFromGame(String player){
        inGame.remove(player);
    }

    public ArrayList<String> returnParticipants(){
        return inGame;
    }




    public void makePool(String[] pool){

      ArrayList<String> leftInPool = new ArrayList<>();
        for(String s : pool){
            leftInPool.add(s);
        }

        ArrayList<String> noRole = new ArrayList<String>();
        for(String s : inGame){
            noRole.add(s);
        }

        while (!noRole.isEmpty()) {
            Random random = new Random();
            int upperbound = noRole.size();
            int int_random = random.nextInt(upperbound);
            String player = noRole.get(int_random);
            String Role = leftInPool.get(0);

            if(Role.equalsIgnoreCase("Werwolf")){
               Roles.put(player, "Werwolf");
            }
            if(Role.equalsIgnoreCase("Dorfbewohner")){
                Roles.put(player, "Dorfbewohner");
            }
            if(Role.equalsIgnoreCase("Hexe")){
                Roles.put(player, "Hexe");
            }
            if(Role.equalsIgnoreCase("Seher")){
                Roles.put(player, "Seher");
            }
            if(Role.equalsIgnoreCase("Jäger")){
                Roles.put(player, "Jäger");
            }
            alive.add(player);
            noRole.remove(int_random);
            leftInPool.remove(0);

        }
        actualizeAliveList();
        poolMade = true;
    }




    public void StartGame(){

        Bukkit.dispatchCommand(console, "say Lade...");
        skipNight();
        executeCommand("gamerule doImmediateRespawn true",20);
        executeCommand("gamerule doDaylightCycle false",30);
        executeCommand("difficulty peaceful",40);
        executeCommand("gamerule fallDamage false",50);
        executeCommand("gamerule fireDamage false",60);
        executeCommand("gamerule fallDamage false",70);
        executeCommand("gamerule drowningDamage false",80);
        executeCommand("gamerule freezeDamage false",90);
        executeCommand("effect give @a glowing infinite 0 true",100);

        for(String s : inGame){
            executeCommand(  "gamemode survival "+ s,110);
        }

        executeCommand(  "say Game startet!",120);





        isGameRunning = true;
    }

    public void changeTime(){
        if(isDay){
            skipDay();

        }else{
            skipNight();
        }
    }




    public void skipDay(){
        isDay = false;
        for(Player p1 : Bukkit.getOnlinePlayers()){
            if(!canSeeEveryone.contains(p1.getName()) && !isGameMaster.contains(p1.getName())) {
                for (Player p2 : Bukkit.getOnlinePlayers()) {
                    if(Roles.get(p1.getDisplayName()).equals("Werwolf") && Roles.get(p2.getDisplayName()).equals("Werwolf"))
                    {
                        break;
                    }
                    p1.hidePlayer(plugin, p2);
                }
            }
        }
        executeCommand("time set 18000",10);

        giveCustomRoleItems();

    }

    public void giveCustomRoleItems(){
        int delay = 5;
        for(String s : inGame){
            executeCommand("clear " + s,delay);
            delay += 5;
        }

        new BukkitRunnable(){
            @Override
            public void run() {

                for(String s : alive){

                    if(Roles.get(s).equals("Werwolf")){

                        executeCommand("give "+ s+ " minecraft:bone{display:{Name:'{\"text\":\"Todesabstimmung\"}'}}",0);
                    }
                    if(Roles.get(s).equals("Hexe")){

                        executeCommand("give "+ s+ " minecraft:splash_potion{display:{Name:'{\"text\":\"Zaubertrank\"}'}}",0);
                    }
                    if(Roles.get(s).equals("Seher")){
                        executeCommand("give "+ s+ " minecraft:ender_eye{display:{Name:'{\"text\":\"Kristallkugel\"}'}}",0);

                    }
                    if(Roles.get(s).equals("Jäger")){
                        executeCommand("give "+ s+ " minecraft:crossbow{display:{Name:'{\"text\":\"Gewehr\"}'}}",0);

                    }
                }

            }
        }.runTaskLater(Werewolfs.getInstance(),delay);

    }

    public void giveDorfiItem(){
        int delay = 10;
        for(String s : inGame) {
            executeCommand("give "+ s+ " minecraft:paper{display:{Name:'{\"text\":\"Demokratie\"}'}}", delay);
            delay += 5;
        }

    }




    public void skipNight(){
        isDay = true;

        int delay = 5;
        for(String s : inGame){
            executeCommand("clear " + s,delay);
            delay += 5;
        }

        new BukkitRunnable(){
            @Override
            public void run() {
                for(Player p1 : Bukkit.getOnlinePlayers()){
                    for (Player p2 : Bukkit.getOnlinePlayers()) {
                        p1.showPlayer(plugin,p2);
                    }
                }
                if(isGameRunning) {
                    giveDorfiItem();
                }
            }
        }.runTaskLater(Werewolfs.getInstance(),delay);


        executeCommand("time set 6000",10);
        actualizeAliveList();
    }


    public void actualizeAliveList(){

        for(Player p: Bukkit.getOnlinePlayers()) {
            new AliveScoreboard(p,alive);
        }

    }





    public void EndGame(){
        isGameRunning = false;
        skipNight();
        executeCommand("effect clear @a glowing",20);
        int delay = 30;
        for(String s : inGame){
            executeCommand(  "gamemode survival "+ s,delay);
            delay += 10 ;
        }
        new BukkitRunnable(){
            @Override
            public void run() {
                inGame.clear();
            }
        }.runTaskLater(Werewolfs.getInstance(),delay + 10);
        alive.clear();
        isGameMaster.clear();
        canSeeEveryone.clear();
        HuntersThatCanRespawn.clear();
        votedFor.clear();
        voteResults.clear();
        Roles.clear();
        accused.clear();
        poolMade = false;
        anyoneAtAllCanVote =false;
        anyoneAtAllCanAccuse = false;

    }

    //TODO spieler bei wiederbelebung wieder unsichtbar für andere Spieler
    public void setAlive(String player){



    }
    public void setDead(String player){
        executeCommand("clear " + player,10);
        executeCommand("gamemode spectator " + player,20);
        alive.remove(player);

    }
    public void setFinalDeath(String player){

    }

    public void executeCommand(String command, int delay){
        new BukkitRunnable(){
            @Override
            public void run() {
                Bukkit.dispatchCommand(console, command);
            }
        }.runTaskLater(Werewolfs.getInstance(),delay);

    }

    public void openGameMasterGUI(Player player){
        Inventory gui = Bukkit.createInventory(player, 18, ChatColor.AQUA + "SpielleiterGUI");

        ItemStack makeMeGameMaster = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);

        ItemMeta makeMeGameMaster_meta = makeMeGameMaster.getItemMeta();
        makeMeGameMaster_meta.setDisplayName("Mach Mich zum Spielleiter");
        makeMeGameMaster.setItemMeta(makeMeGameMaster_meta);


        ItemStack accusePeopleStart = new ItemStack(Material.PAPER);

        ItemMeta accusePeopleStart_meta = accusePeopleStart.getItemMeta();
        accusePeopleStart_meta.setDisplayName("Starte eine neue Anklage");
        accusePeopleStart.setItemMeta(accusePeopleStart_meta);

        ItemStack voteStart = new ItemStack(Material.WRITABLE_BOOK);

        ItemMeta voteStart_meta = voteStart.getItemMeta();
        voteStart_meta.setDisplayName("Starte eine neue neue Abstimmung");
        voteStart.setItemMeta(voteStart_meta);


        ItemStack voteResult= new ItemStack(Material.WRITTEN_BOOK);

        ItemMeta voteResult_meta = voteResult.getItemMeta();
        voteResult_meta.setDisplayName("Erhalte das Ergebnis der Abstimmung");
        voteResult.setItemMeta(voteResult_meta);


        ItemStack addPlayersToGame= new ItemStack(Material.AXOLOTL_BUCKET);

        ItemMeta addPlayersToGame_meta = addPlayersToGame.getItemMeta();
        addPlayersToGame_meta.setDisplayName("Füge dem Spiel Spieler hinzu");
        addPlayersToGame.setItemMeta(addPlayersToGame_meta);


        ItemStack removePlayersFromGame= new ItemStack(Material.BUCKET);

        ItemMeta removePlayersFromGame_meta = removePlayersFromGame.getItemMeta();
        removePlayersFromGame_meta.setDisplayName("Entferne Spieler aus dem Spiel");
        removePlayersFromGame.setItemMeta(removePlayersFromGame_meta);

        ItemStack listPlayers= new ItemStack(Material.WATER_BUCKET);

        ItemMeta listPlayers_meta = listPlayers.getItemMeta();
        listPlayers_meta.setDisplayName("Liste alle Spieler auf");
        listPlayers.setItemMeta(listPlayers_meta);


        ItemStack pool= new ItemStack(Material.OAK_BOAT);

        ItemMeta pool_meta = pool.getItemMeta();
        pool_meta.setDisplayName("Erstelle den Pool");
        pool.setItemMeta(pool_meta);


        ItemStack skipDayNight= new ItemStack(Material.CLOCK);

        ItemMeta skipDayNight_meta = skipDayNight.getItemMeta();
        skipDayNight_meta.setDisplayName("Überspringe die Tag/Nacht-Phase");
        skipDayNight.setItemMeta(skipDayNight_meta);


        ItemStack startGame= new ItemStack(Material.OAK_SAPLING);

        ItemMeta startGame_meta = startGame.getItemMeta();
        startGame_meta.setDisplayName("Starte das Spiel");
        startGame.setItemMeta(startGame_meta);


        ItemStack endGame= new ItemStack(Material.DEAD_BUSH);

        ItemMeta endGame_meta = endGame.getItemMeta();
        endGame_meta.setDisplayName("Beende das Spiel");
        endGame.setItemMeta(endGame_meta);

        ItemStack nothing= new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);

        ItemMeta nothing_meta = nothing.getItemMeta();
        nothing_meta.setDisplayName(" ");
        nothing.setItemMeta(nothing_meta);


        ItemStack[] menu_items = {makeMeGameMaster,addPlayersToGame,removePlayersFromGame,listPlayers,pool,startGame,accusePeopleStart,voteStart,voteResult,skipDayNight,endGame,nothing,nothing,nothing,nothing,nothing,nothing,nothing};
        gui.setContents(menu_items);

        player.openInventory(gui);



    }



    public HashMap<String, String> getRoles() {
        return Roles;
    }

    public void setRoles(HashMap<String, String> roles) {
        Roles = roles;
    }


    public HashMap<String, String> getVotedFor() {
        return votedFor;
    }

    public void setVotedFor(HashMap<String, String> votedFor) {
        this.votedFor = votedFor;
    }

    public ArrayList<String> getInGame() {
        return inGame;
    }

    public void setInGame(ArrayList<String> inGame) {
        this.inGame = inGame;
    }

    public ArrayList<String> getIsGameMaster() {
        return isGameMaster;
    }

    public void setIsGameMaster(ArrayList<String> isGameMaster) {
        this.isGameMaster = isGameMaster;
    }

    public HashMap<String, String> getAccused() {
        return accused;
    }

    public void setAccused(HashMap<String, String> accused) {
        this.accused = accused;
    }

    public HashMap<String, ArrayList<String>> getVoteResults() {
        return voteResults;
    }

    public void setVoteResults(HashMap<String, ArrayList<String>> voteResults) {
        this.voteResults = voteResults;
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        isGameRunning = gameRunning;
    }
    public ArrayList<String> getAlive() {
        return alive;
    }

    public void setAlive(ArrayList<String> alive) {
        this.alive = alive;
    }

    public HashMap<String, String> getWwvotedFor() {
        return wwvotedFor;
    }

    public void setWwvotedFor(HashMap<String, String> wwvotedFor) {
        this.wwvotedFor = wwvotedFor;
    }

    public HashMap<String, ArrayList<String>> getWwvoteResults() {
        return wwvoteResults;
    }

    public void setWwvoteResults(HashMap<String, ArrayList<String>> wwvoteResults) {
        this.wwvoteResults = wwvoteResults;
    }


}