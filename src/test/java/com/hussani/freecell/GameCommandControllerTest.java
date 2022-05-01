package com.hussani.freecell;

import com.hussani.freecell.game.FreeCellGame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GameCommandControllerTest {

    @Mock
    private FreeCellGame game;

    @Test
    void testCommandMoveFromBoardToFreeCell() {
        GameCommandController controller = new GameCommandController(game);
        controller.command("move game 1 freecell");

        verify(game).moveToFreeCell("GAME_1");
    }

    @Test
    void testCommandInGameStacks() {
        GameCommandController controller = new GameCommandController(game);
        controller.command("move game 1 6");

        verify(game).moveTopCard("GAME_1", "GAME_6");
    }

    @Test
    void testCommandMoveFromBoardToFoundation() {
        GameCommandController controller = new GameCommandController(game);
        controller.command("move game 1 foundation");

        verify(game).moveFromGameToFoundation("GAME_1");
    }

    @Test
    void testCommandMoveFromFreeCellToBoard() {
        GameCommandController controller = new GameCommandController(game);
        controller.command("move freecell 1 2");

        verify(game).moveFreeCellToGame(1, "GAME_2");
    }

    @Test
    void testCommandMoveFromFreeCellToFoundation() {
        GameCommandController controller = new GameCommandController(game);
        controller.command("move freecell 1 foundation");

        verify(game).moveFreeCellToFoundation(1);
    }
}