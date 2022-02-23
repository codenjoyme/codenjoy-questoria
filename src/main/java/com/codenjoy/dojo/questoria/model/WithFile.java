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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class WithFile {

    private final File file;

    public WithFile(String fileName) {
        this.file = openFile(fileName);
    }

    private File openFile(String fileName) {
        try {
            URL resource = getClass().getClassLoader().getResource(fileName);
            if (resource == null) {
                File file = new File(fileName);
                file.createNewFile();
                return file;
            }
            return new File(resource.toURI());
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(String data) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String load() {
        try (FileReader reader = new FileReader(file)) {
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            return new String(chars);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
