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
import com.codenjoy.dojo.questoria.model.items.impl.Gold;
import com.codenjoy.dojo.questoria.model.items.impl.Wall;
import com.codenjoy.dojo.questoria.services.saver.dummy.*;
import com.codenjoy.dojo.services.field.PointField;
import com.codenjoy.dojo.utils.smart.SmartAssert;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import static com.codenjoy.dojo.services.PointImpl.pt;
import static com.codenjoy.dojo.utils.JsonUtils.prettyPrint;
import static com.codenjoy.dojo.utils.smart.SmartAssert.assertEquals;

public class SaverLoaderTest {

    @After
    public void after() {
        SmartAssert.checkResult();
    }

    private Runner getObjectTree(String map) {
        Runner result = new Runner(map);
        PlayerOld player1 = result.getPlayerByCode(result.register("player1"));
        PlayerOld player2 = result.getPlayerByCode(result.register("player2"));

        player1.getJoystick().moveDown();
        player2.getJoystick().moveLeft();

        result.tick();
        return result;
    }

    public static Object load(String expected) {
        return new Loader().load(expected);
    }

    public static String save(Object object) {
        return new Saver().save(object);
    }

    public static void assertSave(Object object, String expected) {
        String saved = save(object);
        assertEquals(prettyPrint(expected), prettyPrint(saved));
    }

    public static void assertSaveLoad(Object object, String expected) {
        if (expected == null) {
            String saved = save(object);
            String loaded = save(load(saved));
            assertEquals(prettyPrint(saved), prettyPrint(loaded));
        } else {
            String saved = save(object);
            assertEquals(prettyPrint(expected), prettyPrint(saved));

            String loaded = save(load(saved));
            assertEquals(prettyPrint(expected), prettyPrint(loaded));

            String loaded2 = save(load(expected));
            assertEquals(prettyPrint(expected), prettyPrint(loaded2));
        }
    }

    @Test
    public void testSimpleMap() {
        assertSaveLoad(getObjectTree(
                "#############\n" +
                "#I  @   @  ##\n" +
                "########## $#\n" +
                "## @   @   ##\n" +
                "#$ ##########\n" +
                "##   @   @ ##\n" +
                "########## $#\n" +
                "## @   @   ##\n" +
                "#$ ##########\n" +
                "##    @  @ ##\n" +
                "########## $#\n" +
                "## @   @   ##\n" +
                "#############\n"), null);
    }

    @Test
    @Ignore // TODO optimize performance
    public void testLargeMap() {
        assertSaveLoad(getObjectTree(null), null);
    }

