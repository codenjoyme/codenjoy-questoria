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

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class LoadFieldFromFileTest {

    @Test
    public void shouldLoadFileToField() {
        FieldLoader loader = new FieldLoaderImpl().load(
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
                "###################################\n");

        assertEquals(33, loader.height());
        assertEquals(35, loader.width());
        assertEquals("[10,8]", loader.initPosition().toString());
        verifyField(loader.field(),
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
                "###################################\n");
    }

    @Test
    public void shouldLoadFileToField2() {
        FieldLoader loader = new FieldLoaderImpl().load(
                "##################################################\n" +
                "##################################################\n" +
                "##################################################\n" +
                "##I            @                 @             ###\n" +
                "##############################################@###\n" +
                "####         @               @                 ###\n" +
                "####@#############################################\n" +
                "####       @             @              @      ###\n" +
                "##############################################@###\n" +
                "########      @                @               ###\n" +
                "########@#########################################\n" +
                "##################################################\n");

        assertEquals(12, loader.height());
        assertEquals(50, loader.width());
        assertEquals("[2,8]", loader.initPosition().toString());
        verifyField(loader.field(),
                "##################################################\n" +
                "##################################################\n" +
                "##################################################\n" +
                "##             @                 @             ###\n" +
                "##############################################@###\n" +
                "####         @               @                 ###\n" +
                "####@#############################################\n" +
                "####       @             @              @      ###\n" +
                "##############################################@###\n" +
                "########      @                @               ###\n" +
                "########@#########################################\n" +
                "##################################################\n");
    }

    private void verifyField(FieldOld field, String expected) {
        int width = field.getWidth();
        int height = field.getHeight();

        final StringBuffer result = new StringBuffer();
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                result.append(field.get(x, y));
            }
            result.append('\n');
        }

        assertEquals(expected, result.toString());
    }
}
