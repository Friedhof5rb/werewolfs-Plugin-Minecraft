package me.friedhof.werewolfs.Events;

import me.friedhof.werewolfs.Werewolfs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class MyListener implements Listener {


    ConsoleCommandSender console;






    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        Item item = event.getItemDrop();
        console =  Bukkit.getServer().getConsoleSender();
        if(item.getItemStack().getItemMeta().getDisplayName().equals("KillerSchwert")){
           event.setCancelled(true);
        }
        if(item.getItemStack().getItemMeta().getDisplayName().equals("Demokratie")){
            event.setCancelled(true);
        }
        if(item.getItemStack().getItemMeta().getDisplayName().equals("Todesabstimmung")){
            event.setCancelled(true);
        }
        if(item.getItemStack().getItemMeta().getDisplayName().equals("Zaubertrank")){
            event.setCancelled(true);
        }
        if(item.getItemStack().getItemMeta().getDisplayName().equals("Kristallkugel")){
            event.setCancelled(true);
        }
        if(item.getItemStack().getItemMeta().getDisplayName().equals("Gewehr")){
            event.setCancelled(true);
        }

    }






   @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        if(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("Demokratie")) {

            if(!Werewolfs.getInstance().getGameManager().anyoneAtAllCanAccuse &&  !Werewolfs.getInstance().getGameManager().anyoneAtAllCanVote){
                e.getPlayer().sendMessage("Es gibt momentan noch keine Wahl.");
                e.setCancelled(true);
                return;
            }
            if(Werewolfs.getInstance().getGameManager().anyoneAtAllCanAccuse ){


                ItemStack enthaltung = new ItemStack(Material.PAPER);

                ItemMeta enthaltung_meta = enthaltung.getItemMeta();
                enthaltung_meta.setDisplayName("Zurückziehen");
                enthaltung.setItemMeta(enthaltung_meta);




                int slot = 0;
                Inventory gui = Bukkit.createInventory(e.getPlayer(), 54, ChatColor.AQUA + "AnklageGui");
                gui.addItem(enthaltung);
                for(String p : Werewolfs.getInstance().getGameManager().getAlive()  ){
                    slot++;
                    gui.addItem(getHead(Bukkit.getPlayer(p),false));
                }



                ItemStack nothing= new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);

                ItemMeta nothing_meta = nothing.getItemMeta();
                nothing_meta.setDisplayName(" ");
                nothing.setItemMeta(nothing_meta);


                if(slot>53){
                    e.getPlayer().sendMessage("Zu viele Spieler Online");
                    e.setCancelled(true);
                    return;
                }



                for(int i = 53; i > slot; i--) {
                    gui.setItem(i,nothing);
                }
                e.getPlayer().openInventory(gui);

                e.setCancelled(true);
                return;

            }
            if(Werewolfs.getInstance().getGameManager().anyoneAtAllCanVote ){
                int slot = 0;
                Inventory gui = Bukkit.createInventory(e.getPlayer(), 54, ChatColor.AQUA + "RausVoteGui");

                ArrayList<String> accusedToChoose = new ArrayList<>();

                for(String p : Werewolfs.getInstance().getGameManager().getAccused().keySet()  ) {
                    if(!accusedToChoose.contains(Werewolfs.getInstance().getGameManager().getAccused().get(p))){
                        accusedToChoose.add(Werewolfs.getInstance().getGameManager().getAccused().get(p));
                    }
                }

                ItemStack enthaltung = new ItemStack(Material.PAPER);

                ItemMeta enthaltung_meta = enthaltung.getItemMeta();
                enthaltung_meta.setDisplayName("Enthaltung");
                enthaltung.setItemMeta(enthaltung_meta);

                gui.addItem(enthaltung);
                for(String p : accusedToChoose  ){
                    slot++;
                    gui.addItem(getHead(Bukkit.getPlayer(p),false));
                }

                ItemStack nothing= new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);

                ItemMeta nothing_meta = nothing.getItemMeta();
                nothing_meta.setDisplayName(" ");
                nothing.setItemMeta(nothing_meta);


                if(slot>53){
                    e.getPlayer().sendMessage("Zu viele Spieler Online");
                    e.setCancelled(true);
                    return;
                }



                for(int i = 53; i > slot; i--) {
                    gui.setItem(i,nothing);
                }

                e.getPlayer().openInventory(gui);
                e.setCancelled(true);
                return;


            }


        }
       if(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("Todesabstimmung")) {


           int slot = 0;
           Inventory gui = Bukkit.createInventory(e.getPlayer(), 54, ChatColor.AQUA + "Todesabstimmung");



           ItemStack enthaltung = new ItemStack(Material.PAPER);

           ItemMeta enthaltung_meta = enthaltung.getItemMeta();
           enthaltung_meta.setDisplayName("Enthaltung");
           enthaltung.setItemMeta(enthaltung_meta);

           gui.addItem(enthaltung);
           for(String p : Werewolfs.getInstance().getGameManager().getInGame()  ){
               slot++;
               gui.addItem(getHead(Bukkit.getPlayer(p),false));
           }

           ItemStack nothing= new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);

           ItemMeta nothing_meta = nothing.getItemMeta();
           nothing_meta.setDisplayName(" ");
           nothing.setItemMeta(nothing_meta);


           if(slot>53){
               e.getPlayer().sendMessage("Zu viele Spieler Online");
               e.setCancelled(true);
               return;
           }



           for(int i = 53; i > slot; i--) {
               gui.setItem(i,nothing);
           }

           e.getPlayer().openInventory(gui);
           e.setCancelled(true);
           return;




       }
    }





    public void executeCommand(String command, int delay){
        new BukkitRunnable(){
            @Override
            public void run() {
                Bukkit.dispatchCommand(console, command);
            }
        }.runTaskLater(Werewolfs.getInstance(),delay);

    }
    @EventHandler
    public void clickEvent(InventoryClickEvent event){



        if(!event.isCancelled()) {
            HumanEntity ent = event.getWhoClicked();

            if (ent instanceof Player) {
                Player player = (Player) ent;
                Inventory inv = event.getInventory();

                if (inv instanceof AnvilInventory && Werewolfs.getInstance().getGameManager().isGameRunning()) {
                    event.setCancelled(true);
                }
            }
        }

        Player player = (Player) event.getWhoClicked();
        ConsoleCommandSender console =  Bukkit.getServer().getConsoleSender();




        if(event.getView().getTitle().equalsIgnoreCase(ChatColor.AQUA + "SpielleiterGUI")){

            if(event.getCurrentItem() == null){
                event.setCancelled(true);
                return;
            }
            if(event.getCurrentItem().getType() == Material.LIGHT_GRAY_STAINED_GLASS_PANE) {
                event.setCancelled(true);
                return;
            }


            //makeMeGameMaster
            if(event.getCurrentItem().getType() == Material.ENCHANTED_GOLDEN_APPLE){
                if(!Werewolfs.getInstance().getGameManager().getIsGameMaster().contains(player.getName())) {
                    Werewolfs.getInstance().getGameManager().getIsGameMaster().add(player.getName());
                    player.sendMessage("Du bist jetzt Spielleiter");
                }else{
                    player.sendMessage("Du bist bereits Spielleiter");
                }
                player.closeInventory();
                event.setCancelled(true);
                return;
            }

            //startAccusation
            if(event.getCurrentItem().getType() == Material.PAPER){
                Werewolfs.getInstance().getGameManager().anyoneAtAllCanAccuse = true;
                Bukkit.dispatchCommand(console,"say Anklage ist offen!");
                player.closeInventory();
                event.setCancelled(true);
                return;
            }

            //start Vote
            if(event.getCurrentItem().getType() == Material.WRITABLE_BOOK){
                Werewolfs.getInstance().getGameManager().anyoneAtAllCanAccuse = false;
                Werewolfs.getInstance().getGameManager().anyoneAtAllCanVote = true;
                Bukkit.dispatchCommand(console,"say Abstimmung ist offen!");
                player.closeInventory();
                event.setCancelled(true);
                return;
            }

            //addPlayersToGame
            if(event.getCurrentItem().getType() == Material.AXOLOTL_BUCKET){

                player.closeInventory();

                int slot = 0;
                Inventory addPlayersGui = Bukkit.createInventory(player, 54, ChatColor.AQUA + "Spieler hinzufügen");
                for(Player p : Bukkit.getOnlinePlayers()){
                    slot++;
                    addPlayersGui.addItem(getHead(p,false));
                }

                ItemStack nothing= new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);

                ItemMeta nothing_meta = nothing.getItemMeta();
                nothing_meta.setDisplayName(" ");
                nothing.setItemMeta(nothing_meta);


                if(slot>26){
                    player.sendMessage("Zu viele Spieler Online");
                    event.setCancelled(true);
                    return;
                }



                for(int i = 44; i >= slot; i--) {
                    addPlayersGui.setItem(i,nothing);
                }





                ItemStack back = new ItemStack(Material.RED_DYE);

                ItemMeta back_meta = back.getItemMeta();
                back_meta.setDisplayName("Zurück");
                back.setItemMeta(back_meta);

                addPlayersGui.addItem(back);

                for(int i = 46; i< 53; i++) {
                    addPlayersGui.setItem(i, nothing);
                }


                ItemStack confirm = new ItemStack(Material.LIME_DYE);

                ItemMeta confirm_meta = confirm.getItemMeta();
                confirm_meta.setDisplayName("Bestätige");
                confirm.setItemMeta(confirm_meta);

                addPlayersGui.addItem(confirm);

                player.openInventory(addPlayersGui);

                event.setCancelled(true);
                return;
            }
            //removePlayersFromGame
            if(event.getCurrentItem().getType() == Material.BUCKET){

                player.closeInventory();

                int slot = 0;
                Inventory removePlayersGui = Bukkit.createInventory(player, 54, ChatColor.AQUA + "Spieler entfernen");
                for(String p : Werewolfs.getInstance().getGameManager().getInGame()  ){
                    slot++;
                    removePlayersGui.addItem(getHead(Bukkit.getPlayer(p),false));
                }

                ItemStack nothing= new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);

                ItemMeta nothing_meta = nothing.getItemMeta();
                nothing_meta.setDisplayName(" ");
                nothing.setItemMeta(nothing_meta);

                if(slot>35){
                    player.sendMessage("Zu viele Spieler im Game");
                    event.setCancelled(true);
                    return;
                }

                for(int i = 44; i >= slot; i--) {
                    removePlayersGui.setItem(i,nothing);
                }

                ItemStack back = new ItemStack(Material.RED_DYE);

                ItemMeta back_meta = back.getItemMeta();
                back_meta.setDisplayName("Zurück");
                back.setItemMeta(back_meta);

                removePlayersGui.addItem(back);

                for(int i = 46; i< 53; i++) {
                    removePlayersGui.setItem(i, nothing);
                }


                ItemStack confirm = new ItemStack(Material.LIME_DYE);

                ItemMeta confirm_meta = confirm.getItemMeta();
                confirm_meta.setDisplayName("Bestätige");
                confirm.setItemMeta(confirm_meta);

                removePlayersGui.addItem(confirm);

                player.openInventory(removePlayersGui);



                event.setCancelled(true);
                return;
            }
            //listPlayers
            if(event.getCurrentItem().getType() == Material.WATER_BUCKET){
                player.closeInventory();

                int slot = 0;
                Inventory PlayersGui = Bukkit.createInventory(player, 54, ChatColor.AQUA + "Spieler auflisten");
                for(String p : Werewolfs.getInstance().getGameManager().getInGame()  ){
                    slot++;
                    PlayersGui.addItem(getHead(Bukkit.getPlayer(p),Werewolfs.getInstance().getGameManager().poolMade));
                }

                ItemStack nothing= new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);

                ItemMeta nothing_meta = nothing.getItemMeta();
                nothing_meta.setDisplayName(" ");
                nothing.setItemMeta(nothing_meta);

                if(slot>35){
                    player.sendMessage("Zu viele Spieler im Game");
                    event.setCancelled(true);
                    return;
                }

                for(int i = 44; i >= slot; i--) {
                    PlayersGui.setItem(i,nothing);
                }
                ItemStack back = new ItemStack(Material.RED_DYE);

                ItemMeta back_meta = back.getItemMeta();
                back_meta.setDisplayName("Zurück");
                back.setItemMeta(back_meta);

                PlayersGui.addItem(back);

                for(int i = 46; i< 54; i++) {
                    PlayersGui.setItem(i, nothing);
                }

                player.openInventory(PlayersGui);

                event.setCancelled(true);
                return;
            }
            //Pool
            if(event.getCurrentItem().getType() == Material.OAK_BOAT){
                player.closeInventory();
                Inventory poolGui = Bukkit.createInventory(player, 54, ChatColor.AQUA + "Pool");

                ItemStack dorfi = new ItemStack(Material.VILLAGER_SPAWN_EGG);

                ItemMeta dorfi_meta = dorfi.getItemMeta();
                dorfi_meta.setDisplayName("Dorfbewohner");
                dorfi.setItemMeta(dorfi_meta);

                ItemStack werwolf = new ItemStack(Material.BONE);

                ItemMeta werwolf_meta = werwolf.getItemMeta();
                werwolf_meta.setDisplayName("Werwolf");
                werwolf.setItemMeta(werwolf_meta);

                ItemStack hunter = new ItemStack(Material.CROSSBOW);

                ItemMeta hunter_meta = hunter.getItemMeta();
                hunter_meta.setDisplayName("Jäger");
                hunter.setItemMeta(hunter_meta);

                ItemStack hexe = new ItemStack(Material.SPLASH_POTION);

                ItemMeta hexe_meta = hexe.getItemMeta();
                hexe_meta.setDisplayName("Hexe");
                hexe.setItemMeta(hexe_meta);

                ItemStack seher = new ItemStack(Material.ENDER_EYE);

                ItemMeta seher_meta = seher.getItemMeta();
                seher_meta.setDisplayName("Seher");
                seher.setItemMeta(seher_meta);

                poolGui.addItem(dorfi, werwolf, hunter, hexe, seher);


                ItemStack nothing= new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);

                ItemMeta nothing_meta = nothing.getItemMeta();
                nothing_meta.setDisplayName(" ");
                nothing.setItemMeta(nothing_meta);


                for(int i = 44; i >= 5; i--) {
                    poolGui.setItem(i,nothing);
                }

                ItemStack back = new ItemStack(Material.RED_DYE);

                ItemMeta back_meta = back.getItemMeta();
                back_meta.setDisplayName("Zurück");
                back.setItemMeta(back_meta);

                poolGui.addItem(back);

                for(int i = 46; i< 53; i++) {
                    poolGui.setItem(i, nothing);
                }


                ItemStack confirm = new ItemStack(Material.LIME_DYE);

                ItemMeta confirm_meta = confirm.getItemMeta();
                confirm_meta.setDisplayName("Bestätige");
                confirm.setItemMeta(confirm_meta);

                poolGui.addItem(confirm);

                player.openInventory(poolGui);

                event.setCancelled(true);
                return;
            }
            //skipDayNight
            if(event.getCurrentItem().getType() == Material.CLOCK){
                Werewolfs.getInstance().getGameManager().changeTime();
                player.closeInventory();
                event.setCancelled(true);
                return;
            }

            //vote Result
            if(event.getCurrentItem().getType() == Material.WRITTEN_BOOK){

                Werewolfs.getInstance().getGameManager().voteResult();
                Bukkit.dispatchCommand(console,"say "+ ChatColor.DARK_RED + "Wahlergebnisse: ");
                for(String s :  Werewolfs.getInstance().getGameManager().getVoteResults().keySet()){
                    Bukkit.dispatchCommand(console,"say " + ChatColor.GOLD + s + " wurde von " +  Werewolfs.getInstance().getGameManager().getVoteResults().get(s).size() +" Personen gewählt.");
                    Bukkit.dispatchCommand(console,"say " + ChatColor.GOLD+"Diese sind: ");
                    for(String s1 :  Werewolfs.getInstance().getGameManager().getVotedFor().keySet()) {
                        if(Werewolfs.getInstance().getGameManager().getVotedFor().get(s1).equals(s)){
                            Bukkit.dispatchCommand(console,"say " + s1);
                        }

                    }
                }


                Werewolfs.getInstance().getGameManager().getVotedFor().clear();
                Werewolfs.getInstance().getGameManager().getVoteResults().clear();
                Werewolfs.getInstance().getGameManager().anyoneAtAllCanAccuse = false;
                Werewolfs.getInstance().getGameManager().anyoneAtAllCanVote = false;

                player.closeInventory();
                event.setCancelled(true);
                return;
            }

            //startGame
            if(event.getCurrentItem().getType() == Material.OAK_SAPLING){
                Werewolfs.getInstance().getGameManager().StartGame();
                player.closeInventory();
                event.setCancelled(true);
                return;
            }
            //endGame
            if(event.getCurrentItem().getType() == Material.DEAD_BUSH){
                Werewolfs.getInstance().getGameManager().EndGame();
                player.closeInventory();
                event.setCancelled(true);
                return;
            }





        }
        if(event.getView().getTitle().equalsIgnoreCase(ChatColor.AQUA + "Spieler hinzufügen")) {

            if(event.getCurrentItem() == null){
                event.setCancelled(true);
                return;
            }
            if(event.getCurrentItem().getType() == Material.LIGHT_GRAY_STAINED_GLASS_PANE) {
                event.setCancelled(true);
                return;
            }
            if(event.getCurrentItem().getType() == Material.PLAYER_HEAD && event.getSlot() <= 26) {
                int freeSlot = 27;

            boolean containsThisPlayer = false;
            for(int j = freeSlot; j <event.getInventory().getSize(); j++){
                if(event.getInventory().getItem(j).getItemMeta().getDisplayName().equals(event.getCurrentItem().getItemMeta().getDisplayName())){
                    containsThisPlayer = true;
                    break;
                }
            }

            for(int i = 0; i<18; i++) {

                if (event.getInventory().getItem(freeSlot).getType() == Material.LIGHT_GRAY_STAINED_GLASS_PANE && !containsThisPlayer ) {

                    event.getInventory().setItem(freeSlot, event.getCurrentItem());
                    break;
                }
                freeSlot++;
            }

            player.updateInventory();
            }
            if(event.getCurrentItem().getType() == Material.PLAYER_HEAD && event.getSlot() > 26) {

                ItemStack nothing= new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);

                ItemMeta nothing_meta = nothing.getItemMeta();
                nothing_meta.setDisplayName(" ");
                nothing.setItemMeta(nothing_meta);


                event.getInventory().setItem(event.getSlot(),nothing );
                player.updateInventory();
            }


            if(event.getCurrentItem().getType() == Material.RED_DYE) {
                player.closeInventory();
                Werewolfs.getInstance().getGameManager().openGameMasterGUI(player);
            }
            if(event.getCurrentItem().getType() == Material.LIME_DYE) {

                ArrayList<String> PlayersToAdd = new ArrayList<>();

                for(int i = 27; i< 45; i++ ){
                    if(event.getInventory().getItem(i).getType() == Material.PLAYER_HEAD) {
                        PlayersToAdd.add(event.getInventory().getItem(i).getItemMeta().getDisplayName());
                    }
                }
                for(String s : PlayersToAdd){
                    if(!Werewolfs.getInstance().getGameManager().getInGame().contains(s)) {
                        Werewolfs.getInstance().getGameManager().addToGame(s);
                    }
                }
                player.sendMessage("Spieler wurden hinzugefügt");
                player.closeInventory();
            }

            event.setCancelled(true);

        }

        if(event.getView().getTitle().equalsIgnoreCase(ChatColor.AQUA + "Spieler entfernen")) {
            if(event.getCurrentItem() == null){
                event.setCancelled(true);
                return;
            }
            if(event.getCurrentItem().getType() == Material.LIGHT_GRAY_STAINED_GLASS_PANE) {
                event.setCancelled(true);
                return;
            }
            if(event.getCurrentItem().getType() == Material.PLAYER_HEAD) {


                ItemStack nothing= new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);

                ItemMeta nothing_meta = nothing.getItemMeta();
                nothing_meta.setDisplayName(" ");
                nothing.setItemMeta(nothing_meta);


                event.getInventory().setItem(event.getSlot(),nothing );
                player.updateInventory();

            }
            if(event.getCurrentItem().getType() == Material.RED_DYE) {
                player.closeInventory();
                Werewolfs.getInstance().getGameManager().openGameMasterGUI(player);
            }
            if(event.getCurrentItem().getType() == Material.LIME_DYE) {

                ArrayList<String> PlayersToKeep = new ArrayList<>();

                for(int i = 0; i< 45; i++ ){
                    if(event.getInventory().getItem(i).getType() == Material.PLAYER_HEAD) {
                        PlayersToKeep.add(event.getInventory().getItem(i).getItemMeta().getDisplayName());
                    }
                }


                ArrayList<String> gameListCopy = new ArrayList<>();
                for(String s : Werewolfs.getInstance().getGameManager().getInGame()) {
                    gameListCopy.add(s);
                }
                for(String s : gameListCopy){
                    if(!PlayersToKeep.contains(s)) {
                        Werewolfs.getInstance().getGameManager().removeFromGame(s);
                    }
                }
                player.sendMessage("Spieler wurden entfernt");
                player.closeInventory();
            }


            event.setCancelled(true);
            return;

        }

        if(event.getView().getTitle().equalsIgnoreCase(ChatColor.AQUA + "Spieler auflisten")) {

            if(event.getCurrentItem() == null){
                event.setCancelled(true);
                return;
            }
            if(event.getCurrentItem().getType() == Material.LIGHT_GRAY_STAINED_GLASS_PANE) {
                event.setCancelled(true);
                return;
            }
            if(event.getCurrentItem().getType() == Material.RED_DYE) {
                player.closeInventory();
                Werewolfs.getInstance().getGameManager().openGameMasterGUI(player);
            }

            event.setCancelled(true);
            return;
        }
        if(event.getView().getTitle().equalsIgnoreCase(ChatColor.AQUA + "Pool")) {

            if(event.getCurrentItem() == null){
                event.setCancelled(true);
                return;
            }
            if(event.getCurrentItem().getType() == Material.LIGHT_GRAY_STAINED_GLASS_PANE) {
                event.setCancelled(true);
                return;
            }
            if(event.getCurrentItem().getType() == Material.RED_DYE) {
                player.closeInventory();
                Werewolfs.getInstance().getGameManager().openGameMasterGUI(player);
            }
            if((event.getCurrentItem().getType() == Material.VILLAGER_SPAWN_EGG ||
                    event.getCurrentItem().getType() == Material.BONE ||
                    event.getCurrentItem().getType() == Material.CROSSBOW ||
                    event.getCurrentItem().getType() == Material.SPLASH_POTION ||
                    event.getCurrentItem().getType() == Material.ENDER_EYE  )&& event.getSlot() < 9) {

                int freeSlot = 18;

                boolean containsThisPlayer = false;
                for(int j = freeSlot; j <event.getInventory().getSize(); j++){
                    if(event.getInventory().getItem(j).getItemMeta().getDisplayName().equals(event.getCurrentItem().getItemMeta().getDisplayName())){
                        containsThisPlayer = true;
                        break;
                    }
                }

                for(int i = 0; i<27; i++) {

                    if (event.getInventory().getItem(freeSlot).getType() == Material.LIGHT_GRAY_STAINED_GLASS_PANE ) {

                        event.getInventory().setItem(freeSlot, event.getCurrentItem());
                        break;
                    }
                    freeSlot++;
                }
                player.updateInventory();
            }

            if((event.getCurrentItem().getType() == Material.VILLAGER_SPAWN_EGG ||
                    event.getCurrentItem().getType() == Material.BONE ||
                    event.getCurrentItem().getType() == Material.CROSSBOW ||
                    event.getCurrentItem().getType() == Material.SPLASH_POTION ||
                    event.getCurrentItem().getType() == Material.ENDER_EYE  )&& event.getSlot() > 9) {

                ItemStack nothing= new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);

                ItemMeta nothing_meta = nothing.getItemMeta();
                nothing_meta.setDisplayName(" ");
                nothing.setItemMeta(nothing_meta);


                event.getInventory().setItem(event.getSlot(),nothing );
                player.updateInventory();

            }

            if(event.getCurrentItem().getType() == Material.LIME_DYE) {

                ArrayList<String> Roles = new ArrayList<>();

                for(int i = 18; i< 45; i++ ){
                    if((event.getInventory().getItem(i).getType() == Material.VILLAGER_SPAWN_EGG ||
                            event.getInventory().getItem(i).getType() == Material.BONE ||
                            event.getInventory().getItem(i).getType()== Material.CROSSBOW ||
                            event.getInventory().getItem(i).getType() == Material.SPLASH_POTION ||
                            event.getInventory().getItem(i).getType() == Material.ENDER_EYE  )) {
                        Roles.add(event.getInventory().getItem(i).getItemMeta().getDisplayName());
                    }
                }

                String[] pool = new String[Roles.size()];
                for(int j = 0; j < Roles.size();j++){
                    pool[j] = Roles.get(j);
                }
                if(pool.length != 0) {
                    Werewolfs.getInstance().getGameManager().makePool(pool);
                    player.sendMessage("Rollen wurden verteilt!");
                }else{
                    player.sendMessage("Du musst mindestens eine Rolle auswählen!");
                }


                player.closeInventory();
            }



            event.setCancelled(true);
            return;
        }

        if(event.getView().getTitle().equalsIgnoreCase(ChatColor.AQUA + "AnklageGui")) {

            if(event.getCurrentItem() == null){
                event.setCancelled(true);
                return;
            }
            if(event.getCurrentItem().getType() == Material.LIGHT_GRAY_STAINED_GLASS_PANE) {
                event.setCancelled(true);
                return;
            }
            if(event.getCurrentItem().getType() == Material.PLAYER_HEAD) {
                Werewolfs.getInstance().getGameManager().getAccused().put(player.getDisplayName(),event.getCurrentItem().getItemMeta().getDisplayName());
                player.closeInventory();
                Bukkit.dispatchCommand(console,"say " + player.getDisplayName() + " hat "+ event.getCurrentItem().getItemMeta().getDisplayName() + " angeklagt.");
                event.setCancelled(true);
                return;
            }

            if(event.getCurrentItem().getType() == Material.PAPER) {
                String accusedTillNow = Werewolfs.getInstance().getGameManager().getAccused().get(player.getDisplayName());
                Werewolfs.getInstance().getGameManager().getAccused().remove(player.getDisplayName());
                player.closeInventory();
                Bukkit.dispatchCommand(console,"say " + player.getDisplayName() + " hat seine Anklage gegen " + accusedTillNow + " zurückgezogen." );
                event.setCancelled(true);
                return;

            }

        }
        if(event.getView().getTitle().equalsIgnoreCase(ChatColor.AQUA + "RausVoteGui")) {
            if(event.getCurrentItem() == null){
                event.setCancelled(true);
                return;
            }
            if(event.getCurrentItem().getType() == Material.LIGHT_GRAY_STAINED_GLASS_PANE) {
                event.setCancelled(true);
                return;
            }
            if(event.getCurrentItem().getType() == Material.PLAYER_HEAD) {
                Werewolfs.getInstance().getGameManager().getVotedFor().put(player.getDisplayName(),event.getCurrentItem().getItemMeta().getDisplayName());
                player.closeInventory();

               for(String gm : Werewolfs.getInstance().getGameManager().getIsGameMaster()) {
                   Player gmp = Bukkit.getPlayer(gm);
                   gmp.sendMessage(player.getDisplayName() + " hat für " + event.getCurrentItem().getItemMeta().getDisplayName() + " abgestimmt.");
               }
                player.sendMessage("Du hast für " + event.getCurrentItem().getItemMeta().getDisplayName() + " abgestimmt.");
                event.setCancelled(true);
                return;
            }

            if(event.getCurrentItem().getType() == Material.PAPER) {
                Werewolfs.getInstance().getGameManager().getVotedFor().remove(player.getDisplayName());
                player.closeInventory();
                for(String gm : Werewolfs.getInstance().getGameManager().getIsGameMaster()) {
                    Player gmp = Bukkit.getPlayer(gm);
                    gmp.sendMessage(player + " enthält sich.");
                }
                player.sendMessage("Du enthälst dich.");
                event.setCancelled(true);
                return;


            }

        }
        if(event.getView().getTitle().equalsIgnoreCase(ChatColor.AQUA + "Todesabstimmung")) {
            if(event.getCurrentItem() == null){
                event.setCancelled(true);
                return;
            }
            if(event.getCurrentItem().getType() == Material.LIGHT_GRAY_STAINED_GLASS_PANE) {
                event.setCancelled(true);
                return;
            }
            if(event.getCurrentItem().getType() == Material.PLAYER_HEAD) {
                Werewolfs.getInstance().getGameManager().getWwvotedFor().put(player.getDisplayName(),event.getCurrentItem().getItemMeta().getDisplayName());
                Werewolfs.getInstance().getGameManager().wwvoteResult();
                player.closeInventory();

                for(String gm : Werewolfs.getInstance().getGameManager().getIsGameMaster()) {
                    Player gmp = Bukkit.getPlayer(gm);
                    gmp.sendMessage("Werwolf-"+ player.getDisplayName() + " hat für " + event.getCurrentItem().getItemMeta().getDisplayName() + " abgestimmt.");
                }
                player.sendMessage("Du hast für " + event.getCurrentItem().getItemMeta().getDisplayName() + " abgestimmt.");
                event.setCancelled(true);
                return;
            }

            if(event.getCurrentItem().getType() == Material.PAPER) {
                Werewolfs.getInstance().getGameManager().getWwvotedFor().remove(player.getDisplayName());
                player.closeInventory();
                for(String gm : Werewolfs.getInstance().getGameManager().getIsGameMaster()) {
                    Player gmp = Bukkit.getPlayer(gm);
                    gmp.sendMessage("Werwolf-"+player + " enthält sich.");
                }
                player.sendMessage("Du enthälst dich.");
                event.setCancelled(true);
                return;


            }


        }
    }


    public static ItemStack getHead(Player p, boolean poolExists) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skull = (SkullMeta) item.getItemMeta();

        if(poolExists){
            skull.setDisplayName(p.getName() + " - " + Werewolfs.getInstance().getGameManager().getRoles().get(p.getName()));
        }else {
            skull.setDisplayName(p.getName());
        }


        skull.setOwningPlayer(p);
        item.setItemMeta(skull);
        return item;
    }

    //TODO check for Hunter/ Total Death
    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event){
        Player player = event.getEntity();
        if(Werewolfs.getInstance().getGameManager().getAlive().contains(player.getDisplayName())){
                Werewolfs.getInstance().getGameManager().setDead(player.getDisplayName());

        }
    }


    //TODO cancel if damage not by special sword or against wrong target
    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event){



    }



}