    @Test
    public void arrayOfArrayOfChar() {
        assertSaveLoad(new ArrayOfArrayOfCharContainer(6),
                "{\n" +
                "  'main':'ArrayOfArrayOfCharContainer@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'array':'char[][]@1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'ArrayOfArrayOfCharContainer@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.dummy.ArrayOfArrayOfCharContainer'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'char[]@2',\n" +
                "        'char[]@3'\n" +
                "      ],\n" +
                "      'id':'char[][]@1',\n" +
                "      'type':'[[C'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'abc'\n" +
                "      ],\n" +
                "      'id':'char[]@2',\n" +
                "      'type':'[C'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'qwe'\n" +
                "      ],\n" +
                "      'id':'char[]@3',\n" +
                "      'type':'[C'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }


    @Test
    public void arrayOfArrayOfList() {
        assertSaveLoad(new ArrayOfArrayOfListContainer(6),
                "{\n" +
                "  'main':'ArrayOfArrayOfListContainer@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'array':'List[][]@1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'ArrayOfArrayOfListContainer@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.dummy.ArrayOfArrayOfListContainer'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'List[]@2',\n" +
                "        'List[]@3'\n" +
                "      ],\n" +
                "      'id':'List[][]@1',\n" +
                "      'type':'[[Ljava.util.List;'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'ArrayList@4',\n" +
                "        'LinkedList@5',\n" +
                "        'ArrayList@6'\n" +
                "      ],\n" +
                "      'id':'List[]@2',\n" +
                "      'type':'[Ljava.util.List;'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'LinkedList@7',\n" +
                "        'ArrayList@8',\n" +
                "        'LinkedList@9'\n" +
                "      ],\n" +
                "      'id':'List[]@3',\n" +
                "      'type':'[Ljava.util.List;'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'a1'\n" +
                "      ],\n" +
                "      'id':'ArrayList@4',\n" +
                "      'type':'java.util.ArrayList'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'b1',\n" +
                "        'b2'\n" +
                "      ],\n" +
                "      'id':'LinkedList@5',\n" +
                "      'type':'java.util.LinkedList'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'c1',\n" +
                "        'c2',\n" +
                "        'c3'\n" +
                "      ],\n" +
                "      'id':'ArrayList@6',\n" +
                "      'type':'java.util.ArrayList'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'q1'\n" +
                "      ],\n" +
                "      'id':'LinkedList@7',\n" +
                "      'type':'java.util.LinkedList'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'w1',\n" +
                "        'w2'\n" +
                "      ],\n" +
                "      'id':'ArrayList@8',\n" +
                "      'type':'java.util.ArrayList'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'e1',\n" +
                "        'e2',\n" +
                "        'e3'\n" +
                "      ],\n" +
                "      'id':'LinkedList@9',\n" +
                "      'type':'java.util.LinkedList'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    @Test
    public void oneFieldInSuperClass_casse2() {
        assertSaveLoad(new ChildForIntContainer(1, 2),
                "{\n" +
                "  'main':'ChildForIntContainer@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'a':'1'\n" +
                "        },\n" +
                "        {\n" +
                "          'b':'2'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'ChildForIntContainer@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.dummy.ChildForIntContainer'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    @Test
    public void classWithInnerClass() {
        ClassWithInnerClass object = new ClassWithInnerClass(null);
        ClassWithInnerClass.Inner inner = object.new Inner(2);
        object.a = inner;

        assertSaveLoad(object,
                "{\n" +
                "  'main':'ClassWithInnerClass@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'a':'Inner@1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'ClassWithInnerClass@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.dummy.ClassWithInnerClass'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'this$0':'ClassWithInnerClass@0'\n" +
                "        },\n" +
                "        {\n" +
                "          'b':'2'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'Inner@1',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.dummy.ClassWithInnerClass$Inner'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    @Test
    public void classWithStaticInnerClass() {
        ClassWithStaticInnerClass object = new ClassWithStaticInnerClass(new ClassWithStaticInnerClass.Inner(4));

        assertSaveLoad(object,
                "{\n" +
                "  'main':'ClassWithStaticInnerClass@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'a':'Inner@1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'ClassWithStaticInnerClass@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.dummy.ClassWithStaticInnerClass'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'b':'4'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'Inner@1',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.dummy.ClassWithStaticInnerClass$Inner'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    @Test
    public void mapWithKeyCharacter() {
        Map<Character, Class<?>> map = new HashMap<>();
        map.put('2', Object.class);
        map.put('6', String.class);

        assertSaveLoad(map,
                "{\n" +
                "  'main':'HashMap@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          '2':'Class@1'\n" +
                "        },\n" +
                "        {\n" +
                "          '6':'Class@2'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'HashMap@0',\n" +
                "      'type':'java.util.HashMap'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'java.lang.Object'\n" +
                "      ],\n" +
                "      'id':'Class@1',\n" +
                "      'type':'java.lang.Class'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'java.lang.String'\n" +
                "      ],\n" +
                "      'id':'Class@2',\n" +
                "      'type':'java.lang.Class'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
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
            assertSaveLoad(object,
                "{\n" +
                "  'main':'ClassWithAnnonymClass@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'r':'@1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'ClassWithAnnonymClass@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.dummy.ClassWithAnnonymClass'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'this$0':'SaverLoaderTest@2'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'@1',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$1'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[],\n" +
                "      'id':'SaverLoaderTest@2',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
            assertEquals(true, false);
        } catch (UnsupportedOperationException e) {
            assertEquals("Попытка загрузить анонимный класс 'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$1'. Не разобрался еще с этим...", e.getMessage());
        }
    }

    @Test
    public void pointField() {
        PointField field = new PointField().size(3);
        field.add(new Wall(pt(0, 1)));
        field.add(new Gold(pt(1, 1)));
        field.add(new Wall(pt(2, 2)));

        assertSaveLoad(field,
                "{\n" +
                "  'main':'PointField@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'field':'MultimapMatrix@1'\n" +
                "        },\n" +
                "        {\n" +
                "          'all':'Multimap@2'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'PointField@0',\n" +
                "      'type':'com.codenjoy.dojo.services.field.PointField'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'field':'Multimap[][]@3'\n" +
                "        },\n" +
                "        {\n" +
                "          'size':'3'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'MultimapMatrix@1',\n" +
                "      'type':'com.codenjoy.dojo.services.field.MultimapMatrix'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'map':'LinkedHashMap@4'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'Multimap@2',\n" +
                "      'type':'com.codenjoy.dojo.services.field.Multimap'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'Multimap[]@5',\n" +
                "        'Multimap[]@6',\n" +
                "        'Multimap[]@7'\n" +
                "      ],\n" +
                "      'id':'Multimap[][]@3',\n" +
                "      'type':'[[Lcom.codenjoy.dojo.services.field.Multimap;'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'Class@8':'LinkedList@9'\n" +
                "        },\n" +
                "        {\n" +
                "          'Class@10':'LinkedList@11'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'LinkedHashMap@4',\n" +
                "      'type':'java.util.LinkedHashMap'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        '@NULL',\n" +
                "        'Multimap@12',\n" +
                "        '@NULL'\n" +
                "      ],\n" +
                "      'id':'Multimap[]@5',\n" +
                "      'type':'[Lcom.codenjoy.dojo.services.field.Multimap;'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        '@NULL',\n" +
                "        'Multimap@13',\n" +
                "        '@NULL'\n" +
                "      ],\n" +
                "      'id':'Multimap[]@6',\n" +
                "      'type':'[Lcom.codenjoy.dojo.services.field.Multimap;'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        '@NULL',\n" +
                "        '@NULL',\n" +
                "        'Multimap@14'\n" +
                "      ],\n" +
                "      'id':'Multimap[]@7',\n" +
                "      'type':'[Lcom.codenjoy.dojo.services.field.Multimap;'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'com.codenjoy.dojo.questoria.model.items.impl.Gold'\n" +
                "      ],\n" +
                "      'id':'Class@8',\n" +
                "      'type':'java.lang.Class'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'Gold@15'\n" +
                "      ],\n" +
                "      'id':'LinkedList@9',\n" +
                "      'type':'java.util.LinkedList'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'com.codenjoy.dojo.questoria.model.items.impl.Wall'\n" +
                "      ],\n" +
                "      'id':'Class@10',\n" +
                "      'type':'java.lang.Class'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'Wall@16',\n" +
                "        'Wall@17'\n" +
                "      ],\n" +
                "      'id':'LinkedList@11',\n" +
                "      'type':'java.util.LinkedList'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'map':'LinkedHashMap@18'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'Multimap@12',\n" +
                "      'type':'com.codenjoy.dojo.services.field.Multimap'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'map':'LinkedHashMap@19'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'Multimap@13',\n" +
                "      'type':'com.codenjoy.dojo.services.field.Multimap'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'map':'LinkedHashMap@20'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'Multimap@14',\n" +
                "      'type':'com.codenjoy.dojo.services.field.Multimap'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'amount':'0'\n" +
                "        },\n" +
                "        {\n" +
                "          'messenger':'@NULL'\n" +
                "        },\n" +
                "        {\n" +
                "          'onChange':'OnChange@21'\n" +
                "        },\n" +
                "        {\n" +
                "          'beforeChange':'OnBeforeChange@22'\n" +
                "        },\n" +
                "        {\n" +
                "          'x':'1'\n" +
                "        },\n" +
                "        {\n" +
                "          'y':'1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'Gold@15',\n" +
                "      'type':'com.codenjoy.dojo.questoria.model.items.impl.Gold'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'messenger':'@NULL'\n" +
                "        },\n" +
                "        {\n" +
                "          'onChange':'OnChange@23'\n" +
                "        },\n" +
                "        {\n" +
                "          'beforeChange':'OnBeforeChange@24'\n" +
                "        },\n" +
                "        {\n" +
                "          'x':'0'\n" +
                "        },\n" +
                "        {\n" +
                "          'y':'1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'Wall@16',\n" +
                "      'type':'com.codenjoy.dojo.questoria.model.items.impl.Wall'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'messenger':'@NULL'\n" +
                "        },\n" +
                "        {\n" +
                "          'onChange':'OnChange@25'\n" +
                "        },\n" +
                "        {\n" +
                "          'beforeChange':'OnBeforeChange@26'\n" +
                "        },\n" +
                "        {\n" +
                "          'x':'2'\n" +
                "        },\n" +
                "        {\n" +
                "          'y':'2'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'Wall@17',\n" +
                "      'type':'com.codenjoy.dojo.questoria.model.items.impl.Wall'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'Class@10':'LinkedList@27'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'LinkedHashMap@18',\n" +
                "      'type':'java.util.LinkedHashMap'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'Class@8':'LinkedList@28'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'LinkedHashMap@19',\n" +
                "      'type':'java.util.LinkedHashMap'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'Class@10':'LinkedList@29'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'LinkedHashMap@20',\n" +
                "      'type':'java.util.LinkedHashMap'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'field':'PointField@0'\n" +
                "        },\n" +
                "        {\n" +
                "          'key':'Gold@15'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'OnChange@21',\n" +
                "      'type':'com.codenjoy.dojo.services.field.PointField$OnChange'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'field':'PointField@0'\n" +
                "        },\n" +
                "        {\n" +
                "          'key':'Gold@15'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'OnBeforeChange@22',\n" +
                "      'type':'com.codenjoy.dojo.services.field.PointField$OnBeforeChange'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'field':'PointField@0'\n" +
                "        },\n" +
                "        {\n" +
                "          'key':'Wall@16'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'OnChange@23',\n" +
                "      'type':'com.codenjoy.dojo.services.field.PointField$OnChange'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'field':'PointField@0'\n" +
                "        },\n" +
                "        {\n" +
                "          'key':'Wall@16'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'OnBeforeChange@24',\n" +
                "      'type':'com.codenjoy.dojo.services.field.PointField$OnBeforeChange'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'field':'PointField@0'\n" +
                "        },\n" +
                "        {\n" +
                "          'key':'Wall@17'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'OnChange@25',\n" +
                "      'type':'com.codenjoy.dojo.services.field.PointField$OnChange'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'field':'PointField@0'\n" +
                "        },\n" +
                "        {\n" +
                "          'key':'Wall@17'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'OnBeforeChange@26',\n" +
                "      'type':'com.codenjoy.dojo.services.field.PointField$OnBeforeChange'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'Wall@16'\n" +
                "      ],\n" +
                "      'id':'LinkedList@27',\n" +
                "      'type':'java.util.LinkedList'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'Gold@15'\n" +
                "      ],\n" +
                "      'id':'LinkedList@28',\n" +
                "      'type':'java.util.LinkedList'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'Wall@17'\n" +
                "      ],\n" +
                "      'id':'LinkedList@29',\n" +
                "      'type':'java.util.LinkedList'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    @Test
    public void simpleDynamicInnerClass() {
        class D25 {
            int a, b;
        }

        class C25 {
            D25 d;
            int i;
        }

        class B25 {
            C25 a;
            C25 c;
        }

        class A25 {
            C25 c;
            B25 b;
            D25 d;
        }

        D25 d = new D25();
        d.a = 5;
        d.b = 6;

        C25 c = new C25();
        c.d = d;
        c.i = 4;

        B25 b = new B25();
        b.a = c;
        b.c = new C25();

        A25 a = new A25();
        a.b = b;
        a.c = c;
        a.d = d;

        // TODO реализовать загрузку в Load классе
        assertSave(a, "{\n" +
                "  'main':'A25@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'b':'B25@1'\n" +
                "        },\n" +
                "        {\n" +
                "          'c':'C25@2'\n" +
                "        },\n" +
                "        {\n" +
                "          'd':'D25@3'\n" +
                "        },\n" +
                "        {\n" +
                "          'this$0':'SaverLoaderTest@4'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'A25@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$1A25'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'a':'C25@2'\n" +
                "        },\n" +
                "        {\n" +
                "          'c':'C25@5'\n" +
                "        },\n" +
                "        {\n" +
                "          'this$0':'SaverLoaderTest@4'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'B25@1',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$1B25'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'd':'D25@3'\n" +
                "        },\n" +
                "        {\n" +
                "          'this$0':'SaverLoaderTest@4'\n" +
                "        },\n" +
                "        {\n" +
                "          'i':'4'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'C25@2',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$1C25'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'this$0':'SaverLoaderTest@4'\n" +
                "        },\n" +
                "        {\n" +
                "          'a':'5'\n" +
                "        },\n" +
                "        {\n" +
                "          'b':'6'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'D25@3',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$1D25'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[],\n" +
                "      'id':'SaverLoaderTest@4',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'd':'@NULL'\n" +
                "        },\n" +
                "        {\n" +
                "          'this$0':'SaverLoaderTest@4'\n" +
                "        },\n" +
                "        {\n" +
                "          'i':'0'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'C25@5',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$1C25'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    static class D1 {
        int a, b;
    }

    static class C1 {
        D1 d;
        int i;
    }

    static class B1 {
        C1 a;
        C1 c;
    }

    static class A1 {
        C1 c;
        B1 b;
        D1 d;
    }

    @Test
    public void simple() {
        D1 d = new D1();
        d.a = 5;
        d.b = 6;

        C1 c = new C1();
        c.d = d;
        c.i = 4;

        B1 b = new B1();
        b.a = c;
        b.c = new C1();

        A1 a = new A1();
        a.b = b;
        a.c = c;
        a.d = d;

        assertSaveLoad(a, "{\n" +
                "  'main':'A1@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'b':'B1@1'\n" +
                "        },\n" +
                "        {\n" +
                "          'c':'C1@2'\n" +
                "        },\n" +
                "        {\n" +
                "          'd':'D1@3'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'A1@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$A1'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'a':'C1@2'\n" +
                "        },\n" +
                "        {\n" +
                "          'c':'C1@4'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'B1@1',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B1'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'd':'D1@3'\n" +
                "        },\n" +
                "        {\n" +
                "          'i':'4'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'C1@2',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$C1'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'a':'5'\n" +
                "        },\n" +
                "        {\n" +
                "          'b':'6'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'D1@3',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$D1'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'd':'@NULL'\n" +
                "        },\n" +
                "        {\n" +
                "          'i':'0'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'C1@4',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$C1'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    static class B2 {
        int c;

        public B2(int c) {
            this.c = c;
        }
    }

    static class A2 {
        List<B2> bs;
    }

    @Test
    public void list() {
        A2 a = new A2();
        a.bs = new LinkedList<>(Arrays.asList(new B2(12), new B2(23), new B2(34)));

        assertSaveLoad(a, "{\n" +
                "  'main':'A2@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'bs':'LinkedList@1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'A2@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$A2'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'B2@2',\n" +
                "        'B2@3',\n" +
                "        'B2@4'\n" +
                "      ],\n" +
                "      'id':'LinkedList@1',\n" +
                "      'type':'java.util.LinkedList'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'c':'12'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'B2@2',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B2'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'c':'23'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'B2@3',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B2'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'c':'34'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'B2@4',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B2'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    static class B3 {
        Integer c;

        public B3(Integer c) {
            this.c = c;
        }
    }

    static class A3 {
        Map<Object, B3> bmap;
    }

    @Test
    public void map() {
        A3 a = new A3();
        a.bmap = new HashMap<>();
        a.bmap.put(1, new B3(11));
        a.bmap.put(new B3(22), new B3(23));
        a.bmap.put(3, new B3(null));
        a.bmap.put(4, null);

        assertSaveLoad(a, "{\n" +
                "  'main':'A3@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'bmap':'HashMap@1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'A3@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$A3'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          '1':'B3@2'\n" +
                "        },\n" +
                "        {\n" +
                "          '3':'B3@3'\n" +
                "        },\n" +
                "        {\n" +
                "          '4':'@NULL'\n" +
                "        },\n" +
                "        {\n" +
                "          'B3@4':'B3@5'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'HashMap@1',\n" +
                "      'type':'java.util.HashMap'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'c':'11'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'B3@2',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B3'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'c':'@NULL'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'B3@3',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B3'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'c':'22'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'B3@4',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B3'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'c':'23'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'B3@5',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B3'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    static class B4 {
        String c;

        public B4(String c) {
            this.c = c;
        }
    }

    static class A4 {
        B4[] bs;
    }

    @Test
    public void array() {
        A4 a = new A4();
        a.bs = new B4[]{new B4("11"), new B4("22"), new B4("33")};

        assertSaveLoad(a, "{\n" +
                "  'main':'A4@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'bs':'B4[]@1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'A4@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$A4'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'B4@2',\n" +
                "        'B4@3',\n" +
                "        'B4@4'\n" +
                "      ],\n" +
                "      'id':'B4[]@1',\n" +
                "      'type':'[Lcom.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B4;'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'c':'11'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'B4@2',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B4'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'c':'22'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'B4@3',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B4'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'c':'33'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'B4@4',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B4'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    static class B5 {
        String c;

        public B5(String c) {
            this.c = c;
        }
    }

    static class A5 {
        B5[][] bs;
    }

    @Test
    public void arrayOfArray() {
        A5 a = new A5();
        a.bs = new B5[][]{
                new B5[]{new B5("11"), new B5("22"), new B5("33")},
                new B5[]{new B5("44"), new B5("55")}
        };

        assertSaveLoad(a, "{\n" +
                "  'main':'A5@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'bs':'B5[][]@1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'A5@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$A5'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'B5[]@2',\n" +
                "        'B5[]@3'\n" +
                "      ],\n" +
                "      'id':'B5[][]@1',\n" +
                "      'type':'[[Lcom.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B5;'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'B5@4',\n" +
                "        'B5@5',\n" +
                "        'B5@6'\n" +
                "      ],\n" +
                "      'id':'B5[]@2',\n" +
                "      'type':'[Lcom.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B5;'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'B5@7',\n" +
                "        'B5@8'\n" +
                "      ],\n" +
                "      'id':'B5[]@3',\n" +
                "      'type':'[Lcom.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B5;'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'c':'11'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'B5@4',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B5'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'c':'22'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'B5@5',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B5'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'c':'33'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'B5@6',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B5'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'c':'44'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'B5@7',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B5'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'c':'55'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'B5@8',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B5'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    static class B6 {
        String c;

        public B6(String c) {
            this.c = c;
        }
    }

    static class A6 {
        B6[][][] bs;
    }

    @Test
    public void arrayOfArrayOfArray() {
        A6 a = new A6();
        a.bs = new B6[][][]{
                new B6[][]{
                        new B6[]{new B6("11"), new B6("22"), new B6("33")},
                        new B6[]{new B6("44"), new B6("55")}
                },
                new B6[][]{
                        new B6[]{new B6("66"), new B6("77")},
                        new B6[]{new B6("88")}
                }
        };

        // TODO реализовать загрузку в Load классе
        assertSave(a, "{\n" +
                "  'main':'A6@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'bs':'B6[][][]@1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'A6@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$A6'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'B6[][]@2',\n" +
                "        'B6[][]@3'\n" +
                "      ],\n" +
                "      'id':'B6[][][]@1',\n" +
                "      'type':'[[[Lcom.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B6;'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'B6[]@4',\n" +
                "        'B6[]@5'\n" +
                "      ],\n" +
                "      'id':'B6[][]@2',\n" +
                "      'type':'[[Lcom.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B6;'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'B6[]@6',\n" +
                "        'B6[]@7'\n" +
                "      ],\n" +
                "      'id':'B6[][]@3',\n" +
                "      'type':'[[Lcom.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B6;'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'B6@8',\n" +
                "        'B6@9',\n" +
                "        'B6@10'\n" +
                "      ],\n" +
                "      'id':'B6[]@4',\n" +
                "      'type':'[Lcom.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B6;'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'B6@11',\n" +
                "        'B6@12'\n" +
                "      ],\n" +
                "      'id':'B6[]@5',\n" +
                "      'type':'[Lcom.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B6;'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'B6@13',\n" +
                "        'B6@14'\n" +
                "      ],\n" +
                "      'id':'B6[]@6',\n" +
                "      'type':'[Lcom.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B6;'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'B6@15'\n" +
                "      ],\n" +
                "      'id':'B6[]@7',\n" +
                "      'type':'[Lcom.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B6;'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'c':'11'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'B6@8',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B6'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'c':'22'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'B6@9',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B6'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'c':'33'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'B6@10',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B6'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'c':'44'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'B6@11',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B6'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'c':'55'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'B6@12',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B6'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'c':'66'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'B6@13',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B6'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'c':'77'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'B6@14',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B6'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'c':'88'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'B6@15',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$B6'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    static class A7 {
        int[] bs;
    }


    @Test
    public void primitiveIntArray() {
        A7 a = new A7();
        a.bs = new int[]{10, 11, 12};

        assertSaveLoad(a, "{\n" +
                "  'main':'A7@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'bs':'int[]@1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'A7@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$A7'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        '10',\n" +
                "        '11',\n" +
                "        '12'\n" +
                "      ],\n" +
                "      'id':'int[]@1',\n" +
                "      'type':'[I'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    static class A8 {
        int[][] bs;
    }

    @Test
    public void primitiveIntArrayOfArray() {
        A8 a = new A8();
        a.bs = new int[][]{
                new int[]{10, 11, 12},
                new int[]{13, 14}
        };

        assertSaveLoad(a, "{\n" +
                "  'main':'A8@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'bs':'int[][]@1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'A8@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$A8'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'int[]@2',\n" +
                "        'int[]@3'\n" +
                "      ],\n" +
                "      'id':'int[][]@1',\n" +
                "      'type':'[[I'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        '10',\n" +
                "        '11',\n" +
                "        '12'\n" +
                "      ],\n" +
                "      'id':'int[]@2',\n" +
                "      'type':'[I'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        '13',\n" +
                "        '14'\n" +
                "      ],\n" +
                "      'id':'int[]@3',\n" +
                "      'type':'[I'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    static class A9 {
        char[] bs;
    }

    @Test
    public void primitiveCharArray() {
        A9 a = new A9();
        a.bs = new char[]{'A', 'B', 'C'};

        assertSaveLoad(a, "{\n" +
                "  'main':'A9@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'bs':'char[]@1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'A9@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$A9'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'ABC'\n" +
                "      ],\n" +
                "      'id':'char[]@1',\n" +
                "      'type':'[C'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    static class A10 {
        char[][] bs;
    }

    @Test
    public void primitiveCharArrayOfArray() {
        A10 a = new A10();
        a.bs = new char[][]{
                new char[]{'A', 'B', 'C'},
                new char[]{'D', 'E'}
        };

        assertSaveLoad(a, "{\n" +
                "  'main':'A10@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'bs':'char[][]@1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'A10@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$A10'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'char[]@2',\n" +
                "        'char[]@3'\n" +
                "      ],\n" +
                "      'id':'char[][]@1',\n" +
                "      'type':'[[C'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'ABC'\n" +
                "      ],\n" +
                "      'id':'char[]@2',\n" +
                "      'type':'[C'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'DE'\n" +
                "      ],\n" +
                "      'id':'char[]@3',\n" +
                "      'type':'[C'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    static class A11 {
        boolean[] bs;
    }

    @Test
    public void primitiveBooleanArray() {
        A11 a = new A11();
        a.bs = new boolean[]{true, false, true};

        assertSaveLoad(a, "{\n" +
                "  'main':'A11@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'bs':'boolean[]@1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'A11@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$A11'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'true',\n" +
                "        'false',\n" +
                "        'true'\n" +
                "      ],\n" +
                "      'id':'boolean[]@1',\n" +
                "      'type':'[Z'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    static class A12 {
        boolean[][] bs;
    }

    @Test
    public void primitiveBooleanArrayOfArray() {
        A12 a = new A12();
        a.bs = new boolean[][]{
                new boolean[]{true, false, true},
                new boolean[]{false, true}
        };

        assertSaveLoad(a, "{\n" +
                "  'main':'A12@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'bs':'boolean[][]@1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'A12@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$A12'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'boolean[]@2',\n" +
                "        'boolean[]@3'\n" +
                "      ],\n" +
                "      'id':'boolean[][]@1',\n" +
                "      'type':'[[Z'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'true',\n" +
                "        'false',\n" +
                "        'true'\n" +
                "      ],\n" +
                "      'id':'boolean[]@2',\n" +
                "      'type':'[Z'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'false',\n" +
                "        'true'\n" +
                "      ],\n" +
                "      'id':'boolean[]@3',\n" +
                "      'type':'[Z'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    static class A13 {
        long[] bs;
    }

    @Test
    public void primitiveLongArray() {
        A13 a = new A13();
        a.bs = new long[]{1L, 2L, 3l};

        assertSaveLoad(a, "{\n" +
                "  'main':'A13@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'bs':'long[]@1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'A13@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$A13'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        '1',\n" +
                "        '2',\n" +
                "        '3'\n" +
                "      ],\n" +
                "      'id':'long[]@1',\n" +
                "      'type':'[J'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    static class A14 {
        long[][] bs;
    }

    @Test
    public void primitiveLongArrayOfArray() {
        A14 a = new A14();
        a.bs = new long[][]{
                new long[]{1L, 2L, 3L},
                new long[]{4L, 5L}
        };

        assertSaveLoad(a, "{\n" +
                "  'main':'A14@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'bs':'long[][]@1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'A14@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$A14'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'long[]@2',\n" +
                "        'long[]@3'\n" +
                "      ],\n" +
                "      'id':'long[][]@1',\n" +
                "      'type':'[[J'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        '1',\n" +
                "        '2',\n" +
                "        '3'\n" +
                "      ],\n" +
                "      'id':'long[]@2',\n" +
                "      'type':'[J'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        '4',\n" +
                "        '5'\n" +
                "      ],\n" +
                "      'id':'long[]@3',\n" +
                "      'type':'[J'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    static class A15 {
        byte[] bs;
    }

    @Test
    public void primitiveByteArray() {
        A15 a = new A15();
        a.bs = new byte[]{1, 2, 3};

        assertSaveLoad(a, "{\n" +
                "  'main':'A15@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'bs':'byte[]@1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'A15@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$A15'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        '1',\n" +
                "        '2',\n" +
                "        '3'\n" +
                "      ],\n" +
                "      'id':'byte[]@1',\n" +
                "      'type':'[B'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    static class A16 {
        byte[][] bs;
    }

    @Test
    public void primitiveByteArrayOfArray() {
        A16 a = new A16();
        a.bs = new byte[][]{
                new byte[]{1, 2, 3},
                new byte[]{4 ,5}
        };

        assertSaveLoad(a, "{\n" +
                "  'main':'A16@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'bs':'byte[][]@1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'A16@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$A16'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'byte[]@2',\n" +
                "        'byte[]@3'\n" +
                "      ],\n" +
                "      'id':'byte[][]@1',\n" +
                "      'type':'[[B'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        '1',\n" +
                "        '2',\n" +
                "        '3'\n" +
                "      ],\n" +
                "      'id':'byte[]@2',\n" +
                "      'type':'[B'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        '4',\n" +
                "        '5'\n" +
                "      ],\n" +
                "      'id':'byte[]@3',\n" +
                "      'type':'[B'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    static class A17 {
        double[] bs;
    }

    @Test
    public void primitiveDoubleArray() {
        A17 a = new A17();
        a.bs = new double[]{1, 2, 3};

        assertSaveLoad(a, "{\n" +
                "  'main':'A17@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'bs':'double[]@1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'A17@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$A17'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        '1.0',\n" +
                "        '2.0',\n" +
                "        '3.0'\n" +
                "      ],\n" +
                "      'id':'double[]@1',\n" +
                "      'type':'[D'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    static class A18 {
        double[][] bs;
    }

    @Test
    public void primitiveDoubleArrayOfArray() {
        A18 a = new A18();
        a.bs = new double[][]{
                new double[]{1D, 2D, 3D},
                new double[]{4D, 5D}
        };

        assertSaveLoad(a, "{\n" +
                "  'main':'A18@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'bs':'double[][]@1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'A18@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$A18'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'double[]@2',\n" +
                "        'double[]@3'\n" +
                "      ],\n" +
                "      'id':'double[][]@1',\n" +
                "      'type':'[[D'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        '1.0',\n" +
                "        '2.0',\n" +
                "        '3.0'\n" +
                "      ],\n" +
                "      'id':'double[]@2',\n" +
                "      'type':'[D'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        '4.0',\n" +
                "        '5.0'\n" +
                "      ],\n" +
                "      'id':'double[]@3',\n" +
                "      'type':'[D'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    static class A19 {
        float[] bs;
    }

    @Test
    public void primitiveFloatArray() {
        A19 a = new A19();
        a.bs = new float[]{1, 2, 3};

        assertSaveLoad(a, "{\n" +
                "  'main':'A19@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'bs':'float[]@1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'A19@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$A19'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        '1.0',\n" +
                "        '2.0',\n" +
                "        '3.0'\n" +
                "      ],\n" +
                "      'id':'float[]@1',\n" +
                "      'type':'[F'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    static class A20 {
        float[][] bs;
    }

    @Test
    public void primitiveFloatArrayOfArray() {
        A20 a = new A20();
        a.bs = new float[][]{
                new float[]{1F, 2F, 3F},
                new float[]{4F, 5F}
        };

        assertSaveLoad(a, "{\n" +
                "  'main':'A20@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'bs':'float[][]@1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'A20@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$A20'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'float[]@2',\n" +
                "        'float[]@3'\n" +
                "      ],\n" +
                "      'id':'float[][]@1',\n" +
                "      'type':'[[F'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        '1.0',\n" +
                "        '2.0',\n" +
                "        '3.0'\n" +
                "      ],\n" +
                "      'id':'float[]@2',\n" +
                "      'type':'[F'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        '4.0',\n" +
                "        '5.0'\n" +
                "      ],\n" +
                "      'id':'float[]@3',\n" +
                "      'type':'[F'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    static class A21 {
        short[] bs;
    }

    @Test
    public void primitiveShortArray() {
        A21 a = new A21();
        a.bs = new short[]{1, 2, 3};

        assertSaveLoad(a, "{\n" +
                "  'main':'A21@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'bs':'short[]@1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'A21@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$A21'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        '1',\n" +
                "        '2',\n" +
                "        '3'\n" +
                "      ],\n" +
                "      'id':'short[]@1',\n" +
                "      'type':'[S'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    static class A22 {
        short[][] bs;
    }

    @Test
    public void primitiveShortArrayOfArray() {
        A22 a = new A22();
        a.bs = new short[][]{
                new short[]{1, 2, 3},
                new short[]{4, 5}
        };

        assertSaveLoad(a, "{\n" +
                "  'main':'A22@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'bs':'short[][]@1'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'A22@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$A22'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        'short[]@2',\n" +
                "        'short[]@3'\n" +
                "      ],\n" +
                "      'id':'short[][]@1',\n" +
                "      'type':'[[S'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        '1',\n" +
                "        '2',\n" +
                "        '3'\n" +
                "      ],\n" +
                "      'id':'short[]@2',\n" +
                "      'type':'[S'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        '4',\n" +
                "        '5'\n" +
                "      ],\n" +
                "      'id':'short[]@3',\n" +
                "      'type':'[S'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    static class A23 {
        final int b = 4;
    }

    @Test
    public void onlyOneFinalField() {
        A23 a = new A23();

        assertSaveLoad(a, "{\n" +
                "  'main':'A23@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[],\n" +
                "      'id':'A23@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$A23'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    static class B24 {
        int b;
    }

    static class A24 extends B24 {
        int c;
    }

    @Test
    public void oneFieldInSuperClass_case1() {
        A24 a = new A24();
        a.b = 1;
        a.c = 2;

        assertSaveLoad(a, "{\n" +
                "  'main':'A24@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'b':'1'\n" +
                "        },\n" +
                "        {\n" +
                "          'c':'2'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'A24@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$A24'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    static class A26 {
        int int1;
        Integer int2;
        byte byte1;
        Byte byte2;
        short short1;
        Short short2;
        long long1;
        Long long2;
        boolean boolean1;
        Boolean boolean2;
        double double1;
        Double double2;
        float float1;
        Float float2;
        char char1;
        Character char2;
    }

    @Test
    public void primitivesAndWrappers() {
        A26 a = new A26();
        a.int1 = 1;
        a.int2 = 2;
        a.byte1 = 3;
        a.byte2 = 4;
        a.short1 = 5;
        a.short2 = 6;
        a.long1 = 7L;
        a.long2 = 8L;
        a.boolean1 = true;
        a.boolean2 = false;
        a.double1 = 9D;
        a.double2 = 10D;
        a.float1 = 11F;
        a.float2 = 12F;
        a.char1 = 'a';
        a.char2 = 'b';

        assertSaveLoad(a, "{\n" +
                "  'main':'A26@0',\n" +
                "  'objects':[\n" +
                "    {\n" +
                "      'fields':[\n" +
                "        {\n" +
                "          'boolean1':'true'\n" +
                "        },\n" +
                "        {\n" +
                "          'byte1':'3'\n" +
                "        },\n" +
                "        {\n" +
                "          'char1':'a'\n" +
                "        },\n" +
                "        {\n" +
                "          'boolean2':'false'\n" +
                "        },\n" +
                "        {\n" +
                "          'byte2':'4'\n" +
                "        },\n" +
                "        {\n" +
                "          'char2':'b'\n" +
                "        },\n" +
                "        {\n" +
                "          'double2':'10.0'\n" +
                "        },\n" +
                "        {\n" +
                "          'float2':'12.0'\n" +
                "        },\n" +
                "        {\n" +
                "          'int2':'2'\n" +
                "        },\n" +
                "        {\n" +
                "          'long2':'8'\n" +
                "        },\n" +
                "        {\n" +
                "          'short2':'6'\n" +
                "        },\n" +
                "        {\n" +
                "          'double1':'9.0'\n" +
                "        },\n" +
                "        {\n" +
                "          'float1':'11.0'\n" +
                "        },\n" +
                "        {\n" +
                "          'int1':'1'\n" +
                "        },\n" +
                "        {\n" +
                "          'long1':'7'\n" +
                "        },\n" +
                "        {\n" +
                "          'short1':'5'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'A26@0',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.SaverLoaderTest$A26'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }
}