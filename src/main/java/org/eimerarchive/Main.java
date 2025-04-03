package org.eimerarchive;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import java.awt.*;
import java.util.EnumSet;

public class Main extends ListenerAdapter {
    public static void main(String[] args) {
        EnumSet<GatewayIntent> intents = EnumSet.of(
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.DIRECT_MESSAGES,
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_MESSAGE_REACTIONS,
                GatewayIntent.DIRECT_MESSAGE_REACTIONS
        );

        try {
            JDA jda = JDABuilder.createLight(args[0], intents)
                    .addEventListeners(new Main())
                    .setActivity(Activity.watching("for new server jars"))
                    .build();

            jda.getRestPing().queue(ping ->
                    System.out.println("Logged in with ping: " + ping)
            );

            CommandListUpdateAction commands = jda.updateCommands();

            commands.addCommands(
                    Commands.slash("help", "Returns a list of commands")
                            .setContexts(InteractionContextType.GUILD),
                    Commands.slash("spreadsheet", "Returns a link to the server jar spreadsheet"),
                    Commands.slash("about", "About Eimer Archive")
            ).queue();

            jda.awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "help" -> {
                event.replyEmbeds(new EmbedBuilder()
                        .setColor(Color.ORANGE)
                        .setTitle("Commands")
                        .addField("/help", "Displayed this help command smh", false)
                        .addField("/spreadsheet", "Responds with a link to a spreadsheet of server file information. What builds correspond to what version and what we have found", false)
                        .addField("/about", "Displays a message going over who we are and what we do", false)
                        .build()).queue();
            }
            case "spreadsheet" -> {
                event.replyEmbeds(new EmbedBuilder()
                        .setColor(Color.ORANGE)
                        .setTitle("Spreadsheet")
                        .setDescription("This [spreadsheet](https://docs.google.com/spreadsheets/u/1/d/e/2PACX-1vQa3D6cn75jIR8T8hEXqrlphdkWI0WBweyqeVnf2wgo8tEvxwxn8ynD4F-oKKaaZQpvT2t1ej6f6ykX/pubhtml#) contains a list of server files (Bukkit, Spigot etc) and what build corresponds with what version. Including other information such as hash and our found status.")
                        .build()).queue();
            }
            case "about" -> {
                event.replyEmbeds(new EmbedBuilder()
                        .setColor(Color.ORANGE)
                        .setTitle("About Us")
                        .setDescription("Eimer Archive is an archival group primarily targeting server software files such as CraftBukkit and Spigot, but we sometimes collect plugins or other related things too!\nWe also have a website https://eimerarchive.org/")
                        .build()).queue();
            }
        }
    }
}