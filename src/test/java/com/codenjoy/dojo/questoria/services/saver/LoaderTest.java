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

import java.util.HashMap;
import java.util.Map;

import static com.codenjoy.dojo.services.PointImpl.pt;
import static com.codenjoy.dojo.utils.JsonUtils.prettyPrint;
import static com.codenjoy.dojo.utils.smart.SmartAssert.assertEquals;

public class LoaderTest {

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
    public void oneFieldInSuperClass() {
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
                "          'this$0':'LoaderTest@2'\n" +
                "        }\n" +
                "      ],\n" +
                "      'id':'@1',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.LoaderTest$1'\n" +
                "    },\n" +
                "    {\n" +
                "      'fields':[],\n" +
                "      'id':'LoaderTest@2',\n" +
                "      'type':'com.codenjoy.dojo.questoria.services.saver.LoaderTest'\n" +
                "    }\n" +
                "  ]\n" +
                "}");
            assertEquals(true, false);
        } catch (UnsupportedOperationException e) {
            assertEquals("Попытка загрузить анонимный класс 'com.codenjoy.dojo.questoria.services.saver.LoaderTest$1'. Не разобрался еще с этим...", e.getMessage());
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
}