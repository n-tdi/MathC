package world.ntdi.mathc;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("mathc.use")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("enable")) {
                    if (MathC.enabled) {
                        sender.sendMessage(ChatColor.GOLD + "MathC is already enabled!");
                    } else {
                        MathC.enabled = true;
                        sender.sendMessage(ChatColor.GREEN + "MathC has been enabled!");
                    }
                } else if (args[0].equalsIgnoreCase("disable")) {
                    if (!MathC.enabled) {
                        sender.sendMessage(ChatColor.GOLD + "MathC is already disabled!");
                    } else {
                        MathC.enabled = false;
                        sender.sendMessage(ChatColor.RED + "MathC has been disabled!");
                    }
                } else {
                    sendHelp(sender);
                }
            } else {
                sendHelp(sender);
            }
        }
        return true;
    }

    public void sendHelp (CommandSender sender){
        sender.sendMessage("");
        sender.sendMessage(ChatColor.BLUE + "Math" + ChatColor.DARK_BLUE + "C " + ChatColor.GOLD + "Help");
        sender.sendMessage(ChatColor.RED + "enable" + ChatColor.GOLD + " - Enables MathC");
        sender.sendMessage(ChatColor.RED + "disable" + ChatColor.GOLD + " - Disables MathC");
        sender.sendMessage(ChatColor.GRAY + "All other settings can be located in the config.yml");
        sender.sendMessage("");
    }
}
