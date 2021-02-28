package net.kunmc.lab.dimensionswitchplugin;

import com.destroystokyo.paper.Title;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable {
    private long startTime;
    private int interval;
    private Dimension currentDimension;
    private int prevRemainningTime;

    public Timer(int interval) {
        startTime = System.currentTimeMillis();
        setInterval(interval);
        currentDimension = Dimension.OVERWORLD;
    }

    @Override
    public void run() {
        // 経過時間（秒）
        int elapsedTime = (int) ((System.currentTimeMillis() - startTime) / 1000L);
        // 残り時間（秒）
        int remainingTime = interval - elapsedTime;

        if (remainingTime <= 0) {
            startTime = System.currentTimeMillis();

            Bukkit.getOnlinePlayers().forEach(player -> {
                Location location = player.getLocation();
                if (location.getY() > 120) {
                    location.setY(120);
                }
                location.setWorld(Bukkit.getWorld(getNextDimension(currentDimension).getName()));
                player.teleport(location);
                player.teleport(location);
                player.playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
            });
            currentDimension = getNextDimension(currentDimension);

            return;
        }
        else if (remainingTime % 60 == 0 && remainingTime != prevRemainningTime) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.sendTitle("", "残り" + ChatColor.RED + remainingTime / 60 + ChatColor.WHITE + "分！！！",
                        10, 40, 10);
                player.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, 1.0f, 1.0f);
            });
        }

        if (remainingTime <= 5) {
            if (remainingTime == prevRemainningTime) {
                return;
            }
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.sendActionBar(" ");
                player.updateTitle(new Title("", "残り" + ChatColor.RED + remainingTime + ChatColor.WHITE + "秒！！！",
                        0, 20, 10));
                player.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, 1.0f, 1.0f);
            });
        } else {
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.sendActionBar("残り" + ChatColor.RED + remainingTime + ChatColor.WHITE + "秒で" +
                        ChatColor.GOLD + getNextDimension(currentDimension).getDisplayName() + ChatColor.WHITE + "に転送されます！！！");
            });
        }

        prevRemainningTime = remainingTime;
    }

    public void setInterval(int interval) {
        if (interval <= 0) {
            interval = 180;
        }

        this.interval = interval;
    }

    private Dimension getNextDimension(Dimension dimension) {
        switch (dimension) {
            case OVERWORLD:
                return Dimension.NETHER;
            case NETHER:
            case THE_END:
                return Dimension.OVERWORLD;
            default:
                throw new RuntimeException("[ERROR] DimensionSwitchPlugin: テレポート先のワールドが見つかりません！");
        }
    }

    private enum Dimension {
        OVERWORLD("オーバーワールド", "world"),
        NETHER("ネザー", "world_nether"),
        THE_END("ジ・エンド", "world_the_end");

        private final String displayName;
        private final String name;

        Dimension(String displayName, String name) {
            this.displayName = displayName;
            this.name = name;
        }

        String getDisplayName() {
            return displayName;
        }

        String getName() {
            return name;
        }

    }

}
