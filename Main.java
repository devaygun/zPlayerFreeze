package me.nmexhunterz.zplayerfreeze;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

public class Main extends JavaPlugin implements Listener {
	
	ArrayList<UUID> frozenPlayers = new ArrayList<UUID>();
			
	String prefix = (ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix")));
	
	int freezeToggle = 0;
	
	int frozenTime = 0;
	
	HashMap<UUID, ItemStack> helmet = new HashMap<UUID, ItemStack>();
		
	@Override
	public void onEnable(){
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = Logger.getLogger("Minecraft");
		logger.info(pdfFile.getName() + " has been enabled (v" + pdfFile.getVersion() + ")");
		
		getServer().getPluginManager().registerEvents(this, this);
		
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = Logger.getLogger("Minecraft");
		logger.info(pdfFile.getName() + " has been disabled (v" + pdfFile.getVersion() + ")");
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (this.frozenPlayers.contains(player.getUniqueId())) {
			Location to = event.getFrom();
			to.setPitch(event.getTo().getPitch());
			to.setYaw(event.getTo().getYaw());
			event.setTo(event.getFrom());
		}
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if((player.hasPermission("zplayerfreeze.use") || (player.isOp()))) {
			return;
		}
		if (this.frozenPlayers.contains(player.getUniqueId()))  {
			event.setCancelled(true);
			event.getPlayer().sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', getConfig().getString("commandsMsg")));
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (this.frozenPlayers.contains(player.getUniqueId())) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', getConfig().getString("breakMsg")));
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (this.frozenPlayers.contains(player.getUniqueId())) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', getConfig().getString("placeMsg")));
		}
	}
	
	@EventHandler
	public void onInventoryOption(InventoryOpenEvent event) {
		Player player = (Player) event.getPlayer();
		if(this.frozenPlayers.contains(player.getUniqueId())) {
			InventoryType type = event.getInventory().getType();
			if(type == InventoryType.CHEST) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', getConfig().getString("interactMsg")));
			}
			else if (type == InventoryType.ENDER_CHEST) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', getConfig().getString("interactMsg")));
			}
			else if (type == InventoryType.HOPPER) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', getConfig().getString("interactMsg")));
			}
			else if (type == InventoryType.PLAYER) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', getConfig().getString("interactMsg")));
			}
			else if (type == InventoryType.FURNACE) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', getConfig().getString("interactMsg")));
			}
			else if (type == InventoryType.ENCHANTING) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', getConfig().getString("interactMsg")));
			}
			else if (type == InventoryType.MERCHANT) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', getConfig().getString("interactMsg")));
			}
			else if (type == InventoryType.ANVIL) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', getConfig().getString("interactMsg")));
			}
			else if (type == InventoryType.BEACON) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', getConfig().getString("interactMsg")));
			}
			else if (type == InventoryType.DROPPER) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', getConfig().getString("interactMsg")));
			}
			
			player.closeInventory();
		}
	}
	
	@EventHandler
	public void onDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		
		if (this.frozenPlayers.contains(player.getUniqueId())) {
			event.setCancelled(true);
			player.closeInventory();
			event.getPlayer().sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', getConfig().getString("dropItemMsg")));
		}
	}

	@EventHandler
    public void onClick(InventoryClickEvent event){
		Player player = (Player)event.getWhoClicked();
		if (this.frozenPlayers.contains(player.getUniqueId())) {			
			event.setCancelled(true);
		}
	}
	
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
            if(event.getEntity() instanceof Player) {
                    Player player = (Player)event.getEntity();
        			if(this.frozenPlayers.contains(player.getUniqueId())) {
                            event.setCancelled(true);
            				event.getDamager().sendMessage(prefix + ChatColor.RED + "You cannot attack a frozen player.");
                    }
            }
            if(event.getDamager() instanceof Player) {
                    Player attacker = (Player)event.getDamager();
                   
    				if(this.frozenPlayers.contains(attacker.getUniqueId())) {
                            event.setCancelled(true);
        					attacker.sendMessage(prefix + ChatColor.RED + "You cannot attack others whilst frozen.");
                    }
            }
    }
    
    @EventHandler
    public void freezePlayer(UUID uuid) {
    	this.frozenPlayers.add(uuid);
    }
    
    @EventHandler
    public void unFreezePlayer(UUID uuid) {
    	this.frozenPlayers.remove(uuid);
    }
	
	@EventHandler
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("freeze") && (sender.hasPermission("zplayerfreeze.use") || (sender.isOp()))) {
			
			if (args.length == 0 || args[0].equalsIgnoreCase(null)) {
				sender.sendMessage(prefix + ChatColor.BLUE + "Commands:");
				sender.sendMessage(ChatColor.RED + "/freeze [name]" + ChatColor.WHITE + " - Freeze a player.");
				sender.sendMessage(ChatColor.RED + "/freezetemp [name] [seconds]" + ChatColor.WHITE + " - Temporarily freeze a player (Coming soon).");
				sender.sendMessage(ChatColor.RED + "/frozen [name]" + ChatColor.WHITE + " - Check if a player is frozen.");
				sender.sendMessage(ChatColor.RED + "/frozen list" + ChatColor.WHITE + " - See all frozen players.");
				sender.sendMessage(ChatColor.RED + "/freeze all" + ChatColor.WHITE + " - Freeze all online players.");
				sender.sendMessage(ChatColor.RED + "/freeze reload" + ChatColor.WHITE + " - Reloads the config.");
				return true;
			}
			
			Player target = Bukkit.getServer().getPlayer(args[0]);
			UUID uuid = target.getUniqueId();
			Player player = Bukkit.getPlayer(uuid);
			String playerName = player.getName();
			
			if (args.length == 1 && args[0].equalsIgnoreCase("reload") && (sender.hasPermission("zplayerfreeze.reload") || (sender.isOp()))) {
				reloadConfig();
				sender.sendMessage(prefix + ChatColor.GREEN + "zPlayerFreeze has been reloaded.");
				return true;
			}
			else if (!sender.hasPermission("zplayerfreeze.reload")){
				sender.sendMessage(prefix + ChatColor.RED + "You need 'zplayerfreeze.reload' to perform this.");
				return true;
			}

			if (this.frozenPlayers.contains(uuid)) {
				this.frozenPlayers.remove(uuid);
				
				for(Player staff : Bukkit.getOnlinePlayers()){
		            if(staff.hasPermission("zplayerfreeze.notify")){
		                staff.sendMessage(prefix + ChatColor.GREEN + playerName + " " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("notifyUnfrozenMsg")) + ".");
		            }
		        }
				player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', getConfig().getString("unfrozenMsg") + "."));
				player.getInventory().setHelmet(helmet.get(player.getUniqueId()));
				return true;
			}
			
			
			this.frozenPlayers.add(uuid);
			
			for(Player staff : Bukkit.getOnlinePlayers()){
	            if(staff.hasPermission("zplayerfreeze.notify") && frozenTime > 0){
	                staff.sendMessage(prefix + ChatColor.GREEN + target.getName() + " " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("notifyFrozenMsg")) + " for " + frozenTime + " seconds.");
	            }
	            else if (staff.hasPermission("zplayerfreeze.notify") && frozenTime < 1){
	                staff.sendMessage(prefix + ChatColor.GREEN + target.getName() + " " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("notifyFrozenMsg")) + ".");
	            }
	        }		
			
            if(frozenTime > 1){
                player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', getConfig().getString("frozenMsg")) + " for " + frozenTime + " seconds.");
            }
            else if (frozenTime == 1) {
                player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', getConfig().getString("frozenMsg")) + " for " + frozenTime + " second.");
            }
            else if (frozenTime < 1){
                player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', getConfig().getString("frozenMsg")) + ".");
            }
            
			helmet.put(player.getUniqueId(), player.getInventory().getHelmet());
			player.getInventory().setHelmet(new ItemStack(Material.ICE, 1));
			return true;
			 
		}
		
		else if (!sender.hasPermission("zplayerfreeze.use")){
			sender.sendMessage(ChatColor.RED + "You need 'zplayerfreeze.use' to perform this.");
			return true; 
		}
		
		
		if (commandLabel.equalsIgnoreCase("freezeall") && (sender.hasPermission("zplayerfreeze.use") || (sender.isOp()))) {
			if (freezeToggle == 0 ) {
				sender.sendMessage(prefix + ChatColor.YELLOW + "All players have been frozen.");
				
				for (Player player : Bukkit.getOnlinePlayers()) {
					this.frozenPlayers.add(player.getUniqueId());
					player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', getConfig().getString("frozenMsg")) + ".");
				}
				freezeToggle = 1;
			}
			else if (freezeToggle == 1){
				sender.sendMessage(prefix + ChatColor.YELLOW + "All players been unfrozen.");

				for (Player player : Bukkit.getOnlinePlayers()) {
					this.frozenPlayers.remove(player.getUniqueId());
					player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', getConfig().getString("unfrozenMsg")) + ".");
				}
				freezeToggle = 0;
			}
			
			return true;
		}
		else if (commandLabel.equalsIgnoreCase("freezeall") && (!sender.hasPermission("zplayerfreeze.use") || (sender.isOp()))) {
			sender.sendMessage(ChatColor.RED + "You need 'zplayerfreeze.use' to perform this.");
			return true;
		}
			
		if (commandLabel.equalsIgnoreCase("frozen")) {
			Player target = Bukkit.getServer().getPlayer(args[0]);

			
			if (args.length == 1 && args[0].equalsIgnoreCase("list") && (sender.hasPermission("zplayerfreeze.use") || (sender.isOp()))) {
				sender.sendMessage(prefix + ChatColor.BLUE + "Frozen players:");
				
				for(UUID uuid : this.frozenPlayers){
					Player player = Bukkit.getPlayer(uuid);
					String playerName = player.getName();
					sender.sendMessage(ChatColor.GOLD + playerName);
				}
				return true;
			}
			if (args.length == 0) {
				sender.sendMessage(prefix + ChatColor.RED + "You must specify a username.");
				return true;
			}
			else if (target == null) {
				sender.sendMessage(prefix + ChatColor.RED + "Could not find player " + args[0] + ".");
				return true;
			}
			
			
			
			UUID uuid = target.getUniqueId();
			Player player = Bukkit.getPlayer(uuid);
			String playerName = player.getName();
			
			if (this.frozenPlayers.contains(uuid)) {
				for(Player staff : Bukkit.getOnlinePlayers()){
		            if(staff.hasPermission("zplayerfreeze.use")){
		                staff.sendMessage(prefix + ChatColor.GREEN + playerName + " is frozen.");
		            }
		        }			
				return true;
			}
			else {
				for(Player staff : Bukkit.getOnlinePlayers()) {
		            if(staff.hasPermission("zplayerfreeze.use")){
		                staff.sendMessage(prefix + ChatColor.GREEN + playerName + " is not frozen.");
		            }
		        }					
				return true;
			}
		}
		return false;
		
		
	}
	
}
