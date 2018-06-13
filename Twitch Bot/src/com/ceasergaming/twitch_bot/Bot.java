package com.ceasergaming.twitch_bot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

public class Bot {

	static Socket socket;
	static BufferedWriter writer;
	static BufferedReader reader;

	public static void main(String[] args) throws Exception {
		String server = "irc.chat.twitch.tv";
		String nick = "Salad_Bot_";
		String channel = "sudokid";
		String trusted_users[] = { "Nex_Infinite", "SudoKid", "HackingTV", "lovemesenpai101" };
		socket = new Socket(server, 6667);
		writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writer.write("PASS " + Authentication.oauth + "\r\n");
		writer.write("NICK " + nick + "\r\n");
		writer.write("JOIN #" + channel + "\r\n");
		writer.flush();
		String line = null;
		while ((line = reader.readLine()) != null) {
			if (line.indexOf("004") >= 0) {
				break;
			} else if (line.indexOf("433") >= 0) {
				System.out.println("Nickname is already in use.");
				return;
			}
		}
		writer.write("JOIN #" + channel + "\r\n");
		writer.flush();
		sendMsg(channel, "Bot has been started!");
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
			if (line.toLowerCase().startsWith("PING ")) {
				writer.write("PONG " + line.substring(5) + "\r\n");
				writer.write("PRIVMSG " + channel + " :I got pinged!\r\n");
				writer.flush();
			} else {
				String[] lines = line.split(" ");
				if (!line.startsWith(":salad_bot_") && !line.startsWith(":-tmi.twitch.tv")
						&& !line.startsWith(":tmi.twitch.tv") && !line.startsWith("PING")) {
					if (lines.length >= 4) {
						String users[] = lines[0].split("!");
						String user = users[0].replace(":", "");
						String cmd = lines[3];
						String argss[];
						argss = lines;
						for (int i = 4; i < lines.length; i++) {
							int f = i - 4;
							argss[f] = lines[i];
						}
						Main.command(writer, cmd, user, argss, channel, trusted_users);
					} else {
						System.out.println("Else occoured");
					}
				}
			}
		}
	}

	public static void sendMsg(String channel, String message) {
		send("PRIVMSG #" + channel + " :" + message + "\r\n");
	}

	public static void send(String s) {
		try {
			writer.write(s);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String sendGET(String GET_URL) throws IOException { //TODO: WORK ON DAY 4
		URL obj = new URL(GET_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.addRequestProperty("Content-Type", "appliction/json");
		con.addRequestProperty("Client-ID", "a7t50xbc15f4z7c4mod2yzpdfv6zf7");
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response.toString();
		} else {
			System.out.println("GET request not worked");
			return null;
		}

	}
}
