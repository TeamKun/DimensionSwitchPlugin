package net.kunmc.lab.dimensionswitchplugin.commands;

import net.kunmc.lab.dimensionswitchplugin.DimensionSwitchPlugin;
import net.kunmc.lab.dimensionswitchplugin.Timer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DimensionSwitchCommand implements CommandExecutor, TabCompleter {
    
    @Override
    public boolean onCommand(CommandSender sender,  Command command,  String label,  String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "コマンドの引数が足りましぇ～ん");
            sender.sendMessage(ChatColor.GREEN + "使用方法：/dimension <start|interval|end>");
            return true;
        }

        if (!sender.hasPermission("dimensionswitchplugin.dimension")) {
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "start":
                return onStart(sender, Arrays.copyOfRange(args, 1, args.length));
            case "interval":
                return onInterval(sender, Arrays.copyOfRange(args, 1, args.length));
            case "end":
                return onEnd(sender, Arrays.copyOfRange(args, 1, args.length));
            default:
                sender.sendMessage(ChatColor.RED + "引数が間違ってるよ～ん");
                sender.sendMessage(ChatColor.GREEN + "使用方法：/dimension <start|interval|end>");
                break;
        }

        return true;
    }

    boolean onStart(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "コマンドの引数が足りましぇ～ん");
            sender.sendMessage(ChatColor.GREEN + "使用方法：/dimension start <interval(数字)>");
            return true;
        }

        if (DimensionSwitchPlugin.timer != null) {
            sender.sendMessage(ChatColor.RED + "既にタイマーが始まってるよ～ん");
            sender.sendMessage(ChatColor.GOLD + "終わらせるには次のコマンドを入力してね～！");
            sender.sendMessage(ChatColor.GREEN + "使用方法：/dimension end");
            return true;
        }

        int interval;

        try {
            interval = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "引数が間違よ～ん");
            sender.sendMessage(ChatColor.GREEN + "使用方法：/dimension start <interval(数字)>");
            return true;
        }

        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendMessage(ChatColor.GREEN + "==================================================");
            player.sendMessage(ChatColor.RED + Integer.toString(interval) + ChatColor.WHITE + "秒後にネザーに移動しちゃうよ～ん");
            player.sendMessage(ChatColor.GREEN + "==================================================");
        });

        DimensionSwitchPlugin.timer = new Timer(interval);
        DimensionSwitchPlugin.timer.runTaskTimer(DimensionSwitchPlugin.instance, 0, 1);

        return true;
    }

    boolean onInterval(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "コマンドの引数が足りましぇ～ん");
            sender.sendMessage(ChatColor.GREEN + "使用方法：/dimension interval <interval(数字)>");
            return true;
        }

        if (DimensionSwitchPlugin.timer == null) {
            sender.sendMessage(ChatColor.RED + "まだタイマーが始まってないよ～ん");
            sender.sendMessage(ChatColor.GOLD + "始めるには次のコマンドを入力してね～！");
            sender.sendMessage(ChatColor.GREEN + "使用方法：/dimension start <interval(数字)>");
            return true;
        }

        int interval;

        try {
            interval = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "引数が間違よ～ん");
            sender.sendMessage(ChatColor.GREEN + "使用方法：/dimension interval <interval(数字)>");
            return true;
        }

        DimensionSwitchPlugin.timer.setInterval(interval);
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendMessage("ディメンジョン変更の間隔が" + ChatColor.RED + interval + ChatColor.WHITE + "秒に変更されたよ～ん");
        });

        return true;
    }

    boolean onEnd(CommandSender sender, String[] args) {
        if (DimensionSwitchPlugin.timer == null) {
            sender.sendMessage(ChatColor.RED + "まだタイマーが始まってないよ～ん");
            sender.sendMessage(ChatColor.GOLD + "始めるには次のコマンドを入力してね～！");
            sender.sendMessage(ChatColor.GREEN + "使用方法：/dimension start <interval(数字)>");
            return true;
        }

        DimensionSwitchPlugin.timer.cancel();
        DimensionSwitchPlugin.timer = null;

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender,  Command command,  String alias,  String[] args) {
        List<String> result;

        switch (args.length) {
            case 1:
                result = Arrays.asList("start", "interval", "end");
                break;
            case 2:
                switch (args[1]) {
                    case "start":
                    case "interval":
                        return Collections.singletonList("interval(数字)");
                }
                return Collections.emptyList();
            default:
                return Collections.emptyList();
        }

        String last = args[args.length - 1].toLowerCase();
        return result.stream()
                .filter(opt -> opt.startsWith(last))
                .collect(Collectors.toList());
    }

}
