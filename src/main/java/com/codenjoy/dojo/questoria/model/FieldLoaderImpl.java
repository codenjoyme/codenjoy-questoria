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

import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.utils.SmokeUtils;
import com.codenjoy.dojo.utils.TestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.codenjoy.dojo.questoria.client.Element.HERO;
import static com.codenjoy.dojo.questoria.client.Element.NOTHING;
import static com.codenjoy.dojo.services.PointImpl.pt;

public class FieldLoaderImpl implements FieldLoader {

    private FieldOld field;
    private int posx;
    private int posy;

    public FieldLoader load(File file) {
        return load(readFile(file));
    }

    public FieldLoader loadFromResources(String relativePath) {
        return load(readFileFromResources(relativePath));
    }

    public FieldLoader load(String data) {
        if (!data.contains("\n")) {
            // TODO убрать это после того как выравняю все методы загрузки
            data = TestUtils.injectN(data);
        }
        List<String> lines = Arrays.asList(data.split("\n"));

        int size = lines.size();

        field = new FieldOld(size);

        for (int y = 0; y < size; y++) {
            String line = lines.get(y);
            int dy = size - 1 - y;
            for (int x = 0; x < size; x++) {
                field.set(x, dy, line.charAt(x));

                if (field.get(x, dy) == HERO.ch()) {
                    posx = x;
                    posy = dy;
                    field.set(x, dy, NOTHING.ch());
                }
            }
        }

        return this;
    }

    public static String readFile(File file) {
        return SmokeUtils.load(file)
                .replace('.', NOTHING.ch());
    }

    public static String readFileFromResources(String fileName) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                FieldLoaderImpl.class.getResourceAsStream(fileName), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"))
                    .replace('.', NOTHING.ch());
        } catch (IOException e) {
            e.printStackTrace();
            return StringUtils.EMPTY;
        }
    }

    @Override
    public int size() {
        return field.size();
    }

    @Override
    public FieldOld field() {
        return field;
    }

    @Override
    public Point initPosition() {
        return pt(posx, posy);
    }
}
