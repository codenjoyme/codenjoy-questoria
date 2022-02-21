package com.codenjoy.dojo.questoria.services.saver;

import com.codenjoy.dojo.questoria.model.Player;
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
        Player player1 = result.getPlayerByCode(result.register("player1"));
        Player player2 = result.getPlayerByCode(result.register("player2"));

        player1.getGame().getJoystick().moveDown();
        player2.getGame().getJoystick().moveLeft();

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
