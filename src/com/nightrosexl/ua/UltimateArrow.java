package com.nightrosexl.ua;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class UltimateArrow extends JavaPlugin {
    
    private final String uaPrefix = "[Ultimate Arrow] ";
    private List<UAPlayer> ultimateArrowGeneralPlayerRoster = new ArrayList<UAPlayer>();
    private Location redTeamPlateLoc, redTeamSide, blueTeamPlateLoc, blueTeamSide, uaTeamSelectArea, viewing_deck1;
    private World w;
    private Gameplay gp;

    @Override
    public void onEnable() {
        w = getServer().getWorld("world"); // Centralize the world object for all the locations
        redTeamPlateLoc = new Location(w, 408, 64, 636);
        redTeamSide = new Location(w, 317.5, 64, -350.5, 180f, 0f);
        blueTeamPlateLoc = new Location(w, 404, 64, 632);
        blueTeamSide = new Location(w, 317.5, 65, -442.5, 0f, 0f);
        uaTeamSelectArea = new Location(w, 406.5, 64, 634.5); // Teleport location is now in the middle of that block
        viewing_deck1 = new Location(w, 316, 72, -342);
        gp = new Gameplay(this);
        this.getCommand("ua").setExecutor(new Commands(this));
        getServer().getPluginManager().registerEvents(new TTHandler(this), this);
        getServer().getPluginManager().registerEvents(gp, this);
    }
    
    @Override
    public void onDisable() {
        
    }
    
    public Location getRedSide() {
        return redTeamSide;
    }
    
    public Location getBlueSide() {
        return blueTeamSide;
    }
    
    public Location getRedPlate() {
        return redTeamPlateLoc;
    }
    
    public Location getBluePlate() {
        return blueTeamPlateLoc;
    }
    
    public Location getViewingArea() {
        return viewing_deck1;
    }
    
    public Location getSelectArea() {
        return uaTeamSelectArea;
    }
    
    public String getPrefix() {
        return uaPrefix;
    }
    
    public Gameplay getGameplay() {
        return gp;
    }
    
    // add
    public void addToUAGeneralRoster(Player player, String team) {
        if (getPlayer(player) != null) return; //They're already in the list, don't want a duplicate!
        ultimateArrowGeneralPlayerRoster.add(new UAPlayer(player, team));
    }
    
    // remove
    public void removeFromUAGeneralRoster(Player player) {
        if (getPlayer(player) == null) return; //Don't want to remove player if they're not in the game!
        ultimateArrowGeneralPlayerRoster.remove(getPlayer(player));
    }
    
    public UAPlayer getPlayer(Player player) {
        for (UAPlayer uap : getUAGeneralPlayerRoster()) {
            if (uap.getPlayer().getUniqueId().equals(player.getUniqueId())) return uap;
        }
        return null;
    }
    
    public List<UAPlayer> getUAGeneralPlayerRoster() {
        return ultimateArrowGeneralPlayerRoster;
    }
    
    public List<UAPlayer> getRedTeamPlayers() {
        List<UAPlayer> redTeam = new ArrayList<UAPlayer>();
        for (UAPlayer uap : getUAGeneralPlayerRoster()) {
            if (uap.getTeam().equalsIgnoreCase("Red")) redTeam.add(uap);
        }
        return redTeam;
    }
    
    public List<UAPlayer> getBlueTeamPlayers() {
        List<UAPlayer> blueTeam = new ArrayList<UAPlayer>();
        for (UAPlayer uap : getUAGeneralPlayerRoster()) {
            if (uap.getTeam().equalsIgnoreCase("Blue")) blueTeam.add(uap);
        }
        return blueTeam;
    }
}

/*
 * Ultimate Arrow a mini-game conceived by NightRoseXL.
 * 
 * Objective of Ultimate Arrow:
 * 
 * Everybody has a bow
 * There will be 1-5 arrows in play depending on the number of players and preferences set beforehand.
 * Different colored arrows are potion arrows. (Perhaps it gives particle, but does not give effects though).
 * You 'pass' the arrow by shooting it.
 * When hit, you don't take knockback, and the arrow appears in your inventory.
 * When you have it, you can only walk four steps before being frozen by the game until you shoot it.
 * Once you get it to the other side of the arena, it is given to a random player.
 * If you miss your teammate, the arrow goes to the nearest opponent.
 * If a member of the opposite team is able to hit you five times, they get the arrow you have.
 * You are only allowed to have one arrow at a time.
 */

/*
 * TODO:
 * - Put join/leave args into one class. -DONE-
 * - Tidy up Commands class.
 * - Test out teleport stuff. -DONE-
 * - Add in team-balancing later on. -Work in Progress-
 * -
 */