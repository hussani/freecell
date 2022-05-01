package com.hussani.freecell;

import com.hussani.freecell.game.FreeCellGame;
import com.hussani.freecell.game.Stacks;

public class GameCommandController {

    private final FreeCellGame game;
    public GameCommandController(FreeCellGame game) {
        this.game = game;
    }

    public void command(final String textCommand) {
        String[] commands = textCommand.split(" ");
        if (commands.length == 4 && commands[0].equals("move")) {
            handleMoveCommand(commands);
        }
    }

    private void handleMoveCommand(String[] commands) {
        if (commands[1].equals("game")) {
            Stacks source = Stacks.valueOf("GAME_" + commands[2]);
            if (isInt(commands[3])) {
                Stacks destination = Stacks.valueOf("GAME_" + commands[3]);
                game.moveTopCard(source.name(), destination.name());
                return;
            }
            if (commands[3].equals("freecell")) {
                game.moveToFreeCell(source.name());
            }
            if (commands[3].equals("foundation")) {
                game.moveFromGameToFoundation(source.name());
            }
        }
        if (commands[1].equals("freecell")) {
            if (!isInt(commands[2])) {
                return;
            }
            if (commands[3].equals("foundation")) {
                game.moveFreeCellToFoundation(Integer.parseInt(commands[2]));
                return;
            }
            if (isInt(commands[3])) {
                Stacks destination = Stacks.valueOf("GAME_" + commands[3]);
                game.moveFreeCellToGame(Integer.parseInt(commands[2]), destination.name());
            }
        }
    }

    private boolean isInt(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
