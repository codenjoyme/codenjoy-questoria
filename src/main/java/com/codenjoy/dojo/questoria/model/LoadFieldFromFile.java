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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class LoadFieldFromFile implements FieldLoader {

    private Field field;
    private int posx;
    private int posy;

    public LoadFieldFromFile(String fileName) {
        List<String> lines = loadFromFile(fileName);

        int height = lines.get(0).length();
        int width = lines.size();

        field = new Field(height, width);

        for (int y = 0; y < width; y++) {
            String line = lines.get(y);
            int dy = width - 1 - y;
            for (int x = 0; x < line.length(); x++) {
                field.set(x, dy, line.charAt(x));

                if (field.get(x, dy) == 'I') {
                    posx = x;
                    posy = dy;
                    field.set(x, dy, ' ');
                }
            }
        }
    }

    private List<String> loadFromFile(String fileName) {
        URI uri = null;
        try {
            uri = getClass().getClassLoader().getResource(fileName).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        List<String> lines = new LinkedList<String>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(uri));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
            }
        } catch (IOException e) {
            scanner.close();
            throw new RuntimeException(e);
        }
        return lines;
    }

    @Override
    public int width() {
        return field.getWidth();
    }

    @Override
    public int height() {
        return field.getHeight();
    }

    @Override
    public Field field() {
        return field;
    }

    @Override
    public Point initPosition() {
        return new PointImpl(posx, posy);
    }
}
