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

import com.codenjoy.dojo.questoria.model.items.monster.test.TestResult;
import com.codenjoy.dojo.questoria.model.items.monster.test.TestSuite;

import java.util.Arrays;
import java.util.List;

public abstract class CodeRunnerMonster extends Monster implements MonsterTest {

    public final static String HELP = "Попробуй еще раз!";
    public final static String LEAVE = "Просто так ты не уйдешь!";
    public static final String ANSWER = "4E0A";

    protected MonsterTest test;
    protected TestSuite testSuite = new TestSuite();

    public CodeRunnerMonster(String question, String signature, int weight) {
        super(question, ANSWER, HELP, LEAVE, signature, weight);
        this.test = this;
    }

    @Override
    public void answer(String code) {
        if (code.equals(ANSWER)) { // TODO подумать и упростить этот костыль с 4E0A/OK и другими константами
            super.answer(code);
            return;
        }
        try {
            List<String> answers = Arrays.asList(code.split("\n"));
            Object[][] testData = getTestData();
            for (int i = 0; i < testData.length; i++) {
                Object[] data = testData[i];
                String answer = answers.get(i);
                TestResult testResult = test.test(data, objects -> answer);
                testSuite.add(testResult);
                if (testSuite.isEnough()) break;
            }
        } catch (Exception e) {
            testSuite.add(new TestResult(e));
        }
        processResults(testSuite.getResults());
        testSuite.clear();
    }

    protected abstract Object[][] getTestData();

    protected void processResults(String message) {
        if (message.equals("OK")) {
            super.answer("4E0A");
        } else {
            messenger.say(message);
            messenger.say(help);
        }
    }

    public TestSuite getTestSuite() {
        return testSuite;
    }
}
