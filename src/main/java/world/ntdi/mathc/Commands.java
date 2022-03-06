package world.ntdi.mathc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import redempt.redlib.commandmanager.CommandHook;

public class Commands {
    @CommandHook("enable")
    public void enablecmd(CommandSender sender){
        if (MathC.enabled) {
            sender.sendMessage(ChatColor.GOLD + "MathC is already enabled!");
        } else {
            MathC.enabled = true;
            sender.sendMessage(ChatColor.GREEN + "MathC has been enabled!");
        }
    }
    @CommandHook("disable")
    public void disblaecmd(CommandSender sender){
        if (!MathC.enabled) {
            sender.sendMessage(ChatColor.GOLD + "MathC is already disabled!");
        } else {
            MathC.enabled = false;
            sender.sendMessage(ChatColor.RED + "MathC has been disabled!");
        }
    }
    @CommandHook("help")
    public void helpcmd(CommandSender sender){
        sender.sendMessage("");
        sender.sendMessage(ChatColor.BLUE + "Math" + ChatColor.DARK_BLUE + "C " + ChatColor.GOLD + "Help");
        sender.sendMessage(ChatColor.RED + "enable" + ChatColor.GOLD + " - Enables MathC");
        sender.sendMessage(ChatColor.RED + "disable" + ChatColor.GOLD + " - Disables MathC");
        sender.sendMessage(ChatColor.GRAY + "All other settings can be located in the config.yml");
        sender.sendMessage("");
    }
}
