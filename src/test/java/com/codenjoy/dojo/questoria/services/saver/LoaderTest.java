package com.codenjoy.dojo.questoria.services.saver;

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

import com.codenjoy.dojo.questoria.model.PlayerOld;
import com.codenjoy.dojo.questoria.model.Runner;
import com.codenjoy.dojo.questoria.services.saver.dummy.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.codenjoy.dojo.questoria.services.saver.SaverTest.sort;
import static junit.framework.Assert.*;

public class LoaderTest {

    @Test
    public void test() {
        Object object = getObjectTree();

        String expected = toString(object);

        assertFalse(expected.contains("DummyMe"));

        Object newObject = load(expected);

        String actual = toString(newObject);
        assertEquals(ln(expected), ln(actual));

    }

    private Runner getObjectTree() {
        Runner result = new Runner();
        PlayerOld player1 = result.getPlayerByCode(result.register("player1"));
        PlayerOld player2 = result.getPlayerByCode(result.register("player2"));

        player1.getJoystick().moveDown();
        player2.getJoystick().moveLeft();

        result.tick();
        return result;
    }

    private Object load(String expected) {
        return new Loader().load(expected);
    }

    private String toString(Object object) {
        return new Saver()./*exclude(char[][].class, char[].class).excludeChildren(Monster.class).*/save(object);
    }

    private String ln(String test) {
        return test.replaceAll("\\{'id'", "\n{'id'");
    }

    @Test
    public void arrayOfArrayOfChar() {
        checkSaveAndLoad(new ArrayOfArrayOfCharContainer(6),
                "{'objects':[{'id':'ArrayOfArrayOfCharContainer@0','type':'com.codenjoy.dojo.questoria.services.saver.dummy.ArrayOfArrayOfCharContainer','fields':[{'array':'char[][]@1'}]},{'id':'char[][]@1','type':'[[C','fields':['char[]@2','char[]@3']},{'id':'char[]@2','type':'[C','fields':['abc']},{'id':'char[]@3','type':'[C','fields':['qwe']}],'main':'ArrayOfArrayOfCharContainer@0'}");
    }

    @Test
    public void oneFieldInSuperClass() {
        checkSaveAndLoad(new ChildForIntContainer(1, 2),
                "{'objects':[{'id':'ChildForIntContainer@0','type':'com.codenjoy.dojo.questoria.services.saver.dummy.ChildForIntContainer','fields':[{'a':'1'},{'b':'2'}]}],'main':'ChildForIntContainer@0'}");
    }

    @Test
    public void classWithInnerClass() {
        ClassWithInnerClass object = new ClassWithInnerClass(null);
        ClassWithInnerClass.Inner inner = object.new Inner(2);
        object.a = inner;

        checkSaveAndLoad(object,
                "{'objects':[{'id':'ClassWithInnerClass@0','type':'com.codenjoy.dojo.questoria.services.saver.dummy.ClassWithInnerClass','fields':[{'a':'Inner@1'}]},{'id':'Inner@1','type':'com.codenjoy.dojo.questoria.services.saver.dummy.ClassWithInnerClass$Inner','fields':[{'this$0':'ClassWithInnerClass@0'},{'b':'2'}]}],'main':'ClassWithInnerClass@0'}");
    }

    @Test
    public void classWithStaticInnerClass() {
        ClassWithStaticInnerClass object = new ClassWithStaticInnerClass(new ClassWithStaticInnerClass.Inner(4));

        checkSaveAndLoad(object,
                "{'objects':[{'id':'ClassWithStaticInnerClass@0','type':'com.codenjoy.dojo.questoria.services.saver.dummy.ClassWithStaticInnerClass','fields':[{'a':'Inner@1'}]},{'id':'Inner@1','type':'com.codenjoy.dojo.questoria.services.saver.dummy.ClassWithStaticInnerClass$Inner','fields':[{'b':'4'}]}],'main':'ClassWithStaticInnerClass@0'}");
    }

    @Test
    public void mapWithKeyCharacter() {
        Map<Character, Class<?>> map = new HashMap<>();
        map.put('2', Object.class);
        map.put('6', String.class);

        checkSaveAndLoad(map,
                "{'objects':[{'id':'HashMap@0','type':'java.util.HashMap','fields':[{'2':'Class@1'},{'6':'Class@2'}]},{'id':'Class@1','type':'java.lang.Class','fields':['java.lang.Object']},{'id':'Class@2','type':'java.lang.Class','fields':['java.lang.String']}],'main':'HashMap@0'}");
    }

    private void checkSaveAndLoad(Object object, String expected) {
        String saved = new Saver().save(object);
        assertEquals(sort(expected), sort(saved));

        String loaded = new Saver().save(new Loader().load(expected));
        assertEquals(sort(saved), sort(loaded));
    }

    @Test
    public void anonymClass() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // do something
            }
        };

        ClassWithAnnonymClass object = new ClassWithAnnonymClass(runnable);

        try {
            checkSaveAndLoad(object,
                "{'objects':[{'id':'ClassWithAnnonymClass@0','type':'com.codenjoy.dojo.questoria.services.saver.dummy.ClassWithAnnonymClass','fields':[{'r':'@1'}]},{'id':'@1','type':'com.codenjoy.dojo.questoria.services.saver.LoaderTest$1','fields':[{'this$0':'LoaderTest@2'}]},{'id':'LoaderTest@2','type':'com.codenjoy.dojo.questoria.services.saver.LoaderTest','fields':[]}],'main':'ClassWithAnnonymClass@0'}");
            fail();
        } catch (UnsupportedOperationException e) {
            assertEquals("Попытка загрузить аннонимный класс 'com.codenjoy.dojo.questoria.services.saver.LoaderTest$1'. Не разобрался еще с этим...", e.getMessage());
        }
    }
}
