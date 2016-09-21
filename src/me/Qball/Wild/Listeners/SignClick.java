package me.Qball.Wild.Listeners;

import me.Qball.Wild.Wild;
import me.Qball.Wild.Utils.GetRandomLocation;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
public class SignClick implements Listener {
	public Wild wild = Wild.getInstance();
	RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
	public Economy econ = rsp.getProvider();
	public int Rem;
	public int cool = wild.getConfig().getInt("Cooldown");
	public int cost = wild.getConfig().getInt("Cost");
	String costmsg = wild.getConfig().getString("Costmsg");
	String Cost = String.valueOf(cost);
	String Costmsg = costmsg.replaceAll("\\{cost\\}", Cost);
	String Cool = String.valueOf(cool);
	String coolmsg = wild.getConfig().getString("Cooldownmsg");
	String Coolmsg = coolmsg.replaceAll("\\{cool\\}",Cool);
	public GetRandomLocation random = new GetRandomLocation();

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		
		Player target = e.getPlayer();
		Sign sign;
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		if (e.getClickedBlock().getState() instanceof Sign) {
			sign = (Sign) e.getClickedBlock().getState();
			if (sign.getLine(1).equalsIgnoreCase("[§1Wild§0]")&& sign.getLine(0).equalsIgnoreCase("§4====================")) {

				if (target.hasPermission("wild.wildtp.cooldown.bypass")&&target.hasPermission("wild.wildtp.cost.bypass"))
				{
					random.getWorldInfo(target);
				}
				else if (target.hasPermission("!wild.wildtp.cooldown.bypass")&&target.hasPermission("wild.wildtp.cost.bypass"))
				{
				if (Wild.check(target)) {
					random.getWorldInfo(target);
				} else {
					String rem = String.valueOf(Rem);
					Coolmsg = Coolmsg.replaceAll("\\{rem\\}", rem);
					target.sendMessage(ChatColor.translateAlternateColorCodes('&', Coolmsg));

				}
				}
				else if (target.hasPermission("wild.wildtp.cooldown")&&!target.hasPermission("wild.wildtp.cost.bypass"))
				{
					if(econ.getBalance(target) >= cost)
					{
						
						EconomyResponse r =econ.withdrawPlayer(target, cost);
						if(r.transactionSuccess())
						{
							random.getWorldInfo(target);
							target.sendMessage(ChatColor.translateAlternateColorCodes('&', Costmsg));
							

						}
						else
						{
							target.sendMessage(ChatColor.RED + "Something has gone wrong sorry but we will be unable to teleport you :( ");
						}
					}
					else
					{
						target.sendMessage(ChatColor.RED + "You do not have enough money to use this command");
					}

				}
				else if (!target.hasPermission("wild.wildtp.cooldown")&&!target.hasPermission("wild.wildtp.cost.bypass"))
				{
					if(econ==null)
						target.sendMessage("Econ is null");
					if(Wild.check(target))
					{
					if(econ.getBalance(target) >= cost)
					{
						
						EconomyResponse r =econ.withdrawPlayer(target, cost);
						if(r.transactionSuccess())
						{
							random.getWorldInfo(target);
							target.sendMessage(ChatColor.translateAlternateColorCodes('&', Costmsg));
							

						}
						else
						{
							target.sendMessage(ChatColor.RED + "Something has gone wrong sorry but we will be unable to teleport you :( ");
						}
					}
					else
					{
						target.sendMessage(ChatColor.RED + "You do not have enough money to use this command");
					}

				}
					else
				{
						String rem = String.valueOf(Rem);
						Coolmsg = Coolmsg.replaceAll("\\{rem\\}", rem);
						target.sendMessage(ChatColor.translateAlternateColorCodes('&', Coolmsg));
				}
				}
				
			
			}
		}
	}

	
}
