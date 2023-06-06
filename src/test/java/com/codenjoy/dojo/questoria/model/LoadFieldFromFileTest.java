package com.codenjoy.dojo.questoria.model;

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

import com.codenjoy.dojo.utils.smart.SmartAssert;
import org.junit.After;
import org.junit.Test;

import java.io.File;

import static com.codenjoy.dojo.utils.smart.SmartAssert.assertEquals;

public class LoadFieldFromFileTest {

    @After
    public void after() {
        SmartAssert.checkResult();
    }

    @Test
    public void shouldLoadTextToField() {
        FieldLoader loader = new FieldLoaderImpl().load(
                "###################################\n" +
                "###################################\n" +
                "#################      ############\n" +
                "######              @  ############\n" +
                "######                #############\n" +
                "####    @   #######################\n" +
                "#####        ######################\n" +
                "######                      #######\n" +
                "#######                     #######\n" +
                "#######      @              #######\n" +
                "#######                 ###########\n" +
                "###                  ##############\n" +
                "###########           #############\n" +
                "############                #######\n" +
                "##########                 ########\n" +
                "#################         #########\n" +
                "####################    @   #######\n" +
                "#####################        ######\n" +
                "###################        ########\n" +
                "##################         ########\n" +
                "###################       #########\n" +
                "#####################     #########\n" +
                "#################        ##########\n" +
                "###############      ##############\n" +
                "##########          ###############\n" +
                "#######   I       #################\n" +
                "####          #####################\n" +
                "########                  #########\n" +
                "#########                   #######\n" +
                "############             ##########\n" +
                "##############          ###########\n" +
                "#################      ############\n" +
                "###################################\n" +
                "###################################\n" +
                "###################################\n");

        assertEquals(35, loader.size());

        assertEquals("[10,9]", loader.initPosition().toString());

        verifyField(loader.field(),
                "###################################\n" +
                "###################################\n" +
                "#################      ############\n" +
                "######              @  ############\n" +
                "######                #############\n" +
                "####    @   #######################\n" +
                "#####        ######################\n" +
                "######                      #######\n" +
                "#######                     #######\n" +
                "#######      @              #######\n" +
                "#######                 ###########\n" +
                "###                  ##############\n" +
                "###########           #############\n" +
                "############                #######\n" +
                "##########                 ########\n" +
                "#################         #########\n" +
                "####################    @   #######\n" +
                "#####################        ######\n" +
                "###################        ########\n" +
                "##################         ########\n" +
                "###################       #########\n" +
                "#####################     #########\n" +
                "#################        ##########\n" +
                "###############      ##############\n" +
                "##########          ###############\n" +
                "#######           #################\n" +
                "####          #####################\n" +
                "########                  #########\n" +
                "#########                   #######\n" +
                "############             ##########\n" +
                "##############          ###########\n" +
                "#################      ############\n" +
                "###################################\n" +
                "###################################\n" +
                "###################################\n");
    }

    @Test
    public void shouldLoadFileToField() {
        FieldLoader loader = new FieldLoaderImpl()
                .load(new File("src/test/resources/test_field.txt"));

        assertEquals(13, loader.size());

        assertEquals("[1,11]", loader.initPosition().toString());

        verifyField(loader.field(),
                "#############\n" +
                "#   @   @  ##\n" +
                "##########  #\n" +
                "## @   @   ##\n" +
                "#  ##########\n" +
                "##   @   @ ##\n" +
                "##########  #\n" +
                "## @   @   ##\n" +
                "#  ##########\n" +
                "##    @  @ ##\n" +
                "##########  #\n" +
                "## @   @   ##\n" +
                "#############\n");
    }

    @Test
    public void shouldLoadJarFileToField() {
        // given when content loaded from .jar file
        FieldLoader loader = new FieldLoaderImpl()
                .loadFromResources("/META-INF/maven/org.apache.commons/commons-lang3/pom.properties");

        // then  // .jar file content read and field is constructed based on the number of lines in the file
        assertEquals(3, loader.size());

        verifyField(loader.field(),
                "art\n" +
                         "gro\n" +
                         "ver\n");
    }

    private void verifyField(FieldOld field, String expected) {
        int size = field.size();

        final StringBuffer result = new StringBuffer();
        for (int y = size - 1; y >= 0; y--) {
            for (int x = 0; x < size; x++) {
                result.append(field.get(x, y));
            }
            result.append('\n');
        }

        assertEquals(expected, result.toString());
    }
}