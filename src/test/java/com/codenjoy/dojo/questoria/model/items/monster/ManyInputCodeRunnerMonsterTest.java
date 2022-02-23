package com.codenjoy.dojo.questoria.model.items.monster;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.codenjoy.dojo.questoria.model.Messages;
import com.codenjoy.dojo.questoria.model.items.*;
import com.codenjoy.dojo.questoria.model.items.impl.Gold;
import com.codenjoy.dojo.questoria.model.items.monster.impl.LongDivisionMonster;
import org.junit.Test;

import static com.codenjoy.dojo.questoria.model.Messages.withoutSeparator;
import static com.codenjoy.dojo.questoria.model.items.monster.CodeRunnerMonster.ANSWER;
import static com.codenjoy.dojo.questoria.model.items.monster.impl.LongDivisionMonster.HELP;
import static com.codenjoy.dojo.questoria.model.items.monster.impl.LongDivisionMonster.QUESTION;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class ManyInputCodeRunnerMonsterTest {

    public static final String BAD_CODE_WARNINGS =
            "Для [1, 2] метод должен вернуть “0.5”, но ты вернул “0”\n" +
            "Для [1, 1] метод должен вернуть “1”, но ты вернул “0”\n" +
            "Для [5, 5] метод должен вернуть “1”, но ты вернул “0”\n" +
            "Для [55, 5] метод должен вернуть “11”, но ты вернул “0”\n" +
            "Для [55, 44] метод должен вернуть “1.25”, но ты вернул “0”\n" +
            "Для [0, 56] метод работает правильно - ты вернул “0”\n" +
            "Для [56, 1] метод должен вернуть “56”, но ты вернул “0”\n";

    private Messages messages;
    private CodeRunnerMonster monster;

    private boolean die = false;

    @Test
    public void shouldPrintErrorWhenNotPass_caseEmptyString() {
        buildMonster(QUESTION, HELP);
        assertMonsterAskMe("LongDivisionMonster: " + QUESTION);
        assertMonsterHelpMeWithMyAnswer(
                "",
                "LongDivisionMonster: Для [1, 2] метод должен вернуть “0.5”, но ты вернул “”\n" +
                "LongDivisionMonster: " + HELP);
    }

    @Test
    public void shouldPrintErrorWhenNotPass_caseBadValue() {
        buildMonster(QUESTION, HELP);
        assertMonsterAskMe("LongDivisionMonster: " + QUESTION);
        assertMonsterHelpMeWithMyAnswer(
                "0",
                "LongDivisionMonster: Для [1, 2] метод должен вернуть “0.5”, но ты вернул “0”\n" +
                "LongDivisionMonster: " + HELP);
    }

    @Test
    public void shouldPrintErrorWhenNotPass_caseBadValue_severalAnswers() {
        buildMonster(QUESTION, HELP);
        assertMonsterAskMe("LongDivisionMonster: " + QUESTION);
        assertMonsterHelpMeWithMyAnswer(
                "0\n1\n2\n3",
                "LongDivisionMonster: Для [1, 2] метод должен вернуть “0.5”, но ты вернул “0”\n" +
                "Для [1, 1] метод работает правильно - ты вернул “1”\n" +
                "Для [5, 5] метод должен вернуть “1”, но ты вернул “2”\n" +
                "Для [55, 5] метод должен вернуть “11”, но ты вернул “3”\n" +
                "LongDivisionMonster: " + HELP);
    }

    @Test
    public void shouldPrintErrorWhenNotPass_caseBadType() {
        buildMonster(QUESTION, HELP);
        assertMonsterAskMe("LongDivisionMonster: " + QUESTION);
        assertMonsterHelpMeWithMyAnswer(
                "string",
                "LongDivisionMonster: Для [1, 2] метод должен вернуть “0.5”, но ты вернул “string”\n" +
                "LongDivisionMonster: " + HELP);
    }

    @Test
    public void shouldPrintOkAndDieWhenOk_caseAllTestsPassed() {
        buildMonster(QUESTION, HELP);
        assertMonsterAskMe("LongDivisionMonster: " + QUESTION);
        assertMonsterHelpMeWithMyAnswer(
                "0.5\n" +
                "1\n" +
                "1\n" +
                "11\n" +
                "1.25\n" +
                "0\n" +
                "56\n" +
                "-0.5\n" +
                "-0.5\n" +
                "0.5\n" +
                "0.001\n" +
                "1.2(4)\n" +
                "1.00(90)\n" +
                "10.0(90)\n" +
                "1010.0(90)\n" +
                "0.0(495)\n" +
                "-5.0(45)\n" +
                "0.000(3)\n" +
                "1.1(153846)\n" +
                "0.803(571428)\n" +
                "1.(593984962406015037)\n" +
                "96.6(1739130434782608695652)\n" +
                "0.3(5652173913043478260869)\n" +
                "0.8576942320118070532237143486041032667124906968586017840186012266888836921194851616559161340739231484\n" +
                "8.5769423201180705322371434860410326671249069685860178401860122668888369211948516165591613407392314847\n" +
                "85.7694309253951322673994120762759564567464112247141529983428059238030371899258141588263756955847311713\n" +
                "1.0(309278350515463917525773195876288659793814432989690721649484536082474226804123711340206185567010)\n" +
                "Div by zero error!",

                "LongDivisionMonster: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$");
        assertMonsterDie();
    }

    @Test
    public void shouldPrintOkAndDieWhenOk_caseMagicWord() {
        buildMonster(QUESTION, HELP);
        assertMonsterAskMe("LongDivisionMonster: " + QUESTION);
        assertMonsterHelpMeWithMyAnswer(ANSWER,
                "LongDivisionMonster: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$");
        assertMonsterDie();
    }

    private void assertMonsterDie() {
        assertTrue(die);
    }

    private void assertMonsterHelpMeWithMyAnswer(String myAnswer, String expectedHelp) {
        monster.answer(myAnswer);
        assertEquals(expectedHelp, withoutSeparator(messages.get()));
        messages.clear();
    }

    private void assertMonsterAskMe(String expected) {
        monster.ask();
        assertEquals(expected, messages.get());
        messages.clear();
    }

    private void buildMonster(String question, String help) {
        monster = new LongDivisionMonster();
        Messenger messenger = new MessengerImpl();
        monster.setWorld(new World() {

            @Override
            public Place place() {
                return mock(Place.class);
            }

            @Override
            public void move(int x, int y) {
            }

            @Override
            public Something make(char c, Object... params) {
                die = true;
                Gold gold = new Gold();
                gold.setMessenger(messenger);
                gold.setParameters((Integer)params[0]);
                return gold;
            }
        });
        messages = new Messages();
        monster.setMessenger(messenger);
        monster.getMessenger().add(messages);
    }
}
