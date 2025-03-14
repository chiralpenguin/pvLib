package com.purityvanilla.pvlib.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGuard {

    public static boolean senderNotPlayer(CommandSender sender, Component errorMessage) {
        if (sender instanceof Player) {
            return false;
        }
        sender.sendMessage(errorMessage);
        return true;
    }

    public static boolean argsSizeInvalid(int minArgs, String[] args, CommandSender sender, Component errorMessage) {
        if (args.length < minArgs) {
            sender.sendMessage(errorMessage);
            return true;
        }
        return false;
    }
}
