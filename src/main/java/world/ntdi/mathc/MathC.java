package world.ntdi.mathc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class MathC extends JavaPlugin implements Listener {

    public static MathC instance;
    public static FileConfiguration configFile;

    public static boolean enabled;
    public static boolean acceptingSolutions;
    public static int answer;
    public static Long startTime;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("MathC has been enabled!");
        instance = this;

        getCommand("mathc").setExecutor(new Commands());
        getServer().getPluginManager().registerEvents(this, this);

        configFile = this.getConfig();
        initConfig();

        enabled = true;
        acceptingSolutions = false;

        int min = configFile.getInt("min");
        int max = configFile.getInt("max");

        new BukkitRunnable() {
            @Override
            public void run() {
                if (enabled) {
                    startTime = System.currentTimeMillis();
                    int sign = (int) (Math.random() * (3-1+1)+1);
                    int x = (int) (Math.random()*(max-min+1)+min);
                    int y = (int) (Math.random()*(max-min+1)+min);
                    if (sign == 2) {
                        answer = x - y;
                        Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.BLUE + "MathC" + ChatColor.GRAY + "] " + ChatColor.translateAlternateColorCodes('&', configFile.getString("question").replace("%x%", String.valueOf(x)).replace("%y%", String.valueOf(y)).replace("%sign%", "-")));
                    } else if (sign == 3) {
                        answer = x * y;
                        Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.BLUE + "MathC" + ChatColor.GRAY + "] " + ChatColor.translateAlternateColorCodes('&', configFile.getString("question").replace("%x%", String.valueOf(x)).replace("%y%", String.valueOf(y)).replace("%sign%", "*")));
                    } else {
                        answer = x + y;
                        Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.BLUE + "MathC" + ChatColor.GRAY + "] " + ChatColor.translateAlternateColorCodes('&', configFile.getString("question").replace("%x%", String.valueOf(x)).replace("%y%", String.valueOf(y)).replace("%sign%", "+")));
                    }
                    acceptingSolutions = true;
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (acceptingSolutions) {
                                acceptingSolutions = false;
                                Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.BLUE + "MathC" + ChatColor.GRAY + "] " + ChatColor.translateAlternateColorCodes('&', configFile.getString("time-limit-exceeded").replace("%answer%", String.valueOf(answer))));
                            }
                        }
                    }.runTaskLater(MathC.getInstance(), (long) configFile.getInt("time-limit")*20);
                }
            }
        }.runTaskTimer(this, (long) (configFile.getInt("interval"))*20, (long) (configFile.getInt("interval"))*20);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("MathC has been disabled!");

        this.saveConfig();
    }

    public void initConfig(){
        configFile.options().copyDefaults(true);
        this.saveConfig();
    }

    public void timeLimitTimer() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (acceptingSolutions) {
                    if (System.currentTimeMillis() - startTime > (long) configFile.getInt("time-limit")*60*1000) {
                        acceptingSolutions = false;
                        Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.BLUE + "MathC" + ChatColor.GRAY + "] " + ChatColor.translateAlternateColorCodes('&', configFile.getString("time-limit-exceeded").replace("%answer%", String.valueOf(answer))));
                    }
                }
            }
        }.runTaskLater(this, 20);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (acceptingSolutions) {
            int msg = Integer.parseInt(e.getMessage());
            if (msg == answer) {
                long time = (System.currentTimeMillis() - startTime)/1000;
                
                Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.BLUE + "MathC" + ChatColor.GRAY + "] " + ChatColor.translateAlternateColorCodes('&', configFile.getString("answer").replace("%player%", e.getPlayer().getName()).replace("%time%", String.valueOf(time)).replace("%answer%", String.valueOf(answer))));
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                e.setCancelled(true);
                acceptingSolutions = false;
            }
        }
    }

    public static MathC getInstance() {
        return instance;
    }
}
