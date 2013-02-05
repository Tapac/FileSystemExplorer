package com.example.fsexplorer.api;

import com.example.fsexplorer.fs.FileType;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * @author Andrey Tarashevsky
 *         Date: 05.02.13
 */
public class FileTypeTest {

    @Test
    public void testEqualsAndHashCodeContract() throws Exception {
        EqualsVerifier.forClass(FileType.class).verify();
    }
}
