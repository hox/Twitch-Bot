package com.ceasergaming.twitch_bot;

import java.io.BufferedWriter;
import java.io.IOException;

public class Main {

	public static void command(BufferedWriter writer, String cmd, String user, String args[], String channel,
		String trusted_users[]) {
		String trusted = "";
		for (int i = 0; i < trusted_users.length; i++) {
			if (i == trusted_users.length-1) {
				trusted += trusted_users[i];
			} else {
				trusted += trusted_users[i] + ", ";
			}
		}
		String command = cmd.substring(1);
		if (!command.startsWith("*"))
			return;
		command = command.substring(1);
		switch (command) {
		case "help":
		case "commands":
			Bot.sendMsg(channel, "Here is a list of the available commands:");
			Bot.sendMsg(channel, "*help - Send's this command prompt");
			Bot.sendMsg(channel, "*trusted - Send's a message with all trusted users in it");
			Bot.sendMsg(channel, "*botinfo - Send's a message containing the bot's info.");
			break;
		case "botinfo":
			Bot.sendMsg(channel,
					"This bot is made by the streamer @Ceaser_Gaming, It is also made with salad dressing.");
			break;
		case "trusted_users":
		case "trusted":
			Bot.sendMsg(channel, "The current trusted users are: " + trusted);
			break;
		case "crashcode":
			Bot.sendMsg(channel, "You man not crash me!");
			break;
		default:
			break;
		}
	}

	
	
}
