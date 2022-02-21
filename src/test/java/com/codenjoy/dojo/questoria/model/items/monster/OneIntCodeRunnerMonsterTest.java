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
import com.codenjoy.dojo.questoria.model.items.monster.impl.FizzBuzzMonster;
import org.junit.Test;

import static com.codenjoy.dojo.questoria.model.Messages.withoutSeparator;
import static com.codenjoy.dojo.questoria.model.items.monster.CodeRunnerMonster.ANSWER;
import static com.codenjoy.dojo.questoria.model.items.monster.impl.FizzBuzzMonster.HELP;
import static com.codenjoy.dojo.questoria.model.items.monster.impl.FizzBuzzMonster.QUESTION;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class OneIntCodeRunnerMonsterTest {

    public static final String BAD_CODE_WARNINGS = "Для [1] метод работает правильно - ты вернул “1”\n" +
            "Для [2] метод работает правильно - ты вернул “2”\n" +
            "Для [3] метод работает правильно - ты вернул “Fizz”\n" +
            "Для [4] метод работает правильно - ты вернул “4”\n" +
            "Для [5] метод должен вернуть “Buzz”, но ты вернул “5”\n" +
            "Для [6] метод работает правильно - ты вернул “Fizz”\n" +
            "Для [7] метод работает правильно - ты вернул “7”\n" +
            "Для [8] метод работает правильно - ты вернул “8”\n" +
            "Для [9] метод работает правильно - ты вернул “Fizz”\n" +
            "Для [10] метод должен вернуть “Buzz”, но ты вернул “10”\n" +
            "Для [11] метод работает правильно - ты вернул “11”\n" +
            "Для [12] метод работает правильно - ты вернул “Fizz”\n" +
            "Для [13] метод работает правильно - ты вернул “13”\n" +
            "Для [14] метод работает правильно - ты вернул “14”\n" +
            "Для [15] метод должен вернуть “FizzBuzz”, но ты вернул “Fizz”\n" +
            "Для [16] метод работает правильно - ты вернул “16”\n" +
            "Для [17] метод работает правильно - ты вернул “17”\n" +
            "Для [18] метод работает правильно - ты вернул “Fizz”\n" +
            "Для [19] метод работает правильно - ты вернул “19”\n" +
            "Для [20] метод должен вернуть “Buzz”, но ты вернул “20”\n" +
            "Для [21] метод работает правильно - ты вернул “Fizz”\n" +
            "Для [22] метод работает правильно - ты вернул “22”\n" +
            "Для [23] метод работает правильно - ты вернул “23”\n" +
            "Для [24] метод работает правильно - ты вернул “Fizz”\n" +
            "Для [25] метод должен вернуть “Buzz”, но ты вернул “25”\n" +
            "Для [26] метод работает правильно - ты вернул “26”\n" +
            "Для [27] метод работает правильно - ты вернул “Fizz”\n" +
            "Для [28] метод работает правильно - ты вернул “28”\n" +
            "Для [29] метод работает правильно - ты вернул “29”\n" +
            "Для [30] метод должен вернуть “FizzBuzz”, но ты вернул “Fizz”\n";

    private Messages messages;
    private CodeRunnerMonster monster;


    private boolean die = false;

    @Test
    public void shouldPrintErrorWhenNotPass_caseEmptyString() {
        buildMonster(QUESTION, HELP);
        assertMonsterAskMe("FizzBuzzMonster: " + QUESTION);
        assertMonsterHelpMeWithMyAnswer(
                "",
                "FizzBuzzMonster: Для [1] метод должен вернуть “1”, но ты вернул “”\n" +
                "FizzBuzzMonster: " + HELP);
    }

    @Test
    public void shouldPrintErrorWhenNotPass_caseBadValue() {
        buildMonster(QUESTION, HELP);
        assertMonsterAskMe("FizzBuzzMonster: " + QUESTION);
        assertMonsterHelpMeWithMyAnswer(
                "0",
                "FizzBuzzMonster: Для [1] метод должен вернуть “1”, но ты вернул “0”\n" +
                "FizzBuzzMonster: " + HELP);
    }

    @Test
    public void shouldPrintErrorWhenNotPass_caseBadValue_severalAnswers() {
        buildMonster(QUESTION, HELP);
        assertMonsterAskMe("FizzBuzzMonster: " + QUESTION);
        assertMonsterHelpMeWithMyAnswer(
                "0\n1\n2\n3",
                "FizzBuzzMonster: Для [1] метод должен вернуть “1”, но ты вернул “0”\n" +
                "Для [2] метод должен вернуть “2”, но ты вернул “1”\n" +
                "Для [3] метод должен вернуть “Fizz”, но ты вернул “2”\n" +
                "Для [4] метод должен вернуть “4”, но ты вернул “3”\n" +
                "FizzBuzzMonster: " + HELP);
    }

    @Test
    public void shouldPrintErrorWhenNotPass_caseBadType() {
        buildMonster(QUESTION, HELP);
        assertMonsterAskMe("FizzBuzzMonster: " + QUESTION);
        assertMonsterHelpMeWithMyAnswer(
                "0.1.2.3.4",
                "FizzBuzzMonster: Для [1] метод должен вернуть “1”, но ты вернул “0.1.2.3.4”\n" +
                "FizzBuzzMonster: " + HELP);
    }

    @Test
    public void shouldPrintErrorWhenNotPass_caseSomeValuesAreBad() {
        buildMonster(QUESTION, HELP);
        assertMonsterAskMe("FizzBuzzMonster: " + QUESTION);
        assertMonsterHelpMeWithMyAnswer(
                "1\n" +
                "2\n" +
                "3\n" +
                "4\n" +
                "5\n" +
                "6\n" +
                "7\n" +
                "8\n" +
                "9\n" +
                "10\n" +
                "11\n" +
                "12\n" +
                "13\n" +
                "14\n" +
                "15\n" +
                "16\n" +
                "17\n" +
                "18\n" +
                "19\n" +
                "20\n" +
                "21\n" +
                "22\n" +
                "23\n" +
                "24\n" +
                "25\n" +
                "26",

                "FizzBuzzMonster: Для [1] метод работает правильно - ты вернул “1”\n" +
                "Для [2] метод работает правильно - ты вернул “2”\n" +
                "Для [3] метод должен вернуть “Fizz”, но ты вернул “3”\n" +
                "Для [4] метод работает правильно - ты вернул “4”\n" +
                "Для [5] метод должен вернуть “Buzz”, но ты вернул “5”\n" +
                "Для [6] метод должен вернуть “Fizz”, но ты вернул “6”\n" +
                "Для [7] метод работает правильно - ты вернул “7”\n" +
                "Для [8] метод работает правильно - ты вернул “8”\n" +
                "Для [9] метод должен вернуть “Fizz”, но ты вернул “9”\n" +
                "Для [10] метод должен вернуть “Buzz”, но ты вернул “10”\n" +
                "Для [11] метод работает правильно - ты вернул “11”\n" +
                "Для [12] метод должен вернуть “Fizz”, но ты вернул “12”\n" +
                "FizzBuzzMonster: " + HELP);
    }

    @Test
    public void shouldPrintOkAndDieWhenOk_caseAllTestsPassed() {
        buildMonster(QUESTION, HELP);
        assertMonsterAskMe("FizzBuzzMonster: " + QUESTION);
        assertMonsterHelpMeWithMyAnswer(
                "1\n" +
                "2\n" +
                "Fizz\n" +
                "4\n" +
                "Buzz\n" +
                "Fizz\n" +
                "7\n" +
                "8\n" +
                "Fizz\n" +
                "Buzz\n" +
                "11\n" +
                "Fizz\n" +
                "13\n" +
                "14\n" +
                "FizzBuzz\n" +
                "16\n" +
                "17\n" +
                "Fizz\n" +
                "19\n" +
                "Buzz\n" +
                "Fizz\n" +
                "22\n" +
                "23\n" +
                "Fizz\n" +
                "Buzz\n" +
                "26",

                "FizzBuzzMonster: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$");
        assertMonsterDie();
    }

    @Test
    public void shouldPrintOkAndDieWhenOk_caseMagicWord() {
        buildMonster(QUESTION, HELP);
        assertMonsterAskMe("FizzBuzzMonster: " + QUESTION);
        assertMonsterHelpMeWithMyAnswer(
                ANSWER,
                "FizzBuzzMonster: тЫ @#& Уб$%@&^ил ме:ня $!@!\n" +
                "Gold: Привет, я - 10$");
        assertMonsterDie();
    }

    private void assertMonsterDie() {
        assertTrue(die);
    }

    private void assertMonsterHelpMeWithMyAnswer(String myAnswer, String expectedHelp) {
        monster.answer(myAnswer);
        assertMessage(expectedHelp, messages);
        messages.clear();
    }

    private void assertMessage(String expected, Messages messages) {
        assertEquals(expected, withoutSeparator(messages.get()));
    }

    private void assertMonsterAskMe(String expected) {
        monster.ask();
        assertMessage(expected, messages);
        messages.clear();
    }

    private void buildMonster(String question, String help) {
        monster = new FizzBuzzMonster();
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
                gold.setWorld(this);
                gold.setParameters((Integer)params[0]);
                return gold;
            }
        });
        messages = new Messages();
        monster.setMessenger(messenger);
        monster.getMessenger().add(messages);
    }
}
